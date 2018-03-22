package com.hans.newslook.di;

import com.hans.newslook.ui.activity.ZhihuDetailActivity;

import dagger.Component;

/**
 * Created by hans on 2017/7/25 17:28.
 */

@Component(modules = ZhihuDetailModule.class)
public interface ZhihuDetailComponent {
    void inject(ZhihuDetailActivity zhihuDetailActivity);
}
