package com.demo.newsdemo.contract;

import android.content.Context;

import com.demo.newsdemo.bean.ZhihuEntity;
import com.demo.newsdemo.callback.GetDataCallBack;

/**
 * Created by hans on 2017/7/25 13:31.
 */

public interface HomeContract{
    interface View{
        void updateList(ZhihuEntity zhihuEntity);
        void showGetdataFailed();
    }
    interface Presenter{
        void loadData(Context context);
    }
    interface Model{
        void requestLastDailyData(Context context,GetDataCallBack<ZhihuEntity> getDataCallBack);
    }

}
