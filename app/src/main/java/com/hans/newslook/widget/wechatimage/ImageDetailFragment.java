package com.hans.newslook.widget.wechatimage;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.graphics.Palette;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;


import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.DrawableImageViewTarget;
import com.bumptech.glide.request.target.Target;
import com.hans.newslook.R;
import com.hans.newslook.callbacks.PaletteCallBack;
import com.hans.newslook.utils.baseutils.GetSize;
import com.hans.newslook.utils.baseutils.LogUtils;
import com.hans.newslook.utils.image.GlideApp;
import com.hans.newslook.utils.palette.PaletteUtils;

import uk.co.senab.photoview.PhotoViewAttacher;


/**
 * 单张图片显示Fragment
 */
public class ImageDetailFragment extends Fragment {
    private String mImageUrl;
    private ImageView mImageView;
    private LinearLayout wximageLllayout;

    private PhotoViewAttacher mAttacher;
    private Palette.Swatch mSwatch;
    private SwatchListener swatchListener;

    public static ImageDetailFragment newInstance(String imageUrl) {
        final ImageDetailFragment f = new ImageDetailFragment();

        final Bundle args = new Bundle();
        args.putString("url", imageUrl);
        f.setArguments(args);

        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mImageUrl = getArguments() != null ? getArguments().getString("url") : null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.image_detail_fragment, container, false);
        mImageView = (ImageView) v.findViewById(R.id.image);
        wximageLllayout = v.findViewById(R.id.wximage_lllayout);
        mAttacher = new PhotoViewAttacher(mImageView);
        mAttacher.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {

            @Override
            public void onPhotoTap(View arg0, float arg1, float arg2) {
                getActivity().finish();
            }
        });
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        GlideApp.with(getContext()).load(mImageUrl)
                .override(GetSize.getScreenWidth(getContext()), GetSize.getScreenHeight(getContext()))
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {

                        PaletteUtils.getBitmap(((BitmapDrawable) resource).getBitmap(), new PaletteCallBack() {
                            @Override
                            public void onCallBack(Palette.Swatch swatch) {
                                mSwatch = swatch;
                                if (swatchListener != null) {
                                    swatchListener.swatchInited(swatch);
                                }
                                setBackGroundColor(swatch.getRgb());
                            }
                        });
                        return false;
                    }
                })
                .into(new DrawableImageViewTarget(mImageView) {

                    @Override
                    protected void setResource(@Nullable Drawable resource) {
                        super.setResource(resource);
                        mAttacher.update();
                    }
                });

    }

    @Override
    public void onDestroy() {
        mAttacher.cleanup();
        super.onDestroy();
    }

    public Palette.Swatch getSwatch() {
        return mSwatch;
    }

    public void setSwatchListener(SwatchListener swatchListener) {
        this.swatchListener = swatchListener;
    }

    public interface SwatchListener {
        void swatchInited(Palette.Swatch swatch);
    }


    public void setBackGroundColor(int color) {
        wximageLllayout.setBackgroundColor(color);
    }

    public void setStatusBarColor(int color) {
        if (Build.VERSION.SDK_INT >= 21) {
            Window statusBar = getActivity().getWindow();
            statusBar.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            statusBar.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            statusBar.setStatusBarColor(color);
        }
    }


}

