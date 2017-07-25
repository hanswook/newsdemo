package com.demo.newsdemo.http;


import com.qgzn.edu.funnystar.bean.CodeBean;
import com.qgzn.edu.funnystar.bean.DataBean;
import com.qgzn.edu.funnystar.bean.LoginData;
import com.qgzn.edu.funnystar.bean.MainBean;
import com.qgzn.edu.funnystar.bean.PageDataBean;
import com.qgzn.edu.funnystar.bean.allcontent.AllContentBean;
import com.qgzn.edu.funnystar.bean.bbc.BabyGameDataBean;
import com.qgzn.edu.funnystar.bean.bbc.BabyUserInfoBean;
import com.qgzn.edu.funnystar.bean.bbc.HarvestGameBean;
import com.qgzn.edu.funnystar.bean.bbc.ToyDataBean;
import com.qgzn.edu.funnystar.bean.otherInfo.ParentPageInfo;
import com.qgzn.edu.funnystar.bean.otherInfo.RecentPageInfo;
import com.qgzn.edu.funnystar.bean.videoplay.VideoEpisode2Bean;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * 网络请求的接口
 * Created by Administrator on 2016/11/9.
 * pid=1，paopId=1：竞技平台
 * pid=2，paopId=2：游戏平台
 * pid=3，paopId=3：教育平台
 * 凡是日志记录接口，get/post都可以
 * 凡是需要从服务器获取数据的，只能post
 */
public interface HttpService {
    /**
     * 接口为登陆;
     * recommendId=57&open=1
     */
    //    @FormUrlEncoded
    @POST("/gpf-web/common/recommon.json")
    Observable<DataBean<LoginData>> login(@Query("recommendId") String recommendId,
                                          @Query("open") String open,
                                          @Query("pid") String pid);

    /**
     * 接口为获取首页数据;
     * http://192.168.11.56:8081/gpf-web/common/findPageInfo.json?actionName=edu_main&open=1&pid=3&paopId=3
     */
    @POST("/gpf-web/common/findPageData.json")
    Observable<DataBean<MainBean>> getMainData(@Query("actionName") String actionName,
                                               @Query("open") String open,
                                               @Query("pid") String pid);

    /**
     * 接口为获取全部内容界面数据;
     * http://192.168.11.56:8081/gpf-web/common/findPageData.json?actionName=edu_all&videos=2&pid=3&paopId=3&open=1
     */
    @POST("/gpf-web/common/findPageData.json")
    Observable<DataBean<AllContentBean>> getAllContentData(@Query("actionName") String actionName,
                                                           @Query("videos") String videos,
                                                           @Query("pid") String pid,
                                                           @Query("paopId") String paopId,
                                                           @Query("open") String open
    );

    /**
     * 接口为获取最近播放列表数据;
     * http://192.168.11.56:8081/gpf-web/common/findPageData.json?
     * actionName=edu_latest&pid=3&userLatestPlay=1&userId=1111&paopId=3&open=1
     */
    @POST("/gpf-web/common/findPageData.json")
    Observable<DataBean<PageDataBean<RecentPageInfo>>> getUserRecentViewData(@Query("actionName") String actionName,
                                                                             @Query("pid") String pid,
                                                                             @Query("userLatestPlay") String userLatestPlay,
                                                                             @Query("userId") String userId,
                                                                             @Query("paopId") String paopId,
                                                                             @Query("open") String open
    );


    /**
     * 接口为获取父母收藏列表;
     * http://192.168.11.56:8081/gpf-web/common/findPageData.json?
     * actionName=edu_collect&userCollectVideo=1&pid=3&paopId=3&open=1&userId=1111
     */
    @POST("/gpf-web/common/findPageData.json")
    Observable<DataBean<PageDataBean<ParentPageInfo>>> getParentCollectedData(@Query("actionName") String actionName,
                                                                              @Query("userCollectVideo") String userCollectVideo,
                                                                              @Query("pid") String pid,
                                                                              @Query("paopId") String paopId,
                                                                              @Query("open") String open,
                                                                              @Query("userId") String userId
    );

    /**
     * 接口为用户收藏视频接口
     * http://192.168.11.56:8081/gpf-web/log/logCollect.json?
     * pid=3&paopId=3&open=1&userId=chentong3&videosId=1&videoId=333
     * pid=3&paopId=3&open=1&userId=chentong3&videosId=1&videoId=333
     */
    @POST("/gpf-web/log/logCollect.json")
    Observable<CodeBean> postUserCollect(@Query("pid") String pid,
                                         @Query("paopId") String paopId,
                                         @Query("open") String open,
                                         @Query("userId") String userId,
                                         @Query("videosId") String videosId,
                                         @Query("videoId") String videoId,
                                         @Query("deleteFlag") String deleteFlag
    );


