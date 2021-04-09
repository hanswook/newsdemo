package com.hans.newslook.utils.baseutils;

import android.view.View;

import com.google.android.material.snackbar.Snackbar;

/**
 * Created by hans on 2017/8/8 17:31.
 */

public class SnackBarUtil {
    public static void show(View rootView,int textId){
        Snackbar.make(rootView,textId, Snackbar.LENGTH_SHORT).show();
    }
    public static void show(View rootView,String text){
        Snackbar.make(rootView,text,Snackbar.LENGTH_SHORT).show();

    }

}
