package com.wallan.multimediacamera.library.camera;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wallan.base.app.library.BaseFragment;
import com.wallan.base.app.library.BasePresenter;
import com.wallan.baseui.util.DpdipUtil;
import com.wallan.common.utils.library.EmptyUtils;
import com.wallan.common.utils.library.LogUtils;
import com.wallan.common.utils.library.SchedulerProvider;
import com.wallan.common.utils.library.SharedPreferencesUtils;
import com.wallan.common.utils.library.ToastUtils;
import com.wallan.multimediacamera.library.R;
import com.wallan.multimediacamera.library.R2;
import com.wallan.multimediacamera.library.bean.SwitchValue;
import com.wallan.multimediacamera.library.camera.bean.MediaType;
import com.wallan.multimediacamera.library.camera.contract.CameraVideoContract;
import com.wallan.multimediacamera.library.camera.contract.CameraPhotoContract;
import com.wallan.multimediacamera.library.camera.presenter.CameraVideoPresenter;
import com.wallan.multimediacamera.library.camera.presenter.CameraPhotoPresenter;
import com.wallan.multimediacamera.library.camera.widget.SizeModePopupWindow;
import com.wallan.multimediacamera.library.utils.BitmapUtils;
import com.wallan.multimediacamera.library.utils.CameraHelper;
import com.wallan.multimediacamera.library.utils.CameraTouchHelper;
import com.wallan.multimediacamera.library.utils.CameraUtils;
import com.wallan.multimediacamera.library.camera.widget.SettingPopupWindow;
import com.wallan.multimediacamera.library.utils.ImageSaveUtils;
import com.wallan.multimediacamera.library.widget.BottomTabView;
import com.wallan.multimediacamera.library.widget.MultiPhotoView;
import com.wallan.multimediacamera.library.widget.RecordButtonView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import jp.co.cyberagent.android.gpuimage.GPUImage;
import jp.co.cyberagent.android.gpuimage.GPUImageFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageFilterGroup;
import jp.co.cyberagent.android.gpuimage.GPUImageGrayscaleFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageMovieWriter;

import static com.wallan.multimediacamera.library.camera.CameraSettingActivity.VALUE_SAVE_IMAGE_QUANLITY_ORIGINAL;
import static com.wallan.multimediacamera.library.camera.presenter.CameraVideoPresenter.RECORD_STATUS_BEFORE_RECORD;
import static com.wallan.multimediacamera.library.camera.presenter.CameraVideoPresenter.RECORD_STATUS_GROUP_RECORDING;
import static com.wallan.multimediacamera.library.camera.presenter.CameraVideoPresenter.RECORD_STATUS_RECORDING;
import static com.wallan.multimediacamera.library.camera.presenter.CameraVideoPresenter.RECORD_STATUS_RECORD_TIME_ENOUGH;
import static com.wallan.multimediacamera.library.camera.widget.SettingPopupWindow.CAMERA_DELAY_MODE_NO;
import static com.wallan.multimediacamera.library.camera.widget.SettingPopupWindow.CAMERA_DELAY_MODE_THREE;


/**
 * A simple {@link Fragment} subclass.
 */
public class CameraHomeFragment extends BaseFragment implements View.OnClickListener, View.OnTouchListener, CameraVideoContract.View, CameraPhotoContract.View {

    private final String IMAGE_FLODER_NAME = "wander";

    public static final String RESULT_TYPE = "media_type";
    public static final String RESULT_PATH = "path";


    public static final int FULL_SCREEN_MODE = 101;
    public static final int FOUR_TO_THREE_SCREEN_MODE = 102;
    public static final int THREE_TO_TWO_SCREEN_MODE = 103;
    public static final int ONE_TO_ONE_SCREEN_MODE = 104;

    public static final int MULTISHOT_MODE_NO = 200;
    public static final int MULTISHOT_MODE_TWO_H = 201;
    public static final int MULTISHOT_MODE_TWO_W = 202;
    public static final int MULTISHOT_MODE_FOUR = 203;
    public static final int MULTISHOT_MODE_NINE = 204;

    /**
     * 当前模式，拍照or录制视频
     */
    public static final int CAMERA_MODE_TAKE_PHOTO = 100001;
    public static final int CAMERA_MODE_RECORD_VIDEO = 100002;
    private int currentMode = CAMERA_MODE_TAKE_PHOTO;


    @BindView(R2.id.gl_surface)
    GLSurfaceView glSurfaceView;
    @BindView(R2.id.ll_camera_setup)
    LinearLayout llCameraSetup;
    @BindView(R2.id.iv_camera_pic)
    ImageView ivCameraPic;
    @BindView(R2.id.iv_camera_size_mode)
    ImageView ivCameraSize;
    @BindView(R2.id.iv_camera_set)
    ImageView ivCameraSet;
    @BindView(R2.id.iv_camera_turn)
    ImageView ivCameraTurn;
    @BindView(R2.id.iv_camera_close)
    ImageView ivCameraClose;
    @BindView(R2.id.rb_camera_sti)
    RadioButton rbCameraSti;
    @BindView(R2.id.rb_camera_filter)
    RadioButton rbCameraFilter;
    @BindView(R2.id.iv_camera_photo)
    ImageView ivCameraPhoto;
    @BindView(R2.id.rb_camera_beauty)
    RadioButton rbCameraBeauty;
    @BindView(R2.id.rb_camera_dre)
    RadioButton rbCameraDre;
    @BindView(R2.id.ll_camera_video_save_confirm)
    LinearLayout llCameraVideoSaveConfirm;
    @BindView(R2.id.ll_camera_right_original)
    LinearLayout llCameraRightOriginal;
    @BindView(R2.id.tv_camera_video_cancel)
    TextView tvCameraVideoCancel;
    @BindView(R2.id.tv_camera_video_ok)
    TextView tvCameraVideoOk;
    @BindView(R2.id.tv_take_photo_timer)
    TextView tvTakePhotoTimer;
    @BindView(R2.id.rl_camera_setting)
    RelativeLayout rlCameraSetting;
    @BindView(R2.id.iv_preview)
    ImageView ivPreview;
    @BindView(R2.id.iv_camera_save)
    ImageView ivCameraSave;
    @BindView(R2.id.iv_camera_edit)
    ImageView ivCameraEdit;
    @BindView(R2.id.tv_save_share)
    TextView tvSaveShare;
    @BindView(R2.id.tv_save_cancel)
    TextView tvSaveCancel;
    @BindView(R2.id.rl_confirm_save)
    RelativeLayout rlConfirmSave;
    @BindView(R2.id.ll_camera_bottom_container)
    LinearLayout llCameraBottomContainer;
    @BindView(R2.id.rl_n_mode_container)
    RelativeLayout rlNModeContainer;
    @BindView(R2.id.mpv_camera_current_mode)
    MultiPhotoView mpvCameraCurrentMode;
    @BindView(R2.id.iv_camera_multi_confirm)
    ImageView ivCameraMultiConfirm;
    @BindView(R2.id.iv_camera_multi_cancel)
    ImageView ivCameraMultiCancel;
    @BindView(R2.id.iv_show_no_filter)
    ImageView ivShowNoFilter;
    @BindView(R2.id.btv_bottom_tab)
    BottomTabView btvBottomTab;
    @BindView(R2.id.rbv_record_button)
    RecordButtonView recordButtonView;


