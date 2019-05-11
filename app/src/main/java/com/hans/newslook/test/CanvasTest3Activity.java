package com.hans.newslook.test;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.hans.newslook.R;
import com.hans.newslook.base.BaseActivity;
import com.hans.newslook.test.download.Constant;
import com.hans.newslook.test.download.DownloadService;
import com.hans.newslook.test.download.FileBean;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author Hans
 * @create 2019/1/4.
 * @Description:
 */
public class CanvasTest3Activity extends BaseActivity {


    @BindView(R.id.btn_download)
    Button btnDownload;
    @BindView(R.id.btn_stop)
    Button btnStop;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.progress_bar1)
    ProgressBar progressBar1;

    @Override
    protected void init() {
        register();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_canvas_test3;
    }


    @OnClick({R.id.btn_download, R.id.btn_stop})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_download:
                start();
                break;
            case R.id.btn_stop:
                stop();
                break;
            default:
                break;
        }
    }

    private void start() {
        FileBean fileBean = new FileBean(0, Constant.FILE_URL, 0, 0, "juejin.apk");
        Intent intent = new Intent(context, DownloadService.class);
        intent.setAction(Constant.ACTION_START);
        intent.putExtra(Constant.KEY_SEND_FILE_BEAN, fileBean);
        startService(intent);
        btnDownload.setText(fileBean.getFileName());
    }

    private void stop() {
        Intent intent = new Intent(context, DownloadService.class);
        intent.setAction(Constant.ACTION_END);
        startService(intent);
    }

    private UpdateReceiver mUpdateReceiver;

    /**
     * 注册广播接收者
     */
    private void register() {
        //注册广播接收者
        mUpdateReceiver = new UpdateReceiver(new ProgressBar[]{progressBar, progressBar1});
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constant.ACTION_UPDATE);
        registerReceiver(mUpdateReceiver, filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mUpdateReceiver != null) {//注销广播
            unregisterReceiver(mUpdateReceiver);
        }
    }

}
