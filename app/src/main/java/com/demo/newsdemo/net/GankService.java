package com.demo.newsdemo.net;


import com.demo.newsdemo.model.bean.GankItemData;
import com.demo.newsdemo.model.bean.HttpResult;
import com.demo.newsdemo.model.bean.SplashBean;
import com.demo.newsdemo.model.bean.zhihu.TheStoryBean;
import com.demo.newsdemo.model.bean.zhihu.ZhihuEntity;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * 网络请求的接口
 * Created by Administrator on 2016/11/9.
 */
public interface GankService {

//    http://gank.io/api/data/Android/10/1


    @GET("data/{type}/10/{page_count}")
    Observable<HttpResult<List<GankItemData>>> getGankData(@Path("type") String type,
                                                           @Path("page_count") int page_count);


}
