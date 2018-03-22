package com.hans.newslook.model;

import com.hans.newslook.base.BaseContract;
import com.hans.newslook.base.BaseModel;
import com.hans.newslook.model.bean.zhihu.TheStoryBean;
import com.hans.newslook.contract.callback.GetDataCallBack;
import com.hans.newslook.contract.ZhihuDetailContract;
import com.hans.newslook.utils.CommonSubscriber;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by hans on 2017/7/25 15:24.
 */

public class ZhihuDetailModel extends BaseModel implements ZhihuDetailContract.Model {


    @Override
    public void getDetailData(BaseContract.BaseView mView, String id, final GetDataCallBack<TheStoryBean> callBack) {
        retrofitService.getZhihuStory(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CommonSubscriber<TheStoryBean>(mView) {
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
