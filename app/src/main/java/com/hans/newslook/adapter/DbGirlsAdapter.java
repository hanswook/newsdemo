package com.hans.newslook.adapter;

//import androidx.annotation.Nullable;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hans.newslook.R;
import com.hans.newslook.model.bean.GirlItemData;
import com.hans.newslook.utils.baseutils.ScaleImageView;

import java.util.List;

/**
 * Created by hans
 * date: 2017/12/6 14:49.
 * e-mail: hxxx1992@163.com
 */

public class DbGirlsAdapter extends BaseQuickAdapter<GirlItemData, BaseViewHolder> {

    public DbGirlsAdapter(int layoutResId, @Nullable List<GirlItemData> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GirlItemData item) {
        ScaleImageView imageView = helper.getView(R.id.girl_item_iv);
        imageView.setInitSize(item.getWidth(), item.getHeight());
        Glide.with(mContext).load(item.getUrl()).into(imageView);
    }
}
