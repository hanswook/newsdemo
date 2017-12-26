package com.demo.newsdemo.presenter;

import com.demo.newsdemo.base.BasePresenter;
import com.demo.newsdemo.contract.GankItemContract;
import com.demo.newsdemo.contract.HomeContract;
import com.demo.newsdemo.contract.callback.GetDataCallBack;
import com.demo.newsdemo.model.bean.GankItemData;
import com.demo.newsdemo.utils.LogUtil;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by hans on 2017/8/11 10:54.
 */

public class GankItemPresenter extends BasePresenter<GankItemContract.View> implements GankItemContract.Presenter<GankItemContract.View> {


    private GankItemContract.Model model;

    @Inject
    public GankItemPresenter(GankItemContract.View mView, GankItemContract.Model model) {
        attachView(mView);
        this.model = model;
    }

    @Override
    public void loadData(String type, int pageCount) {
        LogUtil.e("GankItemPresenter", "loadData:type:" + type + ",pageCount:" + pageCount);
        model.requestNetForData(type, pageCount, mView, new GetDataCallBack<List<GankItemData>>() {
            @Override
            public void getDataSuccess(List<GankItemData> gankItemData) {
                LogUtil.e("getDataSuccess:", "gankItemData.size:" + gankItemData.size());
                if (gankItemData != null && gankItemData.size() > 0)
                    mView.updateUI(gankItemData);
            }

            @Override
            public void getDataFailed() {
                mView.showError();
            }
        });
    }
}
