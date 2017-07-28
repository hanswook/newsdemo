package com.demo.newsdemo.adapter;

import android.content.Context;

import com.demo.newsdemo.bean.StoriesBean;
import com.demo.newsdemo.utils.recycler.BaseRecyclerAdapter;

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