    /**
     * 接口为删除最近浏览记录。
     * http://192.168.11.56:8081/gpf-web/
     * log/logLatestPlay.json?pid=3&paopId=3&open=1&userId=chentong3&videosId=1&videoId=333&deleteFlag=1
     * videoId 为删除的哪一集的ID,可以不填写。
     */
    @POST("/gpf-web/log/logLatestPlay.json")
    Observable<DataBean<PageDataBean<ParentPageInfo>>> deleteRecentView(@Query("pid") String pid,
                                                                        @Query("paopId") String paopId,
                                                                        @Query("open") String open,
                                                                        @Query("userId") String userId,
                                                                        @Query("videosId") String videosId,
//                                                                        @Query("videoId") String videoId,
                                                                        @Query("deleteFlag") String deleteFlag
    );


    /**
     * 接口为增加最近浏览记录。
     * http://192.168.11.56:8081/gpf-web/log/logLatestPlay.json?pid=3&paopId=3&open=1&userId=1111&videosId=140&videoId=1
     * http://192.168.11.56:8081/gpf-web/
     * log/logLatestPlay.json?pid=3&paopId=3&open=1&userId=chentong3&videosId=1&videoId=333&deleteFlag=1
     * videoId 为删除的哪一集的ID,可以不填写。
     */
    @POST("/gpf-web/log/logLatestPlay.json")
    Observable<CodeBean> addRecentView(@Query("pid") String pid,
                                       @Query("paopId") String paopId,
                                       @Query("open") String open,
                                       @Query("userId") String userId,
                                       @Query("videosId") String videosId,
                                       @Query("videoId") String videoId
    );

    /**
     * 接口为获取某视频下所有的集数列表
     * http://192.168.11.56:8081/gpf-web/common/findPageData.json?actionName=edu_play&videosId=1&open=1&paopId=3&pid=1
     * actionName=edu_play&videosId=1&open=1&paopId=3&pid=1
     * pid,open,paopId：这三个参数同上，无论何时都要传递
     * actionName=edu_play：必填，代表当前操作的是播放页面
     * videosId=1:必填，代表是哪部视频的编号
     * 切记：接口中包含了所有的集数列表数据，同时包含视频信息
     */
    @POST("/gpf-web/common/findPageData.json")
    Observable<DataBean<VideoEpisode2Bean>> getAllEpisode(@Query("actionName") String actionName,
                                                          @Query("videosId") String videosId,
                                                          @Query("open") String open,
                                                          @Query("paopId") String paopId,
                                                          @Query("pid") String pid
    );


    /**
     * 接口为获取某集视频的详情
     * http://192.168.11.56:8081/gpf-web/common/findPageData.json?actionName=edu_play&videoId=2&open=1&paopId=3&pid=1
     * actionName=edu_play&videoId=2&open=1&paopId=3&pid=1
     * pid,open,paopId：这三个参数同上，无论何时都要传递
     * actionName=edu_play：必填，代表当前操作的是播放页面
     * videoId=2：必填，代表是哪个集数的编号
     * 切记：本接口不仅包含了该集视频的数据，同时包含视频信息
     */
    @POST("/gpf-web/common/findPageData.json")
    Observable<CodeBean> getOneEpisodeDetail(@Query("actionName") String actionName,
                                             @Query("videoId") String videoId,
                                             @Query("open") String open,
                                             @Query("paopId") String paopId,
                                             @Query("pid") String pid
    );

    /**
     * 接口为记录用户页面访问日志
     * http://192.168.11.56:8081/gpf-web/log/logPage.json?pid=3&paopId=3&open=1&curActionName=edu_main&lastActionName=edu_main&beFrom=jsyd&userId=chentong3
     * pid=3&paopId=3&open=1&curActionName=edu_main&lastActionName=edu_main&beFrom=jsyd&userId=chentong3
     * pid,open,paopId：这三个参数同上，无论何时都要传递
     * userId：必填，用户的唯一标识，用户编号
     * curActionName=edu_main：必填，代表用户当前访问页面的action名称
     * lastActionName=edu_main：必填，代表用户进入当前页面的上一个页面action的名称
     * beFrom=jsyd：必填，代表当前用户是从什么入口进入平台的，本案例模拟的值代表江苏移动
     */
    @POST("/gpf-web/log/logPage.json")
    Observable<CodeBean> logUserPageRecord(@Query("pid") String pid,
                                           @Query("paopId") String paopId,
                                           @Query("open") String open,
                                           @Query("curActionName") String curActionName,
                                           @Query("lastActionName") String lastActionName,
                                           @Query("beFrom") String beFrom,
                                           @Query("userId") String userId
    );


