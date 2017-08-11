package com.demo.newsdemo.presenter;

import android.content.Context;

import com.demo.newsdemo.base.BasePresenter;
import com.demo.newsdemo.contract.SplashContract;
import com.demo.newsdemo.contract.callback.GetDataCallBack;
import com.demo.newsdemo.model.bean.SplashBean;
import com.demo.newsdemo.model.bean.zhihu.TheStoryBean;

import javax.inject.Inject;

/**
 * Created by hans on 2017/8/9 15:54.
 */

public class SplashPresenter extends BasePresenter<SplashContract.View> implements SplashContract.Presenter<SplashContract.View> {

    SplashContract.Model model;

    @Inject
    public SplashPresenter(SplashContract.View view, SplashContract.Model model) {
        attachView(view);
        this.model = model;
    }

    @Override
    public void loadData() {
        model.getData(mView, new GetDataCallBack<SplashBean>() {
            @Override
            public void getDataSuccess(SplashBean splashBean) {
                mView.updateUI(splashBean);
            }

            @Override
            public void getDataFailed() {
                mView.getDataFailed();
            }
        });

    }

}
