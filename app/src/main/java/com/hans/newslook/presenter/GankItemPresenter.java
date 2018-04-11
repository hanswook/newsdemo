package com.hans.newslook.presenter;

import com.hans.newslook.base.BasePresenter;
import com.hans.newslook.contract.GankItemContract;
import com.hans.newslook.model.bean.GankItemData;
import com.hans.newslook.model.bean.HttpResult;
import com.hans.newslook.net.RetrofitHelper;
import com.hans.newslook.net.RetrofitService;
import com.hans.newslook.utils.CommonSubscriber;
import com.hans.newslook.utils.baseutils.LogUtils;
import com.hans.newslook.utils.rxutils.RxUtils;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * Created by hans on 2017/8/11 10:54.
 */

public class GankItemPresenter extends BasePresenter<GankItemContract.View> implements GankItemContract.Presenter<GankItemContract.View> {



    @Inject
    public GankItemPresenter(GankItemContract.View mView) {
        attachView(mView);
    }

    @Override
    public void loadData(String type, int pageCount) {
        LogUtils.e("GankItemPresenter", "loadData:type:" + type + ",pageCount:" + pageCount);
        RetrofitHelper.getInstance().create(RetrofitService.class)
                .getGankData(type, pageCount)
                .compose(RxUtils.applySchedulers())
                .map(new Function<HttpResult<List<GankItemData>>, List<GankItemData>>() {
                    @Override
                    public List<GankItemData> apply(@NonNull HttpResult<List<GankItemData>> listHttpResult) throws Exception {
                        if (listHttpResult.isError())
                            throw new Exception("网络请求失败");
                        return listHttpResult.getResults();
                    }
                })
                .subscribe(new CommonSubscriber<List<GankItemData>>(mView) {
                    @Override
                    public void onNext(List<GankItemData> gankItemData) {
                        if (!isAttached()) {
                            return;
                        }
                        mView.updateUI(gankItemData);
                    }
                });
    }


}