    /**
     * 接口为记录用户点击按钮日志
     * http://192.168.11.56:8081/gpf-web/log/logButton.json?pid=3&paopId=3&open=1&buttonId=32&userId=chentong3
     * pid=3&paopId=3&open=1&buttonId=32&userId=chentong3
     * pid,open,paopId：这三个参数同上，无论何时都要传递
     * userId：必填，用户的唯一标识，用户编号
     * buttonId=32：必填，按钮编号
     */
    @POST("/gpf-web/log/logButton.json")
    Observable<CodeBean> logUserButtonRecord(@Query("pid") String pid,
                                             @Query("paopId") String paopId,
                                             @Query("open") String open,
                                             @Query("buttonId") String buttonId,
                                             @Query("userId") String userId
    );


    /**
     * 接口为记录用户点播时长日志
     * http://192.168.11.56:8081/gpf-web/log/logVideo.json?pid=3&paopId=3&open=1&videoId=459&startTime=2017-07-05 12:32:36&endTime=2017-07-05 12:32:38&userId=chentong3
     * pid=3&paopId=3&open=1&videoId=459&startTime=2017-07-05 12:32:36&endTime=2017-07-05 12:32:38&userId=chentong3
     * pid,open,paopId：这三个参数同上，无论何时都要传递
     * userId：必填，用户的唯一标识，用户编号
     * videoId=459：必填，代表被记录的是哪一集视频的编号
     * startTime=2017-07-05 12:32:36：必填，代表该集视频开始播放的时间，切记：时间格式必须保持统一
     * endTime=2017-07-05 12:32:38：必填，代表该集视频结束播放的时间，用户退出播放或者播放完成的实际，切记，时间格式必须保持统一
     */
    @POST("/gpf-web/log/logVideo.json")
    Observable<CodeBean> logUserVideoPlayRecord(@Query("pid") String pid,
                                                @Query("paopId") String paopId,
                                                @Query("open") String open,
                                                @Query("videoId") String videoId,
                                                @Query("startTime") String startTime,
                                                @Query("endTime") String endTime,
                                                @Query("userId") String userId
    );


    /**
     * 接口为获取当前平台某尺寸所有的头像数据接口
     * http://192.168.11.56:8081/gpf-web/common/findPageData.json?actionName=edu_center_babyinfo&headIds=1&width=143&height=143&pid=3&paopId=3&open=1
     * actionName=edu_center_babyinfo&headIds=1&width=143&height=143&pid=3&paopId=3&open=1
     * pid,open,paopId：这三个参数同上，无论何时都要传递
     * userId：必填，用户的唯一标识，用户编号
     * videoId=459：必填，代表被记录的是哪一集视频的编号
     * startTime=2017-07-05 12:32:36：必填，代表该集视频开始播放的时间，切记：时间格式必须保持统一
     * endTime=2017-07-05 12:32:38：必填，代表该集视频结束播放的时间，用户退出播放或者播放完成的实际，切记，时间格式必须保持统一
     */
    @POST("/gpf-web/common/findPageData.json")
    Observable<CodeBean> getAllAvatarData(@Query("actionName") String actionName,
                                          @Query("headIds") String headIds,
                                          @Query("width") String width,
                                          @Query("height") String height,
                                          @Query("pid") String pid,
                                          @Query("paopId") String paopId,
                                          @Query("open") String open
    );


