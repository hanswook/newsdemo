package com.wallan.multimediacamera.library.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.wallan.baseui.util.DpdipUtil;
import com.wallan.multimediacamera.library.R;

/**
 * Created by hanxu on 2018/5/30.
 */

public class MultiPhotoView extends View {
    private Context context;

    private int defaultWidth;
    private int defaultHeight;

    private int rectSideWidth;

    Paint paint = new Paint();
    Paint paintGray = new Paint();
    Paint paintGreen = new Paint();


    public static final int MULTISHOT_MODE_NO = 200;
    public static final int MULTISHOT_MODE_TWO_H = 201;
    public static final int MULTISHOT_MODE_TWO_W = 202;
    public static final int MULTISHOT_MODE_FOUR = 203;
    public static final int MULTISHOT_MODE_NINE = 204;

    private int multiShotMode = MULTISHOT_MODE_NO;


    public MultiPhotoView(Context context) {
        this(context, null);
    }

    public MultiPhotoView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MultiPhotoView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView();
    }


    private void initView() {
        defaultWidth = DpdipUtil.dip2px(context, 50);
        defaultHeight = DpdipUtil.dip2px(context, 50);

        rectSideWidth = DpdipUtil.dip2px(context, 2);

        paint.setColor(context.getResources().getColor(R.color.mmcamera_camera_multi_view_side_color));
        paint.setStrokeWidth(rectSideWidth);
        paint.setStyle(Paint.Style.STROKE);

        paintGray.setColor(context.getResources().getColor(R.color.mmcamera_camera_multi_view_already));
        paintGray.setStyle(Paint.Style.FILL);

        paintGreen.setColor(context.getResources().getColor(R.color.mmcamera_camera_multi_view_current));
        paintGreen.setStyle(Paint.Style.FILL);

    }


    private int alreadySave = 0;

    private int currentIndex = 0;

    int rectSideLength;

    int paddingLeft;
    int paddingRight;
    int paddingTop;
    int paddingBottom;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int measureWidth = getMeasuredWidth();
        int measureHeight = getMeasuredHeight();


        if (measureWidth > measureHeight) {
            rectSideLength = measureHeight;
        } else {
            rectSideLength = measureWidth;
        }
        switch (multiShotMode) {
            case MULTISHOT_MODE_TWO_H:
                drawTwoHMode(canvas);
                break;
            case MULTISHOT_MODE_TWO_W:
                drawTwoWMode(canvas);
                break;
            case MULTISHOT_MODE_FOUR:
                drawFourMode(canvas);
                break;
            case MULTISHOT_MODE_NINE:
                drawNineMode(canvas);
                break;
            default:
                break;

        }

    }

    private void drawTwoHMode(Canvas canvas) {
        int moleculeLength;
        int other = (rectSideLength - 3 * rectSideWidth) % 2;
        rectSideLength = rectSideLength - other;
        moleculeLength = (rectSideLength - 3 * rectSideWidth) / 2;
        paddingLeft = (rectSideLength - moleculeLength - rectSideWidth * 2) / 2;
        canvas.drawRect(paddingLeft + rectSideWidth / 2, rectSideWidth / 2, paddingLeft + rectSideWidth / 2 + moleculeLength + rectSideWidth, (rectSideWidth + moleculeLength) * 2 + rectSideWidth / 2, paint);
        canvas.drawLine(paddingLeft, rectSideLength / 2, rectSideWidth + moleculeLength + paddingLeft, rectSideLength / 2, paint);

        int tempLeft = 0;
        int tempRight = 0;
        int tempTop = 0;
        int tempBottom = 0;
        int rowIndex = 0; //排
        int columnIndex = 0;//列
        if (alreadySave > 0) {
            for (int i = 0; i < alreadySave; i++) {
                rowIndex = i;
                tempLeft = rectSideWidth * (columnIndex + 1) + moleculeLength * columnIndex + paddingLeft;
                tempTop = rectSideWidth * (rowIndex + 1) + moleculeLength * rowIndex + paddingTop;
                tempRight = (rectSideWidth + moleculeLength) * (columnIndex + 1) + paddingLeft;
                tempBottom = (rectSideWidth + moleculeLength) * (rowIndex + 1) + paddingTop;
                canvas.drawRect(tempLeft, tempTop, tempRight, tempBottom, paintGray);
            }
        }


        int currentLeft;
        int currentRight;
        int currentTop;
        int currentBottom;
        int currentRowIndex = currentIndex;
        int currentColumnIndex = 0;

        currentLeft = rectSideWidth * (currentColumnIndex + 1) + moleculeLength * currentColumnIndex + paddingLeft;
        currentTop = rectSideWidth * (currentRowIndex + 1) + moleculeLength * currentRowIndex;
        currentRight = (rectSideWidth + moleculeLength) * (currentColumnIndex + 1) + paddingLeft;
        currentBottom = (rectSideWidth + moleculeLength) * (currentRowIndex + 1);

        canvas.drawRect(currentLeft, currentTop, currentRight, currentBottom, paintGreen);
    }


    private void drawTwoWMode(Canvas canvas) {
        int moleculeLength;
        int other = (rectSideLength - 3 * rectSideWidth) % 2;
        rectSideLength = rectSideLength - other;
        moleculeLength = (rectSideLength - 3 * rectSideWidth) / 2;
        paddingTop = (rectSideLength - moleculeLength - rectSideWidth * 2) / 2;
        canvas.drawRect(rectSideWidth / 2, paddingTop + rectSideWidth / 2, rectSideLength - rectSideWidth / 2, paddingTop + rectSideWidth + moleculeLength + rectSideWidth / 2, paint);
        canvas.drawLine(rectSideLength / 2, paddingTop + rectSideWidth / 2, rectSideLength / 2, paddingTop + rectSideWidth + moleculeLength + rectSideWidth / 2, paint);

        int tempLeft = 0;
        int tempRight = 0;
        int tempTop = 0;
        int tempBottom = 0;
        int rowIndex = 0; //排
        int columnIndex = 0;//列
        if (alreadySave > 0) {
            for (int i = 0; i < alreadySave; i++) {
                columnIndex = i % 2;
                tempLeft = rectSideWidth * (columnIndex + 1) + moleculeLength * columnIndex;
                tempTop = rectSideWidth * (rowIndex + 1) + moleculeLength * rowIndex + paddingTop;
                tempRight = (rectSideWidth + moleculeLength) * (columnIndex + 1);
                tempBottom = (rectSideWidth + moleculeLength) * (rowIndex + 1) + paddingTop;
                canvas.drawRect(tempLeft, tempTop, tempRight, tempBottom, paintGray);
            }
        }


        int currentLeft;
        int currentRight;
        int currentTop;
        int currentBottom;
        int currentRowIndex = 0;
        int currentColumnIndex = currentIndex;

        currentLeft = rectSideWidth * (currentColumnIndex + 1) + moleculeLength * currentColumnIndex;
        currentTop = rectSideWidth * (currentRowIndex + 1) + moleculeLength * currentRowIndex + paddingTop;
        currentRight = (rectSideWidth + moleculeLength) * (currentColumnIndex + 1);
        currentBottom = (rectSideWidth + moleculeLength) * (currentRowIndex + 1) + paddingTop;

        canvas.drawRect(currentLeft, currentTop, currentRight, currentBottom, paintGreen);
    }

    private void drawNineMode(Canvas canvas) {
        int moleculeLength;
        int other = (rectSideLength - 4 * rectSideWidth) % 3;
        rectSideLength = rectSideLength - other;
        moleculeLength = (rectSideLength - 4 * rectSideWidth) / 3;
        canvas.drawRect(rectSideWidth / 2, rectSideWidth / 2, rectSideLength - rectSideWidth / 2, rectSideLength - rectSideWidth / 2, paint);

        canvas.drawLine(rectSideLength / 3, 0, rectSideLength / 3, rectSideLength, paint);
        canvas.drawLine(rectSideLength * 2 / 3, 0, rectSideLength * 2 / 3, rectSideLength, paint);

        canvas.drawLine(0, rectSideLength / 3, rectSideLength, rectSideLength / 3, paint);
        canvas.drawLine(0, rectSideLength * 2 / 3, rectSideLength, rectSideLength * 2 / 3, paint);

        int tempLeft = 0;
        int tempRight = 0;
        int tempTop = 0;
        int tempBottom = 0;
        int rowIndex = 0; //排
        int columnIndex = 0;//列
        if (alreadySave > 0) {
            for (int i = 0; i < alreadySave; i++) {
                rowIndex = i / 3;
                columnIndex = i % 3;

                tempLeft = rectSideWidth * (columnIndex + 1) + moleculeLength * columnIndex;
                tempTop = rectSideWidth * (rowIndex + 1) + moleculeLength * rowIndex;
                tempRight = (rectSideWidth + moleculeLength) * (columnIndex + 1);
                tempBottom = (rectSideWidth + moleculeLength) * (rowIndex + 1);
                canvas.drawRect(tempLeft, tempTop, tempRight, tempBottom, paintGray);
            }
        }


        int currentLeft;
        int currentRight;
        int currentTop;
        int currentBottom;
        int currentRowIndex = currentIndex / 3;
        int currentColumnIndex = currentIndex % 3;

        currentLeft = rectSideWidth * (currentColumnIndex + 1) + moleculeLength * currentColumnIndex;
        currentTop = rectSideWidth * (currentRowIndex + 1) + moleculeLength * currentRowIndex;
        currentRight = (rectSideWidth + moleculeLength) * (currentColumnIndex + 1);
        currentBottom = (rectSideWidth + moleculeLength) * (currentRowIndex + 1);

        canvas.drawRect(currentLeft, currentTop, currentRight, currentBottom, paintGreen);

    }

    private void drawFourMode(Canvas canvas) {
        int moleculeLength;
        int other = (rectSideLength - 3 * rectSideWidth) % 2;
        rectSideLength = rectSideLength - other;
        moleculeLength = (rectSideLength - 3 * rectSideWidth) / 2;
        canvas.drawRect(rectSideWidth / 2, rectSideWidth / 2, rectSideLength - rectSideWidth / 2, rectSideLength - rectSideWidth / 2, paint);
        canvas.drawLine(rectSideLength / 2, 0, rectSideLength / 2, rectSideLength, paint);
        canvas.drawLine(0, rectSideLength / 2, rectSideLength, rectSideLength / 2, paint);

        int tempLeft;
        int tempRight;
        int tempTop;
        int tempBottom;
        int tempLine;
        if (alreadySave > 0) {
            for (int i = 0; i < alreadySave; i++) {
                tempLine = i / 2;
                if (i % 2 == 0) {
                    tempLeft = rectSideWidth;
                    tempTop = rectSideWidth * (tempLine + 1) + moleculeLength * tempLine;
                    tempRight = rectSideWidth + moleculeLength;
                    tempBottom = (rectSideWidth + moleculeLength) * (tempLine + 1);
                } else {
                    tempLeft = rectSideWidth * 2 + moleculeLength;
                    tempTop = rectSideWidth * (tempLine + 1) + moleculeLength * tempLine;
                    tempRight = (rectSideWidth + moleculeLength) * 2;
                    tempBottom = (rectSideWidth + moleculeLength) * (tempLine + 1);
                }
                canvas.drawRect(tempLeft, tempTop, tempRight, tempBottom, paintGray);
            }
        }

        int currentLeft;
        int currentRight;
        int currentTop;
        int currentBottom;
        int top = currentIndex / 2;
        if (currentIndex % 2 == 0) {
            currentLeft = rectSideWidth;
            currentTop = rectSideWidth * (top + 1) + moleculeLength * top;
            currentRight = rectSideWidth + moleculeLength;
            currentBottom = (rectSideWidth + moleculeLength) * (top + 1);
        } else {
            currentLeft = rectSideWidth * 2 + moleculeLength;
            currentTop = rectSideWidth * (top + 1) + moleculeLength * top;
            currentRight = (rectSideWidth + moleculeLength) * 2;
            currentBottom = (rectSideWidth + moleculeLength) * (top + 1);
        }
        canvas.drawRect(currentLeft, currentTop, currentRight, currentBottom, paintGreen);

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

    public void drawNext() {
        currentIndex++;
        alreadySave++;
        invalidate();
    }


    public void resetCount() {
        currentIndex = 0;
        alreadySave = 0;
    }


    public void setMultishotMode(int mode) {
        switch (mode) {
            case 201:
                multiShotMode = MULTISHOT_MODE_TWO_H;
                break;
            case 202:
                multiShotMode = MULTISHOT_MODE_TWO_W;
                break;
            case 203:
                multiShotMode = MULTISHOT_MODE_FOUR;
                break;
            case 204:
                multiShotMode = MULTISHOT_MODE_NINE;
                break;
        }
        resetCount();
        invalidate();
    }
}
