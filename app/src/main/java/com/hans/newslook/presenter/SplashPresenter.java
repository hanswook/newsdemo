package com.hans.newslook.presenter;

import com.hans.newslook.base.BasePresenter;
import com.hans.newslook.contract.SplashContract;
import com.hans.newslook.contract.callback.GetDataCallBack;
import com.hans.newslook.model.bean.SplashBean;

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
