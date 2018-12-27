package com.example.suguoqing.expandablekey;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class Pager_Key_Fragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "Pager_Key_Fragment";
    private EditText editText;
    private RecyclerView key_list;
    private Button comfirm;
    private String key;
    private ImageView ok;
    private IntentFilter intentFilter;
    private LocalBroadcastManager manager;
    private ChangeKeyCodeReceiver receiver;
    private List<String> list;

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.comfirm:
                //当点击确认的时候，弹出ｄｉａｌｏｇ
                int input = getEdittextContent();
                handleInput(input);
                break;

        }
    }


    /*处理输入的数字*/
    private void handleInput(final int input) {
        Log.d(TAG, "handleInput: input"+input);
        if(input == -1){
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle(getResources().getString(R.string.dialogTitle))
                    .setMessage(getResources().getString(R.string.cannot_be_null))
                    .create()
                    .show();
        }else if(input > 283){

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle(getResources().getString(R.string.dialogTitle))
                    .setMessage(getResources().getString(R.string.cannot_exceed_283))
                    .create()
                    .show();
        }else{
            //表示这里输入的键值才是可以的
            Log.d(TAG, "handleInput: ok");
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle(getResources().getString(R.string.dialogTitle))
                    .setMessage(getResources().getString(R.string.if_not_save_key)+input)
                    .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Log.d(TAG, "onClick: you chose yes");
                            //选择是，就发送一条广播
                            sendChangeKeyCodeBroadCast(input);
                            //发送广播之后将按钮设置为不可按，变成对勾
                            changeButtonTook();

                        }
                    })
                    .setNegativeButton(R.string.no,null)
                    .create()
                    .show();
        }
    }

    /* 把按钮设置成对勾，不能点击*/
    private void changeButtonTook() {
        comfirm.setVisibility(View.GONE);
        ok.setImageResource(R.mipmap.img_ok);

    }

    /*发送一条广播*/
    private void sendChangeKeyCodeBroadCast(int input) {
        Intent intent = new Intent("com.example.suguoqing.expandablekey.CHANGE_KEYCODE");
        intent.putExtra("input",input);
        intent.putExtra("key",key);
        manager.sendBroadcast(intent);
        //getContext().sendBroadcast(intent);
    }

    /* 获取edittext中的内容*/
    private int getEdittextContent() {
        String input = editText.getText().toString();
        if(input == null || "".equals(input)){
            Log.d(TAG, "input is null");
            return -1;
        }else if(input.startsWith("0")){
            if(input.length() != 1){
                Log.d(TAG, "can not start with 0");
                return -1;
            }

        }

        return Integer.parseInt(input);

       // return Integer.parseInt(editText.getText().toString());
    }





    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.pager_key,container,false);
       editText = view.findViewById(R.id.find);
       //imgButton = view.findViewById(R.id.search_icon);
       comfirm = view.findViewById(R.id.comfirm);
       ok = view.findViewById(R.id.ok);
       key_list = view.findViewById(R.id.key_list);

     //  imgButton.setOnClickListener(this);
       comfirm.setOnClickListener(this);
       //显示系统的键值
       disPlaySystemKeyCode();
       return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        //获取了点击的键
        key = bundle.getString("key");
        receiver = new ChangeKeyCodeReceiver();
        intentFilter = new IntentFilter();
        //注册广播
        manager = LocalBroadcastManager.getInstance(getContext());
        intentFilter.addAction("com.example.suguoqing.expandablekey.CHANGE_KEYCODE");
        manager.registerReceiver(receiver,intentFilter);

        //disPlaySystemKeyCode();
    }

    /*将系统的keycode显示在listview中*/
    private void disPlaySystemKeyCode() {
        list = new ArrayList<>();
        for(int i = 0;i<=283;i++){
            list.add(KeyEvent.keyCodeToString(i));
        }
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getActivity());

        KeyListAdapter adapter = new KeyListAdapter(list,getContext());
        //这里实现回调，获取Adapter中的name和keycode
        adapter.setmListener(new KeyListAdapter.CallBackListener() {
            @Override
            public void onItemClick(String name, final int keyCode) {
                name = name.substring(0,name.indexOf("\n"));
                Log.d(TAG, "onItemClick: suguoqing "+name+keyCode);
                //发送广播
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(getResources().getString(R.string.dialogTitle))
                        .setMessage(getResources().getString(R.string.if_not_save_key)+keyCode)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Log.d(TAG, "onClick: you chose yes");
                                //选择是，就发送一条广播
                                sendChangeKeyCodeBroadCast(keyCode);
                                //发送广播之后将按钮设置为不可按，变成对勾
                                changeButtonTook();

                            }
                        })
                        .setNegativeButton(R.string.no,null)
                        .create()
                        .show();
            }
        });

        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        key_list.setLayoutManager(layoutManager);
        key_list.setAdapter(adapter);
        key_list.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        manager.unregisterReceiver(receiver);
    }
}
