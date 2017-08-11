package com.demo.newsdemo.contract;

import android.content.Context;

import com.demo.newsdemo.base.BaseActivity;
import com.demo.newsdemo.base.BaseContract;
import com.demo.newsdemo.model.bean.zhihu.TheStoryBean;
import com.demo.newsdemo.contract.callback.GetDataCallBack;

/**
 * Created by hans on 2017/7/25 15:24.
 */

public interface ZhihuDetailContract {
    interface View extends BaseContract.BaseView{
        void updateUI(TheStoryBean data);
        void requestFailed();
    }

    interface Model {
       void getDetailData(BaseContract.BaseView mView, String id, GetDataCallBack<TheStoryBean> callBack);
    }

    interface Presenter<T> extends BaseContract.BasePresenter<T>{
        void loadDetailData( String id);
    }
}
