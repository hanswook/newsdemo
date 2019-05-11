package com.hans.newslook.adapter;

import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hans.newslook.R;
import com.hans.newslook.model.bean.GankItemData;
import com.hans.newslook.utils.baseutils.LogUtils;
import com.hans.newslook.utils.image.GlideApp;

import java.util.List;

/**
 * Created by hans
 * date: 2017/12/6 14:49.
 * e-mail: hxxx1992@163.com
 */

public class GankGirlAdapter extends BaseQuickAdapter<GankItemData, BaseViewHolder> {
    public GankGirlAdapter(int layoutResId, @Nullable List<GankItemData> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GankItemData item) {
        ImageView imageView = helper.getView(R.id.item_girls_image);
        GlideApp.with(mContext)
                .asBitmap()
                .load(item.getUrl())
                .placeholder(R.mipmap.ns_empty_placeholder)
                .error(R.mipmap.ns_empty_placeholder)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                        double width = resource.getWidth();
                        double height = resource.getHeight();
                        double radio = height / width;
                        LogUtils.d("GankGirlAdapter", "width:" + width + ",height:" + height);
                        double widthPixels = mContext.getResources().getDisplayMetrics().widthPixels;
                        double imageHeight = widthPixels * radio;
                        imageView.setLayoutParams(new FrameLayout.LayoutParams((int) widthPixels, (int) imageHeight));
                        imageView.setImageBitmap(resource);
                    }
                });

    }
}
