package com.hans.newslook.ui.activity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.hans.newslook.R;
import com.hans.newslook.base.BaseActivity;
import com.hans.newslook.utils.wx.WxShareUtils;

import butterknife.BindView;

public class ExpressHomeActivity extends BaseActivity {


    @BindView(R.id.express_input)
    EditText expressInput;
    @BindView(R.id.btn_share)
    Button btnShare;


    private String videoUrl="http://v6c.music.126.net/20180404182742/303e480fc7ab204443de0b40377c7d4a/web/cloudmusic/IDEwJTI4IDBgITAkMDAwIg==/mv/5843935/24b31be9a8faf365a554ca4db0ec1177.mp4";


    @Override
    public int getLayoutId() {
        return R.layout.activity_express_home;
    }

    @Override
    protected void init() {
        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = expressInput.getEditableText().toString();
                WxShareUtils.shareText(context, content);
            }
        });

    }
}