    @BindView(R2.id.fl_mask)
    FrameLayout flMask;

    private Activity activity;
    private Context context;

    private DisplayMetrics dm;

    /*** GPUImage相关使用 ***/
    private GPUImage mGPUImage;
    private GPUImage mGPUImageShadow;
    private GPUImageFilterGroup gpuImageFilterGroup;
    private GPUImageFilterGroup gpuImageFilterGroupShadow;

    private GPUImageMovieWriter mMovieWriter;

    private CameraHelper mCameraHelper;
    private CameraLoader mCamera;

    //当前处理的图片的bitmap缓存。
    private Bitmap currentImageCache = null;

    /*** 拍照 配置设置参数 ***/
    //是否需要确认， true 需要确认界面。 false  直接进行存储。
    private boolean needConfirm = true;

    //是否处于多拍模式。
    private boolean isMultiModeSeries = false;

    //是否正在拍摄中。 true 正在进行拍摄。
    private boolean isTakingPhoto = false;

    //底部按钮组是否为黑色系状态。 true为黑色。false为白色。
    private boolean bottomIsBlackMode = false;

    //拍照比例模式。 多拍模式下，默认1：1比例。
    private int photoMode = FULL_SCREEN_MODE;

    //多拍模式。二连横排，二连竖排，四连拍。九连拍。
    private int multiShotMode = MULTISHOT_MODE_NO;

    // 相机缩放。
    private float oldDist;

    //是否正在处理连拍结果。  true 正在合成连拍。 false：没有在处理连拍合成。
    private boolean dealMultiShot = false;


    //是否正在拍摄倒计时。
    private boolean isTakingPhotoTimer = false;

    //是否开启了滤镜效果
    private boolean isOtherFilter = false;

    //滤镜效果隐藏/显示 按钮 是否正在显示。
    private boolean filterShowerIsShow = false;

    private boolean waterMarkMode = false;
    private boolean mirrorMode = false;
    private boolean saveOriginalMode = false;

    private int saveImageQuanlity = 100;

    private List<String> multiShotPathCache;


    //合成视频是否正在播放。
    private boolean haveVideo = false;

    private int previewWidth, previewHeight;


    private CameraVideoContract.Presenter videoPresenter;
    private CameraPhotoContract.Presenter photoPresenter;

    private SettingPopupWindow settingPopupWindow;
    private SizeModePopupWindow sizeModePopupWindow;

    private int REQUEST_CODE_VIDEO_PREVIEW = 1011;

    public CameraHomeFragment() {
        // Required empty public constructor
    }

    @Override
    public int layoutRes() {
        return R.layout.mmcamera_fragment_photo;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        new CameraVideoPresenter(this);
        new CameraPhotoPresenter(this);
        initData();
        initView(view);
        initBottomBtnsViewHeight();
        initListener();
        switchCameraMode();

    }


    @Override
    public void refreshVideoRecordStatus(int currentRecordStatus) {
        if (filterShowerIsShow) {
            ivShowNoFilter.setVisibility(View.VISIBLE);
        }
        switch (currentRecordStatus) {
            case RECORD_STATUS_BEFORE_RECORD:
                btvBottomTab.setVisibility(View.VISIBLE);
                ivCameraSize.setVisibility(View.VISIBLE);
                ivCameraSet.setVisibility(View.VISIBLE);
                llCameraRightOriginal.setVisibility(View.VISIBLE);
                llCameraVideoSaveConfirm.setVisibility(View.GONE);
                break;
            case RECORD_STATUS_GROUP_RECORDING:
                llCameraSetup.setVisibility(View.VISIBLE);
                btvBottomTab.setVisibility(View.INVISIBLE);
                ivCameraSize.setVisibility(View.INVISIBLE);
                ivCameraSet.setVisibility(View.INVISIBLE);
                llCameraRightOriginal.setVisibility(View.GONE);
                llCameraVideoSaveConfirm.setVisibility(View.VISIBLE);
                llCameraBottomContainer.setVisibility(View.VISIBLE);

                break;
            case RECORD_STATUS_RECORD_TIME_ENOUGH:
                llCameraSetup.setVisibility(View.VISIBLE);
                btvBottomTab.setVisibility(View.INVISIBLE);
                llCameraBottomContainer.setVisibility(View.VISIBLE);
                break;
            case RECORD_STATUS_RECORDING:
                if (filterShowerIsShow) {
                    ivShowNoFilter.setVisibility(View.GONE);
                }
                llCameraSetup.setVisibility(View.GONE);
                llCameraBottomContainer.setVisibility(View.GONE);
                ivCameraSize.setVisibility(View.INVISIBLE);
                ivCameraSet.setVisibility(View.INVISIBLE);
                llCameraRightOriginal.setVisibility(View.GONE);
                llCameraVideoSaveConfirm.setVisibility(View.VISIBLE);
                btvBottomTab.setVisibility(View.GONE);
                break;
        }

    }

