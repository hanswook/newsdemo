package com.hans.newslook.di;

import com.hans.newslook.ui.activity.SplashActivity;

import dagger.Component;

/**
 * Created by hans on 2017/8/9 16:03.
 */

@Component(modules = SplashModule.class)
public interface SplashComponent {
    void inject(SplashActivity splashActivity);

}
