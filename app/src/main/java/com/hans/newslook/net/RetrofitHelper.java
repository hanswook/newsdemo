package com.hans.newslook.net;

import com.hans.newslook.utils.baseutils.LogUtils;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by hans on 2017/10/11.
 */

public class RetrofitHelper {

    private static OkHttpClient client;
    private static RetrofitHelper sInstace;
    private static Retrofit retrofit;


    /**
     * 创建单例
     */
    public static RetrofitHelper getInstance() {
        if (sInstace == null) {
            synchronized (RetrofitHelper.class) {
                sInstace = new RetrofitHelper();
            }
        }
        return sInstace;
    }

    private RetrofitHelper() {

        Interceptor interceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                //获取request
                Request request = chain.request();
                //获取request的创建者builder
                Request.Builder builder = request.newBuilder();
                //从request中获取headers，通过给定的键url_name
                List<String> headerValues = request.headers("url_name");
                if (headerValues != null && headerValues.size() > 0) {
                    //如果有这个header，先将配置的header删除，因此header仅用作app和okhttp之间使用
                    builder.removeHeader(API.HEADER_KEY);

                    //匹配获得新的BaseUrl
                    String headerValue = headerValues.get(0);
                    //从request中获取原有的HttpUrl实例oldHttpUrl
                    HttpUrl oldHttpUrl = request.url();
                    HttpUrl newBaseUrl = null;
                    if ("gank".equals(headerValue)) {
                        newBaseUrl = HttpUrl.parse(API.GANK_IO_URL);
                    } else if ("zhihu".equals(headerValue)) {
                        newBaseUrl = HttpUrl.parse(API.ZHIHU_URL);
                    } else if ("dbgirl".equals(headerValue)) {
                        newBaseUrl = HttpUrl.parse(API.URL_GET_GIRL);
                    } else {
                        newBaseUrl = oldHttpUrl;
                    }
                    LogUtils.e("retrofit helper", "new url:" + newBaseUrl);
                    LogUtils.e("retrofit helper", "oldHttpUrl:" + oldHttpUrl);

                    //重建新的HttpUrl，修改需要修改的url部分
                    HttpUrl newFullUrl = oldHttpUrl
                            .newBuilder()
                            .scheme(newBaseUrl.scheme())
                            .host(newBaseUrl.host())
                            .port(newBaseUrl.port())
                            .build();
                    LogUtils.e("retrofit helper", "newFullUrl:" + newFullUrl);

                    //重建这个request，通过builder.url(newFullUrl).build()；
                    //然后返回一个response至此结束修改
                    return chain.proceed(builder.url(newFullUrl).build());
                } else {
                    return chain.proceed(request);
                }
            }
        };

        client = new OkHttpClient
                .Builder()
                .addInterceptor(interceptor)
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(300, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .build();

        // 获取retrofit的实例
        retrofit = new Retrofit
                .Builder()
                .baseUrl(API.GANK_IO_URL)  //自己配置
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();


    }

    public <T> T create(Class<T> service) {
        return retrofit.create(service);
    }


}
