package com.hans.newslook.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.hans.newslook.base_core.LoadingDialogManager;
import com.hans.newslook.widget.dialog.CustomProgressDialog;
import com.hans.newslook.widget.dialog.CustomProgressDialogHelper;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by hans
 * date: 2017/7/10 11:06 17:20.
 * e-mail: hxxx1992@163.com
 */

public abstract class BaseCoreFragment extends Fragment implements IBaseView{
    protected View rootView;
    protected Unbinder unbinder;
    protected String TAG;
    protected Activity activity;
    protected Context context;
    private LoadingDialogManager loadingDialogManager;

    protected abstract int getLayoutId();

    protected abstract void init();

    private CustomProgressDialog mProgressDialog;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    protected abstract void initData();

    @Nullable
    @Override
    public final View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        if (rootView == null) {
            rootView = inflater.inflate(this.getLayoutId(), container, false);
        }
        mProgressDialog = CustomProgressDialogHelper.createDialog(getContext());
        mProgressDialog.setCanceledOnTouchOutside(false);
        unbinder = ButterKnife.bind(this, rootView);
        context = getContext();
        activity = getActivity();
        TAG = this.getClass().getSimpleName();
        init();
        loadingDialogManager = new LoadingDialogManager(getActivity());
        return rootView;
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }

    /**
     * 显示ProgressDialog
     */
    @Override
    public void showProgress(String msg) {
        mProgressDialog.setMessage(msg);
        mProgressDialog.show();
    }

    /**
     * 显示ProgressDialog
     */
    @Override
    public void showProgress() {
        mProgressDialog.show();
    }

    /**
     * 取消ProgressDialog
     */
    @Override
    public void dismissProgress() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }
    public void showLoadingDialog(@StringRes int res) {
        loadingDialogManager.showLoadingDialog(res);
    }
    public void dismissLoadingDialog() {
        loadingDialogManager.dismissLoadingDialog();
    }

}