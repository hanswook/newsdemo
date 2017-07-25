package com.demo.newsdemo.ui.activity;


import android.util.Log;

import com.demo.newsdemo.R;
import com.demo.newsdemo.basepack.BaseActivity;
import com.demo.newsdemo.bean.ZhihuEntity;
import com.demo.newsdemo.contract.HomeContract;
import com.demo.newsdemo.di.DaggerHomeComponent;
import com.demo.newsdemo.di.HomeModule;
import com.demo.newsdemo.model.HomeModel;
import com.demo.newsdemo.presenter.HomePresenter;
import com.demo.newsdemo.utils.LogUtil;

import javax.inject.Inject;

public class HomeActivity extends BaseActivity implements HomeContract.View {

    @Inject
    HomePresenter homePresenter;


    @Override
    public int getLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    protected void initData() {
        super.initData();
        LogUtil.e(context.getClass().getSimpleName(),"initData");

        DaggerHomeComponent.builder()
                .homeModule(new HomeModule(this,new HomeModel()))
                .build()
                .inject(this);
        homePresenter.loadData(this);
    }

    @Override
    protected void initView() {
        super.initView();
    }


    @Override
    public void updateList(ZhihuEntity zhihuEntity) {
        LogUtil.e(TAG, "zhihu:" + zhihuEntity.getDate() + ",size:" + zhihuEntity.getStories().size());
    }

    @Override
    public void showGetdataFailed() {
        LogUtil.e(TAG, "zhihu:showGetdataFailed");

    }
}
