package com.demo.newsdemo.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.demo.newsdemo.R;
import com.demo.newsdemo.model.bean.GankItemData;
import com.demo.newsdemo.model.bean.GirlItemData;
import com.demo.newsdemo.utils.LogUtil;
import com.demo.newsdemo.utils.ScaleImageView;
import com.demo.newsdemo.utils.image.GlideApp;

import java.util.List;

/**
 * Created by hans
 * date: 2017/12/6 14:49.
 * e-mail: hxxx1992@163.com
 */

public class GankItemAdapter extends BaseQuickAdapter<GankItemData, BaseViewHolder> {
    public GankItemAdapter(int layoutResId, @Nullable List<GankItemData> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GankItemData item) {
        TextView descTv = helper.getView(R.id.gank_item_desc);
        TextView whoTv = helper.getView(R.id.gank_item_who);
        TextView publishDateTv = helper.getView(R.id.gank_item_publishedat);
        ImageView iconImg = helper.getView(R.id.gank_item_icon);

        descTv.setText(item.getDesc());
        String who = TextUtils.isEmpty(item.getWho()) ? "null" : item.getWho();
        whoTv.setText(who);
        publishDateTv.setText(item.getPublishedAt().substring(0, 10));
        String[] images = item.getImages();
        if (images != null && images.length > 0) {
            LogUtil.e(TAG, images[0] + "?imageView2/0/w/100");
            GlideApp.with(mContext).load(images[0] + "?imageView2/0/w/100").placeholder(R.mipmap.web).into(iconImg);
        } else {
            String url = item.getUrl();
            int iconId;
            if (url.contains("github")) {
                iconId = R.mipmap.github;
            } else if (url.contains("jianshu")) {
                iconId = R.mipmap.jianshu;
            } else if (url.contains("csdn")) {
                iconId = R.mipmap.csdn;
            } else if (url.contains("miaopai")) {
                iconId = R.mipmap.miaopai;
            } else if (url.contains("acfun")) {
                iconId = R.mipmap.acfun;
            } else if (url.contains("bilibili")) {
                iconId = R.mipmap.bilibili;
            } else if (url.contains("youku")) {
                iconId = R.mipmap.youku;
            } else if (url.contains("weibo")) {
                iconId = R.mipmap.weibo;
            } else if (url.contains("weixin")) {
                iconId = R.mipmap.weixin;
            } else {
                iconId = R.mipmap.web;
            }
            GlideApp.with(mContext).load(iconId).into(iconImg);
        }
    }
}
