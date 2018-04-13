package com.hans.newslook.ui.activity;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.hans.newslook.R;
import com.hans.newslook.base.BaseActivity;
import com.hans.newslook.contract.SplashContract;
import com.hans.newslook.di.DaggerSplashComponent;
import com.hans.newslook.di.SplashModule;
import com.hans.newslook.model.SplashModel;
import com.hans.newslook.model.bean.SplashBean;
import com.hans.newslook.presenter.SplashPresenter;
import com.hans.newslook.utils.baseutils.LogUtils;
import com.hans.newslook.utils.palette.PaletteUtil;
import com.hans.newslook.utils.palette.PaletteUtils;

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
    protected void init() {
        DaggerSplashComponent.builder()
                .splashModule(new SplashModule(this, new SplashModel()))
                .build()
                .inject(this);
        addPresenter(splashPresenter);
        splashPresenter.loadData();

    }

    @Override
    public void updateUI(SplashBean splashBean) {
        Glide.with(context)
                .load(splashBean.getData().getBase_url() + splashBean.getData().getImages().get(0).getImage_url())
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        LogUtils.e(TAG, "onLoadFailed");
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        LogUtils.e(TAG, "onResourceReady");

//                        PaletteUtils.getBitmap(((BitmapDrawable) resource).getBitmap());

                        return false;
                    }
                })
                .into(splashImage);
        LogUtils.e(TAG, splashBean.getData().getBase_url() + splashBean.getData().getImages().get(0).getImage_url());
//        PaletteUtils.getBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.trouble_mall));
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
