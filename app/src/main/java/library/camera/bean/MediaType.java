package com.wallan.multimediacamera.library.camera.bean;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.wallan.multimediacamera.library.camera.bean.MediaType.VIDEO;
import static com.wallan.multimediacamera.library.camera.bean.MediaType.PHOTO;

/**
 * Created by hanxu on 2018/6/8.
 */

@StringDef({VIDEO, PHOTO})
@Retention(RetentionPolicy.SOURCE)
public @interface MediaType {
    String VIDEO = "video";
    String PHOTO = "photo";
}
