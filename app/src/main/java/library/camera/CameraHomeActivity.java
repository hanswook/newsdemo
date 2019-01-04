package com.wallan.multimediacamera.library.camera;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.wallan.api.inject.library.ApiInjectBus;
import com.wallan.base.app.library.BaseActivity;
import com.wallan.common.utils.library.LogUtils;
import com.wallan.common.utils.library.ToastUtils;
import com.wallan.multimediacamera.library.R;

import io.reactivex.functions.Consumer;


public class CameraHomeActivity extends BaseActivity {
    private Activity activity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);// 隐藏标题

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,

                WindowManager.LayoutParams.FLAG_FULLSCREEN);// 设置全屏
        setContentView(R.layout.mmcamera_activity_camera_home);
        activity = this;
        new RxPermissions(activity).request(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {

                        } else {
                            Toast.makeText(activity,
                                    activity.getString(R.string.mmcamera_need_camera), Toast.LENGTH_LONG).show();
                            activity.finish();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                    }
                });

        getSupportFragmentManager().beginTransaction().add(R.id.rl_container, new CameraHomeFragment()).commit();


    }

    public static void toCamera(final Activity activity, final int carmerCode) {

        new RxPermissions(activity).request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO, Manifest.permission.CAMERA)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean granted) throws Exception {
                        if (granted) {
                            Intent intent = new Intent(activity, CameraHomeActivity.class);
                            activity.startActivityForResult(intent, carmerCode);
                        } else {
                            ToastUtils.showToast(activity, "请到[设置-权限管理]中开启相应的权限，才能正常使用此项功能");
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        LogUtils.d(throwable);
                    }
                });
    }


}
