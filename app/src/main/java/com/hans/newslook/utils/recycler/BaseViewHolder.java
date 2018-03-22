package com.hans.newslook.utils.recycler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by hans on 2017/4/12 11:09.
 */

public class BaseViewHolder extends RecyclerView.ViewHolder {
    private SparseArray<View> views;
    private View view;
    private Context context;

    public BaseViewHolder(Context context, View itemView) {
        super(itemView);
        this.context = context;
        this.view = itemView;
        views = new SparseArray<>();
    }

    public static BaseViewHolder createViewHolder(Context context, View itemView) {
        BaseViewHolder holder = new BaseViewHolder(context, itemView);
        return holder;
    }


    public static BaseViewHolder createViewHolder(Context context,
                                                  ViewGroup parent, int layoutId) {
        View itemView = LayoutInflater.from(context).inflate(layoutId,
                parent, false);
        BaseViewHolder holder = new BaseViewHolder(context, itemView);
        return holder;
    }

    public <V extends View> V getView(int viewId) {
        View view =  views.get(viewId);
        if (view == null) {
            view =  itemView.findViewById(viewId);
        }
        return (V) view;
    }

    public View getConvertView() {
        return view;
    }
    public Context getContext(){
        return context;
    }

    /**
     * 关于监听事件
     * */
    public BaseViewHolder setOnClickListener(int viewId, View.OnClickListener listener){
        View view=getView(viewId);
        view.setOnClickListener(listener);
        return this;
    }

    public BaseViewHolder setOnTouchListener(int viewId, View.OnTouchListener listener){
        View view=getView(viewId);
        view.setOnTouchListener(listener);
        return this;
    }

    public BaseViewHolder setOnLongClickListener(int viewId, View.OnLongClickListener listener){
        View view=getView(viewId);
        view.setOnLongClickListener(listener);
        return this;
    }

}
