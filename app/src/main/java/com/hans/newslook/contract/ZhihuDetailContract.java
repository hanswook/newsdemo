package com.hans.newslook.contract;

import com.hans.newslook.base.BaseContract;
import com.hans.newslook.model.bean.zhihu.TheStoryBean;
import com.hans.newslook.contract.callback.GetDataCallBack;

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
