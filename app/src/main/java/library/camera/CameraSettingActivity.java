package com.wallan.multimediacamera.library.camera;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.sevenheaven.iosswitch.ShSwitchView;
import com.wallan.base.app.library.BaseActivity;
import com.wallan.common.utils.library.LogUtils;
import com.wallan.common.utils.library.SharedPreferencesUtils;
import com.wallan.multimediacamera.library.R;
import com.wallan.multimediacamera.library.R2;
import com.wallan.multimediacamera.library.bean.SwitchValue;

import butterknife.BindView;

public class CameraSettingActivity extends BaseActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener, ShSwitchView.OnSwitchStateChangeListener {

    @BindView(R2.id.cb_saveimage_area_switch)
    CheckBox cbSaveimageAreaSwitch;
    @BindView(R2.id.rl_saveimage_quanlity)
    RelativeLayout rlSaveimageQuanlity;
    @BindView(R2.id.ll_saveimage_select_area)
    LinearLayout llSaveimageSelectArea;
    @BindView(R2.id.rb_saveimage_sd)
    RadioButton tvSaveimageSd;
    @BindView(R2.id.rb_saveimage_hd)
    RadioButton tvSaveimageHd;
    @BindView(R2.id.rb_saveimage_original)
    RadioButton tvSaveimageOriginal;
    @BindView(R2.id.ssv_watermark_switch)
    ShSwitchView ssvWatermarkSwitch;
    @BindView(R2.id.ssv_mirrormode_switch)
    ShSwitchView ssvMirrormodeSwitch;
    @BindView(R2.id.ssv_original_pic_switch)
    ShSwitchView ssvOriginalPicSwitch;
    @BindView(R2.id.tv_saveimage_mode)
    TextView tvSaveimageMode;

    private Context mContext;


    public static final String SPKEY_SAVE_IMAGE_QUANLITY = "save_image_quanlity";

    public static final String SPKEY_WATERMARK_SWITCH = "watermark_switch";

    public static final String SPKEY_MIRROR_MODE_SWITCH = "mirror_mode_switch";

    public static final String SPKEY_SAVE_ORIGINAL_SWITCH = "save_original_switch";

    public static final String VALUE_SAVE_IMAGE_QUANLITY_ORIGINAL = "value_save_image_quanlity_original";

    public static final String VALUE_SAVE_IMAGE_QUANLITY_HD = "value_save_image_quanlity_hd";

    public static final String VALUE_SAVE_IMAGE_QUANLITY_SD = "value_save_image_quanlity_sd";

    private boolean cbIsOpen = false;

    private String saveImageQuanlity;

    private String watermarkSwitch = SwitchValue.CLOSE;

    private String mirrorSwitch = SwitchValue.CLOSE;

