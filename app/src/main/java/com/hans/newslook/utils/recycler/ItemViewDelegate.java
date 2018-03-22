package com.hans.newslook.utils.recycler;

/**
 * Created by hans on 2017/4/12 11:01.
 */

public interface ItemViewDelegate <T>{
    int getItemViewLayoutId();
    boolean isForViewType(T item, int position);
    void convert(BaseViewHolder holder, T t, int position);
}
