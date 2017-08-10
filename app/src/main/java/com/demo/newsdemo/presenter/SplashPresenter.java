package com.demo.newsdemo.presenter;

import android.content.Context;

import com.demo.newsdemo.contract.SplashContract;
import com.demo.newsdemo.contract.callback.GetDataCallBack;
import com.demo.newsdemo.model.bean.SplashBean;
import com.demo.newsdemo.model.bean.zhihu.TheStoryBean;

import javax.inject.Inject;

/**
 * Created by hans on 2017/8/9 15:54.
 */

public class SplashPresenter implements SplashContract.Presenter{

    SplashContract.View view;
    SplashContract.Model model;

    @Inject
    public SplashPresenter(SplashContract.View view, SplashContract.Model model) {
        this.view = view;
        this.model = model;
    }

    @Override
    public void loadData(Context context) {
        model.getData(context, new GetDataCallBack<SplashBean>() {
            @Override
            public void getDataSuccess(SplashBean splashBean) {
                view.updateUI(splashBean);
            }

            @Override
            public void getDataFailed() {
                view.getDataFailed();
            }
        });

    }
}
