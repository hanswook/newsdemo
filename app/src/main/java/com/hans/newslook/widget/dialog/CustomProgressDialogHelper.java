package com.hans.newslook.widget.dialog;

import android.content.Context;
import android.view.Gravity;

import com.hans.newslook.R;


/**
 * Created by hans
 * date: 2017/12/7 18:04.
 * e-mail: hxxx1992@163.com
 */

public class CustomProgressDialogHelper {
    //创建dialog的样式
    public static CustomProgressDialog createDialog(Context context){
        CustomProgressDialog mProgressDialog = new CustomProgressDialog(context, R.style.ProgressDialogStyle);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.setContentView(R.layout.dialog_progress);
        mProgressDialog.getWindow().getAttributes().gravity = Gravity.CENTER;
        return mProgressDialog;
    }
}
