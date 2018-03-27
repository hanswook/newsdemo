package com.hans.newslook.net;

import com.hans.newslook.model.bean.GankItemData;
import com.hans.newslook.model.bean.HttpResult;
import com.hans.newslook.model.bean.SplashBean;
import com.hans.newslook.model.bean.zhihu.TheStoryBean;
import com.hans.newslook.model.bean.zhihu.ZhihuEntity;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by hans on 2017/10/11.
 */

public interface RetrofitService {


    @Headers({"url_name:gank"})
    @GET("api/data/{type}/10/{page_count}")
    Observable<HttpResult<List<GankItemData>>> getGankData(@Path("type") String type,
                                                           @Path("page_count") int page_count);

    @Headers({"url_name:gank"})
    @GET("api/data/福利/10/{pageNo}")
    Observable<HttpResult<List<GankItemData>>> getGirlsData(@Path("pageNo") int pageNo);



    @Headers({"url_name:zhihu"})
    @GET("api/4/news/latest")
    Observable<ZhihuEntity> getLastDaily();

    @Headers({"url_name:zhihu"})
    @GET("api/4/news/before/{date}")
    Observable<ZhihuEntity> getTheDaily(@Path("date") String date);

    @Headers({"url_name:zhihu"})
    @GET("api/4/news/{id}")
    Observable<TheStoryBean> getZhihuStory(@Path("id") String id);

    @GET("http://lab.zuimeia.com/wallpaper/category/1/?page_size=1")
    Observable<SplashBean> getImage();




}
