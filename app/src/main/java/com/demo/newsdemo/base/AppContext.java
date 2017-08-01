package com.demo.newsdemo.base;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by hans on 2017/7/25 13:32.
 */

public class AppContext extends Application {
    public static AppContext instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        Realm.init(this);
        RealmConfiguration config=new RealmConfiguration.Builder().build();
        Realm.setDefaultConfiguration(config);
    }


    public static AppContext getInstance() {
        return instance;
    }
}
