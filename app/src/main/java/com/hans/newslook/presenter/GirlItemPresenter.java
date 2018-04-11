package com.hans.newslook.presenter;

import com.hans.newslook.base.BasePresenter;
import com.hans.newslook.contract.GirlItemContract;
import com.hans.newslook.contract.callback.GetDataCallBack;
import com.hans.newslook.model.bean.GirlItemData;
import com.hans.newslook.utils.baseutils.LogUtils;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by hans on 2017/8/11 10:54.
 */

public class GirlItemPresenter extends BasePresenter<GirlItemContract.View> implements GirlItemContract.Presenter<GirlItemContract.View> {


    private GirlItemContract.Model model;

    @Inject
    public GirlItemPresenter(GirlItemContract.View mView, GirlItemContract.Model model) {
        attachView(mView);
        this.model = model;
    }

    @Override
    public void loadData(String type, int pageCount) {
        LogUtils.e("GirlItemPresenter", "loadData:type:" + type + ",pageCount:" + pageCount);
        model.requestNetForData(type, pageCount, mView, new GetDataCallBack<List<GirlItemData>>() {
            @Override
            public void getDataSuccess(List<GirlItemData> girlItemData) {
                if (!isAttached()) {
                    return;
                }
                if (girlItemData != null && girlItemData.size() > 0)
                    mView.updateUI(girlItemData);
            }

            @Override
            public void getDataFailed() {
                if (!isAttached()) {
                    return;
                } mView.showError();
            }
        });
    }
}
