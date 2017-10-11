package com.demo.newsdemo.model;

import android.content.Context;

import com.demo.newsdemo.base.BaseActivity;
import com.demo.newsdemo.base.BaseContract;
import com.demo.newsdemo.base.BaseModel;
import com.demo.newsdemo.model.bean.zhihu.StoriesBean;
import com.demo.newsdemo.model.bean.zhihu.ZhihuEntity;
import com.demo.newsdemo.contract.callback.GetDataCallBack;
import com.demo.newsdemo.contract.HomeContract;
import com.demo.newsdemo.utils.CommonSubscriber;
import com.demo.newsdemo.utils.LogUtil;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by hans on 2017/7/25 14:01.
 */

public class HomeModel extends BaseModel implements HomeContract.Model {


    @Override
    public void requestLastDailyData(final BaseContract.BaseView mView, final GetDataCallBack<List<StoriesBean>> getDataCallBack) {

        retrofitService.getLastDaily()
                .map(new Function<ZhihuEntity, List<StoriesBean>>() {
                    @Override
                    public List<StoriesBean> apply(@NonNull ZhihuEntity zhihuEntity) throws Exception {
                        return zhihuEntity.getStories();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CommonSubscriber<List<StoriesBean>>(mView) {
                    @Override
                    public void onNext(List<StoriesBean> zhihuEntity) {
                        LogUtil.e(mView.getClass().getSimpleName(), "loadDetailData");
                        getDataCallBack.getDataSuccess(zhihuEntity);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        getDataCallBack.getDataFailed();
                    }
                });
    }

    @Override
    public void requestMoreData(String date, final BaseContract.BaseView mView, final GetDataCallBack<List<StoriesBean>> callBack) {
        retrofitService.getTheDaily(date)
                .map(new Function<ZhihuEntity, List<StoriesBean>>() {
                    @Override
                    public List<StoriesBean> apply(@NonNull ZhihuEntity zhihuEntity) throws Exception {
                        return zhihuEntity.getStories();
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CommonSubscriber<List<StoriesBean>>(mView) {
                    @Override
                    public void onNext(List<StoriesBean> zhihuEntity) {
                        callBack.getDataSuccess(zhihuEntity);
                    }

                    @Override
                    public void onError(Throwable e) {
                        callBack.getDataFailed();
                    }
                });
    }

}
