package com.demo.newsdemo.presenter;

import android.content.Context;

import com.demo.newsdemo.bean.TheStoryBean;
import com.demo.newsdemo.callback.GetDataCallBack;
import com.demo.newsdemo.contract.ZhihuDetailContract;

import javax.inject.Inject;

/**
 * Created by hans on 2017/7/25 15:24.
 */

public class ZhihuDetailPresenter implements ZhihuDetailContract.Presenter{

    private ZhihuDetailContract.View view;
    private ZhihuDetailContract.Model model;

    @Inject
    public ZhihuDetailPresenter(ZhihuDetailContract.View view, ZhihuDetailContract.Model model) {
        this.view = view;
        this.model = model;
    }

    @Override
    public void loadData(Context context, String id) {
            model.requestDetailData(context, id, new GetDataCallBack<TheStoryBean>() {
                @Override
                public void getDataSuccess(TheStoryBean theStoryBean) {
                    view.updateUI(theStoryBean);
                }

                @Override
                public void getDataFailed() {
                    view.requestFailed();
                }
            });
    }
}
