package com.hans.newslook.utils;

import io.reactivex.Flowable;
import io.reactivex.annotations.NonNull;
import io.reactivex.processors.FlowableProcessor;
import io.reactivex.processors.PublishProcessor;

/**
 * description：
 * author： bzx
 * date: 2017/7/26
 * e-mail: seeyou_x@126.com
 */

public class RxFlow {
    private final FlowableProcessor<Object> mBus;

    private RxFlow() {
        mBus = PublishProcessor.create().toSerialized();
    }

    private static class Holder {
        private static RxBus instance = new RxBus();
    }

    public static RxBus getInstance() {
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
