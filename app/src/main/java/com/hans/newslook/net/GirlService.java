package com.hans.newslook.net;


import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * 网络请求的接口
 * Created by Administrator on 2016/11/9.
 */
public interface GirlService {

    @GET("{id}")
    Observable<String> getGirlDetailData(@Path("id") String id);

    @GET("http://www.buxiuse.com/")
    Observable<String> getGirlItemData(@Query("cid") String cid, @Query("pager_offset") int pager_offset);

    // @GET("index.html")
    //    Observable<String> getGirlItemData(@Query("cid") String cid, @Query("pager_offset") int pager_offset);

    @GET("show.html")
    Observable<Object> getGirlItemData1(@Query("cid") String cid, @Query("pager_offset") int pager_offset);


}
