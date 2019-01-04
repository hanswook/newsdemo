package com.hans.newslook.test.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.hans.newslook.test.utils.HelpDraw;
import com.hans.newslook.utils.baseutils.DensityUtils;

/**
 * @author Hans
 * @create 2019/1/4
 * @Describe
 */
public class CanvasView extends View {

    private Paint mPaint;
    private Paint mGridPaint;
    private Point mWinSize;
    private Point mCoo;

    public CanvasView(Context context) {
        this(context, null);
    }

    public CanvasView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CanvasView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(10);
        mPaint.setStyle(Paint.Style.FILL);


        //准备屏幕尺寸
        mWinSize = new Point();
        mCoo = new Point(0, 0);
        int screenW = DensityUtils.getScreenW(getContext());
        int screenH = DensityUtils.getScreenH(getContext());
        mWinSize.x = screenW;
        mWinSize.y = screenH;
        mGridPaint = new Paint(Paint.ANTI_ALIAS_FLAG);


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawColor(canvas);
        //TODO drawGrid 绘制网格：release：
        HelpDraw.drawGrid(canvas, mWinSize, mGridPaint);
        //TODO drawCoo 绘制坐标系:release：
        HelpDraw.drawCoo(canvas, mCoo, mWinSize, mGridPaint);

//        drawPoint(canvas);
//        drawLines(canvas);
//        drawRect(canvas);
        drawLikeCircle(canvas);
    }


    private void drawColor(Canvas canvas) {
//        canvas.drawColor(Color.GREEN);
        canvas.drawColor(Color.parseColor("#E0F7F5"));
//        canvas.drawARGB(255, 224, 247, 245);
//        三者等价
//        canvas.drawRGB(224, 247, 245);

    }


    private void drawPoint(Canvas canvas) {

        canvas.drawPoint(50, 50, mPaint);

        canvas.drawPoints(new float[]{
                200, 200, 400, 400,
                400, 400, 500, 500,
                600, 400, 700, 350,
                800, 300, 900, 300
        }, mPaint);
    }


    private void drawLines(Canvas canvas) {
        canvas.drawLines(new float[]{
                100, 100, 300, 300,
                300, 300, 500, 100,
                500, 98, 700, 700,
                700, 700, 100, 1200
        }, mPaint);
    }


    private void drawRect(Canvas canvas) {
        Rect rect = new Rect(200, 200, 500, 700);
        RectF rect2 = new RectF(700, 200, 1000, 700);
        canvas.drawRect(rect, mPaint);

        canvas.drawRoundRect(rect2, 50, 50, mPaint);
    }


    private void drawLikeCircle(Canvas canvas) {
        canvas.drawCircle(200, 200, 150, mPaint);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            canvas.drawOval(100, 300, 350, 600, mPaint);
        }

        RectF rectF = new RectF(100, 700, 350, 1000);
        canvas.drawOval(rectF, mPaint);

        RectF rectFArc = new RectF(400, 200, 800, 700);
        canvas.drawArc(rectFArc, -40, 50, true, mPaint);


        RectF rectFArc2 = new RectF(400, 800, 800, 1300);
        canvas.drawArc(rectFArc2, -50, 50, false, mPaint);


    }

}