    /**
     * 接口为获取用户当前配置的头像接口，如果用户没有配置，或者传递的width与height不存在，那么接口将不反馈头像数据
     * http://192.168.11.56:8081/gpf-web/common/findPageData.json?actionName=edu_center_babyinfo&userId=10001234&userHead=1&width=143&height=143&pid=3&paopId=3&open=1
     * actionName=edu_center_babyinfo&userId=10001234&userHead=1&width=143&height=143&pid=3&paopId=3&open=1
     * pid,open,paopId：这三个参数同上，无论何时都要传递
     * userId：必填，用户的唯一标识，用户编号
     * videoId=459：必填，代表被记录的是哪一集视频的编号
     * startTime=2017-07-05 12:32:36：必填，代表该集视频开始播放的时间，切记：时间格式必须保持统一
     * endTime=2017-07-05 12:32:38：必填，代表该集视频结束播放的时间，用户退出播放或者播放完成的实际，切记，时间格式必须保持统一
     */
    @POST("/gpf-web/common/findPageData.json")
    Observable<CodeBean> getUserAvatarData(@Query("actionName") String actionName,
                                           @Query("userId") String userId,
                                           @Query("userHead") String userHead,
                                           @Query("width") String width,
                                           @Query("height") String height,
                                           @Query("pid") String pid,
                                           @Query("paopId") String paopId,
                                           @Query("open") String open
    );


    /**
     * 更新用户信息。宝宝信息调用此接口。
     * 接口为更新或注册用户信息接口，如果用户在平台不存在，会响应用户创建成功，如果用户平台已存在，会响应用户更新成功
     * http://192.168.11.56:8081/gpf-web/user/register.json?pid=3&paopId=3&open=1&userId=ceshi2&headId=1&phone=123&stbid=1111&sex=1&birthday=2012-2-22&mac=12.1.1.1headId=1
     * pid=3&paopId=3&open=1&userId=ceshi2&headId=1&phone=123&stbid=1111&sex=1&birthday=2012-2-22&mac=12.1.1.1headId=1
     * pid,open,paopId：这三个参数同上，无论何时都要传递
     * userId：必填，用户的唯一标识，用户编号
     * phone：非必填，代表用户手机号码
     * stbid：非必填，代表用户机顶盒号
     * sex：非必填，'用户性别 1:男 0:女',
     * birthday：非必填，用户生日日期，格式必须为yyyy-MM-dd
     * mac:非必填，机顶盒mac地址
     * headId:非必填，用户头像编号
     */
    @POST("/gpf-web/user/register.json")
    Observable<CodeBean> updateOrRegistUserData(@Query("pid") String pid,
                                                @Query("paopId") String paopId,
                                                @Query("open") String open,
                                                @Query("userId") String userId,
                                                @Query("headId") String headId,
                                                @Query("phone") String phone,
                                                @Query("stbid") String stbid,
                                                @Query("sex") String sex,
                                                @Query("birthday") String birthday,
                                                @Query("mac") String mac
    );

    /**
     * 获取用户信息接口，如果用户存在，数据就会有userInfo信息，如果用户不存在，将不会存在该信息
     * http://192.168.11.56:8081/gpf-web/common/findPageData.json?actionName=edu_center_babyinfo&pid=3&paopId=3&open=1&user=1&userId=10001234
     * actionName=edu_center_babyinfo&pid=3&paopId=3&open=1&user=1&userId=10001234
     * pid,open,paopId：这三个参数同上，无论何时都要传递
     * user=1：必填，值为1的时候代表获取用户所有信息，如果不传递该值，说明无需获取用户详细信息
     * actionName=edu_center_babyinfo：必填，代表当前操作的是哪个页面
     * userId：必填，用户的唯一标识，用户编号
     */
    @POST("/gpf-web/common/findPageData.json")
    Observable<DataBean<BabyUserInfoBean>> getUserData(@Query("actionName") String actionName,
                                                       @Query("pid") String pid,
                                                       @Query("paopId") String paopId,
                                                       @Query("open") String open,
                                                       @Query("user") String user,
                                                       @Query("userId") String userId
    );

    /**
     * 接口为获取农牧场信息接口
     * http://192.168.11.56:8081/gpf-web/farm/farmInfo.json?pid=3&paopId=3&open=1&userId=ceshi2&farmType=0
     * pid=3&paopId=3&open=1&userId=ceshi2&farmType=0
     * pid,open,paopId：这三个参数同上，无论何时都要传递
     * farmType=0：必填，值==0时，获取农场的相关信息，值==1时，获取牧场的相关信息
     * userId：必填，用户的唯一标识，用户编号
     * <p>
     * "endTime_MM": "07",				//本次结束时间的	月
     * "startTime": "2017-07-17 06:00:28",		//本次种植开始时间
     * "endTime_ss": "28",				//本次结束时间的	秒
     * "endTime_dd": "17",				//本次结束时间的	日
     * "status": 2,				//0：说明开始状态，1：超过6小时状态，2：可以收获状态
     * "farmId": 11,				//农牧场编号
     * "endTime_mm": "00",				//本次结束时间的	分
     * "endTime_yyyy": "2017",			//本次结束时间的	年
     * "endTime_HH": "18",				//本次结束时间的	小时
     * "endTime": "2017-07-17 18:00:28"		//本次结束时间
     */
    @POST("/gpf-web/farm/farmInfo.json")
    Observable<DataBean<BabyGameDataBean>> getBbcGameData(@Query("pid") String pid,
                                                          @Query("paopId") String paopId,
                                                          @Query("open") String open,
                                                          @Query("userId") String userId,
                                                          @Query("farmType") String farmType
    );

