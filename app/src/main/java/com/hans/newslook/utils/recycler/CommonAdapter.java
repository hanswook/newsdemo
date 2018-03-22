package com.hans.newslook.utils.recycler;

import android.content.Context;
import android.view.LayoutInflater;


import java.util.List;

/**
 * Created by hans on 2017/4/12 11:26.
 */

public abstract class CommonAdapter<T> extends BaseRecyclerAdapter<T>{
    protected Context context;
    protected int layoutId;
    protected List<T> datas;
    protected LayoutInflater inflater;

    public CommonAdapter(final Context context, final int layoutId, List<T> datas) {
        super(context,datas);
        this.context = context;
        this.layoutId = layoutId;
        this.datas = datas;
        inflater=LayoutInflater.from(context);

        addItemViewDelegate(new ItemViewDelegate<T>() {
            @Override
            public int getItemViewLayoutId() {
                return layoutId;
            }

            @Override
            public boolean isForViewType(T item, int position) {
                return true;
            }

            @Override
            public void convert(BaseViewHolder holder, T t, int position) {
                CommonAdapter.this.convert(holder,t,position);
            }
        });

    }
    protected abstract void convert(BaseViewHolder holder,T t,int position);


}
