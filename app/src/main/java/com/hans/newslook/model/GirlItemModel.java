package com.hans.newslook.model;

import com.hans.newslook.base.BaseContract;
import com.hans.newslook.contract.GirlItemContract;
import com.hans.newslook.contract.callback.GetDataCallBack;
import com.hans.newslook.model.bean.GirlItemData;
import com.hans.newslook.net.DBGrilNetHelper;
import com.hans.newslook.net.GirlService;
import com.hans.newslook.utils.CommonSubscriber;
import com.hans.newslook.utils.baseutils.JsoupUtil;
import com.hans.newslook.utils.baseutils.LogUtils;
import com.hans.newslook.utils.rxutils.RxUtils;

import java.util.List;

/**
 * Created by hans on 2017/8/13 17:10.
 */

public class GirlItemModel implements GirlItemContract.Model {


    @Override
    public void requestNetForData(String cid, int pageCount, final BaseContract.BaseView mView, final GetDataCallBack<List<GirlItemData>> callBack) {

        DBGrilNetHelper.getInstance().create(GirlService.class)
                .getGirlItemData(cid, pageCount)
                .compose(RxUtils.applySchedulers())
                .subscribe(new CommonSubscriber<String>(mView) {
                    @Override
                    public void onNext(String s) {
                        LogUtils.d("girl model", "s:" + s);
                        callBack.getDataSuccess(JsoupUtil.parseGirls(s));
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        LogUtils.d("girl model", "e" + e.toString());

                    }
                });
    }
}
