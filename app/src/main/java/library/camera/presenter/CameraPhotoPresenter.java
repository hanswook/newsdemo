package com.wallan.multimediacamera.library.camera.presenter;

import com.wallan.common.utils.library.EmptyUtils;
import com.wallan.multimediacamera.library.camera.contract.CameraPhotoContract;

/**
 * Created by hanxu on 2018/6/5.
 */

public class CameraPhotoPresenter implements CameraPhotoContract.Presenter {
    private CameraPhotoContract.View mView;



    public CameraPhotoPresenter(CameraPhotoContract.View mView) {
        EmptyUtils.checkNotNull(this.mView = mView);
        mView.setPresenter(this);
    }




}
