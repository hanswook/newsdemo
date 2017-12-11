package com.demo.newsdemo.base;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.demo.newsdemo.BuildConfig;
import com.tencent.bugly.crashreport.CrashReport;

import java.util.Arrays;
import java.util.List;


/**
 * Created by hans on 2017/7/25 13:32.
 */

public class AppContext extends Application  {
    public static AppContext instance;

    @Override
    public void onCreate() {
        super.onCreate();
        CrashReport.initCrashReport(getApplicationContext(), "0f2174e3f0", false);
        instance = this;
    }


    public static AppContext getInstance() {
        return instance;
    }
}


