package com.wallan.multimediacamera.library.api;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.wallan.api.inject.library.ApiInjectBus;
import com.wallan.multimediacamera.api.CameraApi;
import com.wallan.multimediacamera.library.api.impl.CameraApiImpl;

/**
 * Created by tubingbing on 2017/9/29.
 */

public class ApiRegisterReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        ApiInjectBus.getInstance(context).register(CameraApi.class, new CameraApiImpl(context));
    }
}
