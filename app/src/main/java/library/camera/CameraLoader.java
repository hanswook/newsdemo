package com.wallan.multimediacamera.library.camera;

/**
 * Created by hanxu on 2018/6/1.
 */


import android.app.Activity;
import android.graphics.Point;
import android.hardware.Camera;
import android.util.DisplayMetrics;

import com.wallan.multimediacamera.library.utils.CameraHelper;
import com.wallan.multimediacamera.library.utils.CameraUtils;

import jp.co.cyberagent.android.gpuimage.GPUImage;
import jp.co.cyberagent.android.gpuimage.Rotation;

/**
 * cameraloader
 * 相机控制管理类。
 */
public class CameraLoader {
    private int mCurrentCameraId = 0;
    public Camera mCameraInstance;

    public boolean isFace = false;

    private CameraHelper mCameraHelper;
    private GPUImage gpuImage;
    private DisplayMetrics dm;
    private Activity activity;

    public void onResume() {
        setUpCamera(mCurrentCameraId);
    }

    public void onPause() {
        releaseCamera();
    }

    public int getmCurrentCameraId() {
        return mCurrentCameraId;
    }

    public void initLoader(CameraHelper mCameraHelper, GPUImage gpuImage, DisplayMetrics dm, Activity activity) {
        this.mCameraHelper = mCameraHelper;
        this.gpuImage = gpuImage;
        this.dm = dm;
        this.activity = activity;

    }

    private boolean cameraIsSetting;

    public boolean isCameraIsSetting() {
        return cameraIsSetting;
    }

    public void switchCamera() {

        releaseCamera();
        mCurrentCameraId = (mCurrentCameraId + 1) % mCameraHelper.getNumberOfCameras();
        setUpCamera(mCurrentCameraId);
    }


    public void changeCameraPreviewSize(int width, int height) {
        releaseCamera();
        resetCameraData(mCurrentCameraId, width, height);

    }

    private void setUpCamera(final int id) {
        resetCameraData(id, dm.widthPixels, dm.heightPixels);
    }

    private void resetCameraData(int id, int width, int height) {
        cameraIsSetting = true;
        mCameraInstance = getCameraInstance(id);
        Camera.Parameters parameters = mCameraInstance.getParameters();
        if (parameters.getSupportedFocusModes().contains(
                Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE)) {
            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
        }
        //设置camera previewsize
        Camera.Size size = CameraUtils.getBestCameraResolution(mCameraInstance.getParameters(), new Point(width, height));

        parameters.setPreviewSize(size.width, size.height);
        //设置camera picturesize
        Camera.Size optimumSize = CameraUtils.getOptimumCameraSize(parameters);
        parameters.setPictureSize(optimumSize.width, optimumSize.height);
        mCameraInstance.setParameters(parameters);

        int orientation = mCameraHelper.getCameraDisplayOrientation(
                activity, mCurrentCameraId);
        CameraHelper.CameraInfo2 cameraInfo = new CameraHelper.CameraInfo2();
        mCameraHelper.getCameraInfo(mCurrentCameraId, cameraInfo);
        boolean flipHorizontal = cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT;
        isFace = flipHorizontal;
        gpuImage.deleteImage();
        gpuImage.setUpCamera(mCameraInstance, orientation, flipHorizontal, false);
        cameraIsSetting = false;

    }


    /**
     * A safe way to get an instance of the Camera object.
     */
    private Camera getCameraInstance(final int id) {
        Camera c = null;
        try {
            c = mCameraHelper.openCamera(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return c;
    }

    private void releaseCamera() {
        if (mCameraInstance != null) {
            synchronized (mCameraInstance) {
                if (mCameraInstance != null) {
                    gpuImage.deleteImage();
                    mCameraInstance.setPreviewCallback(null);
                    mCameraInstance.stopPreview();
                    mCameraInstance.release();
                    mCameraInstance = null;
                }
            }
        }
    }


    public void resetRotation(boolean mirrorMode,GPUImage mGPUImage,Activity activity) {
        if (mirrorMode && isFace) {
            int orientation = mCameraHelper.getCameraDisplayOrientation(
                    activity, getmCurrentCameraId());
            Rotation rotation = Rotation.NORMAL;
            switch (orientation) {
                case 90:
                    rotation = Rotation.ROTATION_90;
                    break;
                case 180:
                    rotation = Rotation.ROTATION_180;
                    break;
                case 270:
                    rotation = Rotation.ROTATION_270;
                    break;
            }
            mGPUImage.setRotation(rotation, false, false);
        }

    }
}
