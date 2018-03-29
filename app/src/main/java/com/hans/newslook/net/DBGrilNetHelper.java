package com.hans.newslook.net;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by hans on 2017/10/11.
 */

public class DBGrilNetHelper {

    private static OkHttpClient client;
    private static DBGrilNetHelper sInstace;
    private static Retrofit retrofit;


    /**
     * 创建单例
     */
    public static DBGrilNetHelper getInstance() {
        if (sInstace == null) {
            synchronized (DBGrilNetHelper.class) {
                sInstace = new DBGrilNetHelper();
            }
        }
        return sInstace;
    }

    private DBGrilNetHelper() {


        client = new OkHttpClient
                .Builder()
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
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();


    }

    public <T> T create(Class<T> service) {
        return retrofit.create(service);
    }


}
