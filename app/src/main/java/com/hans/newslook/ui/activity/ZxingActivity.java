package com.hans.newslook.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.hans.newslook.R;
import com.hans.newslook.base.BaseActivity;
import com.hans.newslook.test.ZxingUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ZxingActivity extends BaseActivity {


    @BindView(R.id.tv_test)
    TextView tvTest;
    @BindView(R.id.pic_test)
    ImageView picTest;
    @BindView(R.id.btn_test)
    Button btnTest;
    @BindView(R.id.btn_test2)
    Button btnTest2;

    @Override
    public int getLayoutId() {
        return R.layout.activity_zxing;
    }

    @Override
    protected void initView() {
        super.initView();
        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bitmap = ZxingUtils.createBitmap("韩旭韩旭韩旭learn");
                picTest.setImageBitmap(bitmap);
            }
        });

        btnTest2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new IntentIntegrator(ZxingActivity.this)
                        .setOrientationLocked(false)
                        .setCaptureActivity(ScanActivity.class) // 设置自定义的activity是ScanActivity
                        .initiateScan(); // 初始化扫描
            }
        });



    }

    // 通过 onActivityResult的方法获取扫描回来的值
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if(intentResult != null) {
            if(intentResult.getContents() == null) {
                Toast.makeText(this,"内容为空",Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this,"扫描成功",Toast.LENGTH_LONG).show();
                // ScanResult 为 获取到的字符串
                String ScanResult = intentResult.getContents();
                tvTest.setText(ScanResult);
            }
        } else {
            super.onActivityResult(requestCode,resultCode,data);
        }
    }
}



