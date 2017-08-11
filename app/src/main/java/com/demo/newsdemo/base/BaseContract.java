package com.demo.newsdemo.base;

/**
 * Created by hans on 2017/8/9 16:39.
 */

public interface BaseContract {

    interface BasePresenter<T> {
        void attachView(T view);

        void detachView();
    }

    interface BaseView {
        void showError();

        void complete();
    }
}
