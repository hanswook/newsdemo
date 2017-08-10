package com.demo.newsdemo.base;

import com.demo.newsdemo.net.ZhihuHttp;
import com.demo.newsdemo.net.ZhihuService;

/**
 * Created by hans on 2017/7/25 14:01.
 */

public class BaseModel {
    protected static ZhihuService zhihuService;

    static {
        zhihuService = ZhihuHttp.getZhihuService();
    }
}
