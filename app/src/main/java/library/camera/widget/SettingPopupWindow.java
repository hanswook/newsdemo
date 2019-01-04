package com.wallan.multimediacamera.library.camera.widget;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.hardware.Camera;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.wallan.baseui.util.DpdipUtil;
import com.wallan.common.utils.library.ActivityUtils;
import com.wallan.common.utils.library.EmptyUtils;
import com.wallan.common.utils.library.ToastUtils;
import com.wallan.multimediacamera.library.R;
import com.wallan.multimediacamera.library.camera.CameraLoader;
import com.wallan.multimediacamera.library.camera.CameraSettingActivity;

/**
 * Created by hanxu on 2018/6/6.
 */

public class SettingPopupWindow implements View.OnClickListener {

    private Activity activity;

    private DisplayMetrics dm;

    private CameraLoader mCamera;


    //触屏拍摄。
    private boolean isTapScreenTakePhoto = false;

    public SettingPopupWindow(Activity activity, DisplayMetrics dm, CameraLoader mCamera) {
        this.activity = activity;
        this.dm = dm;
        this.mCamera = mCamera;
    }

    /*** popupwindow photosetting 相关 ***/
    private PopupWindow popupSetting;

    private TextView tvCameraNightmode;
    private TextView tvCameraTapShot;
    private TextView tvCameraDelayShot;
    private TextView tvCameraCameraFlash;
    private TextView tvCameraCameraSetting;


    public static final int CAMERA_DELAY_MODE_NO = 1001;
    public static final int CAMERA_DELAY_MODE_THREE = 1002;
    public static final int CAMERA_DELAY_MODE_SIX = 1003;
    private int delayShotMode = CAMERA_DELAY_MODE_NO;

    public static final int CAMERA_FLASH_MODE_NO = 2001;
    public static final int CAMERA_FLASH_MODE_ON = 2002;
    public static final int CAMERA_FLASH_MODE_AUTO = 2003;
    private int cameraFlashMode = CAMERA_FLASH_MODE_NO;


    /**
     * 显示 相机参数设置 popupwindow
     */
    public void showPopupSetting(View view) {
        if (EmptyUtils.isEmpty(popupSetting)) {
            initPopupSetting();
        }
        if (popupSetting.isShowing()) {
            popupSetting.dismiss();
        } else {
            popupSetting.showAsDropDown(view, -(screenWidth / 20), DpdipUtil.dip2px(activity, 11));
            popupSetting.setFocusable(true);
        }
    }

    int screenWidth;

    /**
     * 初始化 参数设置 弹窗
     */
    private void initPopupSetting() {
        View popView = LayoutInflater.from(activity).inflate(R.layout.mmcamera_popupwindow_camera_setting_set, null, false);
        initPopSettingView(popView);
        screenWidth = dm.widthPixels;
        int height = DpdipUtil.dip2px(activity, 81);
        popupSetting = new PopupWindow(popView, -1, height);

        LinearLayout llCameraSettingSet = popView.findViewById(R.id.ll_camera_setting_set);
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) llCameraSettingSet.getLayoutParams();
        int marginWidth = screenWidth / 20;
        marginLayoutParams.setMargins(marginWidth, 0, marginWidth, 0);
        llCameraSettingSet.setLayoutParams(marginLayoutParams);