    private void initView(View view) {
        mGPUImage = new GPUImage(getActivity());
        mGPUImageShadow = new GPUImage(getActivity());
        mGPUImage.setGLSurfaceView(glSurfaceView);
        mCameraHelper = new CameraHelper(getContext());
        mCamera = new CameraLoader();
        mCamera.initLoader(mCameraHelper, mGPUImage, dm, getActivity());
        if (!mCameraHelper.hasFrontCamera() || !mCameraHelper.hasBackCamera()) {
            ivCameraTurn.setVisibility(View.GONE);
        }

        settingPopupWindow = new SettingPopupWindow(activity, dm, mCamera);
        sizeModePopupWindow = new SizeModePopupWindow(activity, dm);

        mMovieWriter = new GPUImageMovieWriter();
        gpuImageFilterGroup = new GPUImageFilterGroup();
        gpuImageFilterGroupShadow = new GPUImageFilterGroup();
        gpuImageFilterGroup.addFilter(new GPUImageFilter());
        gpuImageFilterGroupShadow.addFilter(new GPUImageFilter());
        gpuImageFilterGroup.addFilter(mMovieWriter);
        mGPUImage.setFilter(gpuImageFilterGroup);

        //top 按钮 组
        ivCameraPic.setOnClickListener(this);
        ivCameraSize.setOnClickListener(this);
        ivCameraSet.setOnClickListener(this);
        ivCameraTurn.setOnClickListener(this);
        ivCameraClose.setOnClickListener(this);

        //bottom 按钮 图片结果处理 组
        tvSaveShare.setOnClickListener(this);
        tvSaveCancel.setOnClickListener(this);
        ivCameraSave.setOnClickListener(this);
        ivCameraEdit.setOnClickListener(this);

        //bottom 视频组 拍摄 按钮
        tvCameraVideoCancel.setOnClickListener(this);
        tvCameraVideoOk.setOnClickListener(this);

        //bottom 按钮 组
        rbCameraSti.setOnClickListener(this);
        rbCameraFilter.setOnClickListener(this);
        ivCameraPhoto.setOnClickListener(this);
        rbCameraBeauty.setOnClickListener(this);
        rbCameraDre.setOnClickListener(this);

        //gl touch 时间
        glSurfaceView.setOnTouchListener(this);

        //多拍模式 按钮组
        ivCameraMultiConfirm.setOnClickListener(this);
        ivCameraMultiCancel.setOnClickListener(this);

        //滤镜 效果切换显示
        ivShowNoFilter.setOnTouchListener(this);

        //视频拍摄结果 处理 组
        recordButtonView.setOnClickListener(this);

        flMask.setOnClickListener(this);

        setLlCameraBottomContainerBgA0();

    }

    private int lastClickId = 0;

    @Override
    public void onClick(View v) {
        int id = v.getId();
        /*if (lastClickId == id) {
            if (ClickUtils.isFastDoubleClick()) {
                return;
            }
        }
        lastClickId = id;*/
        if (id == R.id.iv_camera_photo) {                       //拍照按钮 圆
            if (!settingPopupWindow.isTapScreenTakePhoto()) {
                performTakePhoto();
            }
        } else if (id == R.id.rb_camera_filter) {               //滤镜效果 开
            cameraFilter();
        } else if (id == R.id.rb_camera_sti) {               //滤镜效果 开
            cameraSti();
        } else if (id == R.id.iv_camera_edit) {                 //拍照完成 去编辑 按钮
            photoEdit();
        } else if (id == R.id.iv_camera_save) {                 //确认保存图片
            needFinish = true;
            confirmSave();
            resetMpcView();
        } else if (id == R.id.tv_save_cancel) {                 //取消保存图片
            cancelSave();
            resetMpcView();
        } else if (id == R.id.tv_save_share) {                  //拍照完成 去分享
            saveShare();
        } else if (id == R.id.iv_camera_multi_confirm) {
            confirmSave();
        } else if (id == R.id.iv_camera_multi_cancel) {
            cancelSave();
        } else if (id == R.id.rb_camera_beauty) {
            clickBeauty();
        } else if (id == R.id.rb_camera_dre) {
            clickDre();
        } else if (id == R.id.rbv_record_button) {
            clickRecordBtn();
        } else if (id == R.id.tv_camera_video_cancel) {
            deletePreviousVideo();
        } else if (id == R.id.tv_camera_video_ok) {
            confirmComposerVideo();
        } else if (id == R.id.iv_camera_set) {                  //上方三个点。设置
            cameraSet();
        } else if (id == R.id.iv_camera_close) {                //叉号。 × 关闭页面
            if (currentMode == CAMERA_MODE_TAKE_PHOTO) {
                activity.finish();
            } else if (currentMode == CAMERA_MODE_RECORD_VIDEO) {
                videoPresenter.clickClose(activity);
            }
        } else if (id == R.id.iv_camera_size_mode) {        //选择拍照模式配置。N连拍 或 拍照比例。
            sizeModePopupWindow.showPopupSizeMode(llCameraSetup, currentMode);
        } else if (id == R.id.iv_camera_turn) {              //切换前后摄像头
            mCamera.switchCamera();
            if (currentMode == CAMERA_MODE_RECORD_VIDEO) {
                mGPUImage.setFilter(gpuImageFilterGroup);
            }
            mCamera.resetRotation(mirrorMode, mGPUImage, activity);
        } else if (id == R.id.iv_camera_pic) {      //选择照片按钮 暂时用来切换保存配置。是否需要存储。
            needConfirm = !needConfirm;
            ToastUtils.showToast(getContext(), "needconfirm:" + needConfirm);
        }

    }


