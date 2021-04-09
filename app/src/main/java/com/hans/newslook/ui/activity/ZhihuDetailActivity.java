package com.hans.newslook.ui.activity;

import android.annotation.SuppressLint;
import androidx.core.widget.NestedScrollView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
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
import com.hans.newslook.utils.baseutils.LogUtils;
import com.hans.newslook.widget.IconFontTextView;

import javax.inject.Inject;

import butterknife.BindView;

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


    @SuppressLint("SetJavaScriptEnabled")
    private void initWebview() {

        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        zhihuWebview = new WebView(context);
//        zhihuWebview = new X5WebView(this, null);
        zhihuWebview.setLayoutParams(params);
        webContent.addView(zhihuWebview);
        //能够的调用JavaScript代码
        WebSettings settings = zhihuWebview.getSettings();
//        com.tencent.smtt.sdk.WebSettings settings = zhihuWebview.getSettings();
        settings.setJavaScriptEnabled(true);
//        settings.setUseWideViewPort(true);
        settings.setSupportZoom(true);
        settings.setLoadWithOverviewMode(true);
        settings.setLoadsImagesAutomatically(true);
        settings.setBlockNetworkImage(false);
        settings.setDomStorageEnabled(true);

        settings.setAllowFileAccess(true);
        settings.setAppCacheEnabled(true);
        // 如果是图片频道，则必须设置该接口为true，否则页面无法展现

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
    protected void init() {
        initDagger();
        initIntentData();
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
        LogUtils.e(TAG, "updateUI");

        String aaa = HtmlUtils.structHtml(data);
        LogUtils.e(TAG, "aaa:" + aaa);
//        zhihuWebview.loadData(aaa, "text/html", "utf-8");
        zhihuWebview.loadDataWithBaseURL("file:///android_asset/", aaa, "text/html", "UTF-8", null);


    }

    @Override
    public void requestFailed() {
        LogUtils.e(TAG, "requestFailed");
    }

    @Override
    public void showError() {

    }


    @Override
    protected void onDestroy() {
        removeWebView();
        super.onDestroy();
    }

    /**
     * 移除 webview相关内容。避免内存泄漏
     */
    private void removeWebView() {
        if (zhihuWebview != null) {
            // 如果先调用destroy()方法，则会命中if (isDestroyed()) return;这一行代码，需要先onDetachedFromWindow()，再
            // destory()
            ViewParent parent = zhihuWebview.getParent();
            if (parent != null) {
                ((ViewGroup) parent).removeView(zhihuWebview);
            }

            zhihuWebview.stopLoading();
            // 退出时调用此方法，移除绑定的服务，否则某些特定系统会报错
            zhihuWebview.getSettings().setJavaScriptEnabled(false);
            zhihuWebview.clearHistory();
            zhihuWebview.clearView();
            zhihuWebview.removeAllViews();
            zhihuWebview.destroy();
        }
    }

}
