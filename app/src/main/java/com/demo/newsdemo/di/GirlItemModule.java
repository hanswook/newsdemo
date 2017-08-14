package com.demo.newsdemo.di;

import com.demo.newsdemo.contract.GirlItemContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by hans on 2017/7/25 14:50.
 */
@Module
public class GirlItemModule {

    private GirlItemContract.View mView;
    private GirlItemContract.Model mModel;

    public GirlItemModule(GirlItemContract.View mView, GirlItemContract.Model mModel) {
        this.mView = mView;
        this.mModel = mModel;
    }

    @Provides
    GirlItemContract.View provideView() {
        return mView;
    }

    @Provides
    GirlItemContract.Model provideModel() {
        return mModel;
    }


}