    private void initListener() {
        btvBottomTab.setOnTabSelectedListener(new BottomTabView.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int id) {
                if (id == R.id.tv_1) {
                    currentMode = CAMERA_MODE_RECORD_VIDEO;
                    switchCameraMode();
                } else if (id == R.id.tv_2) {
                    currentMode = CAMERA_MODE_TAKE_PHOTO;
                    switchCameraMode();
                } else if (id == R.id.tv_3) {

                }

            }
        });

        recordButtonView.setOnRecordButtonListener(new RecordButtonView.OnRecordButtonListener() {
            @Override
            public void onTimeEnd() {
                videoPresenter.recordButtonTimeEnd();
            }

            @Override
            public void onDeletePrevious(boolean success) {
                videoPresenter.deletePreviousVideo(success);
            }
        });

        sizeModePopupWindow.setOnChcekedListener(new SizeModePopupWindow.OnChcekedListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int rbid = buttonView.getId();
                if (!isChecked) {
                    return;
                }
                sizeModePopupWindow.hideSelfIfShowing();
                setLlCameraBottomContainerBg();

                if (rbid == R.id.rb_camera_fullscreen) {
                    chooseSizeModeFullScreen();
                } else if (rbid == R.id.rb_camera_three_two) {
                    chooseSizeModeThreeTwo();
                } else if (rbid == R.id.rb_camera_four_three) {
                    chooseSizeModeFourThree();
                } else if (rbid == R.id.rb_camera_one_one) {
                    chooseSizeModeOneOne();
                } else if (rbid == R.id.rb_camera_twobis_w) {
                    chooseMultiPhotoMode(MULTISHOT_MODE_TWO_W);
                } else if (rbid == R.id.rb_camera_twobis_h) {
                    chooseMultiPhotoMode(MULTISHOT_MODE_TWO_H);
                } else if (rbid == R.id.rb_camera_fourbis) {
                    chooseMultiPhotoMode(MULTISHOT_MODE_FOUR);
                } else if (rbid == R.id.rb_camera_ninebis) {
                    chooseMultiPhotoMode(MULTISHOT_MODE_NINE);
                }
                mCamera.resetRotation(mirrorMode, mGPUImage, activity);

            }
        });
    }


    private void chooseMultiPhotoMode(int mode) {
        if (photoMode != ONE_TO_ONE_SCREEN_MODE) {
            setScreenModeOneOne();
        }
        multiShotMode = mode;
        multiShotPathCache.clear();
        switch (mode) {
            case MULTISHOT_MODE_NINE:
                setImagetypeCenterCrop();
                ivCameraSize.setImageResource(R.drawable.camera_ninebis);
                break;
            case MULTISHOT_MODE_FOUR:
                setImagetypeCenterCrop();
                ivCameraSize.setImageResource(R.drawable.camera_fourbis);
                break;
            case MULTISHOT_MODE_TWO_H:
                setImagetypeCenterinside();
                ivCameraSize.setImageResource(R.drawable.camera_twobis_h);
                break;
            case MULTISHOT_MODE_TWO_W:
                setImagetypeCenterinside();
                ivCameraSize.setImageResource(R.drawable.camera_twobis_w);
                break;

        }
        setMultiModeView(mode);
    }


    private void chooseSizeModeOneOne() {
        isMultiModeSeries = false;
        setImagetypeCenterCrop();
        setScreenModeOneOne();
        mpvCameraCurrentMode.setVisibility(View.GONE);
    }

    private void chooseSizeModeFourThree() {
        isMultiModeSeries = false;
        setImagetypeCenterCrop();
        setScreenModeFourThree();
    }

    private void chooseSizeModeThreeTwo() {
        isMultiModeSeries = false;
        setImagetypeCenterCrop();
        setScreenModeThreeTwo();
    }

    private void chooseSizeModeFullScreen() {
        isMultiModeSeries = false;
        setImagetypeCenterCrop();
        setScreenModeFull();
        setLlCameraBottomContainerBgA0();
    }

    /**
     * 触摸事件处理
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (v.getId() == R.id.iv_show_no_filter) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                mGPUImage.setFilter(new GPUImageFilter());
                return true;
            }
            if (event.getAction() == MotionEvent.ACTION_UP) {
                mGPUImage.setFilter(gpuImageFilterGroup);
                return true;
            }
        }

        if (isTakingPhotoTimer) {
            return true;
        }
        if (event.getPointerCount() == 1) {
            if (settingPopupWindow.isTapScreenTakePhoto()) {
                if (currentMode == CAMERA_MODE_TAKE_PHOTO) {
                    performTakePhoto();
                } else if (currentMode == CAMERA_MODE_RECORD_VIDEO) {
//                    recordButtonView.performClick();
                }
                return true;
            }

//            handleFocusMetering(event, mCamera.mCameraInstance);
        } else {
            switch (event.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_POINTER_DOWN:
                    oldDist = CameraTouchHelper.getFingerSpacing(event);
                    break;
                case MotionEvent.ACTION_MOVE:
                    float newDist = CameraTouchHelper.getFingerSpacing(event);
                    if (newDist > oldDist) {
                        Log.e("Camera", "进入放大手势");
                        CameraTouchHelper.handleZoom(true, mCamera.mCameraInstance);
                    } else if (newDist < oldDist) {
                        Log.e("Camera", "进入缩小手势");
                        CameraTouchHelper.handleZoom(false, mCamera.mCameraInstance);
                    }
                    oldDist = newDist;
                    break;
            }
        }
        return true;
    }

    @Override
    public void onResume() {
        super.onResume();
        mCamera.onResume();
        glSurfaceView.onResume();
        reloadCameraData();
    }

    @Override
    public void onPause() {
        super.onPause();
        mCamera.onPause();
        glSurfaceView.onPause();
        settingPopupWindow.hideSelfIfShowing();
        sizeModePopupWindow.hideSelfIfShowing();
    }


    private void deletePreviousVideo() {
        recordButtonView.deletePreviousVideo();
    }

    private void confirmComposerVideo() {
        videoPresenter.mergeVideos();
    }

    private void initData() {
        activity = getActivity();
        context = getContext();
        dm = CameraUtils.caculateSize(activity);
        multiShotPathCache = new ArrayList<>();
        previewWidth = dm.widthPixels;
        previewHeight = dm.heightPixels;
    }

    private void reloadCameraData() {
        String pictureSaveQuanlity = (String) SharedPreferencesUtils.getData(context, CameraSettingActivity.SPKEY_SAVE_IMAGE_QUANLITY, VALUE_SAVE_IMAGE_QUANLITY_ORIGINAL);
        waterMarkMode = ((String) SharedPreferencesUtils.getData(context, CameraSettingActivity.SPKEY_WATERMARK_SWITCH, SwitchValue.CLOSE)).equalsIgnoreCase(SwitchValue.OPEN);
        mirrorMode = ((String) SharedPreferencesUtils.getData(context, CameraSettingActivity.SPKEY_MIRROR_MODE_SWITCH, SwitchValue.CLOSE)).equalsIgnoreCase(SwitchValue.OPEN);
        saveOriginalMode = ((String) SharedPreferencesUtils.getData(context, CameraSettingActivity.SPKEY_SAVE_ORIGINAL_SWITCH, SwitchValue.CLOSE)).equalsIgnoreCase(SwitchValue.OPEN);

        if (pictureSaveQuanlity.equalsIgnoreCase(CameraSettingActivity.VALUE_SAVE_IMAGE_QUANLITY_ORIGINAL)) {
            saveImageQuanlity = 100;
        } else if (pictureSaveQuanlity.equalsIgnoreCase(CameraSettingActivity.VALUE_SAVE_IMAGE_QUANLITY_HD)) {
            saveImageQuanlity = 90;
        } else if (pictureSaveQuanlity.equalsIgnoreCase(CameraSettingActivity.VALUE_SAVE_IMAGE_QUANLITY_SD)) {
            saveImageQuanlity = 80;
        }
        mCamera.resetRotation(mirrorMode, mGPUImage, activity);
    }


    private void clickRecordBtn() {
        videoPresenter.clickRecordButton();
    }


    @Override
    public void resetFilter() {
        mGPUImage.setFilter(gpuImageFilterGroup);
    }

    @Override
    public void startMovieWriterRecord(String filePath) {
        try {
            mMovieWriter.startRecording(filePath, previewWidth, previewHeight);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stopMovieWriterRecord() {
        try {
            glSurfaceView.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
            mMovieWriter.stopRecording();
            glSurfaceView.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void startDrawCircle() {
        recordButtonView.drawCircle();
    }

    @Override
    public void pauseDrawCircle() {
        recordButtonView.pauseDrawCircle();
    }

    @Override
    public void stopDrawCircle() {
        recordButtonView.stopDrawCircle();
    }

    private void switchCameraMode() {
        if (currentMode == CAMERA_MODE_TAKE_PHOTO) {
            mGPUImage.setFilter(gpuImageFilterGroup);
            refreshVideoRecordStatus(RECORD_STATUS_BEFORE_RECORD);
            hideRedordVideoViews();
        } else if (currentMode == CAMERA_MODE_RECORD_VIDEO) {
            mGPUImage.setFilter(gpuImageFilterGroup);
            hideTakePhotoViews();
            if (isMultiModeSeries) {
                sizeModePopupWindow.performClickOneOne();
            }
        }
    }

    //拍视频模式 隐藏切换view
    private void hideRedordVideoViews() {
        recordButtonView.setVisibility(View.GONE);
        ivCameraPhoto.setVisibility(View.VISIBLE);
//        ivCameraPic.setVisibility(View.VISIBLE);
    }

    //拍照模式 隐藏显示view
    private void hideTakePhotoViews() {
        ivCameraPhoto.setVisibility(View.INVISIBLE);
//        ivCameraPic.setVisibility(View.INVISIBLE);
        recordButtonView.setVisibility(View.VISIBLE);
    }

    private void clickDre() {
        bottomCheckStatus(rbCameraDre);
    }

    private void clickBeauty() {
        bottomCheckStatus(rbCameraBeauty);
    }

    private void cameraSti() {
        bottomCheckStatus(rbCameraSti);
    }

    private void bottomCheckStatus(RadioButton rb) {
        rb.setSelected(!rb.isSelected());
        if (!rb.isSelected()) {
            rb.setChecked(bottomIsBlackMode);
        }
    }


    /**
     * 相机滤镜设置
     */
    private void cameraFilter() {
       /* rbCameraFilter.setSelected(!rbCameraFilter.isSelected());
        if (!rbCameraFilter.isSelected()) {
            rbCameraFilter.setChecked(bottomIsBlackMode);
        }*/
        bottomCheckStatus(rbCameraFilter);

        if (!rbCameraFilter.isSelected()) {
            isOtherFilter = false;
            ivShowNoFilter.setVisibility(View.GONE);
            filterShowerIsShow = false;
            resetFilterGroup();
            gpuImageFilterGroup = new GPUImageFilterGroup();
            gpuImageFilterGroup.addFilter(new GPUImageFilter());
            gpuImageFilterGroup.addFilter(mMovieWriter);
            gpuImageFilterGroupShadow = new GPUImageFilterGroup();
            gpuImageFilterGroupShadow.addFilter(new GPUImageFilter());
            mGPUImage.setFilter(gpuImageFilterGroup);
            mGPUImageShadow.setFilter(gpuImageFilterGroupShadow);
        } else {
            isOtherFilter = true;
            ivShowNoFilter.setVisibility(View.VISIBLE);
            filterShowerIsShow = true;
            GPUImageGrayscaleFilter gpuImageGrayscaleFilter = new GPUImageGrayscaleFilter();
            GPUImageGrayscaleFilter gpuImageGrayscaleFilter2 = new GPUImageGrayscaleFilter();
            addFilterToGroup(gpuImageGrayscaleFilter);
            updateGPUImageShadow(gpuImageGrayscaleFilter2);
            mGPUImage.setFilter(gpuImageFilterGroup);
        }
    }

    private void resetFilterGroup() {
        gpuImageFilterGroup = new GPUImageFilterGroup();
        gpuImageFilterGroupShadow = new GPUImageFilterGroup();
        gpuImageFilterGroup.addFilter(new GPUImageFilter());
        gpuImageFilterGroupShadow.addFilter(new GPUImageFilter());
    }

    private void updateGPUImageShadow(GPUImageFilter filter) {
        gpuImageFilterGroupShadow.addFilter(filter);
        mGPUImageShadow.setFilter(gpuImageFilterGroupShadow);
    }

    private void addFilterToGroup(GPUImageFilter gpuImageFilter) {
        gpuImageFilterGroup = new GPUImageFilterGroup();
        gpuImageFilterGroup.addFilter(new GPUImageFilter());
        gpuImageFilterGroup.addFilter(gpuImageFilter);
        gpuImageFilterGroup.addFilter(mMovieWriter);

    }


    /**
     * 照片编辑
     */
    private void photoEdit() {
        ToastUtils.showToast(context, "photoEdit");
    }

    /**
     * 保存分享
     */
    private void saveShare() {
        ToastUtils.showToast(context, "saveShare");
    }


    private Disposable disposable;

    /**
     * 拍照后 隐藏其他模块
     */
    private void takePhotoHideViews() {
        llCameraSetup.setVisibility(View.GONE);
        btvBottomTab.setVisibility(View.GONE);
        llCameraBottomContainer.setVisibility(View.GONE);
        if (isOtherFilter) {
            ivShowNoFilter.setVisibility(View.GONE);
        }
    }

    /**
     * 拍照结束，显示其他模块
     */
    private void takePhotoEndResumeViews() {
        llCameraSetup.setVisibility(View.VISIBLE);
        btvBottomTab.setVisibility(View.VISIBLE);
        llCameraBottomContainer.setVisibility(View.VISIBLE);
        glSurfaceView.setVisibility(View.VISIBLE);

        if (isOtherFilter) {
            ivShowNoFilter.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 点击拍照按钮
     */
    private void performTakePhoto() {
        if (settingPopupWindow.getDelayShotMode() == CAMERA_DELAY_MODE_NO) {
            doActionTakePhoto();
        } else {
            flMask.setVisibility(View.VISIBLE);
            isTakingPhotoTimer = true;
            disposable = Observable.interval(0, 1, TimeUnit.SECONDS)
                    .subscribeOn(Schedulers.computation())
                    .observeOn(SchedulerProvider.uiThread())
                    .subscribe(new Consumer<Long>() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void accept(Long aLong) throws Exception {
                            int timeGap = 0;
                            if (settingPopupWindow.getDelayShotMode() == CAMERA_DELAY_MODE_THREE) {
                                timeGap = 3;
                            } else {
                                timeGap = 6;
                            }
                            tvTakePhotoTimer.setVisibility(View.VISIBLE);
                            tvTakePhotoTimer.setText(timeGap - aLong + "");
                            if (aLong >= timeGap) {
                                tvTakePhotoTimer.setVisibility(View.GONE);
                                flMask.setVisibility(View.GONE);

                                if (disposable != null) {
                                    disposable.dispose();
                                }
                                doActionTakePhoto();
                            }
                        }
                    });
        }

    }


    /**
     * 执行拍照动作
     */
    private void doActionTakePhoto() {
        if (mCamera.mCameraInstance.getParameters().getFocusMode().equals(
                Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE)) {
            saveCameraCache();
        } else {
            mCamera.mCameraInstance.autoFocus(new Camera.AutoFocusCallback() {
                @Override
                public void onAutoFocus(final boolean success, final Camera camera) {
                    saveCameraCache();
                }
            });
        }
    }

    /**
     * 拍照、显示在预览图上
     */
    private void saveCameraCache() {
        if (isTakingPhoto) {
            return;
        }
        takePhotoHideViews();
        isTakingPhoto = true;
        Camera.Parameters parameters = mCamera.mCameraInstance.getParameters();
        parameters.setRotation(90);
        mCamera.mCameraInstance.setParameters(parameters);
        for (Camera.Size size : parameters.getSupportedPictureSizes()) {
            Log.i("ASDF", "Supported: " + size.width + "x" + size.height);
        }
        glSurfaceView.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
        mCamera.mCameraInstance.takePicture(null, null,
                new Camera.PictureCallback() {

                    @Override
                    public void onPictureTaken(final byte[] data, final Camera camera) {
                        isTakingPhoto = false;
                        camera.startPreview();
                        Observable.just(data)
                                .map(new Function<byte[], Bitmap>() {
                                    @Override
                                    public Bitmap apply(byte[] datas) throws Exception {
                                        BitmapFactory.Options options = new BitmapFactory.Options();
                                        options.inPreferredConfig = Bitmap.Config.RGB_565;
                                        Bitmap bitmap = BitmapFactory.decodeByteArray(datas, 0, datas.length, options);
                                        Log.i("wechat", "压缩后图片的大小" + (bitmap.getByteCount() / 1024 / 1024)
                                                + "M宽度为" + bitmap.getWidth() + "高度为" + bitmap.getHeight());
                                        if (saveOriginalMode) {
                                            saveImage(IMAGE_FLODER_NAME, "original_" + System.currentTimeMillis() + ".jpg", bitmap);
                                        }
                                        bitmap = mGPUImageShadow.getBitmapWithFilterApplied(bitmap);
                                        Camera.Size size = mCamera.mCameraInstance.getParameters().getPreviewSize();
                                        currentImageCache = BitmapUtils.cropBitmapWithMode(bitmap, size, photoMode, mCamera.isFace);
                                        if (mCamera.isFace && mirrorMode) {
                                            currentImageCache = BitmapUtils.horizontalMirrorBitmap(currentImageCache);
                                        }
                                        if (!needConfirm) {
                                            saveImage(IMAGE_FLODER_NAME, System.currentTimeMillis() + ".jpg", currentImageCache);
                                        }
                                        return currentImageCache;
                                    }
                                })
                                .subscribeOn(SchedulerProvider.ioThread())
                                .observeOn(SchedulerProvider.uiThread())
                                .subscribe(new Consumer<Bitmap>() {
                                    @Override
                                    public void accept(Bitmap bitmap) throws Exception {
                                        glSurfaceView.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
                                        if (needConfirm && !isMultiModeSeries) {
                                            normalModeTakePhotoConfirm(bitmap);
                                        }
                                        if (isMultiModeSeries) {
                                            multiModeTakePhotoConfirm(bitmap);
                                        }
                                    }
                                });
                    }
                });

    }

    private void multiModeTakePhotoConfirm(Bitmap bitmap) {
        ivPreview.setVisibility(View.VISIBLE);
        ivPreview.setImageBitmap(bitmap);
        showMultiModeConfirm();
    }

    private void normalModeTakePhotoConfirm(Bitmap bitmap) {
        ivPreview.setImageBitmap(bitmap);
        rlConfirmSave.setVisibility(View.VISIBLE);
        ivPreview.setVisibility(View.VISIBLE);
        llCameraBottomContainer.setVisibility(View.GONE);
    }

    /**
     * 确认取消存储照片
     */
    private void cancelSave() {
        isTakingPhotoTimer = false;

        rlConfirmSave.setVisibility(View.GONE);
        ivPreview.setVisibility(View.GONE);
        hideMultiModeConfirm();
        ivPreview.setImageBitmap(null);
        currentImageCache = null;
        if (dealMultiShot) {
            mpvCameraCurrentMode.setVisibility(View.VISIBLE);
            multiShotPathCache.clear();
            dealMultiShot = false;
        }
        takePhotoEndResumeViews();
    }

    /**
     * 确认存储照片
     */
    private void confirmSave() {
        isTakingPhotoTimer = false;
        Observable.just(1)
                .map(new Function<Integer, Integer>() {
                    @Override
                    public Integer apply(Integer integer) throws Exception {
                        saveImage(IMAGE_FLODER_NAME, System.currentTimeMillis() + ".jpg", currentImageCache);
                        return integer;
                    }
                })
                .subscribeOn(SchedulerProvider.ioThread())
                .observeOn(SchedulerProvider.uiThread())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                    }
                });
    }

    private boolean needFinish = false;

    /**
     * 执行 存储图片
     */
    private void saveImage(final String folderName, final String fileName, final Bitmap image) {
        File file = ImageSaveUtils.getSaveImageFile(folderName, fileName);
        File file1 = ImageSaveUtils.multiShotSaveImage(file, image, saveImageQuanlity);
        ImageSaveUtils.checkImageSaveResult(activity, file1, new ImageSaveUtils.OnImageSaveListener() {
            @Override
            public void saveImageCompletion(String path) {
                currentImageCache = null;
                ivPreview.setImageBitmap(null);
                rlConfirmSave.setVisibility(View.GONE);
                ivPreview.setVisibility(View.GONE);
                glSurfaceView.setVisibility(View.VISIBLE);
                hideMultiModeConfirm();
                if (isMultiModeSeries) {
                    drawMpvNextView();
                    mpvCameraCurrentMode.setVisibility(View.VISIBLE);
                }
                takePhotoEndResumeViews();
                ToastUtils.showToast(context, "图片存储成功，路径：" + path);
                checkMultiShotCount(path);
                if (needFinish) {
                    setResultIntent(MediaType.PHOTO, path);
                    needFinish = false;
                    activity.finish();
                }
            }
        });
    }


    private void saveImageComplation(String path) {
        currentImageCache = null;
        ivPreview.setImageBitmap(null);
        rlConfirmSave.setVisibility(View.GONE);
        ivPreview.setVisibility(View.GONE);
        glSurfaceView.setVisibility(View.VISIBLE);
        hideMultiModeConfirm();
        if (isMultiModeSeries) {
            drawMpvNextView();
            mpvCameraCurrentMode.setVisibility(View.VISIBLE);
        }
        takePhotoEndResumeViews();
        ToastUtils.showToast(context, "图片存储成功，路径：" + path);
        checkMultiShotCount(path);
        if (needFinish) {
            setResultIntent(MediaType.PHOTO, path);
            needFinish = false;
            activity.finish();
        }
    }

    private void drawMpvNextView() {
        mpvCameraCurrentMode.drawNext();
    }

    private void resetMpcView() {
        mpvCameraCurrentMode.resetCount();
    }

    /**
     * 检查 连拍模式下。当前已拍数量，当数量达到目标时，显示存储选项。
     */
    private void checkMultiShotCount(String path) {
        if (multiShotMode == MULTISHOT_MODE_NO) {
            return;
        }
        if (dealMultiShot) {
            resetMpcView();
            multiShotPathCache.clear();
            dealMultiShot = false;
            return;
        }
        int maxMultiCacheSize = 0;
        switch (multiShotMode) {
            case MULTISHOT_MODE_TWO_H:
            case MULTISHOT_MODE_TWO_W:
                maxMultiCacheSize = 2;
                break;
            case MULTISHOT_MODE_FOUR:
                maxMultiCacheSize = 4;
                break;
            case MULTISHOT_MODE_NINE:
                maxMultiCacheSize = 9;
                break;
        }
        if (multiShotPathCache.size() < maxMultiCacheSize) {
            multiShotPathCache.add(path);
        }
        if (multiShotPathCache.size() == maxMultiCacheSize) {
            showMultiShotResult();
            dealMultiShot = true;
        }

    }


    /**
     * 显示多图拼接 结果。拿到bitmap 并填充到图片。
     */
    private void showMultiShotResult() {
        currentImageCache = BitmapUtils.createMultiShotBitmap(multiShotPathCache, multiShotMode);
        ivPreview.setImageBitmap(currentImageCache);
        rlConfirmSave.setVisibility(View.VISIBLE);
        ivPreview.setVisibility(View.VISIBLE);
        glSurfaceView.setVisibility(View.GONE);
        llCameraSetup.setVisibility(View.GONE);
        llCameraBottomContainer.setVisibility(View.GONE);
        mpvCameraCurrentMode.setVisibility(View.GONE);
        btvBottomTab.setVisibility(View.INVISIBLE);
    }


    private void setMultiModeView(int mode) {
        isMultiModeSeries = true;
        mpvCameraCurrentMode.setVisibility(View.VISIBLE);
        hideMultiModeConfirm();
        mpvCameraCurrentMode.setMultishotMode(mode);

    }

    //显示 多选确认
    private void showMultiModeConfirm() {
        rlNModeContainer.setVisibility(View.VISIBLE);
    }

    //隐藏 多选确认
    private void hideMultiModeConfirm() {
        rlNModeContainer.setVisibility(View.GONE);
    }

    private void setImagetypeCenterinside() {
        ivPreview.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
    }

    private void setImagetypeCenterCrop() {
        ivPreview.setScaleType(ImageView.ScaleType.CENTER_CROP);
    }

    /**
     * 相机模式 全屏
     */
    private void setScreenModeFull() {
        ivCameraSize.setImageResource(R.drawable.camera_full_screen);
        photoMode = FULL_SCREEN_MODE;
        multiShotMode = MULTISHOT_MODE_NO;
        setBottomDisplayWhite();
        setTopDisplayWhite();
        double ratio = ((double) dm.heightPixels) / ((double) dm.widthPixels);
        setScreenModeUniversal(dm.widthPixels, ratio, 0);
    }

    /**
     * 相机模式 1:1
     */
    private void setScreenModeOneOne() {
        ivCameraSize.setImageResource(R.drawable.camera_one_to_one);
        if (photoMode == ONE_TO_ONE_SCREEN_MODE) {
            return;
        }
        photoMode = ONE_TO_ONE_SCREEN_MODE;
        multiShotMode = MULTISHOT_MODE_NO;
        setBottomDisplayBlack();
        setTopDisplayBlack();
        setScreenModeUniversal(dm.widthPixels, 1d, DpdipUtil.dip2px(context, 64));
    }

    /**
     * 相机模式 4:3
     */
    private void setScreenModeFourThree() {
        photoMode = FOUR_TO_THREE_SCREEN_MODE;
        multiShotMode = MULTISHOT_MODE_NO;
        ivCameraSize.setImageResource(R.drawable.camera_four_to_three_top);
        setBottomDisplayBlack();
        setTopDisplayWhite();
        setScreenModeUniversal(dm.widthPixels, 4d / 3d, 0);
    }

    /**
     * 相机模式 3：2
     */
    private void setScreenModeThreeTwo() {
        photoMode = THREE_TO_TWO_SCREEN_MODE;
        multiShotMode = MULTISHOT_MODE_NO;
        ivCameraSize.setImageResource(R.drawable.camera_three_to_two_top);

        setBottomDisplayBlack();
        setTopDisplayWhite();
        setScreenModeUniversal(dm.widthPixels, 3d / 2d, 0);
    }

    /**
     * 相机模式 通用
     */
    private void setScreenModeUniversal(int widthPixels, double ratio, int marginTop) {
        if (rlNModeContainer.getVisibility() == View.VISIBLE) {
            rlNModeContainer.setVisibility(View.GONE);
        }
        mpvCameraCurrentMode.setVisibility(View.GONE);
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) glSurfaceView.getLayoutParams();
        params.width = widthPixels;
        params.height = (int) (widthPixels * ratio);
        params.setMargins(0, marginTop, 0, 0);
        glSurfaceView.setLayoutParams(params);
        ViewGroup.MarginLayoutParams ivParams = (ViewGroup.MarginLayoutParams) ivPreview.getLayoutParams();
        ivParams.width = widthPixels;
        ivParams.height = (int) (widthPixels * ratio);
        ivParams.setMargins(0, marginTop, 0, 0);
        ivPreview.setLayoutParams(ivParams);
        previewWidth = params.width;
        previewHeight = params.height;
        mCamera.changeCameraPreviewSize(params.width, params.height);
    }


    private void initBottomBtnsViewHeight() {
        int width = dm.widthPixels;
        double ratio = 4d / 3d;
        double height = width * ratio;
        double marginBottom = DpdipUtil.dip2px(context, 33);
        int wheight = dm.heightPixels;
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) llCameraBottomContainer.getLayoutParams();
        params.setMargins(0, (int) height, 0, (int) marginBottom);
        params.height = (int) (wheight - height - marginBottom);
        llCameraBottomContainer.setLayoutParams(params);

        ViewGroup.MarginLayoutParams rbvParams = (ViewGroup.MarginLayoutParams) recordButtonView.getLayoutParams();
