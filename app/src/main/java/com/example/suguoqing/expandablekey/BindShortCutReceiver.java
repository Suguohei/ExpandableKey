package com.example.suguoqing.expandablekey;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;

public class BindShortCutReceiver extends BroadcastReceiver {
    private static final String TAG = "BindShortCutReceiver";
    private String key;
    private String packageName;
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive: ");
        key = intent.getStringExtra("key");
        packageName = intent.getStringExtra("packageName");

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.System.canWrite(context)) {
                Intent intent2 = new Intent(android.provider.Settings.ACTION_MANAGE_WRITE_SETTINGS);
                intent2.setData(Uri.parse("package:" + context.getPackageName()));
                intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent2);
            } else {
                if(key != null){
                    Log.d(TAG, "save short_key");
                    Settings.System.putString(context.getContentResolver(),"short_key",key);
                }
                if(packageName != null){
                    if("f1".equals(key)){
                        Settings.System.putString(context.getContentResolver(),"short_packagename_f1",packageName);
                        Settings.System.putInt(context.getContentResolver(),"input_f1",-1);
                    }else if("f2".equals(key)){
                        Settings.System.putString(context.getContentResolver(),"short_packagename_f2",packageName);
                        Settings.System.putInt(context.getContentResolver(),"input_f2",-1);
                    }else if("left_scan".equals(key)){
                        Settings.System.putString(context.getContentResolver(),"short_packagename_left_scan",packageName);
                        Settings.System.putInt(context.getContentResolver(),"input_left_scan",-1);
                    }else if("right_scan".equals(key)){
                        Settings.System.putString(context.getContentResolver(),"short_packagename_right_scan",packageName);
                        Settings.System.putInt(context.getContentResolver(),"input_right_scan",-1);
                    }else if("home".equals(key)){
                        Settings.System.putString(context.getContentResolver(),"short_packagename_home",packageName);
                        Settings.System.putInt(context.getContentResolver(),"input_home",-1);
                    }else if("back".equals(key)){
                        Settings.System.putString(context.getContentResolver(),"short_packagename_back",packageName);
                        Settings.System.putInt(context.getContentResolver(),"input_back",-1);
                    }else {
                        Settings.System.putString(context.getContentResolver(),"short_packagename_menu",packageName);
                        Settings.System.putInt(context.getContentResolver(),"input_menu",-1);
                    }

                }
            }
        }
    }
}
