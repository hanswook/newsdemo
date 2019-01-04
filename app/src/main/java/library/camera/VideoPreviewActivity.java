package com.wallan.multimediacamera.library.camera;

import android.content.Context;
import android.content.ContextWrapper;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.wallan.base.app.library.BaseActivity;
import com.wallan.common.utils.library.EmptyUtils;
import com.wallan.common.utils.library.LogUtils;
import com.wallan.multimediacamera.library.R;
import com.wallan.multimediacamera.library.R2;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VideoPreviewActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R2.id.vv_video)
    VideoView vvVideo;
    @BindView(R2.id.iv_camera_video_save)
    ImageView ivCameraVideoSave;
    @BindView(R2.id.tv_camera_video_return)
    TextView tvCameraVideoReturn;
    @BindView(R2.id.tv_camera_video_share)
    TextView tvCameraVideoShare;


    /**
     * 当前正在播放视频 path
     */
    private String currentVideoPath;

    public static final String PATH_KEY = "path";

    public static final int RESULT_CODE_RETURN = 2202;
    public static final int RESULT_CODE_SAVE = 2203;
    public static final int RESULT_CODE_SHARE = 2204;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);// 隐藏标题

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);// 设置全屏

        setContentView(R.layout.mmcamera_activity_video_preview);
        currentVideoPath = getIntent().getStringExtra(PATH_KEY);
        LogUtils.d("path:" + currentVideoPath);
        startPreview(currentVideoPath);

        initListener();
        setResult(RESULT_CODE_RETURN);
    }

    private void initListener() {
        ivCameraVideoSave.setOnClickListener(this);
        tvCameraVideoReturn.setOnClickListener(this);
        tvCameraVideoShare.setOnClickListener(this);
    }

    private void startPreview(String path) {
        vvVideo.setVideoPath(path);
        vvVideo.start();
        vvVideo.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.seekTo(0);
                mp.start();
            }
        });
    }

    @Override
    protected void onResume() {
        if (!EmptyUtils.isEmpty(currentVideoPath) && vvVideo != null) {
            vvVideo.setVideoPath(currentVideoPath);
            vvVideo.start();
        }
        super.onResume();
    }


    @Override
    protected void onPause() {
        if (vvVideo != null && vvVideo.canPause()) {
            vvVideo.pause();
            vvVideo.stopPlayback();
        }
        super.onPause();

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.iv_camera_video_save) {           //视频合成页 保存
            setResult(RESULT_CODE_SAVE,getIntent());
        } else if (id == R.id.tv_camera_video_return) {         //视频合成页 返回
            setResult(RESULT_CODE_RETURN,getIntent());
        } else if (id == R.id.tv_camera_video_share) {          //视频合成页 分享
            setResult(RESULT_CODE_SAVE,getIntent());
        }
        this.finish();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(new ContextWrapper(newBase) {
            @Override
            public Object getSystemService(String name) {
                if (Context.AUDIO_SERVICE.equals(name))
                    return getApplicationContext().getSystemService(name);
                return super.getSystemService(name);
            }
        });
    }
}
