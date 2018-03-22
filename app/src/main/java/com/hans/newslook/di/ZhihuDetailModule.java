package com.hans.newslook.di;

import com.hans.newslook.contract.ZhihuDetailContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by hans on 2017/7/25 17:27.
 */
@Module
public class ZhihuDetailModule {

    private ZhihuDetailContract.View view;
    private ZhihuDetailContract.Model model;

    public ZhihuDetailModule(ZhihuDetailContract.View view, ZhihuDetailContract.Model model) {
        this.view = view;
        this.model = model;
    }
    @Provides
    ZhihuDetailContract.View provideView() {
        return view;
    }

    @Provides
    ZhihuDetailContract.Model provideModel() {
        return model;
    }
}
