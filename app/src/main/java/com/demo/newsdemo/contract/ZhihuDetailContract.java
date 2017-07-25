package com.demo.newsdemo.contract;

import android.content.Context;

import com.demo.newsdemo.bean.TheStoryBean;
import com.demo.newsdemo.callback.GetDataCallBack;

/**
 * Created by hans on 2017/7/25 15:24.
 */

public interface ZhihuDetailContract {
    interface View {
        void updateUI(TheStoryBean data);
        void requestFailed();
    }

    interface Model {
       void requestDetailData(Context context,String id, GetDataCallBack<TheStoryBean> callBack);
    }

    interface Presenter {
        void loadData(Context context,String id);
    }
}
