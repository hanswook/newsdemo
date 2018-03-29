package com.hans.newslook.utils;

import android.util.Log;

import com.hans.newslook.base.AppContext;


/**
 * Created by hans on 2017/4/11 14:56.
 */

public class LogUtils {
    public static void d(String tag, String str) {
        if (Constants.IS_DEBUG_MODE) {
            try {
                Log.d(tag,str);
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }

    public static void e(String tag, String str) {
        if (Constants.IS_DEBUG_MODE) {
            try {
                Log.e(tag,str);
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }

    public static void e(Class tag, String str) {
        if (Constants.IS_DEBUG_MODE) {
            try {
                Log.e(tag.getSimpleName(),str);
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }
    public static void e(String str) {
        if (Constants.IS_DEBUG_MODE) {
            try {
                Log.e(AppContext.getInstance().getClass().getSimpleName(),str);
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }
    public static void i(String tag, String str) {
        if (Constants.IS_DEBUG_MODE) {
            try {
                Log.i(tag,str);
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }

    public static void v(String tag, String str) {
        if (Constants.IS_DEBUG_MODE) {
            try {
                Log.v(tag,str);
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }

}
