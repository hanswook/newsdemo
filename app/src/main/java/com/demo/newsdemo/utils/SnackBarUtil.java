package com.demo.newsdemo.utils;

import android.support.design.widget.Snackbar;
import android.view.View;

/**
 * Created by hans on 2017/8/8 17:31.
 */

public class SnackBarUtil {
    public static void show(View rootView,int textId){
        Snackbar.make(rootView,textId,Snackbar.LENGTH_SHORT).show();
    }
    public static void show(View rootView,String text){
        Snackbar.make(rootView,text,Snackbar.LENGTH_SHORT).show();

    }

}
