package com.hans.newslook.utils.rxutils;

import io.reactivex.Flowable;
import io.reactivex.annotations.NonNull;
import io.reactivex.processors.FlowableProcessor;
import io.reactivex.processors.PublishProcessor;

/**
 * Created by hans
 * date: 2017/8/9 09:13 17:15.
 * e-mail: hxxx1992@163.com
 */


public class RxFlow {
    private final FlowableProcessor<Object> mBus;

    private RxFlow() {
        mBus = PublishProcessor.create().toSerialized();
    }

    private static class Holder {
        private static RxFlow instance = new RxFlow();
    }

    public static RxFlow getInstance() {
        return Holder.instance;
    }

    public void post(@NonNull Object obj) {
        mBus.onNext(obj);
    }


    public <T> Flowable<T> register(Class<T> clz) {
        return mBus.ofType(clz);
    }

    public void unregisterAll() {
        //解除注册
        mBus.onComplete();

    }

    public boolean hasSubscribers() {
        return mBus.hasSubscribers();
    }
}
