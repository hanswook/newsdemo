package com.hans.newslook.base;

/**
 * Created by hans
 * date: 2017/8/9 16:39 17:22.
 * e-mail: hxxx1992@163.com
 */


public interface BaseContract {

    interface BasePresenter<T> {
        void attachView(T view);

        void detachView();

        boolean isAttached();
    }

    interface BaseView extends IBaseView{
        void showError();
    }
}
