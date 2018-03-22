package com.hans.newslook.base;

import com.hans.newslook.net.RetrofitHelper;
import com.hans.newslook.net.RetrofitService;

/**
 * Created by hans on 2017/7/25 14:01.
 */

public class BaseModel {
    protected static RetrofitService retrofitService;

    static {
        retrofitService = RetrofitHelper.getRetrofitService();
    }
}
