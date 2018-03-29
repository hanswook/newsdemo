package com.hans.newslook.ui.activity;

import android.view.MotionEvent;

import com.hans.newslook.R;
import com.hans.newslook.base.BaseActivity;
import com.hans.newslook.utils.LogUtils;


public class AboutActivity extends BaseActivity{


    @Override
    public int getLayoutId() {
        return R.layout.activity_about;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        LogUtils.e(TAG,"event:"+event.toString());
        if (event.getAction()==MotionEvent.ACTION_UP){
            this.finish();
        }
        return super.onTouchEvent(event);
    }
}