    /**
     * 接口为农牧场收获果实接口
     * http://192.168.11.56:8081/gpf-web/farm/harvest.json?pid=3&paopId=3&open=1&userId=ceshi2&farmType=0
     * pid=3&paopId=3&open=1&userId=ceshi2&farmType=0
     * pid,open,paopId：这三个参数同上，无论何时都要传递
     * farmType=0：必填，值==0时，收获农场，值==1时，收获牧场
     * 消息状态为0，代表收获成功.code=2：代表还在生产中，无法收获;	code=1:代表目前没有可操作的农牧场，code=0的时候代表收获成功，同时新的农牧场重新开始
     */
    @POST("/gpf-web/farm/harvest.json")
    Observable<DataBean<HarvestGameBean>> postBbcGameData(@Query("pid") String pid,
                                                          @Query("paopId") String paopId,
                                                          @Query("open") String open,
                                                          @Query("userId") String userId,
                                                          @Query("farmType") String farmType
    );

    /**
     * 接口为获取玩具信息
     * http://192.168.11.56:8081/gpf-web/common/findPageData.json?actionName=edu_center_happyfarm&prizes=1&open=1&paopId=3&pid=3
     * actionName=edu_center_happyfarm&prizes=1&open=1&paopId=3&pid=3
     * pid,open,paopId：这三个参数同上，无论何时都要传递
     * actionName=edu_center_happyfarm：必填，代表当前操作的是哪个页面
     * prizes=1：必填，值为1的时候，代表后去当前产品下所有的奖品，如果非0，将获取不到奖品
     */
    @POST("/gpf-web/common/findPageData.json")
    Observable<DataBean<ToyDataBean>> getToyData(@Query("actionName") String actionName,
                                                 @Query("prizes") String prizes,
                                                 @Query("pid") String pid,
                                                 @Query("paopId") String paopId,
                                                 @Query("open") String open
    );

    /**
     * 接口为兑换玩具信息
     * http://192.168.11.56:8081/gpf-web/common/prizeExchange.action?open=1&paopId=3&pid=3&prizeId=6&userId=1000123456
     * open=1&paopId=3&pid=3&prizeId=6&userId=1000123456
     * pid,open,paopId：这三个参数同上，无论何时都要传递
     * actionName=edu_center_happyfarm：必填，代表当前操作的是哪个页面
     * prizes=1：必填，值为1的时候，代表后去当前产品下所有的奖品，如果非0，将获取不到奖品
     */
    @POST("/gpf-web/common/prizeExchange.action")
    Observable<CodeBean> postConvertToy(@Query("userId") String userId,
                                        @Query("prizeId") String prizeId,
                                        @Query("pid") String pid,
                                        @Query("paopId") String paopId,
                                        @Query("open") String open
    );


    /**
     * 心跳接口
     * http://localhost:8081/gpf-web/log/logActive.json?
     * pid=3&paopId=3&open=1&userId=chentong3&actionName=main
     */
    @POST("/gpf-web/log/logActive.json")
    Observable<Object> postHeartBeat(@Query("pid") String pid,
                                     @Query("paopId") String paopId,
                                     @Query("open") String open,
                                     @Query("userId") String userId
    );

    /**
     * http://192.168.11.56:8081/gpf-web/common/findPageData.json?actionName=edu_play&videosId=1&open=1&paopId=3&pid=1
     */
    @POST("/gpf-web/common/findPageData.json")
    Observable<Object> testRequest(@Query("actionName") String pid,
                                   @Query("videosId") String paopId,
                                   @Query("paopId") String open,
                                   @Query("pid") String userId
    );

    /**
     * http://192.168.11.56:8081/gpf-web/common/findPageData.json?actionName=edu_play&videosId=1&open=1&paopId=3&pid=1
     */
    @POST("test/list")
    Observable<Receive> testRequest1(@Body UserBody body);
}
