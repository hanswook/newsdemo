package com.hans.newslook.model;

import com.hans.newslook.base.BaseContract;
import com.hans.newslook.contract.SplashContract;
import com.hans.newslook.contract.callback.GetDataCallBack;
import com.hans.newslook.model.bean.SplashBean;
import com.hans.newslook.net.RetrofitHelper;
import com.hans.newslook.net.RetrofitService;
import com.hans.newslook.utils.CommonSubscriber;
import com.hans.newslook.utils.RxUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by hans on 2017/8/9 16:00.
 */

public class SplashModel implements SplashContract.Model{
    @Override
    public void getData(final BaseContract.BaseView mView, final GetDataCallBack<SplashBean> callBack) {
        RetrofitHelper.getInstance().create(RetrofitService.class)
                .getImage()
                .compose(RxUtils.applySchedulers())
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
