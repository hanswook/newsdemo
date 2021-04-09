package com.hans.newslook.ui.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import  androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

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
import com.hans.newslook.utils.ZxingUtils;
import com.hans.newslook.utils.Constants;
import com.hans.newslook.utils.baseutils.LogUtils;
import com.hans.newslook.utils.baseutils.SaveImageUtils;
import com.hans.newslook.utils.baseutils.ToastUtils;
import com.hans.newslook.utils.permission.PermissionUtil;

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

    private final String permission = Manifest.permission.WRITE_EXTERNAL_STORAGE;


    @Override
    public int getLayoutId() {
        return R.layout.activity_zxing;
    }

    @Override
    protected void init() {
        PermissionUtil.checkPermission(this, permission, 321);
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
                saveImageFile();
            }
        });


    }

    private void saveImageFile() {
        String filePath = Constants.HOME_PATH;
        picTest.setDrawingCacheEnabled(true);
        Bitmap bitmap = picTest.getDrawingCache();
        if (bitmap == null) {
            return;
        }
        if (!PermissionUtil.checkPermission(this, permission, 321)) {
            return;
        }
        SaveImageUtils.createFile(bitmap, context, filePath);
        picTest.setDrawingCacheEnabled(false);
    }


    private void showDialogTipUserRequestPermission() {
        new AlertDialog.Builder(this)
                .setTitle("存储权限不可用")
                .setMessage("由于支付宝需要获取存储空间，为你存储个人信息；\n否则，您将无法正常使用支付宝")
                .setPositiveButton("立即开启", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        PermissionUtil.checkPermission(ZxingActivity.this, permission, 321);
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).setCancelable(false).show();
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        LogUtils.e(TAG, "onRequestPermissionsResult requestCode:" + requestCode);
        LogUtils.e(TAG, "onRequestPermissionsResult permissions:" + permissions.length);
        LogUtils.e(TAG, "onRequestPermissionsResult grantResults:" + grantResults.length);
        for (String permission : permissions) {
            LogUtils.e(TAG, "onRequestPermissionsResult permission:" + permission);
        }
        for (int result : grantResults) {
            LogUtils.e(TAG, "onRequestPermissionsResult result:" + result);
        }
        if (requestCode == 321) {
            if (grantResults[0] >= 0)
                saveImageFile();
            else
                ToastUtils.show("权限请求失败");
        }
    }
}



