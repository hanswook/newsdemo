package com.demo.newsdemo.presenter;

import android.content.Context;

import com.demo.newsdemo.bean.ZhihuEntity;
import com.demo.newsdemo.callback.GetDataCallBack;
import com.demo.newsdemo.contract.HomeContract;
import com.demo.newsdemo.model.HomeModel;
import com.demo.newsdemo.utils.LogUtil;

import javax.inject.Inject;

/**
 * Created by hans on 2017/7/25 13:57.
 */

public class HomePresenter implements HomeContract.Presenter {

    private HomeContract.View mView;
    private HomeContract.Model model;

    @Inject
    public HomePresenter(HomeContract.View mView, HomeContract.Model model) {
        this.mView = mView;
        this.model = model;
    }

    @Override
    public void loadData(Context context) {
        LogUtil.e(context.getClass().getSimpleName(), "loadData");
        model.requestLastDailyData(context, new GetDataCallBack<ZhihuEntity>() {
            @Override
            public void getDataSuccess(ZhihuEntity zhihuEntity) {

                mView.updateList(zhihuEntity);
            }

            @Override
            public void getDataFailed() {
                mView.showGetdataFailed();
            }
        });
    }


}