//        int halfRbvHeifht=DpdipUtil.dip2px(context, 37);
        int halfRbvHeifht = DpdipUtil.dip2px(context, 63);
        int rbvMargin = (int) (params.height / 2 - halfRbvHeifht + marginBottom);
        rbvParams.setMargins(0, (int) height, 0, (int) rbvMargin);
        recordButtonView.setLayoutParams(rbvParams);

    }

    /**
     * top bottom 按钮组的 色系转换。
     */

    private void setBottomDisplayBlack() {
        bottomIsBlackMode = true;
        setBottomDisplayColorSeries(bottomIsBlackMode);
    }

    private void setBottomDisplayColorSeries(boolean colorMode) {
        rbCameraSti.setChecked(colorMode);
        rbCameraFilter.setChecked(colorMode);
        ivCameraPhoto.setSelected(colorMode);

        rbCameraBeauty.setChecked(colorMode);
        rbCameraDre.setChecked(colorMode);
        tvCameraVideoCancel.setSelected(colorMode);
        tvCameraVideoOk.setSelected(colorMode);
    }

    private void setBottomDisplayWhite() {
        bottomIsBlackMode = false;
        setBottomDisplayColorSeries(bottomIsBlackMode);
    }

    private void setLlCameraBottomContainerBgA0() {
        llCameraBottomContainer.setBackgroundResource(R.color.mmcamera_camera_bottom_btns_bg_a0);
    }

    private void setLlCameraBottomContainerBg() {
        llCameraBottomContainer.setBackgroundResource(R.color.mmcamera_camera_bottom_btns_bg);
    }


    public void setTopDisplayWhite() {
        setTopDisplayColorSeries(false);
    }

    public void setTopDisplayBlack() {
        setTopDisplayColorSeries(true);
    }

    public void setTopDisplayColorSeries(boolean isBlack) {
        ivCameraPic.setSelected(isBlack);
        ivCameraSize.setSelected(isBlack);
        ivCameraSet.setSelected(isBlack);
        ivCameraTurn.setSelected(isBlack);
        ivCameraClose.setSelected(isBlack);
    }

    /**
     * 弹出相机参数设置
     */
    private void cameraSet() {
        settingPopupWindow.showPopupSetting(llCameraSetup);
    }


    @Override
    public void videoMergeSuccess(String path) {
        Intent intent = new Intent(getContext(), VideoPreviewActivity.class);
        intent.putExtra(VideoPreviewActivity.PATH_KEY, path);
        startActivityForResult(intent, REQUEST_CODE_VIDEO_PREVIEW);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        LogUtils.d("requestCode:" + requestCode + "resultCode:" + resultCode);
        if (REQUEST_CODE_VIDEO_PREVIEW == requestCode) {
            switch (resultCode) {
                case VideoPreviewActivity.RESULT_CODE_RETURN:
                    videoPresenter.dismissVideoMergeResultWithoutSave();
                    break;
                case VideoPreviewActivity.RESULT_CODE_SAVE:
                    String path = data.getStringExtra(VideoPreviewActivity.PATH_KEY);
                    videoPresenter.dismissVideoMergeResultWithSave(path);
                    break;
                case VideoPreviewActivity.RESULT_CODE_SHARE:
                    videoPresenter.shareVideoResult();
                    break;
            }
        }

    }

    @Override
    public void saveVideoSuccess(String path) {
        setResultIntent(MediaType.VIDEO, path);
        activity.finish();
    }

    @Override
    public void closeThePage() {
        activity.finish();
    }

    @Override
    public void patchVideoFailed() {
        ToastUtils.showToast(context, "合成视频失败，请重试");
    }

    @Override
    public void setPresenter(BasePresenter presenter) {
    }

    @Override
    public void setPresenter(CameraPhotoContract.Presenter presenter) {
        this.photoPresenter = presenter;
    }

    @Override
    public void setPresenter(CameraVideoContract.Presenter presenter) {
        this.videoPresenter = presenter;
    }


    private void setResultIntent(@MediaType String type, String path) {
        Intent intent = activity.getIntent();
        intent.putExtra(RESULT_PATH, path);
        intent.putExtra(RESULT_TYPE, type);
        activity.setResult(Activity.RESULT_OK, intent);
    }


}
