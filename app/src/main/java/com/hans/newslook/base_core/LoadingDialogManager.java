package com.hans.newslook.base_core;

import android.app.Activity;

import androidx.annotation.StringRes;

import com.afollestad.materialdialogs.MaterialDialog;
import com.hans.newslook.R;

/**
 * Created by hanxu on 2018/8/7.
 */


public class LoadingDialogManager {
    private Activity activity;
    private MaterialDialog dialog;
    public LoadingDialogManager(Activity activity) {
        this.activity = EmptyUtils.checkNotNull(activity);
    }
    public void showLoadingDialog(@StringRes int res) {
        if (dialog == null) {
            dialog = new MaterialDialog.Builder(activity)
                    .content(res)
                    .widgetColorRes(R.color.theme_color)
                    .progress(true, 0)
                    .build();
        } else {
            dialog.setContent(res);
        }
        dialog.show();
    }
    public void dismissLoadingDialog() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }
}
