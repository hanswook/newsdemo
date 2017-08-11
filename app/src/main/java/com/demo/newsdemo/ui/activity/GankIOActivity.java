package com.demo.newsdemo.ui.activity;


import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.demo.newsdemo.R;
import com.demo.newsdemo.base.BaseActivity;
import com.demo.newsdemo.base.BaseCoreFragment;
import com.demo.newsdemo.ui.fragment.TypeFragment;
import com.demo.newsdemo.utils.ResourceUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GankIOActivity extends BaseActivity {

    @BindView(R.id.main_toolbar)
    Toolbar mainToolbar;

    private String mCurrentType;
    private boolean isBackPressed;
    private Map<String, BaseCoreFragment> mTypeFragments;


    @Override
    public int getLayoutId() {
        return R.layout.activity_gank_io;
    }

    @Override
    protected void initView() {
        super.initView();
//        initStatusBar();
        doReplace(ResourceUtil.res2String(context, R.string.gank));
    }

    private void doReplace(String type) {
        if (!type.equalsIgnoreCase(mCurrentType)) {
            replaceFragment(TypeFragment.newInstance(type), type, mCurrentType);
            mCurrentType = type;
        }
    }

    private void replaceFragment(BaseCoreFragment baseFragment, String tag, String lastTag) {

    }

    @Override
    protected void initData() {
        super.initData();
        mTypeFragments = new HashMap<>();

    }
}
