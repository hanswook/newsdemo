package com.demo.newsdemo.net;


import com.demo.newsdemo.model.bean.SplashBean;
import com.demo.newsdemo.model.bean.zhihu.TheStoryBean;
import com.demo.newsdemo.model.bean.zhihu.ZhihuEntity;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * 网络请求的接口
 * Created by Administrator on 2016/11/9.
 */
public interface ZhihuService {
    @GET("/api/4/news/latest")
    Observable<ZhihuEntity> getLastDaily();

    @GET("/api/4/news/before/{date}")
    Observable<ZhihuEntity> getTheDaily(@Path("date") String date);

    @GET("/api/4/news/{id}")
    Observable<TheStoryBean> getZhihuStory(@Path("id") String id);

    @GET("http://lab.zuimeia.com/wallpaper/category/1/?page_size=1")
    Observable<SplashBean> getImage();



}
