package com.hans.newslook.test;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.ProgressBar;

import com.hans.newslook.test.download.Constant;
import com.hans.newslook.utils.baseutils.LogUtils;

/**
 * @author Hans
 * @create 2019/1/12
 * @Describe
 */
public class UpdateReceiver extends BroadcastReceiver {

    protected ProgressBar[] mProgressBar;

    public UpdateReceiver() {
    }

    public UpdateReceiver(ProgressBar[] mProgressBar) {
        this.mProgressBar = mProgressBar;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        LogUtils.e("UpdateReceiver onReceive:" + intent.getAction());
        if (Constant.ACTION_UPDATE.equalsIgnoreCase(intent.getAction())) {

            int progress = intent.getIntExtra(Constant.SEND_LOADED_PROGRESS, 0);
            for (ProgressBar progressBar : mProgressBar) {
                progressBar.setProgress(progress);
            }
        }
    }
}
