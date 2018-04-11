package com.hans.newslook.model;

import com.hans.newslook.base.BaseContract;
import com.hans.newslook.model.bean.zhihu.TheStoryBean;
import com.hans.newslook.contract.callback.GetDataCallBack;
import com.hans.newslook.contract.ZhihuDetailContract;
import com.hans.newslook.net.RetrofitHelper;
import com.hans.newslook.net.RetrofitService;
import com.hans.newslook.utils.CommonSubscriber;
import com.hans.newslook.utils.rxutils.RxUtils;

/**
 * Created by hans on 2017/7/25 15:24.
 */

public class ZhihuDetailModel implements ZhihuDetailContract.Model {


    @Override
    public void getDetailData(BaseContract.BaseView mView, String id, final GetDataCallBack<TheStoryBean> callBack) {
        RetrofitHelper.getInstance().create(RetrofitService.class).getZhihuStory(id)
                .compose(RxUtils.applySchedulers())
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
