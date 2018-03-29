package com.hans.newslook.ui.activity;

import android.content.Intent;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.hans.newslook.R;
import com.hans.newslook.base.BaseActivity;
import com.hans.newslook.contract.SplashContract;
import com.hans.newslook.di.DaggerSplashComponent;
import com.hans.newslook.di.SplashModule;
import com.hans.newslook.model.SplashModel;
import com.hans.newslook.model.bean.SplashBean;
import com.hans.newslook.presenter.SplashPresenter;
import com.hans.newslook.utils.LogUtils;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

public class SplashActivity extends BaseActivity implements SplashContract.View {

    @Inject
    SplashPresenter splashPresenter;

    @BindView(R.id.splash_image)
    ImageView splashImage;

    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initData() {
        super.initData();
        DaggerSplashComponent.builder()
                .splashModule(new SplashModule(this, new SplashModel()))
                .build()
                .inject(this);
        addPresenter(splashPresenter);
    }

    @Override
    protected void initView() {
        super.initView();
        splashPresenter.loadData();
    }

    @Override
    public void updateUI(SplashBean splashBean) {
        Glide.with(context).load(splashBean.getData().getBase_url() + splashBean.getData().getImages().get(0).getImage_url()).into(splashImage);
        LogUtils.e(TAG, splashBean.getData().getBase_url() + splashBean.getData().getImages().get(0).getImage_url());
        turnToNext(3);
    }

    @Override
    public void getDataFailed() {
        Toast.makeText(context, "请求数据失败", Toast.LENGTH_SHORT).show();
        turnToNext(1);
    }

    protected void turnToNext(long delay) {
        Observable.timer(delay, TimeUnit.SECONDS).subscribe(new Consumer<Long>() {
            @Override
            public void accept(@NonNull Long aLong) throws Exception {
                startActivity(new Intent(SplashActivity.this, NavigatorActivity.class));
                SplashActivity.this.finish();
            }
        });
    }

    @Override
    public void showError() {

    }


}
