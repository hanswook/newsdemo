package com.hans.newslook.presenter;

import com.hans.newslook.base.BaseActivity;
import com.hans.newslook.base.BasePresenter;
import com.hans.newslook.model.bean.zhihu.StoriesBean;
import com.hans.newslook.contract.callback.GetDataCallBack;
import com.hans.newslook.contract.HomeContract;
import com.hans.newslook.utils.LogUtil;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by hans on 2017/7/25 13:57.
 */

public class HomePresenter extends BasePresenter<HomeContract.View> implements HomeContract.Presenter<HomeContract.View> {

    private HomeContract.Model model;

    @Inject
    public HomePresenter(HomeContract.View mView, HomeContract.Model model) {
        attachView(mView);
        this.model = model;
    }

    @Override
    public void loadData() {
        LogUtil.e(mView.getClass().getSimpleName(), "loadDetailData");
        model.requestLastDailyData(mView, new GetDataCallBack<List<StoriesBean>>() {
            @Override
            public void getDataSuccess(List<StoriesBean> zhihuEntity) {
                if (zhihuEntity != null && zhihuEntity.size() > 0)
                    mView.updateList(zhihuEntity);
            }

            @Override
            public void getDataFailed() {
                mView.showGetdataFailed();
            }
        });
    }

    @Override
    public void loadMoreData(String date, BaseActivity activity) {
        model.requestMoreData(date, mView, new GetDataCallBack<List<StoriesBean>>() {
            @Override
            public void getDataSuccess(List<StoriesBean> zhihuEntity) {
                mView.updateList(zhihuEntity);
            }

            @Override
            public void getDataFailed() {
                mView.showError();
            }
        });
    }


}
