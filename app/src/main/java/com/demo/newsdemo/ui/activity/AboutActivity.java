package com.demo.newsdemo.ui.activity;

import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.demo.newsdemo.R;
import com.demo.newsdemo.base.BaseActivity;
import com.demo.newsdemo.utils.LogUtil;


public class AboutActivity extends BaseActivity{


    @Override
    public int getLayoutId() {
        return R.layout.activity_about;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        LogUtil.e(TAG,"event:"+event.toString());
        if (event.getAction()==MotionEvent.ACTION_UP){
            this.finish();
        }
        return super.onTouchEvent(event);
    }
}
