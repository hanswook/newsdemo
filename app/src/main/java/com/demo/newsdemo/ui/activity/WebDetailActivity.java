package com.demo.newsdemo.ui.activity;


import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.demo.newsdemo.R;
import com.demo.newsdemo.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WebDetailActivity extends BaseActivity {


    @BindView(R.id.gankio_detail_webview)
    WebView gankioWebview;

    private String loadUrl;
    @Override
    public int getLayoutId() {
        return R.layout.activity_web_detail;
    }

    @Override
    protected void initData() {
        super.initData();
        initIntentData();

        //能够的调用JavaScript代码
        gankioWebview.setWebChromeClient(new WebChromeClient());
        WebSettings settings = gankioWebview.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setLoadsImagesAutomatically(true);
        settings.setBlockNetworkImage(false);
        settings.setDomStorageEnabled(true);
        gankioWebview.loadUrl(loadUrl);

    }

    private void initIntentData() {
        loadUrl=getIntent().getStringExtra("gank_item_data_url");
        Toast.makeText(context, "url:"+loadUrl, Toast.LENGTH_SHORT).show();
    }

}