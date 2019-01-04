package com.wallan.multimediacamera.library.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.media.MediaMetadataRetriever;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wallan.baseui.util.DpdipUtil;
import com.wallan.common.utils.library.LogUtils;
import com.wallan.common.utils.library.SchedulerProvider;
import com.wallan.multimediacamera.library.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by hanxu on 2018/5/30.
 */

public class RecordButtonView extends RelativeLayout {
    private Context context;

    private int defaultWidth;
    private int defaultHeight;

    private int mProgress = 0;
    private Disposable disposable;
    private String timeZero = "0:00";

    Paint paint = new Paint();

    private ImageView recordImage;
    private TextView tvTimeNow;
    private int startProgress, endProgress;

    private boolean lastIsDeleteStatus = false;


    private List<Integer> groupSecond;

    public RecordButtonView(Context context) {
        this(context, null);
    }

    public RecordButtonView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RecordButtonView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView();
        setWillNotDraw(false);
    }


    private void initView() {
        defaultWidth = DpdipUtil.dip2px(context, 74);
        defaultHeight = DpdipUtil.dip2px(context, 100);


        paint.setColor(context.getResources().getColor(R.color.mmcamera_camera_setting_selected_color));
        paint.setStrokeWidth(DpdipUtil.dip2px(context, 4));
        paint.setStyle(Paint.Style.STROKE);

        View view = LayoutInflater.from(context).inflate(R.layout.mmcamera_custom_record_button_view_layout, this);

        recordImage = view.findViewById(R.id.iv_record_center);
        tvTimeNow = view.findViewById(R.id.tv_record_time);
        groupSecond = new ArrayList<>();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint paint1 = new Paint();
        paint1.setColor(context.getResources().getColor(R.color.mmcamera_camera_record_button_circle_color));
        paint1.setStrokeWidth(DpdipUtil.dip2px(context, 4));
        paint1.setStyle(Paint.Style.STROKE);

        paint1.setAntiAlias(true);
        paint.setAntiAlias(true);

        RectF oval = new RectF();
        oval.left = DpdipUtil.dip2px(context, 2);
        oval.top = DpdipUtil.dip2px(context, 2);
        oval.right = DpdipUtil.dip2px(context, 72);
        oval.bottom = DpdipUtil.dip2px(context, 72);
        //画整圆弧
        canvas.drawArc(oval, -90, 360, false, paint1);
        //已使用多少圆弧
        canvas.drawArc(oval, -90, mProgress, false, paint);
        int gapIndex = 0;
        for (int i = 0; i < groupSecond.size(); i++) {
            gapIndex += groupSecond.get(i);
            canvas.drawArc(oval, gapIndex - 92, 3, false, paint1);
        }


        Paint paint2 = new Paint();
        paint2.setColor(context.getResources().getColor(R.color.mmcamera_camera_record_circle_color));
        paint2.setStrokeWidth(DpdipUtil.dip2px(context, 4));
        paint2.setStyle(Paint.Style.STROKE);
        paint2.setAntiAlias(true);

        int size = groupSecond.size();
        if (lastIsDeleteStatus && size > 0) {
            int lastLength = groupSecond.get(size - 1);
            canvas.drawArc(oval, gapIndex - 90 - lastLength, lastLength, false, paint2);
        }

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


    public void drawCircle() {
        if (disposable == null) {
            if (lastIsDeleteStatus) {
                lastIsDeleteStatus = false;
            }
            recordImage.setSelected(true);
            startProgress = mProgress;
            disposable = Observable.interval(83, TimeUnit.MILLISECONDS)
                    .subscribeOn(SchedulerProvider.ioThread())
                    .observeOn(SchedulerProvider.uiThread())
                    .subscribe(new Consumer<Long>() {
                        @Override
                        public void accept(Long aLong) throws Exception {
                            mProgress += 1;
                            postInvalidate();
                            showCurrentRecordTime();
                            if (mProgress >= 360) {
                                if (onRecordButtonListener != null) {
                                    onRecordButtonListener.onTimeEnd();
                                }
                                LogUtils.d("录制完成");
                            }
                        }
                    });
        }
        MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
    }

    private void showCurrentRecordTime() {
        if (mProgress / 12 < 10) {
            tvTimeNow.setText("0:0" + mProgress / 12);
        } else {
            tvTimeNow.setText("0:" + mProgress / 12);
        }
    }

    public long getCurrentSecond() {
        return ((long) mProgress) / 12l;
    }

    public void pauseDrawCircle() {
        recordImage.setSelected(false);
        endProgress = mProgress;
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
            disposable = null;
        }
        groupSecond.add(endProgress - startProgress);
        postInvalidate();
    }

    public void stopDrawCircle() {
        recordImage.setSelected(false);
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
            disposable = null;
        }
        groupSecond.clear();
        mProgress = 0;
        tvTimeNow.setText(timeZero);
        postInvalidate();
    }


    public void deletePreviousVideo() {


        if (groupSecond.size() > 0) {
            if (!lastIsDeleteStatus) {
                lastIsDeleteStatus = true;
                postInvalidate();
                return;
            }
            lastIsDeleteStatus = false;
            int lastIndex = groupSecond.size() - 1;
            mProgress -= groupSecond.get(lastIndex);
            groupSecond.remove(lastIndex);
            showCurrentRecordTime();
            postInvalidate();
            onRecordButtonListener.onDeletePrevious(true);
            if (groupSecond.size() == 0) {
                onRecordButtonListener.onDeletePrevious(false);
            }
        } else {
            onRecordButtonListener.onDeletePrevious(false);
        }


    }


    /**
     * 回调接口
     */
    public interface OnRecordButtonListener {
        void onTimeEnd();

        void onDeletePrevious(boolean success);
    }

    private OnRecordButtonListener onRecordButtonListener;

    public OnRecordButtonListener getOnRecordButtonListener() {
        return onRecordButtonListener;
    }

    public void setOnRecordButtonListener(OnRecordButtonListener onRecordButtonListener) {
        this.onRecordButtonListener = onRecordButtonListener;
    }
}
