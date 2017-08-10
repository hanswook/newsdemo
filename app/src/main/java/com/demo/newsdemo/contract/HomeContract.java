package com.demo.newsdemo.contract;

import android.content.Context;

import com.demo.newsdemo.base.BaseContract;
import com.demo.newsdemo.model.bean.zhihu.StoriesBean;
import com.demo.newsdemo.contract.callback.GetDataCallBack;

import java.util.List;

/**
 * Created by hans on 2017/7/25 13:31.
 */

public interface HomeContract {
    interface View extends BaseContract.BaseView{
        void updateList(List<StoriesBean> stories);
        void showGetdataFailed();
    }
    interface Presenter<T> extends BaseContract.BasePresenter<T>{
        void loadData(Context context);
        void loadMoreData(String date,Context context);
    }
    interface Model{
        void requestLastDailyData(Context context,GetDataCallBack<List<StoriesBean>> getDataCallBack);
        void requestMoreData(String date, Context context, final GetDataCallBack<List<StoriesBean>> callBack);

    }

}
