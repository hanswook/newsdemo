package com.demo.newsdemo.model;

import android.content.Context;

import com.demo.newsdemo.base.BaseActivity;
import com.demo.newsdemo.base.BaseContract;
import com.demo.newsdemo.base.BaseModel;
import com.demo.newsdemo.contract.SplashContract;
import com.demo.newsdemo.contract.callback.GetDataCallBack;
import com.demo.newsdemo.model.bean.SplashBean;
import com.demo.newsdemo.model.bean.zhihu.ZhihuEntity;
import com.demo.newsdemo.utils.CommonSubscriber;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by hans on 2017/8/9 16:00.
 */

public class SplashModel extends BaseModel implements SplashContract.Model{
    @Override
    public void getData(final BaseContract.BaseView mView, final GetDataCallBack<SplashBean> callBack) {
        zhihuService.getImage().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CommonSubscriber<SplashBean>(mView) {
                    @Override
                    public void onNext(SplashBean splashBean) {
                        callBack.getDataSuccess(splashBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        callBack.getDataFailed();

                    }
                });
    }
}
