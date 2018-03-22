package com.hans.newslook.contract;

import com.hans.newslook.base.BaseActivity;
import com.hans.newslook.base.BaseContract;
import com.hans.newslook.model.bean.zhihu.StoriesBean;
import com.hans.newslook.contract.callback.GetDataCallBack;

import java.util.List;

/**
 * Created by hans on 2017/7/25 13:31.
 */

public interface HomeContract {

    interface View extends BaseContract.BaseView {
        void updateList(List<StoriesBean> stories);

        void showGetdataFailed();
    }

    interface Presenter<T> extends BaseContract.BasePresenter<T> {
        void loadData();

        void loadMoreData(String date,BaseActivity activityt);
    }

    interface Model {
        void requestLastDailyData(BaseContract.BaseView mView, GetDataCallBack<List<StoriesBean>> getDataCallBack);

        void requestMoreData(String date, BaseContract.BaseView mView,  GetDataCallBack<List<StoriesBean>> callBack);

    }

}
