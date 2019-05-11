package com.hans.newslook.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.hans.newslook.utils.baseutils.LogUtils;

/**
 * @author Hans
 * @create 2019/1/21
 * @Describe
 */
public class PhoneBroadcast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        LogUtils.e("[Broadcast]"+action);

        if (action.equalsIgnoreCase(Intent.ACTION_NEW_OUTGOING_CALL)){
            String outPhoneNumber = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
            LogUtils.e("[outPhoneNumber]"+outPhoneNumber);
            setResultData("17802149721");

        }

    }
}
