package com.demo.newsdemo.utils;


import android.content.Context;

import com.demo.newsdemo.basepack.BaseActivity;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


/**
 * Created by hans on 2017/3/14 14:41.
 */

public abstract class CommonSubscriber<T> implements Observer<T> {
    private Context context;

    public CommonSubscriber(Context context) {
        this.context = context;
    }

    private static final String TAG = "CommonSubscriber";


    @Override
    public void onSubscribe(Disposable d) {
        if (!NetWorkUtils.isConnectedByState(context)) {
            LogUtil.e(TAG, "网络不可用");
        } else {
//            LogUtil.e(TAG, "网络可用");
        }
        if (context instanceof BaseActivity){
            ((BaseActivity)context).addRxDestroy(d);
//            LogUtil.e("加入Rx池内");
        }
    }

    @Override
    public void onError(Throwable e) {
        LogUtil.e(TAG,"e:"+e.toString());
    }

    @Override
    public void onComplete() {
//        LogUtil.e(TAG, "成功了");
    }
}
