package com.demo.newsdemo.ui.activity;

import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.demo.newsdemo.R;
import com.demo.newsdemo.base.BaseActivity;
import com.demo.newsdemo.model.bean.zhihu.TheStoryBean;
import com.demo.newsdemo.contract.ZhihuDetailContract;
import com.demo.newsdemo.di.DaggerZhihuDetailComponent;
import com.demo.newsdemo.di.ZhihuDetailModule;
import com.demo.newsdemo.model.ZhihuDetailModel;
import com.demo.newsdemo.model.viewModel.ZhihuDetailEntity;
import com.demo.newsdemo.presenter.ZhihuDetailPresenter;
import com.demo.newsdemo.utils.LogUtil;

import javax.inject.Inject;

import butterknife.BindView;

public class ZhihuDetailActivity extends BaseActivity implements ZhihuDetailContract.View {


    @BindView(R.id.zhihu_detail_webview)
    WebView zhihuWebview;
    @BindView(R.id.zhihu_detail_image)
    ImageView zhihuDetailImage;

    @Inject
    ZhihuDetailPresenter zhihuDetailPresenter;

    private ZhihuDetailEntity detailEntity;

    @Override
    public int getLayoutId() {
        return R.layout.activity_zhihu_detail;
    }

    @Override
    protected void initData() {
        super.initData();
        initDataType();
        initDagger();
        initIntentData();
        zhihuDetailPresenter.loadDetailData(this, detailEntity.getDetailId());
    }

    private void initDataType() {
        detailEntity = new ZhihuDetailEntity();
    }

    private void initIntentData() {
        detailEntity.setDetailId("9544617");
        detailEntity.setDetailId(getIntent().getStringExtra("zhihu_id"));
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
        WebSettings settings = zhihuWebview.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setLoadsImagesAutomatically(true);
        settings.setBlockNetworkImage(false);
        settings.setDomStorageEnabled(true);

        zhihuWebview.setWebChromeClient(new WebChromeClient());
        zhihuWebview.loadDataWithBaseURL(data.getShare_url(), data.getBody(), "text/html", "utf-8", data.getCss().get(0));
        Glide.with(context).load(data.getImage()).into(zhihuDetailImage);

    }

    @Override
    public void requestFailed() {
        LogUtil.e(TAG, "requestFailed");
    }
}
