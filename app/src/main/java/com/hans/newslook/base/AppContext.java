package com.hans.newslook.base;

import android.app.Application;
import android.content.Context;

import com.hans.newslook.utils.Constants;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;


/**
 * Created by hans
 * date: 2017/7/25 13:32 .
 * e-mail: hxxx1992@163.com
 */

public class AppContext extends Application {
    public static AppContext instance;


    public IWXAPI api;
    private boolean aaa=false;


    @Override
    public void onCreate() {
        super.onCreate();
        /*if (aaa) {

            if (LeakCanary.isInAnalyzerProcess(this)) {
                return;
            }
            LeakCanary.install(this);
        }*/

//        GrowingIO.startWithConfiguration(this, new Configuration()
//                .trackAllFragments()
//                .setChannel("XXX应用商店")
//        );


        initUmeng();

        CrashReport.initCrashReport(getApplicationContext(), Constants.BUGLY_APP_ID, false);
        instance = this;
        registerWeChat(this);
    }

    private void initUmeng() {
        /**
         * 初始化common库
         * 参数1:上下文，不能为空
         * 参数2:【友盟+】 AppKey
         * 参数3:【友盟+】 Channel
         * 参数4:设备类型，UMConfigure.DEVICE_TYPE_PHONE为手机、UMConfigure.DEVICE_TYPE_BOX为盒子，默认为手机
         * 参数5:Push推送业务的secret
         */
        UMConfigure.init(this, "5c3fe727f1f55626c700107d", "Umeng", UMConfigure.DEVICE_TYPE_PHONE, "");
//        MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.AUTO);
        MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.LEGACY_MANUAL);
        UMConfigure.setLogEnabled(false);
        // 选用AUTO页面采集模式
        // 支持在子进程中统计自定义事件
        UMConfigure.setProcessEvent(true);
    }



    public static AppContext getInstance() {
        return instance;
    }

    public void registerWeChat(Context context) {   //向微信注册app
        api = WXAPIFactory.createWXAPI(context, Constants.WX_APP_ID, true);
        api.registerApp(Constants.WX_APP_ID);
    }
}


