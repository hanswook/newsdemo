package com.demo.newsdemo.utils;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Arrays;
import java.util.List;

/**
 * Created by hans on 2017/8/11 14:57.
 */

public class ResourceUtil {

    public static List<String> stringArray2List(Context context, int arrayId) {
        LogUtil.e("ResourceUtil","arrayId:"+arrayId);
        return Arrays.asList(context.getResources().getStringArray(arrayId));
    }

    public static String res2String(Context context, int strId) {
        return context.getString(strId);
    }

    public static View inflate(Context context, int viewId, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(viewId, viewGroup, false);
    }

}
