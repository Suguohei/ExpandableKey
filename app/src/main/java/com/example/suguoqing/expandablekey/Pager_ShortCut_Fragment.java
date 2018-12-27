package com.example.suguoqing.expandablekey;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Pager_ShortCut_Fragment extends Fragment {
    private static final String TAG = "Pager_ShortCut_Fragment";
    private RecyclerView packageInfo_list;
    private List<AppInfo> list = new ArrayList<>();
    private String key;
    private LocalBroadcastManager localBroadcastManager;
    private BindShortCutReceiver receiver;
    private IntentFilter intentFilter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pager_shortcut,container,false);
        packageInfo_list = view.findViewById(R.id.packageInfo_list);
        displayAllApps();
        return  view;
    }

    /*将系统的所有app显示出来，可以点击选择*/
    private void displayAllApps() {
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        final AppInfoAdapter appInfoAdapter = new AppInfoAdapter(list,getContext(),key);
        /* 这里是设置adapter的回调函数，实现点击每个item*/
        appInfoAdapter.setListener(new AppInfoAdapter.AppCallBackListener() {
            @Override
            public void appItemOnClick(final int postion, final List<AppInfo> list) {
               //
                Toast.makeText(getContext(), getContext().getString(R.string.shortcut_saved), Toast.LENGTH_SHORT).show();
                //将当前的key和对应绑定的包名保存
                Log.d(TAG, "appItemOnClick: "+list.get(postion).getPackageName());
                saveKeyAndPackageName(key, list.get(postion).getPackageName());
            }

        });
        packageInfo_list.setLayoutManager(manager);
        packageInfo_list.setAdapter(appInfoAdapter);
        packageInfo_list.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
    }


    /*将key和packagename保存到settings/system中*/
    private void saveKeyAndPackageName(String key, String packageName) {
        Intent intent = new Intent("com.example.suguoqing.expandablekey.BIND_SHORTCUT");
        intent.putExtra("key",key);
        intent.putExtra("packageName",packageName);
        localBroadcastManager.sendBroadcast(intent);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        key = bundle.getString("key");
        getAppList();
        Log.d(TAG, "onCreate: "+key);

        //注册广播
        localBroadcastManager = LocalBroadcastManager.getInstance(getContext());
        receiver = new BindShortCutReceiver();
        intentFilter = new IntentFilter();
        intentFilter.addAction("com.example.suguoqing.expandablekey.BIND_SHORTCUT");
        localBroadcastManager.registerReceiver(receiver,intentFilter);
    }

    /* 获取所有的桌面上的应用*/
    private void getAppList(){
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.LAUNCHER");

        PackageManager packageManager = getContext().getPackageManager();
        List<ResolveInfo> resolveInfos = new ArrayList<>();
        resolveInfos = packageManager.queryIntentActivities(intent,0);

        for(ResolveInfo info : resolveInfos){
            AppInfo appInfo = new AppInfo();
            appInfo.setIcon(info.loadIcon(packageManager));
            appInfo.setName((String) info.loadLabel(packageManager));
            appInfo.setPackageName(info.activityInfo.packageName);
            list.add(appInfo);
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        localBroadcastManager.unregisterReceiver(receiver);
    }
}
