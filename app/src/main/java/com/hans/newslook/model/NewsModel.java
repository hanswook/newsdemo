package com.hans.newslook.model;

import com.hans.newslook.callbacks.DataCallback;
import com.hans.newslook.model.bean.GankItemData;
import com.hans.newslook.model.bean.HttpResult;
import com.hans.newslook.net.RetrofitHelper;
import com.hans.newslook.net.RetrofitService;
import com.hans.newslook.utils.CommonSubscriber;
import com.hans.newslook.utils.baseutils.LogUtils;
import com.hans.newslook.utils.rxutils.RxUtils;

import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * Created by hans
 * date: 2018/4/18 17:47.
 * e-mail: hxxx1992@163.com
 */

public class NewsModel {
    private static NewsModel sInstace;


    /**
     * 创建单例
     */
    public static NewsModel getInstance() {
        if (sInstace == null) {
            synchronized (NewsModel.class) {
                sInstace = new NewsModel();
            }
        }
        return sInstace;
    }


    public void getGankItemData(String type, int pageCount, DataCallback<List<GankItemData>> dataCallback) {
        LogUtils.e("NewsModel getGankItemData ");
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
                .subscribe(new Consumer<List<GankItemData>>() {
                    @Override
                    public void accept(List<GankItemData> gankItemData) throws Exception {
                        dataCallback.success(gankItemData);
                    }
                });

    }
}