    private String originalSwitch = SwitchValue.CLOSE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mmcamera_activity_camera_setting);
        initData();
        initSPData();
        initView();
    }

    private void initSPData() {
        saveImageQuanlity = (String) SharedPreferencesUtils.getData(mContext, SPKEY_SAVE_IMAGE_QUANLITY, VALUE_SAVE_IMAGE_QUANLITY_ORIGINAL);
        watermarkSwitch = (String) SharedPreferencesUtils.getData(mContext, SPKEY_WATERMARK_SWITCH, SwitchValue.CLOSE);
        mirrorSwitch = (String) SharedPreferencesUtils.getData(mContext, SPKEY_MIRROR_MODE_SWITCH, SwitchValue.CLOSE);
        originalSwitch = (String) SharedPreferencesUtils.getData(mContext, SPKEY_SAVE_ORIGINAL_SWITCH, SwitchValue.CLOSE);

    }

    private void initData() {
        mContext = this;
    }

    private void initView() {
        setBarTitle("相机设置");

        if (saveImageQuanlity.equalsIgnoreCase(VALUE_SAVE_IMAGE_QUANLITY_ORIGINAL)) {
            tvSaveimageMode.setText("原图");
            tvSaveimageOriginal.setChecked(true);
        } else if (saveImageQuanlity.equalsIgnoreCase(VALUE_SAVE_IMAGE_QUANLITY_HD)) {
            tvSaveimageMode.setText("高清");
            tvSaveimageHd.setChecked(true);
        } else if (saveImageQuanlity.equalsIgnoreCase(VALUE_SAVE_IMAGE_QUANLITY_SD)) {
            tvSaveimageMode.setText("标清");
            tvSaveimageSd.setChecked(true);
        }

        ssvWatermarkSwitch.setOn(watermarkSwitch.equalsIgnoreCase(SwitchValue.OPEN));
        ssvMirrormodeSwitch.setOn(mirrorSwitch.equalsIgnoreCase(SwitchValue.OPEN));
        ssvOriginalPicSwitch.setOn(originalSwitch.equalsIgnoreCase(SwitchValue.OPEN));

        cbSaveimageAreaSwitch.setChecked(cbIsOpen);
        rlSaveimageQuanlity.setOnClickListener(this);
        tvSaveimageSd.setOnCheckedChangeListener(this);
        tvSaveimageHd.setOnCheckedChangeListener(this);
        tvSaveimageOriginal.setOnCheckedChangeListener(this);

//        tbWatermarkSwitch.setOnCheckedChangeListener(this);
//        tbMirrormodeSwitch.setOnCheckedChangeListener(this);
//        tbOriginalPicSwitch.setOnCheckedChangeListener(this);
        ssvWatermarkSwitch.setOnSwitchStateChangeListener(new ShSwitchView.OnSwitchStateChangeListener() {
            @Override
            public void onSwitchStateChange(boolean isOn) {
                SharedPreferencesUtils.putData(mContext, SPKEY_WATERMARK_SWITCH, isOn ? SwitchValue.OPEN : SwitchValue.CLOSE);
            }
        });
        ssvMirrormodeSwitch.setOnSwitchStateChangeListener(new ShSwitchView.OnSwitchStateChangeListener() {
            @Override
            public void onSwitchStateChange(boolean isOn) {
                SharedPreferencesUtils.putData(mContext, SPKEY_MIRROR_MODE_SWITCH, isOn ? SwitchValue.OPEN : SwitchValue.CLOSE);
            }
        });
        ssvOriginalPicSwitch.setOnSwitchStateChangeListener(new ShSwitchView.OnSwitchStateChangeListener() {
            @Override
            public void onSwitchStateChange(boolean isOn) {
                SharedPreferencesUtils.putData(mContext, SPKEY_SAVE_ORIGINAL_SWITCH, isOn ? SwitchValue.OPEN : SwitchValue.CLOSE);
            }
        });

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.rl_saveimage_quanlity) {
            cbIsOpen = !cbIsOpen;
            cbSaveimageAreaSwitch.setChecked(cbIsOpen);
            if (cbIsOpen) {
                llSaveimageSelectArea.setVisibility(View.VISIBLE);
            } else {
                llSaveimageSelectArea.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        int btnId = buttonView.getId();
        if (btnId == R.id.rb_saveimage_original && isChecked) {
            SharedPreferencesUtils.putData(mContext, SPKEY_SAVE_IMAGE_QUANLITY, VALUE_SAVE_IMAGE_QUANLITY_ORIGINAL);
            tvSaveimageMode.setText("原图");
        } else if (btnId == R.id.rb_saveimage_hd && isChecked) {
            SharedPreferencesUtils.putData(mContext, SPKEY_SAVE_IMAGE_QUANLITY, VALUE_SAVE_IMAGE_QUANLITY_HD);
            tvSaveimageMode.setText("高清");
        } else if (btnId == R.id.rb_saveimage_sd && isChecked) {
            SharedPreferencesUtils.putData(mContext, SPKEY_SAVE_IMAGE_QUANLITY, VALUE_SAVE_IMAGE_QUANLITY_SD);
            tvSaveimageMode.setText("标清");
        }
    }

    @Override
    public void onSwitchStateChange(boolean isOn) {

    }
}
