package com.demo.newsdemo.basepack;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qgzn.edu.funnystar.http.Http;
import com.qgzn.edu.funnystar.http.HttpService;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by hans on 2017/7/10 11:06.
 */

public abstract class BaseCoreFragment extends Fragment {
    protected View rootView;
    protected LayoutInflater inflater;
    protected static HttpService httpService;

    protected Unbinder unbinder;
    protected Context context;
    protected String TAG;
    static {
        httpService = Http.getHttpService();
    }
    //  加载进度的dialog
//    private CustomProgressDialog mProgressDialog;

    @Nullable
    @Override
    public final View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        this.inflater = inflater;
//        mProgressDialog = CustomProgressDialog.createDialog(getContext());
//        mProgressDialog.setCanceledOnTouchOutside(false);
        if (rootView == null) {
            rootView = inflater.inflate(this.getLayoutId(), container, false);
            unbinder = ButterKnife.bind(this, rootView);
            init();
        }
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }
        context=getContext();
        TAG=context.getClass().getSimpleName();
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
    /*
    *//**
     * 显示ProgressDialog
     *//*
    @Override
    public void showProgress(String msg) {
        mProgressDialog.setMessage(msg);
        mProgressDialog.show();
    }
    *//**
     * 显示ProgressDialog
     *//*
    @Override
    public void showProgress() {
        mProgressDialog.show();
    }

    */

    /**
     * 取消ProgressDialog
     *//*
    @Override
    public void dismissProgress() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }*/
    protected abstract int getLayoutId();

    protected abstract void init();
}