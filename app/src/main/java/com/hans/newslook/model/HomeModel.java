package com.hans.newslook.model;

import com.hans.newslook.base.BaseContract;
import com.hans.newslook.model.bean.zhihu.StoriesBean;
import com.hans.newslook.model.bean.zhihu.ZhihuEntity;
import com.hans.newslook.contract.callback.GetDataCallBack;
import com.hans.newslook.contract.HomeContract;
import com.hans.newslook.net.RetrofitHelper;
import com.hans.newslook.net.RetrofitService;
import com.hans.newslook.utils.CommonSubscriber;
import com.hans.newslook.utils.LogUtils;
import com.hans.newslook.utils.RxUtils;

import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * Created by hans on 2017/7/25 14:01.
 */

public class HomeModel implements HomeContract.Model {


    @Override
    public void requestLastDailyData(final BaseContract.BaseView mView, final GetDataCallBack<List<StoriesBean>> getDataCallBack) {

        RetrofitHelper.getInstance().create(RetrofitService.class).getLastDaily()
                .map(new Function<ZhihuEntity, List<StoriesBean>>() {
                    @Override
                    public List<StoriesBean> apply(@NonNull ZhihuEntity zhihuEntity) throws Exception {
                        return zhihuEntity.getStories();
                    }
                })
                .compose(RxUtils.applySchedulers())
                .subscribe(new CommonSubscriber<List<StoriesBean>>(mView) {
                    @Override
                    public void onNext(List<StoriesBean> zhihuEntity) {
                        LogUtils.e(mView.getClass().getSimpleName(), "loadDetailData");
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
        RetrofitHelper.getInstance().create(RetrofitService.class).getTheDaily(date)
                .map(new Function<ZhihuEntity, List<StoriesBean>>() {
                    @Override
                    public List<StoriesBean> apply(@NonNull ZhihuEntity zhihuEntity) throws Exception {
                        return zhihuEntity.getStories();
                    }
                })
                .compose(RxUtils.applySchedulers())
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