        popupSetting.setOutsideTouchable(true);
    }

    /**
     * 初始化 参数设置 弹窗 view
     */
    private void initPopSettingView(View popSettingView) {
        tvCameraNightmode = popSettingView.findViewById(R.id.tv_camera_nightmode);
        tvCameraTapShot = popSettingView.findViewById(R.id.tv_camera_tap_shot);
        tvCameraDelayShot = popSettingView.findViewById(R.id.tv_camera_delay_shot);
        tvCameraCameraFlash = popSettingView.findViewById(R.id.tv_camera_camera_flash);
        tvCameraCameraSetting = popSettingView.findViewById(R.id.tv_camera_camera_setting);

        tvCameraNightmode.setOnClickListener(this);
        tvCameraTapShot.setOnClickListener(this);
        tvCameraDelayShot.setOnClickListener(this);
        tvCameraCameraFlash.setOnClickListener(this);
        tvCameraCameraSetting.setOnClickListener(this);

        tvCameraCameraSetting.setSelected(true);
    }


    /**
     * 跳转camera setting 界面
     */
    private void showCameraSetting() {
        ToastUtils.showToast(activity, "showCameraSetting");
        ActivityUtils.startActivity(activity, new Intent(activity, CameraSettingActivity.class));
    }


    /**
     * 切换延迟拍摄模式
     */
    private void switchDelayShot() {
        Drawable drawable = null;
        switch (delayShotMode) {
            case CAMERA_DELAY_MODE_NO:
                delayShotMode = CAMERA_DELAY_MODE_THREE;
                drawable = activity.getResources().getDrawable(R.drawable.camera_delay_three);
                tvCameraDelayShot.setSelected(true);
                break;
            case CAMERA_DELAY_MODE_THREE:
                delayShotMode = CAMERA_DELAY_MODE_SIX;
                drawable = activity.getResources().getDrawable(R.drawable.camera_delay_six);
                tvCameraDelayShot.setSelected(true);
                break;
            case CAMERA_DELAY_MODE_SIX:
                delayShotMode = CAMERA_DELAY_MODE_NO;
                drawable = activity.getResources().getDrawable(R.drawable.camera_delay_nor);
                tvCameraDelayShot.setSelected(false);
                break;
        }
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), (int) (drawable.getMinimumHeight()));
        tvCameraDelayShot.setCompoundDrawables(null, drawable, null, null);
    }

    /**
     * 切换相机触屏拍摄 模式
     */
    private void switchCameraTapShot() {
        isTapScreenTakePhoto = !isTapScreenTakePhoto;
        tvCameraTapShot.setSelected(isTapScreenTakePhoto);
    }


    /**
     * 切换相机夜拍模式
     */
    private void switchCameraNightMode() {
        tvCameraNightmode.setSelected(!tvCameraNightmode.isSelected());
        if (tvCameraNightmode.isSelected()) {
            Camera.Parameters parameters = mCamera.mCameraInstance.getParameters();
            if (parameters.getSupportedSceneModes().contains(Camera.Parameters.SCENE_MODE_NIGHT)) {
                parameters.setSceneMode(Camera.Parameters.SCENE_MODE_NIGHT);
            } else {
                ToastUtils.showToast(activity, "相机不支持夜拍模式");
                tvCameraNightmode.setSelected(false);
            }
            for (String s : parameters.getSupportedSceneModes()) {
                Log.e("sence", "s:" + s);
            }
            mCamera.mCameraInstance.setParameters(parameters);
        } else {
            Camera.Parameters parameters = mCamera.mCameraInstance.getParameters();
            if (parameters.getSupportedSceneModes().contains(Camera.Parameters.SCENE_MODE_ACTION)) {
                parameters.setSceneMode(Camera.Parameters.SCENE_MODE_ACTION);
            } else {
                parameters.setSceneMode(Camera.Parameters.SCENE_MODE_AUTO);
            }

            mCamera.mCameraInstance.setParameters(parameters);
        }

    }


    /**
     * 切换相机闪光灯 模式
     */
    private void switchCameraFlash() {
        Camera.Parameters parameters = mCamera.mCameraInstance.getParameters();
        Drawable drawable = null;
        switch (cameraFlashMode) {
            case CAMERA_FLASH_MODE_NO:
                cameraFlashMode = CAMERA_FLASH_MODE_ON;
                drawable = activity.getResources().getDrawable(R.drawable.camera_flash_on);
                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                tvCameraCameraFlash.setSelected(true);
                break;
            case CAMERA_FLASH_MODE_ON:
                cameraFlashMode = CAMERA_FLASH_MODE_AUTO;
                drawable = activity.getResources().getDrawable(R.drawable.camera_flash_auto);
                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_AUTO);
                tvCameraCameraFlash.setSelected(true);
                break;
            case CAMERA_FLASH_MODE_AUTO:
                cameraFlashMode = CAMERA_FLASH_MODE_NO;
                drawable = activity.getResources().getDrawable(R.drawable.camera_flash_nor);
                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                tvCameraCameraFlash.setSelected(false);
                break;
        }
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), (int) (drawable.getMinimumHeight()));
        tvCameraCameraFlash.setCompoundDrawables(null, drawable, null, null);
        mCamera.mCameraInstance.setParameters(parameters);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.tv_camera_nightmode) {        //选择夜拍模式
            switchCameraNightMode();
        } else if (id == R.id.tv_camera_tap_shot) {        //选择触摸拍摄。
            switchCameraTapShot();
        } else if (id == R.id.tv_camera_delay_shot) {        //选择延迟拍摄。
            switchDelayShot();
        } else if (id == R.id.tv_camera_camera_flash) {        //选择闪光灯。
            switchCameraFlash();
        } else if (id == R.id.tv_camera_camera_setting) {        //选择setting。
            showCameraSetting();
        }
    }

    public boolean isTapScreenTakePhoto() {
        return isTapScreenTakePhoto;
    }

    public int getDelayShotMode() {
        return delayShotMode;
    }

    public int getCameraFlashMode() {
        return cameraFlashMode;
    }

    public PopupWindow getPopupSetting() {
        return popupSetting;
    }

    public void hideSelfIfShowing() {
        if (popupSetting != null && popupSetting.isShowing()) {
            popupSetting.dismiss();
        }
    }
}
