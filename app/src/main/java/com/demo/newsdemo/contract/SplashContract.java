package com.demo.newsdemo.contract;

import android.content.Context;

import com.demo.newsdemo.contract.callback.GetDataCallBack;
import com.demo.newsdemo.model.bean.SplashBean;


/**
 * Created by hans on 2017/8/9 15:53.
 */

public interface SplashContract {
    interface View {
        void updateUI(SplashBean splashBean);
        void getDataFailed();

    }

    interface Presenter {
        void loadData(Context context);
    }

    interface Model {
        void getData(Context context, GetDataCallBack<SplashBean> callBack);
    }
}
