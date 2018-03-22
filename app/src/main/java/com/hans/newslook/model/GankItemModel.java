package com.hans.newslook.model;

import com.hans.newslook.base.BaseContract;
import com.hans.newslook.base.BaseModel;
import com.hans.newslook.contract.GankItemContract;
import com.hans.newslook.contract.callback.GetDataCallBack;
import com.hans.newslook.model.bean.GankItemData;
import com.hans.newslook.model.bean.HttpResult;
import com.hans.newslook.utils.CommonSubscriber;
import com.hans.newslook.utils.LogUtil;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by hans on 2017/8/11 11:02.
 */

public class GankItemModel extends BaseModel implements GankItemContract.Model {



    @Override
    public void requestNetForData(String type, int pageCount, BaseContract.BaseView mView, final GetDataCallBack<List<GankItemData>> callBack) {
        LogUtil.e("GankItemModel","requestNetForData");
        retrofitService.getGankData(type, pageCount)
                .map(new Function<HttpResult<List<GankItemData>>, List<GankItemData>>() {
                    @Override
                    public List<GankItemData> apply(@NonNull HttpResult<List<GankItemData>> listHttpResult) throws Exception {
                        if (listHttpResult.isError())
                            throw new Exception("网络请求失败");
                        return listHttpResult.getResults();
                    }
                })
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CommonSubscriber<List<GankItemData>>(mView) {
                    @Override
                    public void onNext(List<GankItemData> gankItemData) {
                        callBack.getDataSuccess(gankItemData);
                    }
                });
    }
}
