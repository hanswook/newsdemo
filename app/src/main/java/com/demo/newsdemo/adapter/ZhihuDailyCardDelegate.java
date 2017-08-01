package com.demo.newsdemo.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.demo.newsdemo.R;
import com.demo.newsdemo.bean.StoriesBean;
import com.demo.newsdemo.utils.recycler.BaseViewHolder;
import com.demo.newsdemo.utils.recycler.ItemViewDelegate;

/**
 * Created by hans on 2017/5/15 11:39.
 */

public class ZhihuDailyCardDelegate implements ItemViewDelegate<StoriesBean> {

    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_gankio_android;
    }

    @Override
    public boolean isForViewType(StoriesBean item, int position) {
        if (item.getDelegateType()==0){
            return true;
        }
        return false;
    }

    @Override
    public void convert(BaseViewHolder holder, StoriesBean storiesBean, int position) {
        TextView tv_title = holder.getView(R.id.item_android_tv_title);
        ImageView iv_show = holder.getView(R.id.item_android_iv_show);
        tv_title.setText(storiesBean.getTitle());
        if (null != storiesBean.getImages()) {
            Glide.with(holder.getContext()).load(storiesBean.getImages().get(0)).into(iv_show);
        } else {
            iv_show.setVisibility(View.GONE);
        }
    }
}