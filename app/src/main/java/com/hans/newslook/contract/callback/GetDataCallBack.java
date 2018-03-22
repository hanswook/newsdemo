package com.hans.newslook.contract.callback;

/**
 * Created by hans on 2017/7/25 14:20.
 */


public interface GetDataCallBack<T> {
    void getDataSuccess(T t);

    void getDataFailed();
}
