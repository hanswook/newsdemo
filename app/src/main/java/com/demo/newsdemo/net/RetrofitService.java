package com.demo.newsdemo.net;

import com.demo.newsdemo.model.bean.GankItemData;
import com.demo.newsdemo.model.bean.HttpResult;
import com.demo.newsdemo.model.bean.SplashBean;
import com.demo.newsdemo.model.bean.zhihu.TheStoryBean;
import com.demo.newsdemo.model.bean.zhihu.ZhihuEntity;

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
    @GET("dbgroup/{id}")
    Observable<String> getGirlDetailData(@Path("id") String id);

    @GET("dbgroup/show.htm")
    Observable<String> getGirlItemData(@Query("cid") String cid, @Query("pager_offset") int pager_offset);

    @GET("dbgroup/show.htm")
    Observable<Object> getGirlItemData1(@Query("cid") String cid, @Query("pager_offset") int pager_offset);


   /* @GET("dbgroup/{id}")
    Observable<String> getGirlDetailData(@Path("id") String id);

    @GET("dbgroup/show.htm")
    Observable<String> getGirlItemData(@Query("cid") String cid, @Query("pager_offset") int pager_offset);

    @GET("dbgroup/show.htm")
    Observable<Object> getGirlItemData1(@Query("cid") String cid, @Query("pager_offset") int pager_offset);*/

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
