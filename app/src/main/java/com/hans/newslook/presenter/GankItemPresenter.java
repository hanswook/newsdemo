package com.hans.newslook.presenter;

import com.hans.newslook.base.BasePresenter;
import com.hans.newslook.callbacks.DataCallback;
import com.hans.newslook.contract.GankItemContract;
import com.hans.newslook.model.NewsModel;
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

    private boolean isLoading = false;

    private int pageCount = 1;


    @Inject
    public GankItemPresenter(GankItemContract.View mView) {
        attachView(mView);
    }

    @Override
    public void loadData(String type) {
        if (isLoading) {
            return;
        }
        mView.showProgress("加载中...");
        isLoading = true;
        LogUtils.e("GankItemPresenter", "loadData:type:" + type + ",pageCount:" + pageCount);
        NewsModel.getInstance()
                .getGankItemData(type, pageCount, new DataCallback<List<GankItemData>>() {
                    @Override
                    public void success(List<GankItemData> gankItemData) {
                        if (!isAttached()) {
                            return;
                        }
                        mView.dismissProgress();
                        pageCount++;
                        mView.updateUI(gankItemData);
                        isLoading = false;
                    }
                });

    }

    @Override
    public void refreshData(String type) {
        if (isLoading) {
            return;
        }
        pageCount = 1;
        loadData(type);
    }

}
