package com.hans.newslook.utils;

import com.jakewharton.rxrelay2.PublishRelay;
import com.jakewharton.rxrelay2.Relay;

import io.reactivex.Observable;

/**
 * Created by hans
 * date: 2017/8/9 09:13 17:15.
 * e-mail: hxxx1992@163.com
 */

public class RxBus {
    private static volatile RxBus instance;
    private final Relay<Object> mBus;

    public RxBus() {
        this.mBus = PublishRelay.create().toSerialized();
    }

    public static RxBus getInstance() {
        if (instance == null) {

            synchronized (RxBus.class) {
                if (instance == null) {
                    instance=Holder.BUS;
                }
            }
        }
        return instance;
    }

    public void post(Object obj){
        mBus.accept(obj);
    }

    public <T> Observable<T> tObservable(Class<T> tClass){
        return mBus.ofType(tClass);
    }

    public Observable<Object> toObserverble(){
        return mBus;
    }

    public boolean hasObservers(){
        return mBus.hasObservers();
    }

    public static class Holder{
        private static final RxBus BUS=new RxBus();
    }


}
