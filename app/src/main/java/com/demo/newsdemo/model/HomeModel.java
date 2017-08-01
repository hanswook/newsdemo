package com.demo.newsdemo.model;

import android.content.Context;

import com.demo.newsdemo.base.BaseModel;
import com.demo.newsdemo.bean.StoriesBean;
import com.demo.newsdemo.bean.ZhihuEntity;
import com.demo.newsdemo.callback.GetDataCallBack;
import com.demo.newsdemo.contract.HomeContract;
import com.demo.newsdemo.utils.CommonSubscriber;
import com.demo.newsdemo.utils.LogUtil;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by hans on 2017/7/25 14:01.
 */

public class HomeModel extends BaseModel implements HomeContract.Model {


    @Override
    public void requestLastDailyData(final Context context, final GetDataCallBack<List<StoriesBean>> getDataCallBack) {
        LogUtil.e(context.getClass().getSimpleName(), "requestLastDailyData");

        zhihuService.getLastDaily()
                .map(new Function<ZhihuEntity, List<StoriesBean>>() {
                    @Override
                    public List<StoriesBean> apply(@NonNull ZhihuEntity zhihuEntity) throws Exception {
                        return zhihuEntity.getStories();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CommonSubscriber<List<StoriesBean>>(context) {
                    @Override
                    public void onNext(List<StoriesBean> zhihuEntity) {
                        LogUtil.e(context.getClass().getSimpleName(), "loadData");
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
    public void requestMoreData(String date, Context context, final GetDataCallBack<List<StoriesBean>> callBack) {
        zhihuService.getTheDaily(date)
                .map(new Function<ZhihuEntity, List<StoriesBean>>() {
                    @Override
                    public List<StoriesBean> apply(@NonNull ZhihuEntity zhihuEntity) throws Exception {
                        return zhihuEntity.getStories();
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CommonSubscriber<List<StoriesBean>>(context) {
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
