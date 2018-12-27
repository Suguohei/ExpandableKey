# ExpandableKey
可以实现音量键，左右扫描键，菜单键，home键，返回键，可编程，也就是修改它的键值。或者将他们绑定应用，做捷径。
![Image text](https://github.com/Suguohei/ExpandableKey/blob/master/Images_README/2018-12-27%2015:49:32%E5%B1%8F%E5%B9%95%E6%88%AA%E5%9B%BE.png)


点击一下的列表就可以将按键和应用绑定，再次点击按键，就可以启动应用。
![Image text](https://github.com/Suguohei/ExpandableKey/blob/master/Images_README/2018-12-27%2015:49:49%E5%B1%8F%E5%B9%95%E6%88%AA%E5%9B%BE.png)

由于需要将键值内容保存到Settings.system中，所有如果将此apk内置系统做预制应用，就需要系统权限。
配置的Android.mk如下
LOCAL_PATH:= $(call my-dir)
include $(CLEAR_VARS)

LOCAL_MODULE_TAGS := optional

LOCAL_SRC_FILES := $(call all-java-files-under, src)

LOCAL_PACKAGE_NAME := ExpandableKey

LOCAL_CERTIFICATE := platform
# add apk into pri_app 
LOCAL_PRIVILEGED_MODULE := true

LOCAL_STATIC_JAVA_LIBRARIES := \
				android-support-v4 \
				android-support-v7-appcompat\
				android-support-design \
				android-support-v7-recyclerview \
				android-support-v7-preference 

LOCAL_RESOURCE_DIR := $(LOCAL_PATH)/res \
    frameworks/support/design/res \
    frameworks/support/v7/preference/res \
    frameworks/support/v14/preference/res \
    frameworks/support/v7/appcompat/res \
    frameworks/support/v7/recyclerview/res


LOCAL_AAPT_FLAGS := --auto-add-overlay
LOCAL_AAPT_FLAGS += --extra-packages android.support.design
LOCAL_AAPT_FLAGS += --extra-packages android.support.v7.appcompat
LOCAL_AAPT_FLAGS += --extra-packages android.support.v7.recyclerview


include frameworks/opt/setupwizard/library/common-full-support.mk
include frameworks/base/packages/SettingsLib/common.mk

include $(BUILD_PACKAGE)

# This finds and builds the test apk as well, so a single make does both.
include $(call all-makefiles-under,$(LOCAL_PATH))

#endif



要使修改的键值在系统中生效，则需要在系统/6757_N_NEW/frameworks/base/services/core/java/com/android/server/policy/PhoneWindowManager.java
中的interceptKeyBeforeQueueing（）这个方法中，将上报的键值拦截，报虚假键值。

// add by suguoqing for expandablekey on 20181221 start

	int input_f1 = Settings.System.getInt(mContext.getContentResolver(),"input_f1", -1);
	int input_f2 = Settings.System.getInt(mContext.getContentResolver(),"input_f2", -1);
	int input_left_scan = Settings.System.getInt(mContext.getContentResolver(),"input_left_scan", -1);
	int input_home = Settings.System.getInt(mContext.getContentResolver(),"input_home", -1);
	int input_menu = Settings.System.getInt(mContext.getContentResolver(),"input_menu", -1);
	int input_right_scan = Settings.System.getInt(mContext.getContentResolver(),"input_right_scan", -1);
	int input_back = Settings.System.getInt(mContext.getContentResolver(),"input_back", -1);

	String short_packagename_f1 = Settings.System.getString(mContext.getContentResolver(),"short_packagename_f1");
	String short_packagename_f2 = Settings.System.getString(mContext.getContentResolver(),"short_packagename_f2");
	String short_packagename_left_scan = Settings.System.getString(mContext.getContentResolver(),"short_packagename_left_scan");
	String short_packagename_home = Settings.System.getString(mContext.getContentResolver(),"short_packagename_home");
	String short_packagename_right_scan = Settings.System.getString(mContext.getContentResolver(),"short_packagename_right_scan");
	String short_packagename_menu = Settings.System.getString(mContext.getContentResolver(),"short_packagename_menu");
	String short_packagename_back = Settings.System.getString(mContext.getContentResolver(),"short_packagename_back");
	PackageManager packageManager = mContext.getPackageManager();   
	Intent shortcut_intent = new Intent();
	Log.d(TAG, "ddddd:"+keyCode);
	switch(keyCode){
		case KeyEvent.KEYCODE_VOLUME_UP:
			if(input_f1 != -1 && !isInjected){
				if(down) {
					Log.d(TAG, "ddddd:");
					keyRemappingSendFakeKeyEvent(KeyEvent.ACTION_DOWN,input_f1);
				}else{
					keyRemappingSendFakeKeyEvent(KeyEvent.ACTION_UP,input_f1);
				}
				return 0;
			}else if(short_packagename_f1 != null){
				shortcut_intent = packageManager.getLaunchIntentForPackage(short_packagename_f1);
				mContext.startActivity(shortcut_intent); 
				return 0;
			}
			break;
		case KeyEvent.KEYCODE_VOLUME_DOWN:
			if(input_f2 != -1 && !isInjected){
				if(down) {
					keyRemappingSendFakeKeyEvent(KeyEvent.ACTION_DOWN,input_f2);
				}else{
					keyRemappingSendFakeKeyEvent(KeyEvent.ACTION_UP,input_f2);
				}
				return 0;
			}else if(short_packagename_f2 != null){
				shortcut_intent = packageManager.getLaunchIntentForPackage(short_packagename_f2);
				mContext.startActivity(shortcut_intent); 
				return 0;
			}
			break;
		case KeyEvent.KEYCODE_F6:
			if(input_left_scan != -1 && !isInjected){
				if(down) {
					keyRemappingSendFakeKeyEvent(KeyEvent.ACTION_DOWN,input_left_scan);
				}else{
					keyRemappingSendFakeKeyEvent(KeyEvent.ACTION_UP,input_left_scan);
				}
				return 0;
			}else if(short_packagename_left_scan != null){
				shortcut_intent = packageManager.getLaunchIntentForPackage(short_packagename_left_scan);
				mContext.startActivity(shortcut_intent); 
				return 0;		
			}
			break;
		case KeyEvent.KEYCODE_F7:
			if(input_right_scan != -1 && !isInjected){
				if(down) {
					keyRemappingSendFakeKeyEvent(KeyEvent.ACTION_DOWN,input_right_scan);
				}else{
					keyRemappingSendFakeKeyEvent(KeyEvent.ACTION_UP,input_right_scan);
				}
				return 0;
			}else if(short_packagename_right_scan != null){
				shortcut_intent = packageManager.getLaunchIntentForPackage(short_packagename_right_scan);
				mContext.startActivity(shortcut_intent); 
				return 0;
			}
			break;
		case KeyEvent.KEYCODE_MENU:
			if(input_menu != -1 && !isInjected){
				if(down) {
					keyRemappingSendFakeKeyEvent(KeyEvent.ACTION_DOWN,input_menu);
				}else{
					keyRemappingSendFakeKeyEvent(KeyEvent.ACTION_UP,input_menu);
				}
				return 0;
			}else if(short_packagename_menu != null){
				shortcut_intent = packageManager.getLaunchIntentForPackage(short_packagename_menu);
				mContext.startActivity(shortcut_intent); 
				return 0;
			}
			break;
		case KeyEvent.KEYCODE_HOME:
			if(input_home != -1 && !isInjected){
				if(down) {
					keyRemappingSendFakeKeyEvent(KeyEvent.ACTION_DOWN,input_home);
				}else{
					keyRemappingSendFakeKeyEvent(KeyEvent.ACTION_UP,input_home);
				}
				return 0;
			}else if(short_packagename_home != null){
				shortcut_intent = packageManager.getLaunchIntentForPackage(short_packagename_home);
				mContext.startActivity(shortcut_intent); 
				return 0;
			}
			break;
		case KeyEvent.KEYCODE_DEL:
			if(input_back != -1 && !isInjected){
				if(down) {
					keyRemappingSendFakeKeyEvent(KeyEvent.ACTION_DOWN,input_back);
				}else{
					keyRemappingSendFakeKeyEvent(KeyEvent.ACTION_UP,input_back);
				}
				return 0;
			}else if(short_packagename_back != null){
				shortcut_intent = packageManager.getLaunchIntentForPackage(short_packagename_back);
				mContext.startActivity(shortcut_intent); 
				return 0;
			}
			break;

	}


// add by suguoqing for expandablekey on 20181221 end
