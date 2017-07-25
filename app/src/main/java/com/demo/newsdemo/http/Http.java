package com.demo.newsdemo.http;


import com.qgzn.edu.funnystar.utils.API;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Http
 * Created by Administrator on 2016/12/14.
 */
public class Http {

    private static OkHttpClient client;
    private static HttpService httpService;
    private static Retrofit retrofit;

    /**
     * @return retrofit的底层利用反射的方式, 获取所有的api接口的类
     */
    public static HttpService getHttpService() {
        if (httpService == null) {
            httpService = getRetrofit().create(HttpService.class);
        }
        return httpService;
    }

    public static Retrofit getRetrofit() {
        if (retrofit == null) {
            synchronized (Http.class) {
                if (retrofit == null) {
                    //添加一个log拦截器,打印所有的log
                    HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
                    //可以设置请求过滤的水平,body,basic,headers
//                    httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                    //设置 请求的缓存的大小跟位置
//                    File cacheFile = new File(HansApp.getAppContext().getCacheDir(), "cache");
//                    Cache cache = new Cache(cacheFile, 1024 * 1024 * 50); //50Mb 缓存的大小

                    client = new OkHttpClient
                            .Builder()
//                            .addInterceptor(addQueryParameterInterceptor())  //参数添加
//                            .addInterceptor(addHeaderInterceptor()) // token过滤
                            .addInterceptor(new HttpLoggingInterceptor()) //日志,所有的请求响应度看到
//                            .cache(cache)  //添加缓存
                            .connectTimeout(10, TimeUnit.SECONDS)
                            .readTimeout(300, TimeUnit.SECONDS)
                            .writeTimeout(10, TimeUnit.SECONDS)
                            .build();

                    // 获取retrofit的实例
                    retrofit = new Retrofit
                            .Builder()
                            .baseUrl(API.INTERFACE_BASE_URL)  //自己配置
                            .client(client)
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create()) //这里是用的fastjson的
                            .build();
                }
            }
        }
        return retrofit;
    }


}
