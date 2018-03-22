package com.hans.newslook.contract;

import com.hans.newslook.base.BaseContract;
import com.hans.newslook.contract.callback.GetDataCallBack;
import com.hans.newslook.model.bean.GirlItemData;

import java.util.List;

/**
 * Created by hans on 2017/8/11 10:55.
 */

public interface GirlItemContract {
    interface View extends BaseContract.BaseView {
        void updateUI(List<GirlItemData> data);
    }

    interface Presenter<T> extends BaseContract.BasePresenter<T> {
        void loadData(String cid, int pageCount);

    }

    interface Model {
        void requestNetForData(String cid, int pageCount, BaseContract.BaseView mView, GetDataCallBack<List<GirlItemData>> callBack);

    }
}
