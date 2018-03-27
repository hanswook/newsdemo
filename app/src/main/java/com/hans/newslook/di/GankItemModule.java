package com.hans.newslook.di;

import com.hans.newslook.contract.GankItemContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by hans on 2017/7/25 14:50.
 */
@Module
public class GankItemModule {

    private GankItemContract.View mView;

    public GankItemModule(GankItemContract.View mView) {
        this.mView = mView;
    }

    @Provides
    GankItemContract.View provideView() {
        return mView;
    }



}
