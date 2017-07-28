package com.demo.newsdemo.ui.activity;

import android.os.Bundle;
import android.text.Html;
import android.webkit.WebView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.demo.newsdemo.R;
import com.demo.newsdemo.basepack.BaseActivity;
import com.demo.newsdemo.bean.TheStoryBean;
import com.demo.newsdemo.contract.ZhihuDetailContract;
import com.demo.newsdemo.di.DaggerZhihuDetailComponent;
import com.demo.newsdemo.di.ZhihuDetailModule;
import com.demo.newsdemo.model.ZhihuDetailModel;
import com.demo.newsdemo.presenter.ZhihuDetailPresenter;
import com.demo.newsdemo.utils.LogUtil;

import javax.inject.Inject;
import butterknife.BindView;

public class ZhihuDetailActivity extends BaseActivity implements ZhihuDetailContract.View {


    @BindView(R.id.zhihu_detail_webview)
    WebView zhihuDetailWebview;
    @BindView(R.id.zhihu_detail_image)
    ImageView zhihuDetailImage;

    @Inject
    ZhihuDetailPresenter zhihuDetailPresenter;

    String zhihuId;
    @Override
    public int getLayoutId() {
        return R.layout.activity_zhihu_detail;
    }

    @Override
    protected void initData() {
        super.initData();
        initDagger();
        initIntentData();
        zhihuDetailPresenter.loadData(this, zhihuId);
    }

    private void initIntentData() {
        zhihuId = getIntent().getStringExtra("zhihu_id");

    }

    private void initDagger() {
        DaggerZhihuDetailComponent.builder()
                .zhihuDetailModule(new ZhihuDetailModule(this, new ZhihuDetailModel()))
                .build()
                .inject(this);
    }

    @Override
    protected void initView() {
        super.initView();
    }


    @Override
    public void updateUI(TheStoryBean data) {
        LogUtil.e(TAG, "updateUI");
        //能够的调用JavaScript代码
        zhihuDetailWebview.getSettings().setJavaScriptEnabled(true);
        zhihuDetailWebview.loadDataWithBaseURL(data.getShare_url(), data.getBody(), "text/html", "utf-8", data.getCss().get(0));
        Glide.with(context).load(data.getImage()).into(zhihuDetailImage);

    }

    @Override
    public void requestFailed() {
        LogUtil.e(TAG, "requestFailed");
    }
}
