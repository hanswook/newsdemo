package com.demo.newsdemo.model;

import android.content.Context;

import com.demo.newsdemo.base.BaseModel;
import com.demo.newsdemo.model.bean.zhihu.TheStoryBean;
import com.demo.newsdemo.contract.callback.GetDataCallBack;
import com.demo.newsdemo.contract.ZhihuDetailContract;
import com.demo.newsdemo.utils.CommonSubscriber;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by hans on 2017/7/25 15:24.
 */

public class ZhihuDetailModel extends BaseModel implements ZhihuDetailContract.Model {


    @Override
    public void getDetailData(Context context, String id, final GetDataCallBack<TheStoryBean> callBack) {
        zhihuService.getZhihuStory(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CommonSubscriber<TheStoryBean>(context) {
                    @Override
                    public void onNext(TheStoryBean theStoryBean) {
                        callBack.getDataSuccess(theStoryBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        callBack.getDataFailed();
                    }
                });
    }
}
