package com.demo.newsdemo.contract;

import android.app.Activity;
import android.content.Context;

import com.demo.newsdemo.base.BaseActivity;
import com.demo.newsdemo.base.BaseContract;
import com.demo.newsdemo.contract.callback.GetDataCallBack;
import com.demo.newsdemo.model.bean.SplashBean;


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
