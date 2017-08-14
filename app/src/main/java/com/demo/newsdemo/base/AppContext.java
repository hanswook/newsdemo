package com.demo.newsdemo.base;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.demo.newsdemo.BuildConfig;

import java.util.Arrays;
import java.util.List;


/**
 * Created by hans on 2017/7/25 13:32.
 */

public class AppContext extends Application  {
    public static AppContext instance;
/*
implements ReactApplication
    private final ReactNativeHost mReactNativeHost = new ReactNativeHost(this) {
        @Override
        public boolean getUseDeveloperSupport() {
            return BuildConfig.DEBUG;
        }

        @Override
        protected List<ReactPackage> getPackages() {
            return Arrays.<ReactPackage>asList(
                    new MainReactPackage()
            );
        }
    };

    @Override
    public ReactNativeHost getReactNativeHost() {
        return mReactNativeHost;
    }
*/

    @Override
    public void onCreate() {
        super.onCreate();
//        SoLoader.init(this, /* native exopackage */ false);
        instance = this;
    }


    public static AppContext getInstance() {
        return instance;
    }
}


