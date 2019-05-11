package com.hans.newslook.base;

import io.reactivex.disposables.Disposable;

/**
 * Created by hans
 * e-mail: hxxx1992@163.com
 */

public interface IBaseView {
    boolean addRxDestroy(Disposable disposable);

    boolean addRxStop(Disposable disposable);

    void remove(Disposable disposable);
    /**
     * 显示ProgressDialog
     */
    void showProgress();

    /**
     * 显示ProgressDialog
     */
    void showProgress(String msg);

    /**
     * 取消ProgressDialog
     */
    void dismissProgress();


}
