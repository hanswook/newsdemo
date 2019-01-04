package com.wallan.multimediacamera.library.camera.contract;


/**
 * Created by hanxu on 2018/6/5.
 */

public interface CameraPhotoContract {


    interface View extends CameraMainContract.View{
        void setPresenter(Presenter presenter);


    }

    interface Presenter extends CameraMainContract.Presenter{


    }
}
