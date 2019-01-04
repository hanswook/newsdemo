package com.wallan.multimediacamera.library.api.impl;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.wallan.common.utils.library.ActivityUtils;
import com.wallan.common.utils.library.EmptyUtils;
import com.wallan.multimediacamera.api.CameraApi;
import com.wallan.multimediacamera.library.camera.CameraHomeActivity;


public class CameraApiImpl implements CameraApi {

    private Context mContext;

    public CameraApiImpl(Context context) {
        mContext = EmptyUtils.checkNotNull(context).getApplicationContext();
    }


    @Override
    public void onCreate() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void turnCameraHomeActivity(Activity activity) {
        ActivityUtils.startActivity(activity, new Intent(activity, CameraHomeActivity.class));
    }
}
