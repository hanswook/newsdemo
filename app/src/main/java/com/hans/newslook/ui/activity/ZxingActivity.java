package com.hans.newslook.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.hans.newslook.R;
import com.hans.newslook.base.BaseActivity;
import com.hans.newslook.test.ZxingUtils;
import com.hans.newslook.utils.Constants;
import com.hans.newslook.utils.baseutils.LogUtils;
import com.hans.newslook.utils.baseutils.SaveImageUtils;
import com.hans.newslook.utils.baseutils.ToastUtils;

import butterknife.BindView;

public class ZxingActivity extends BaseActivity {


    @BindView(R.id.tv_test)
    TextView tvTest;
    @BindView(R.id.pic_test)
    ImageView picTest;
    @BindView(R.id.btn_test)
    Button btnTest;
    @BindView(R.id.btn_test2)
    Button btnTest2;
    @BindView(R.id.btn_test3)
    Button btnTest3;
    @BindView(R.id.zxing_edit)
    EditText zxingEdit;

    @Override
    public int getLayoutId() {
        return R.layout.activity_zxing;
    }

    @Override
    protected void init() {
        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String result = zxingEdit.getEditableText().toString();
                if (result.equalsIgnoreCase("")) {
                    ToastUtils.show("请输入内容。才可以生成二维码");
                } else {
                    Bitmap bitmap = ZxingUtils.createBitmap(result);
                    picTest.setImageBitmap(bitmap);
                }
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
        btnTest3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String filePath = Constants.HOME_PATH;
                picTest.setDrawingCacheEnabled(true);
                Bitmap bitmap = picTest.getDrawingCache();
                LogUtils.e(TAG, "bitmap:" + bitmap.toString());
                SaveImageUtils.createFile(bitmap, context, filePath);
                picTest.setDrawingCacheEnabled(false);
            }
        });


    }

    // 通过 onActivityResult的方法获取扫描回来的值
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (intentResult != null) {
            if (intentResult.getContents() == null) {
                Toast.makeText(this, "内容为空", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "扫描成功", Toast.LENGTH_LONG).show();
                // ScanResult 为 获取到的字符串
                String ScanResult = intentResult.getContents();
                tvTest.setText(ScanResult);
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}



