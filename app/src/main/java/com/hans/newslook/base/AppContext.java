package com.hans.newslook.base;

import android.app.Application;

import com.tencent.bugly.crashreport.CrashReport;


/**
 * Created by hans on 2017/7/25 13:32.
 */

public class AppContext extends Application  {
    public static AppContext instance;

    @Override
    public void onCreate() {
        super.onCreate();
        CrashReport.initCrashReport(getApplicationContext(), "0cf59e02b7", false);
        instance = this;
    }


    public static AppContext getInstance() {
        return instance;
    }
}


