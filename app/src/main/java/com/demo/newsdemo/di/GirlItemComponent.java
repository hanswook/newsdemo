package com.demo.newsdemo.di;

import com.demo.newsdemo.ui.fragment.GirlItemFragment;

import dagger.Component;

/**
 * Created by hans on 2017/7/25 14:50.
 */

@Component(modules = GirlItemModule.class)
public interface GirlItemComponent {
    void inject(GirlItemFragment fragment);
}
