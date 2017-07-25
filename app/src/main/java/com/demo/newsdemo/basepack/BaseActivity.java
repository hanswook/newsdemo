package com.demo.newsdemo.basepack;

import android.content.Context;
import android.os.Bundle;
import android.view.WindowManager;

import com.qgzn.edu.funnystar.bean.CodeBean;
import com.qgzn.edu.funnystar.http.Http;
import com.qgzn.edu.funnystar.http.HttpService;
import com.qgzn.edu.funnystar.utils.Constant;
import com.qgzn.edu.funnystar.utils.LogUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public abstract class BaseActivity extends BaseRxActivity {
    protected Unbinder unbinder;
    protected static HttpService httpService;
    protected Context context;
    protected String TAG;

    static {
        httpService = Http.getHttpService();
    }

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
