package com.demo.newsdemo.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.demo.newsdemo.base.BaseRxFragment;

import java.util.List;

/**
 * Created by hans on 2017/8/11 14:51.
 */

public class TypePageAdapter extends FragmentPagerAdapter{

    private List<BaseRxFragment> fragments;
    private List<String> mTitles;


    public TypePageAdapter(FragmentManager fm, List<BaseRxFragment> fragments, List<String> mTitles) {
        super(fm);
        this.fragments = fragments;
        this.mTitles = mTitles;
    }

    public TypePageAdapter(FragmentManager fm) {
        super(fm);
    }


    @Override
    public Fragment getItem(int position) {
        return null;
    }

    @Override
    public int getCount() {
        return 0;
    }
}
