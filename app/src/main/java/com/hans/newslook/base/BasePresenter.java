package com.hans.newslook.base;

/**
 * Created by hans
 * date: 2017/8/9 16:39 17:22.
 * e-mail: hxxx1992@163.com
 */

public class BasePresenter<T extends BaseContract.BaseView> implements BaseContract.BasePresenter<T> {

    protected T mView;

    @Override
    public void attachView(T view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }

    @Override
    public boolean isAttached() {
        return mView != null;
    }
}
