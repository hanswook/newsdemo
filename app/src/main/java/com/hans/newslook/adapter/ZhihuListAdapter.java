package com.hans.newslook.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hans.newslook.R;
import com.hans.newslook.model.bean.zhihu.StoriesBean;
import com.hans.newslook.utils.Constants;
import com.hans.newslook.utils.baseutils.LogUtils;

import java.util.List;

/**
 * Created by hans
 * date: 2018/4/25 14:09.
 * e-mail: hxxx1992@163.com
 */

public class ZhihuListAdapter extends BaseMultiItemQuickAdapter<StoriesBean, BaseViewHolder> {

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public ZhihuListAdapter(List<StoriesBean> data) {
        super(data);
        addItemType(Constants.ZHIHU_DATETITLE, R.layout.item_recycler_date_layout);
        addItemType(Constants.ZHIHU_ITEMCONTENT, R.layout.item_gankio_android);
    }

    @Override
    protected void convert(BaseViewHolder helper, StoriesBean item) {
        switch (helper.getItemViewType()) {
            case Constants.ZHIHU_DATETITLE:
                helper.setText(R.id.item_date_space, item.getDataDate());
                break;
            case Constants.ZHIHU_ITEMCONTENT:
                helper.setText(R.id.item_android_tv_title, item.getTitle());
                ImageView iv_show = helper.getView(R.id.item_android_iv_show);
                SharedPreferences sharedPreferences = mContext.getSharedPreferences("zhihu_history", Context.MODE_PRIVATE);
                boolean isview = sharedPreferences.getBoolean(item.getId() + "", false);
                LogUtils.e(TAG, "isview:" + isview + ",item.getId():" + item.getId());
                if (isview) {
                    ((TextView) helper.getView(R.id.item_android_tv_title)).setTextColor(mContext.getResources().getColor(R.color.gray_text));
                } else {
                    ((TextView) helper.getView(R.id.item_android_tv_title)).setTextColor(mContext.getResources().getColor(R.color.black));

                }

                if (null != item.getImages()) {
                    Glide.with(mContext).load(item.getImages().get(0)).into(iv_show);
                } else {
                    iv_show.setVisibility(View.GONE);
                }
                break;
        }

    }
}
