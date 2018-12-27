package com.example.suguoqing.expandablekey;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

public class ChangeKeyCodeReceiver extends BroadcastReceiver {
    private static final String TAG = "ChangeKeyCodeReceiver";
    private Context mContext;
    private String key;
    private int input;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive: ");
        key = intent.getStringExtra("key");
        input = intent.getIntExtra("input",-1);
        mContext = context;

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.System.canWrite(context)) {
                Intent intent2 = new Intent(android.provider.Settings.ACTION_MANAGE_WRITE_SETTINGS);
                intent2.setData(Uri.parse("package:" + context.getPackageName()));
                intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent2);
            } else {
                if(key != null){
                    Log.d(TAG, "save key");
                    Settings.System.putString(context.getContentResolver(),key,key);
                }
                if(input != -1){
                    Log.d(TAG, "save input");
                    if("f1".equals(key)){
                        Log.d(TAG, "save input_f1");
                        Settings.System.putInt(context.getContentResolver(),"input_f1",input);
                        Settings.System.putString(context.getContentResolver(),"short_packagename_f1",null);
                    }else if("f2".equals(key)){
                        Settings.System.putInt(context.getContentResolver(),"input_f2",input);
                        Settings.System.putString(context.getContentResolver(),"short_packagename_f2",null);
                    }else if("left_scan".equals(key)){
                        Settings.System.putInt(context.getContentResolver(),"input_left_scan",input);
                        Settings.System.putString(context.getContentResolver(),"short_packagename_left_scan",null);
                    }else if("right_scan".equals(key)){
                        Settings.System.putString(context.getContentResolver(),"short_packagename_right_scan",null);
                        Settings.System.putInt(context.getContentResolver(),"input_right_scan",input);
                    }else if("home".equals(key)){
                        Settings.System.putString(context.getContentResolver(),"short_packagename_home",null);
                        Settings.System.putInt(context.getContentResolver(),"input_home",input);
                    }else if("back".equals(key)){
                        Settings.System.putString(context.getContentResolver(),"short_packagename_back",null);
                        Settings.System.putInt(context.getContentResolver(),"input_back",input);
                    }else{
                        Settings.System.putString(context.getContentResolver(),"short_packagename_menu",null);
                        Settings.System.putInt(context.getContentResolver(),"input_menu",input);
                    }
                }
            }
        }


    }





}
