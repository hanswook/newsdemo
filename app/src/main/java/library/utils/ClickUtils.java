package com.wallan.multimediacamera.library.utils;

/**
 * Created by hanxu on 2018/6/7.
 */

public class ClickUtils {
    private static long lastClickTime;

    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
//        Log.e("间隔时间",""+timeD);
        if (0 < timeD && timeD < 500) {
            return true;
        } else {
            lastClickTime = time;
            return false;
        }
    }
}
