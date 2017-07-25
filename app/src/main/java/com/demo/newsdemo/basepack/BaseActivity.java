package com.demo.newsdemo.basepack;

import android.content.Context;
import android.os.Bundle;
import android.view.WindowManager;



import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseActivity extends BaseRxActivity {
    protected Unbinder unbinder;
    protected Context context;
    protected String TAG;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        TAG = getClass().getSimpleName();
        unbinder = ButterKnife.bind(this);
        context = this;
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        initData();
        initView();
    }


    protected void initData() {

    }

    protected void initView() {

    }

    public abstract int getLayoutId();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }


}
