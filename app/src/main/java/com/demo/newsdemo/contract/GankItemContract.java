package com.demo.newsdemo.contract;

import com.demo.newsdemo.base.BaseActivity;
import com.demo.newsdemo.base.BaseContract;
import com.demo.newsdemo.contract.callback.GetDataCallBack;
import com.demo.newsdemo.model.bean.GankItemData;
import com.demo.newsdemo.model.bean.SplashBean;
import com.demo.newsdemo.model.bean.zhihu.StoriesBean;

import java.util.List;

/**
 * Created by hans on 2017/8/11 10:55.
 */

public interface GankItemContract {
    interface View extends BaseContract.BaseView {
        void updateUI(List<GankItemData> data);
    }

    interface Presenter<T> extends BaseContract.BasePresenter<T> {
        void loadData(String type, int pageCount);

    }

    interface Model {
        void requestNetForData(String type, int pageCount,BaseContract.BaseView mView, GetDataCallBack<List<GankItemData>> callBack);

    }
}
