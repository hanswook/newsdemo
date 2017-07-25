package com.demo.newsdemo.di;

import com.demo.newsdemo.contract.HomeContract;
import com.demo.newsdemo.model.HomeModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by hans on 2017/7/25 14:50.
 */
@Module
public class HomeModule {

    private HomeContract.View mView;
    private HomeContract.Model mModel;

    public HomeModule(HomeContract.View mView,HomeContract.Model mModel) {
        this.mView = mView;
        this.mModel = mModel;
    }

    @Provides
    HomeContract.View provideView() {
        return mView;
    }

    @Provides
    HomeContract.Model provideModel() {
        return mModel;
    }


}
