package com.wallan.multimediacamera.library.camera.widget;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.HorizontalScrollView;
import android.widget.PopupWindow;
import android.widget.RadioButton;

import com.wallan.baseui.util.DpdipUtil;
import com.wallan.common.utils.library.EmptyUtils;
import com.wallan.multimediacamera.library.R;

import static com.wallan.multimediacamera.library.camera.CameraHomeFragment.CAMERA_MODE_RECORD_VIDEO;

/**
 * Created by hanxu on 2018/6/6.
 */

public class SizeModePopupWindow implements CompoundButton.OnCheckedChangeListener {


    private Activity activity;

    private DisplayMetrics dm;

    public SizeModePopupWindow(Activity activity, DisplayMetrics dm) {
        this.activity = activity;
        this.dm = dm;
    }

    private int screenWidth;

    /*** popupwindow photosizeMode 相关 ***/
    private RadioButton rbCameraTwobisW;
    private RadioButton rbCameraTwobisH;
    private RadioButton rbCameraFourbis;
    private RadioButton rbCameraNinebis;
    private RadioButton rbCameraFullscreen;
    private RadioButton rbCameraThreeTwo;
    private RadioButton rbCameraFourThree;
    private RadioButton rbCameraOneOne;
    private View vGapLine;

    private PopupWindow popupSizeMode;


    /**
     * 初始化 模式设置 弹窗
     */
    private void initPopupSizeMode() {
        @SuppressLint("InflateParams")
        View popView = LayoutInflater.from(activity).inflate(R.layout.mmcamera_popupwindow_size_mode_set, null, false);
        initPopSizeModeView(popView);
        screenWidth = dm.widthPixels;
        int height = DpdipUtil.dip2px(activity, 73);
        popupSizeMode = new PopupWindow(popView, -1, height);
        HorizontalScrollView hsv = popView.findViewById(R.id.hsv_popup_setting);
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) hsv.getLayoutParams();
        int marginWidth = screenWidth / 10;
        marginLayoutParams.setMargins(marginWidth, 0, marginWidth, 0);
        hsv.setLayoutParams(marginLayoutParams);
        popupSizeMode.setOutsideTouchable(true);
    }


    /**
     * 初始化 模式设置 弹窗 view
     */
    private void initPopSizeModeView(View popSizeModeView) {

        rbCameraTwobisW = popSizeModeView.findViewById(R.id.rb_camera_twobis_w);
        rbCameraTwobisH = popSizeModeView.findViewById(R.id.rb_camera_twobis_h);
        rbCameraFourbis = popSizeModeView.findViewById(R.id.rb_camera_fourbis);
        rbCameraNinebis = popSizeModeView.findViewById(R.id.rb_camera_ninebis);
        vGapLine = popSizeModeView.findViewById(R.id.v_gap_line);

        rbCameraFullscreen = popSizeModeView.findViewById(R.id.rb_camera_fullscreen);
        rbCameraThreeTwo = popSizeModeView.findViewById(R.id.rb_camera_three_two);
        rbCameraFourThree = popSizeModeView.findViewById(R.id.rb_camera_four_three);
        rbCameraOneOne = popSizeModeView.findViewById(R.id.rb_camera_one_one);
        rbCameraFullscreen.setChecked(true);

        rbCameraTwobisW.setOnCheckedChangeListener(this);
        rbCameraTwobisH.setOnCheckedChangeListener(this);
        rbCameraFourbis.setOnCheckedChangeListener(this);
        rbCameraNinebis.setOnCheckedChangeListener(this);
        rbCameraFullscreen.setOnCheckedChangeListener(this);
        rbCameraThreeTwo.setOnCheckedChangeListener(this);
        rbCameraFourThree.setOnCheckedChangeListener(this);
        rbCameraOneOne.setOnCheckedChangeListener(this);
    }

    private void hidePopupSizeModeButtons() {
        rbCameraTwobisW.setVisibility(View.GONE);
        rbCameraTwobisH.setVisibility(View.GONE);
        rbCameraFourbis.setVisibility(View.GONE);
        rbCameraNinebis.setVisibility(View.GONE);
        vGapLine.setVisibility(View.GONE);
    }

    private void showPopupSizeModeButtons() {
        rbCameraTwobisW.setVisibility(View.VISIBLE);
        rbCameraTwobisH.setVisibility(View.VISIBLE);
        rbCameraFourbis.setVisibility(View.VISIBLE);
        rbCameraNinebis.setVisibility(View.VISIBLE);
        vGapLine.setVisibility(View.VISIBLE);
    }

    /**
     * 显示 相机模式设置 popupwindow
     */
    public void showPopupSizeMode(View view, int currentMode) {
        if (EmptyUtils.isEmpty(popupSizeMode)) {
            initPopupSizeMode();
        }
        if (popupSizeMode.isShowing()) {
            popupSizeMode.dismiss();
        } else {
            popupSizeMode.showAsDropDown(view, -(screenWidth / 10), DpdipUtil.dip2px(activity, 11));
            popupSizeMode.setFocusable(true);
            if (currentMode == CAMERA_MODE_RECORD_VIDEO) {
                hidePopupSizeModeButtons();
            } else {
                showPopupSizeModeButtons();
            }
        }
    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (onChcekedListener != null) {
            onChcekedListener.onCheckedChanged(buttonView, isChecked);
        }
    }


    public void hideSelfIfShowing() {
        if (popupSizeMode != null && popupSizeMode.isShowing()) {
            popupSizeMode.dismiss();
        }
    }

    public void performClickOneOne() {
        rbCameraOneOne.performClick();
    }


    public interface OnChcekedListener {
        void onCheckedChanged(CompoundButton buttonView, boolean isChecked);
    }

    private OnChcekedListener onChcekedListener;

    public OnChcekedListener getOnChcekedListener() {
        return onChcekedListener;
    }

    public void setOnChcekedListener(OnChcekedListener onChcekedListener) {
        this.onChcekedListener = onChcekedListener;
    }
}
