package com.example.suguoqing.expandablekey;


import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class BindKeyActivity extends AppCompatActivity {
    private static final String TAG = "BindKeyActivity";
    private String key;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private List<Fragment> mfragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bind_key);
        Intent intent = getIntent();
        key = intent.getStringExtra("keyValue");
        Log.d(TAG, "onCreate: "+key);
        init();
    }


    private void init() {
        tabLayout = findViewById(R.id.tab);
        viewPager = findViewById(R.id.viewPager);
        mfragments = new ArrayList<>();
        Bundle bundle = new Bundle();
        bundle.putString("key",key);
        Pager_Key_Fragment fragment = new Pager_Key_Fragment();
        Pager_ShortCut_Fragment fragment1 = new Pager_ShortCut_Fragment();
        //把ｋｅｙ值传入ｆｒａｇｍｅｎｔ中
        fragment.setArguments(bundle);
        fragment1.setArguments(bundle);
        mfragments.add(fragment);
        mfragments.add(fragment1);
        FragmentManager manager = getSupportFragmentManager();

        ViewPagerAdapter adapter = new ViewPagerAdapter(manager,mfragments,this);
        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);

    }
}
