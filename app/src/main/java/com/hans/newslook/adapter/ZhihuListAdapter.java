package com.hans.newslook.adapter;

import android.content.Context;

import com.hans.newslook.model.bean.zhihu.StoriesBean;
import com.hans.newslook.utils.recycler.BaseRecyclerAdapter;

import java.util.List;

/**
 * Created by hans on 2017/7/28 11:33.
 */

public class ZhihuListAdapter extends BaseRecyclerAdapter<StoriesBean> {
    public ZhihuListAdapter(Context context, List<StoriesBean> datas) {
        super(context, datas);
        addItemViewDelegate(new ZhihuDailyCardDelegate());
        addItemViewDelegate(new ZhihuDateDelegate());
    }



}
