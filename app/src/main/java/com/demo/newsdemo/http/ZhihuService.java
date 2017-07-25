package com.demo.newsdemo.http;


import com.demo.newsdemo.bean.TheStoryBean;
import com.demo.newsdemo.bean.ZhihuEntity;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * 网络请求的接口
 * Created by Administrator on 2016/11/9.
 * pid=1，paopId=1：竞技平台
 * pid=2，paopId=2：游戏平台
 * pid=3，paopId=3：教育平台
 * 凡是日志记录接口，get/post都可以
 * 凡是需要从服务器获取数据的，只能post
 */
public interface ZhihuService {
    @GET("/api/4/news/latest")
    Observable<ZhihuEntity> getLastDaily();

    @GET("/api/4/news/before/{date}")
    Observable<ZhihuEntity> getTheDaily(@Path("date") String date);

    @GET("/api/4/news/{id}")
    Observable<TheStoryBean> getZhihuStory(@Path("id") String id);

    @GET("http://lab.zuimeia.com/wallpaper/category/1/?page_size=1")
    Observable<ZhihuEntity> getImage();
}
