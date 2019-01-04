package com.hans.newslook.base;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.hans.newslook.utils.Constants;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;


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
        CrashReport.initCrashReport(getApplicationContext(), Constants.BUGLY_APP_ID, false);
        instance = this;
        registerWeChat(this);
    }


    public static AppContext getInstance() {
        return instance;
    }

    public void registerWeChat(Context context) {   //向微信注册app
        api = WXAPIFactory.createWXAPI(context, Constants.WX_APP_ID, true);
        api.registerApp(Constants.WX_APP_ID);
    }

    @Override
    public void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

}


