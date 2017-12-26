package com.demo.newsdemo.presenter;

import com.demo.newsdemo.base.BasePresenter;
import com.demo.newsdemo.contract.GankItemContract;
import com.demo.newsdemo.contract.GirlItemContract;
import com.demo.newsdemo.contract.callback.GetDataCallBack;
import com.demo.newsdemo.model.bean.GankItemData;
import com.demo.newsdemo.model.bean.GirlItemData;
import com.demo.newsdemo.utils.LogUtil;

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
        LogUtil.e("GankItemPresenter","loadData:type:"+type+",pageCount:"+pageCount);
        model.requestNetForData(type, pageCount, mView, new GetDataCallBack<List<GirlItemData>>() {
            @Override
            public void getDataSuccess(List<GirlItemData> girlItemData) {
                LogUtil.e("getDataSuccess:","gankItemData.size:"+girlItemData.size());
                if (girlItemData != null && girlItemData.size() > 0)
                    mView.updateUI(girlItemData);
            }

            @Override
            public void getDataFailed() {
                mView.showError();
            }
        });
    }
}
