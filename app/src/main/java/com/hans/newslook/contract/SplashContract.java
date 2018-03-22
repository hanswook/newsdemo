package com.hans.newslook.contract;

import com.hans.newslook.base.BaseContract;
import com.hans.newslook.contract.callback.GetDataCallBack;
import com.hans.newslook.model.bean.SplashBean;


/**
 * Created by hans on 2017/8/9 15:53.
 */

public interface SplashContract {

    interface View extends BaseContract.BaseView{
        void updateUI(SplashBean splashBean);
        void getDataFailed();

    }

    interface Presenter<T> extends BaseContract.BasePresenter<T>{
        void loadData();
    }

    interface Model {
        void getData(BaseContract.BaseView mView, GetDataCallBack<SplashBean> callBack);
    }
}
