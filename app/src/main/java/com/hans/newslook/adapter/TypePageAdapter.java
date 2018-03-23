package com.hans.newslook.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.hans.newslook.base.BaseRxFragment;

import java.util.List;

/**
 * Created by hans
 * date: 2017/8/11 17:36.
 * e-mail: hxxx1992@163.com
 */

public class TypePageAdapter extends FragmentPagerAdapter {

    private List<BaseRxFragment> fragments;
    private List<String> mTitles;


    public void setData(List<BaseRxFragment> fragments, List<String> mTitles) {
        this.fragments = fragments;
        this.mTitles = mTitles;
    }

    public TypePageAdapter(FragmentManager fm) {
        super(fm);
    }


    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
    }
}
