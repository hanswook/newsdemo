package com.hans.newslook.ui.activity;


import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.hans.newslook.R;
import com.hans.newslook.base.BaseActivity;

import butterknife.BindView;

public class WebDetailActivity extends BaseActivity {


    @BindView(R.id.gankio_detail_webview)
    WebView gankioWebview;

    private String loadUrl;

    @Override
    protected void init() {
        initIntentData();

        //能够的调用JavaScript代码
        gankioWebview.setWebChromeClient(new WebChromeClient());
        WebSettings settings = gankioWebview.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setLoadsImagesAutomatically(true);
        settings.setBlockNetworkImage(false);
        gankioWebview.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                loadurlLocalMethod(view, url);
                return false;
            }


        });
        settings.setDomStorageEnabled(true);
        gankioWebview.loadUrl(loadUrl);
    }

    public void loadurlLocalMethod(final WebView webView, final String url) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                webView.loadUrl(url);
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_web_detail;
    }


    private void initIntentData() {
        loadUrl = getIntent().getStringExtra("gank_item_data_url");
        Toast.makeText(context, "url:" + loadUrl, Toast.LENGTH_SHORT).show();
    }

}
