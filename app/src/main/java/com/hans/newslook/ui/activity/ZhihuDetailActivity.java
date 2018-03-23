package com.hans.newslook.ui.activity;

import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.hans.newslook.R;
import com.hans.newslook.base.BaseActivity;
import com.hans.newslook.model.bean.zhihu.TheStoryBean;
import com.hans.newslook.contract.ZhihuDetailContract;
import com.hans.newslook.di.DaggerZhihuDetailComponent;
import com.hans.newslook.di.ZhihuDetailModule;
import com.hans.newslook.model.ZhihuDetailModel;
import com.hans.newslook.presenter.ZhihuDetailPresenter;
import com.hans.newslook.utils.HtmlUtils;
import com.hans.newslook.utils.LogUtil;

import javax.inject.Inject;

import butterknife.BindView;

public class ZhihuDetailActivity extends BaseActivity implements ZhihuDetailContract.View {


    @BindView(R.id.zhihu_detail_webview)
    WebView zhihuWebview;

    @Inject
    ZhihuDetailPresenter zhihuDetailPresenter;

    private String detailId;

    @Override
    public int getLayoutId() {
        return R.layout.activity_zhihu_detail;
    }

    @Override
    protected void initData() {
        super.initData();
        initDagger();
        initIntentData();
        zhihuDetailPresenter.loadDetailData(detailId);
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
    }


    @Override
    public void updateUI(TheStoryBean data) {
        LogUtil.e(TAG, "updateUI");
        //能够的调用JavaScript代码
        WebSettings settings = zhihuWebview.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setLoadsImagesAutomatically(true);
        settings.setBlockNetworkImage(false);
        settings.setDomStorageEnabled(true);

        zhihuWebview.setWebChromeClient(new WebChromeClient());
        String aaa=HtmlUtils.structHtml(data);
        LogUtil.e(TAG,"aaa:"+aaa);
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

    @Override
    public void complete() {

    }
}
