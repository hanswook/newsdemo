package com.hans.newslook.model;

import com.hans.newslook.base.BaseContract;
import com.hans.newslook.contract.GirlItemContract;
import com.hans.newslook.contract.callback.GetDataCallBack;
import com.hans.newslook.model.bean.GirlItemData;
import com.hans.newslook.net.GirlService;
import com.hans.newslook.net.RetrofitHelper;
import com.hans.newslook.net.RetrofitService;
import com.hans.newslook.utils.CommonSubscriber;
import com.hans.newslook.utils.JsoupUtil;
import com.hans.newslook.utils.LogUtil;
import com.hans.newslook.utils.RxUtils;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by hans on 2017/8/13 17:10.
 */

public class GirlItemModel implements GirlItemContract.Model {


    @Override
    public void requestNetForData(String cid, int pageCount, final BaseContract.BaseView mView, final GetDataCallBack<List<GirlItemData>> callBack) {

        RetrofitHelper.getInstance().create(GirlService.class)
                .getGirlItemData(cid, pageCount)
                .compose(RxUtils.applySchedulers())
                .subscribe(new CommonSubscriber<String>(mView) {
                    @Override
                    public void onNext(String s) {
                        LogUtil.d("girl model", "s:" + s);
                        callBack.getDataSuccess(JsoupUtil.parseGirls(s));
                    }
                });
    }
}
