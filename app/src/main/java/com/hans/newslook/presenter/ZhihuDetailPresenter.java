package com.hans.newslook.presenter;

import com.hans.newslook.base.BasePresenter;
import com.hans.newslook.model.bean.zhihu.TheStoryBean;
import com.hans.newslook.contract.callback.GetDataCallBack;
import com.hans.newslook.contract.ZhihuDetailContract;

import javax.inject.Inject;

/**
 * Created by hans on 2017/7/25 15:24.
 */

public class ZhihuDetailPresenter extends BasePresenter<ZhihuDetailContract.View> implements ZhihuDetailContract.Presenter<ZhihuDetailContract.View> {

    private ZhihuDetailContract.Model model;

    @Inject
    public ZhihuDetailPresenter(ZhihuDetailContract.View view, ZhihuDetailContract.Model model) {
        attachView(view);
        this.model = model;
    }

    @Override
    public void loadDetailData(String id) {
        model.getDetailData(mView, id, new GetDataCallBack<TheStoryBean>() {
            @Override
            public void getDataSuccess(TheStoryBean theStoryBean) {
                mView.updateUI(theStoryBean);
            }

            @Override
            public void getDataFailed() {
                mView.requestFailed();
            }
        });
    }


}
