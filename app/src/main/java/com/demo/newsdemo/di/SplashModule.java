package com.demo.newsdemo.di;

import com.demo.newsdemo.contract.SplashContract;
import com.demo.newsdemo.contract.ZhihuDetailContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by hans on 2017/8/9 16:03.
 */

@Module
public class SplashModule {
    private SplashContract.View view;
    private SplashContract.Model model;

    public SplashModule(SplashContract.View view, SplashContract.Model model) {
        this.view = view;
        this.model = model;
    }
    @Provides
    SplashContract.View provideView() {
        return view;
    }

    @Provides
    SplashContract.Model provideModel() {
        return model;
    }

}
