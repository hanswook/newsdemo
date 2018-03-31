package com.hans.newslook.base;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;


import com.hans.newslook.utils.LogUtils;
import com.hans.newslook.widget.dialog.CustomProgressDialog;
import com.hans.newslook.widget.dialog.CustomProgressDialogHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseActivity extends BaseRxActivity {
    protected Unbinder unbinder;
    protected Context context;
    protected String TAG;

    private List<BasePresenter> presenterList = new ArrayList<>();
    //  加载进度的dialog
    private CustomProgressDialog mProgressDialog;

    protected void addPresenter(BasePresenter presenter) {
        presenterList.add(presenter);
        LogUtils.e(TAG, "addpresenter presenter.size:" + presenterList.size());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        TAG = getClass().getSimpleName();
        unbinder = ButterKnife.bind(this);
        context = this;
        mProgressDialog = CustomProgressDialogHelper.createDialog(this);
        mProgressDialog.setCanceledOnTouchOutside(false);
        initData();
        initView();
    }


    protected void initData() {

    }

    protected void initView() {

    }

    public abstract int getLayoutId();

    public void setStatusBarColor(Activity activity, int color) {
        if (Build.VERSION.SDK_INT >= 21) {
            Window statusBar = getWindow();
            statusBar.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            statusBar.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            statusBar.setStatusBarColor(color);
        }
    }

    public void setStatusBarColor(int color) {
        if (Build.VERSION.SDK_INT >= 21) {
            Window statusBar = getWindow();
            statusBar.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            statusBar.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            statusBar.setStatusBarColor(color);
        }
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



    @Override
    protected void onDestroy() {
        LogUtils.e(TAG, "onDestroy  bef  presenter.size:" + presenterList.size());
        if (unbinder != null)
            unbinder.unbind();
        for (BasePresenter p : presenterList)
            p.detachView();
        LogUtils.e(TAG, "onDestroy  aft  presenter.size:" + presenterList.size());

        super.onDestroy();

    }


}
