package com.demo.newsdemo.ui.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.demo.newsdemo.R;
import com.demo.newsdemo.base.BaseActivity;
import com.demo.newsdemo.utils.SnackBarUtil;

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

    @Override
    public int getLayoutId() {
        return R.layout.activity_navigator;
    }

    @OnClick({R.id.btn1, R.id.btn2, R.id.btn3})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn1:
                startActivity(new Intent(context, HomeActivity.class));

                break;
            case R.id.btn2:
                startActivity(new Intent(context, AboutActivity.class));

                break;
            case R.id.btn3:
                startActivity(new Intent(context, SplashActivity.class));

                break;
        }
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
