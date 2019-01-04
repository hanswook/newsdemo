package com.hans.newslook.ui.activity;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.Button;

import com.hans.newslook.R;
import com.hans.newslook.base.BaseActivity;
import com.hans.newslook.test.CanvasTest1Activity;
import com.hans.newslook.utils.baseutils.LogUtils;
import com.hans.newslook.utils.baseutils.SnackBarUtil;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

public class NavigatorActivity extends BaseActivity {


    @BindView(R.id.btn1)
    Button btn1;
    @BindView(R.id.btn2)
    Button btn2;
    @BindView(R.id.btn3)
    Button btn3;
    @BindView(R.id.btn4)
    Button btn4;
    @BindView(R.id.btn_float)
    FloatingActionButton btnFloat;

    @Override
    protected void init() {
        LogUtils.e(TAG, "init");
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_navigator;
    }

    @OnClick({R.id.btn1, R.id.btn_float, R.id.btn3, R.id.btn2, R.id.btn4})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn1:
                toBtn1();
                break;
            case R.id.btn_float:
                toBtnFloat();
                break;
            case R.id.btn2:
                toBtn2();
                break;
            case R.id.btn3:
                toBtn5();

//                toBtn3();
                break;
            case R.id.btn4:
                toBtn4();
                break;
            default:
                break;
        }
    }

    private void toBtn5() {
        startActivity(new Intent(context, CanvasTest1Activity.class));
    }

    private void toBtn3() {
        startActivity(new Intent(context, ZxingActivity.class));

    }

    private void toBtn4() {
        startActivity(new Intent(context, MortgageActivity.class));

    }

    private void toBtn2() {
        startActivity(new Intent(context, GankIOActivity.class));

    }

    private void toBtnFloat() {
        startActivity(new Intent(context, AboutActivity.class));
    }

    private void toBtn1() {
        startActivity(new Intent(context, HomeActivity.class));
    }


    private boolean isBackPressed = false;

    @Override
    public void onBackPressed() {
        if (isBackPressed) {
            super.onBackPressed();
            return;
        } else {
            isBackPressed = true;
            SnackBarUtil.show(btn1, "再按一次就退出了");
            Observable.timer(2, TimeUnit.SECONDS).subscribe(new Consumer<Long>() {
                @Override
                public void accept(@NonNull Long aLong) throws Exception {
                    isBackPressed = false;
                }
            });
        }
    }
}
