package com.example.suguoqing.expandablekey;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> mfragments;
    private Context mContext;

    public ViewPagerAdapter(FragmentManager fm,List<Fragment> fragments,Context context) {
        super(fm);
        mfragments = fragments;
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        return mfragments.get(position);
    }

    @Override
    public int getCount() {
        return mfragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if(position == 0){
           return mContext.getResources().getString(R.string.key);
        }else {
            return mContext.getResources().getString(R.string.shortcut);
        }
    }
}
