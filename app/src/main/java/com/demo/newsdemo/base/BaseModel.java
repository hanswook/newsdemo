package com.demo.newsdemo.base;

import com.demo.newsdemo.net.GirlHttp;
import com.demo.newsdemo.net.GirlService;
import com.demo.newsdemo.net.RetrofitHelper;
import com.demo.newsdemo.net.RetrofitService;

/**
 * Created by hans on 2017/7/25 14:01.
 */

public class BaseModel {
    protected static RetrofitService retrofitService;

    static {
        retrofitService = RetrofitHelper.getRetrofitService();
    }
}
