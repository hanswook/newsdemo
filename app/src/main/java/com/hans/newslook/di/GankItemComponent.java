package com.hans.newslook.di;

import com.hans.newslook.ui.fragment.GankItemFragment;

import dagger.Component;

/**
 * Created by hans on 2017/7/25 14:50.
 */

@Component(modules = GankItemModule.class)
public interface GankItemComponent {
    void inject(GankItemFragment fragment);
}
