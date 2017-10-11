package com.demo.newsdemo.model;

import com.demo.newsdemo.base.BaseContract;
import com.demo.newsdemo.base.BaseModel;
import com.demo.newsdemo.contract.GirlItemContract;
import com.demo.newsdemo.contract.callback.GetDataCallBack;
import com.demo.newsdemo.model.bean.GirlItemData;
import com.demo.newsdemo.utils.CommonSubscriber;
import com.demo.newsdemo.utils.JsoupUtil;
import com.demo.newsdemo.utils.LogUtil;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by hans on 2017/8/13 17:10.
 */

public class GirlItemModel extends BaseModel implements GirlItemContract.Model {



    @Override
    public void requestNetForData(String cid, int pageCount, final BaseContract.BaseView mView, final GetDataCallBack<List<GirlItemData>> callBack) {

        girlService.getGirlItemData(cid,pageCount)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new CommonSubscriber<String>(mView) {
                    @Override
                    public void onNext(String s) {
                        LogUtil.e("girl model","s:"+s);
                        callBack.getDataSuccess(JsoupUtil.parseGirls(s));
                    }
                });
    }
}
