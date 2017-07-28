package com.demo.newsdemo.utils;

/**
 * Created by hans on 2017/5/15 14:14.
 */

public class ShortTimeUtil {
    private static long lastClickTime;
    public synchronized static boolean isFastClick() {
        long time = System.currentTimeMillis();
        if ( time - lastClickTime < 1000) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    public synchronized static boolean isShortClick() {
        long time = System.currentTimeMillis();
        if ( time - lastClickTime < 3000) {
            return true;
        }
        lastClickTime = time;
        return false;
    }
}
