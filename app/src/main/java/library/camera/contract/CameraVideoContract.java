package com.wallan.multimediacamera.library.camera.contract;


import android.app.Activity;

/**
 * Created by hanxu on 2018/6/5.
 */

public interface CameraVideoContract {


    interface View extends CameraMainContract.View {
        void setPresenter(Presenter presenter);


        void videoMergeSuccess(String path);

        void patchVideoFailed();


        void closeThePage();

        void startMovieWriterRecord(String filePath);

        void stopMovieWriterRecord();

        void startDrawCircle();

        void pauseDrawCircle();

        void stopDrawCircle();

        void resetFilter();

        void refreshVideoRecordStatus(int currentRecordStatus);

        void saveVideoSuccess(String path);
    }

    interface Presenter extends CameraMainContract.Presenter {
        void mergeVideos();

        void addVideoToVideoGroup(String videoPath);

        void deletePreviousVideo();

        void dismissVideoMergeResultWithSave(String path);

        void dismissVideoMergeResultWithoutSave();

        void shareVideoResult();

        void clickClose(Activity activity);

        void clearRecordedVideo();

        void clickRecordButton();

        void recordButtonTimeEnd();

        void deletePreviousVideo(boolean success);
    }


}
