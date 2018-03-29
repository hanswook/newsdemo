package com.hans.newslook.ui.fragment;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.hans.newslook.R;
import com.hans.newslook.adapter.TypePageAdapter;
import com.hans.newslook.base.BaseCoreFragment;
import com.hans.newslook.base.BaseRxFragment;
import com.hans.newslook.utils.LogUtils;
import com.hans.newslook.utils.ResourceUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class TypeFragment extends BaseCoreFragment {

    private static final String TYPE = "type";

    @BindView(R.id.type_tablayout)
    TabLayout mTablayout;
    @BindView(R.id.type_viewpager)
    ViewPager mViewpager;

    private String mType;
    private List<BaseRxFragment> fragments;
    private List<String> mTitles;

    private TypePageAdapter mTypeAdapter;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_type;
    }

    @Override
    protected void init() {
        LogUtils.e(TAG, "init");
        mTypeAdapter = new TypePageAdapter(getChildFragmentManager());
        mTypeAdapter.setData(fragments, mTitles);
        mViewpager.setAdapter(mTypeAdapter);
        mViewpager.setOffscreenPageLimit(mTitles.size() - 1);
        mTablayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        mTablayout.setupWithViewPager(mViewpager);
        mTablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewpager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    protected void initData() {
        LogUtils.e(TAG, "initData");
        if (getArguments() == null)
            return;
        fragments = new ArrayList<>();
        mType = getArguments().getString(TYPE);
        if (ResourceUtil.res2String(getActivity(), R.string.gank).equalsIgnoreCase(mType)) {
            mTitles = ResourceUtil.stringArray2List(getActivity(), R.array.gank);
            LogUtils.e(TAG, "mTitles:" + mTitles.size() + ",:" + mTitles.get(0));
            for (String title : mTitles) {
                fragments.add(GankItemFragment.newInstance(title));
            }

        } else if (ResourceUtil.res2String(getActivity(), R.string.girl).equalsIgnoreCase(mType)) {
            mTitles = ResourceUtil.stringArray2List(getActivity(), R.array.girl);
            List<String> subTypes = ResourceUtil.stringArray2List(getActivity(), R.array.girl_cid);
            for (String subtype : subTypes) {
                fragments.add(GirlItemFragment.newInstance(subtype));
            }
        }
    }

    public static TypeFragment newInstance(String type) {
        LogUtils.e("TypeFragment", "newInstance type:" + type);
        TypeFragment fragment = new TypeFragment();
        Bundle bundle = new Bundle();
        bundle.putString(TYPE, type);
        fragment.setArguments(bundle);
        return fragment;
    }


}
