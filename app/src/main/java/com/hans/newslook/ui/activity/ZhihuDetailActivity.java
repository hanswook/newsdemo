package com.hans.newslook.ui.activity;

import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hans.newslook.R;
import com.hans.newslook.base.BaseActivity;
import com.hans.newslook.contract.ZhihuDetailContract;
import com.hans.newslook.di.DaggerZhihuDetailComponent;
import com.hans.newslook.di.ZhihuDetailModule;
import com.hans.newslook.model.ZhihuDetailModel;
import com.hans.newslook.model.bean.zhihu.TheStoryBean;
import com.hans.newslook.presenter.ZhihuDetailPresenter;
import com.hans.newslook.utils.HtmlUtils;
import com.hans.newslook.utils.LogUtil;
import com.hans.newslook.widget.IconFontTextView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ZhihuDetailActivity extends BaseActivity implements ZhihuDetailContract.View {


    @BindView(R.id.content_zhihu_detail)
    NestedScrollView contentZhihuDetail;
    @BindView(R.id.web_content)
    FrameLayout webContent;
    @BindView(R.id.tv_return)
    IconFontTextView tvReturn;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rl_topbar_layout)
    RelativeLayout rlTopbarLayout;


    @Inject
    ZhihuDetailPresenter zhihuDetailPresenter;

    private String detailId;
    private WebView zhihuWebview;

    @Override
    public int getLayoutId() {
        return R.layout.activity_zhihu_detail;
    }

    @Override
    protected void initData() {
        super.initData();
        initDagger();
        initIntentData();
    }

    private void initWebview() {
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        zhihuWebview = new WebView(context);
        zhihuWebview.setLayoutParams(params);
        webContent.addView(zhihuWebview);
        //能够的调用JavaScript代码
        WebSettings settings = zhihuWebview.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setLoadsImagesAutomatically(true);
        settings.setBlockNetworkImage(false);
        settings.setDomStorageEnabled(true);
        zhihuWebview.setWebChromeClient(new WebChromeClient());
    }


    private void initIntentData() {
        detailId = getIntent().getStringExtra("zhihu_id");
    }

    private void initDagger() {
        DaggerZhihuDetailComponent.builder()
                .zhihuDetailModule(new ZhihuDetailModule(this, new ZhihuDetailModel()))
                .build()
                .inject(this);
        addPresenter(zhihuDetailPresenter);
    }

    @Override
    protected void initView() {
        super.initView();
        tvReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        initWebview();
        zhihuDetailPresenter.loadDetailData(detailId);
    }


    @Override
    public void updateUI(TheStoryBean data) {
        LogUtil.e(TAG, "updateUI");

        String aaa = HtmlUtils.structHtml(data);
        LogUtil.e(TAG, "aaa:" + aaa);
//        zhihuWebview.loadData(aaa, "text/html", "utf-8");
        zhihuWebview.loadDataWithBaseURL("file:///android_asset/", aaa, "text/html", "UTF-8", null);


    }

    @Override
    public void requestFailed() {
        LogUtil.e(TAG, "requestFailed");
    }

    @Override
    public void showError() {

    }


}
