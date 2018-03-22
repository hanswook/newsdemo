package com.hans.newslook.di;

import com.hans.newslook.ui.activity.HomeActivity;

import dagger.Component;

/**
 * Created by hans on 2017/7/25 14:50.
 */

@Component(modules = HomeModule.class)
public interface HomeComponent {
    void inject(HomeActivity activity);
}
