package com.demo.newsdemo.net;



import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * ZhihuHttp
 * Created by Administrator on 2016/12/14.
 */
public class GirlHttp {

    private static OkHttpClient client;
    private static GirlService girlService;
    private static Retrofit retrofit;

    /**
     * @return retrofit的底层利用反射的方式, 获取所有的api接口的类
     */
    public static GirlService getGirlService() {
        if (girlService == null) {
            girlService = getRetrofit().create(GirlService.class);
        }
        return girlService;
    }

    public static Retrofit getRetrofit() {
        if (retrofit == null) {
            synchronized (GirlHttp.class) {
                if (retrofit == null) {
                    //添加一个log拦截器,打印所的log
                    client = new OkHttpClient
                            .Builder()
                            .addInterceptor(new HttpLoggingInterceptor()) //日志,所有的请求响应度看到
//                            .cache(cache)  //添加缓存
                            .connectTimeout(10, TimeUnit.SECONDS)
                            .readTimeout(300, TimeUnit.SECONDS)
                            .writeTimeout(10, TimeUnit.SECONDS)
                            .build();

                    // 获取retrofit的实例
                    retrofit = new Retrofit
                            .Builder()
                            .baseUrl(API.URL_GET_GIRL)  //自己配置
                            .client(client)
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .addConverterFactory(ScalarsConverterFactory.create()) //这里是用的fastjson的
                            .build();
                }
            }
        }
        return retrofit;
    }


}
