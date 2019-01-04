package com.wallan.multimediacamera.library.camera.presenter;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.wallan.common.utils.library.EmptyUtils;
import com.wallan.common.utils.library.LogUtils;
import com.wallan.common.utils.library.SchedulerProvider;
import com.wallan.common.utils.library.ToastUtils;
import com.wallan.multimediacamera.library.R;
import com.wallan.multimediacamera.library.camera.contract.CameraVideoContract;
import com.wallan.multimediacamera.library.utils.CameraUtils;
import com.wallan.multimediacamera.library.utils.FileUtils;
import com.wallan.multimediacamera.library.utils.VideoComposer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;


/**
 * Created by hanxu on 2018/6/5.
 */

public class CameraVideoPresenter implements CameraVideoContract.Presenter {

    private CameraVideoContract.View mView;

    private List<String> videoPathGroup;

    private String currentVideo;

    private boolean videoIsRecording = false;

    /**
     * 视频录制 状态
     */
    public static final int RECORD_STATUS_BEFORE_RECORD = 100301;
    public static final int RECORD_STATUS_GROUP_RECORDING = 100302;
    public static final int RECORD_STATUS_RECORD_TIME_ENOUGH = 100303;
    public static final int RECORD_STATUS_RECORDING = 100304;

    private int currentRecordStatus = RECORD_STATUS_BEFORE_RECORD;


    public CameraVideoPresenter(CameraVideoContract.View mView) {
        EmptyUtils.checkNotNull(this.mView = mView);
        mView.setPresenter(this);
        initDataType();
    }

    private void initDataType() {
        videoPathGroup = new ArrayList<>();
    }


    @Override
    public void mergeVideos() {
        if (videoPathGroup.size() <= 0) {
            return;
        }
        mView.showLoadingDialog(R.string.loading);
        Observable.just(1)
                .map(new Function<Integer, String>() {
                    @Override
                    public String apply(Integer integer) throws Exception {
                        File file = CameraUtils.getOutputMediaFile(2);
                        String videoPath = file.getAbsolutePath();
                        VideoComposer videoComposer = new VideoComposer(videoPathGroup, file.getAbsolutePath());
                        boolean result = videoComposer.joinVideo();
                        LogUtils.d("result:" + result);
                        if (result) {
                            return videoPath;
                        } else {
                            return "ERROR";
                        }
                    }
                })
                .subscribeOn(SchedulerProvider.ioThread())
                .observeOn(SchedulerProvider.uiThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String path) throws Exception {
                        mView.dismissLoadingDialog();
                        if (path.equalsIgnoreCase("ERROR")) {
                            mView.patchVideoFailed();
                        } else {
                            currentVideo = path;
                            ToastUtils.showToast(mView.getBaseApplication(), "path:" + path);
//                            mView.showVideoPatchResult(path);
                            mView.videoMergeSuccess(path);
                        }
                    }
                });
    }


    @Override
    public void addVideoToVideoGroup(String videoPath) {
        videoPathGroup.add(videoPath);
    }

    @Override
    public void deletePreviousVideo() {
        int size = videoPathGroup.size();
        if (size > 0) {
            FileUtils.deleteFile(videoPathGroup.get(size - 1));
            videoPathGroup.remove(size - 1);
        }
    }

    @Override
    public void dismissVideoMergeResultWithSave(String path) {
        Observable.just(path)
                .map(new Function<String, String>() {
                    @Override
                    public String apply(String integer) throws Exception {
                        FileUtils.deleteFiles(videoPathGroup);
                        videoPathGroup.clear();
                        return integer;
                    }
                })
                .subscribeOn(SchedulerProvider.ioThread())
                .observeOn(SchedulerProvider.uiThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String path) throws Exception {
                        mView.stopDrawCircle();
                        currentRecordStatus = RECORD_STATUS_BEFORE_RECORD;
                        mView.refreshVideoRecordStatus(currentRecordStatus);
                        mView.saveVideoSuccess(path);
                    }
                });

    }

    @Override
    public void dismissVideoMergeResultWithoutSave() {
        if (EmptyUtils.isEmpty(currentVideo)) {
            return;
        }
        boolean success = FileUtils.deleteFile(currentVideo);
        LogUtils.d("currentVideo:" + currentVideo + (success ? "删除成功" : "删除失败"));
        currentVideo = null;
    }

    @Override
    public void shareVideoResult() {
        ToastUtils.showToast(mView.getBaseApplication(), "shareVideoResult");
    }

    @Override
    public void clickClose(Activity activity) {
        LogUtils.d("videoPathGroup.size:" + videoPathGroup.size());
        if (videoPathGroup.size() > 0) {
            showAbadonDialog(activity);
        } else {
            mView.closeThePage();
        }
    }

    private void showAbadonDialog(Activity activity) {
        new MaterialDialog.Builder(activity)
                .content("视频还没有保存，确认放弃本次编辑么？")
                .widgetColorRes(R.color.theme_color)
                .positiveText("不放弃")
                .negativeText("放弃")
                .onAny(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        switch (which) {
                            case NEGATIVE:
                                mView.stopDrawCircle();
                                clearRecordedVideo();
                                currentRecordStatus = RECORD_STATUS_BEFORE_RECORD;
                                mView.refreshVideoRecordStatus(currentRecordStatus);
                                break;
                            case POSITIVE:
//                                mergeVideos();
                                break;
                        }
                    }
                })
                .build().show();
    }

    @Override
    public void clearRecordedVideo() {
        Observable.just(0)
                .map(new Function<Integer, Integer>() {
                    @Override
                    public Integer apply(Integer integer) throws Exception {
                        FileUtils.deleteFiles(videoPathGroup);
                        videoPathGroup.clear();
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


    @Override
    public void clickRecordButton() {
        if (currentRecordStatus == RECORD_STATUS_RECORD_TIME_ENOUGH) {
            ToastUtils.showToast(mView.getBaseApplication(), "当前可录制时间已达到30s");
            return;
        }
        if (!videoIsRecording) {
            startRecord();
        } else {
            stopRecord();
        }
    }

    private void startRecord() {
        mView.resetFilter();
        File recordFile = CameraUtils.getOutputMediaFile(2);
        addVideoToVideoGroup(recordFile.getAbsolutePath());
        mView.startMovieWriterRecord(recordFile.getAbsolutePath());
        videoIsRecording = true;
        mView.startDrawCircle();
        currentRecordStatus = RECORD_STATUS_RECORDING;
        mView.refreshVideoRecordStatus(currentRecordStatus);
    }

    private void stopRecord() {
        mView.stopMovieWriterRecord();
        videoIsRecording = false;
        mView.pauseDrawCircle();
        currentRecordStatus = RECORD_STATUS_GROUP_RECORDING;
        mView.refreshVideoRecordStatus(currentRecordStatus);
    }

    @Override
    public void recordButtonTimeEnd() {
        stopRecord();
        currentRecordStatus = RECORD_STATUS_RECORD_TIME_ENOUGH;
        mView.refreshVideoRecordStatus(currentRecordStatus);
        mergeVideos();
    }

    @Override
    public void deletePreviousVideo(boolean success) {
        if (success) {
            deletePreviousVideo();
            currentRecordStatus = RECORD_STATUS_GROUP_RECORDING;
            mView.refreshVideoRecordStatus(currentRecordStatus);
        } else {
            currentRecordStatus = RECORD_STATUS_BEFORE_RECORD;
            mView.refreshVideoRecordStatus(currentRecordStatus);
        }
    }


}
