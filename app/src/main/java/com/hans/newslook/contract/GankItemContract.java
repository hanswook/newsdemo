package com.hans.newslook.contract;

import com.hans.newslook.base.BaseContract;
import com.hans.newslook.contract.callback.GetDataCallBack;
import com.hans.newslook.model.bean.GankItemData;

import java.util.List;

/**
 * Created by hans on 2017/8/11 10:55.
 */

public interface GankItemContract {
    interface View extends BaseContract.BaseView {
        void updateUI(List<GankItemData> data);
    }

    interface Presenter<T> extends BaseContract.BasePresenter<T> {
        void loadData(String type);
        void refreshData(String type);

    }

    interface Model {
        void requestNetForData(String type, int pageCount,BaseContract.BaseView mView, GetDataCallBack<List<GankItemData>> callBack);

    }
}
