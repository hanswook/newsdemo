package com.demo.newsdemo.base;

import com.demo.newsdemo.net.GankHttp;
import com.demo.newsdemo.net.GankService;
import com.demo.newsdemo.net.GirlHttp;
import com.demo.newsdemo.net.GirlService;
import com.demo.newsdemo.net.RetrofitHelper;
import com.demo.newsdemo.net.RetrofitService;
import com.demo.newsdemo.net.ZhihuHttp;
import com.demo.newsdemo.net.ZhihuService;

/**
 * Created by hans on 2017/7/25 14:01.
 */

public class BaseModel {
    protected static ZhihuService zhihuService;
    protected static GankService gankService;
    protected static GirlService girlService;
    protected static RetrofitService retrofitService;

    static {
        zhihuService = ZhihuHttp.getZhihuService();
        gankService = GankHttp.getGankService();
        girlService = GirlHttp.getGirlService();
        retrofitService = RetrofitHelper.getRetrofitService();
    }
}
