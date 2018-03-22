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
    private GankItemContract.Model mModel;

    public GankItemModule(GankItemContract.View mView, GankItemContract.Model mModel) {
        this.mView = mView;
        this.mModel = mModel;
    }

    @Provides
    GankItemContract.View provideView() {
        return mView;
    }

    @Provides
    GankItemContract.Model provideModel() {
        return mModel;
    }


}
