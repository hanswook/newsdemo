package com.hans.newslook.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hans.newslook.R;
import com.hans.newslook.model.bean.GankItemData;
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
        GlideApp.with(mContext).load(item.getUrl()).into(imageView);

    }
}
