package com.wallan.multimediacamera.library.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wallan.baseui.util.DpdipUtil;
import com.wallan.multimediacamera.library.R;

/**
 * Created by hanxu on 2018/5/31.
 */

public class BottomTabView extends FrameLayout implements View.OnClickListener {
    private Context context;

    public BottomTabView(Context context) {
        this(context, null);
    }

    public BottomTabView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BottomTabView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        if (widthSpecMode == MeasureSpec.AT_MOST && heightSpecMode ==
                MeasureSpec.AT_MOST)
            setMeasuredDimension(defaultWidth, defaultHeight);
        else if (widthSpecMode == MeasureSpec.AT_MOST)
            setMeasuredDimension(defaultWidth, heightSpecSize);
        else if (heightSpecMode == MeasureSpec.AT_MOST)
            setMeasuredDimension(widthSpecSize, defaultHeight);

    }

    int defaultWidth;
    int defaultHeight;

    View view;
    LinearLayout ll;
    TextView tv1, tv2, tv3;
    TextView currentSelect;

    private void initView() {
        defaultWidth = -1;
        defaultHeight = DpdipUtil.dip2px(context, 33);
        view = LayoutInflater.from(context).inflate(R.layout.mmcamera_custom_bottom_view_layout, this);
        ll = view.findViewById(R.id.ll);
        tv1 = view.findViewById(R.id.tv_1);
        tv2 = view.findViewById(R.id.tv_2);
        tv3 = view.findViewById(R.id.tv_3);
        tv1.setOnClickListener(this);
        tv2.setOnClickListener(this);
        tv3.setOnClickListener(this);
        tv2.setSelected(true);
        currentSelect = tv2;
    }

    public void move1() {
        ll.animate().translationX(DpdipUtil.dip2px(context, 42)).start();
        changeSelectedView(tv1);
    }

    public void move2() {
        ll.animate().translationX(0).start();
        changeSelectedView(tv2);
    }

    public void move3() {
        ll.animate().translationX(-DpdipUtil.dip2px(context, 38)).start();
        changeSelectedView(tv3);
    }

    private void changeSelectedView(TextView tv) {
        currentSelect.setSelected(false);
        currentSelect = tv;
        currentSelect.setSelected(true);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.tv_1) {
            move1();
        } else if (id == R.id.tv_2) {
            move2();
        } else if (id == R.id.tv_3) {
            move3();
        }
        if (onTabSelectedListener != null) {
            onTabSelectedListener.onTabSelected(id);
        }
    }

    public interface OnTabSelectedListener {
        void onTabSelected(int id);
    }

    private OnTabSelectedListener onTabSelectedListener;

    public OnTabSelectedListener getOnTabSelectedListener() {
        return onTabSelectedListener;
    }

    public void setOnTabSelectedListener(OnTabSelectedListener onTabSelectedListener) {
        this.onTabSelectedListener = onTabSelectedListener;
    }
}
