package com.hans.newslook.utils;


import com.hans.newslook.base.AppContext;
import com.hans.newslook.base.BaseActivity;
import com.hans.newslook.base.BaseContract;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


/**
 * Created by hans on 2017/3/14 14:41.
 */

public abstract class CommonSubscriber<T> implements Observer<T> {
    private BaseContract.BaseView view;

    public CommonSubscriber(BaseContract.BaseView view) {
        this.view = view;
    }

    private static final String TAG = "CommonSubscriber";

    @Override
    public void onSubscribe(Disposable d) {
        if (!NetWorkUtils.isConnectedByState(AppContext.getInstance())) {
            LogUtil.e(TAG, "网络不可用");
        }
        if (view instanceof BaseActivity) {
            ((BaseActivity) view).addRxDestroy(d);
//            LogUtil.e("加入Rx池内");
        }
    }

    @Override
    public void onError(Throwable e) {
        LogUtil.e(TAG, "e:" + e.toString());
    }

    @Override
    public void onComplete() {
//        LogUtil.e(TAG, "成功了");
    }
}
