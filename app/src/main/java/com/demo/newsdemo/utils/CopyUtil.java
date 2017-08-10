package com.demo.newsdemo.utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

/**
 * Created by hans on 2017/8/8 17:54.
 */

public class CopyUtil {
    public static void copy(Context context, String copyText) {
        ClipboardManager manager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        manager.setPrimaryClip(ClipData.newPlainText(null, copyText));
    }
}
