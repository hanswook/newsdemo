package com.hans.newslook.test.widget;

import android.animation.FloatEvaluator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.LinearInterpolator;

import com.hans.newslook.test.utils.HelpDraw;
import com.hans.newslook.utils.baseutils.DensityUtils;
import com.hans.newslook.utils.baseutils.LogUtils;

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

    private int startX = 0;
    private ValueAnimator mAnimator;


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
        mPaint.setStrokeWidth(5);
        mPaint.setStyle(Paint.Style.STROKE);


        //准备屏幕尺寸
        mWinSize = new Point();
        mCoo = new Point(0, 0);
        int screenW = DensityUtils.getScreenW(getContext());
        int screenH = DensityUtils.getScreenH(getContext());
        mWinSize.x = screenW;
        mWinSize.y = screenH;
        mGridPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        mAnimator = ValueAnimator.ofFloat(0, 1);
        mAnimator.setRepeatCount(-1);
        mAnimator.setDuration(1000);
        mAnimator.setInterpolator(new LinearInterpolator());
        mAnimator.setEvaluator(new FloatEvaluator());
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float animatedValue = (float) animation.getAnimatedValue();
                //更新小球信息
                updateStartX(animatedValue);

                invalidate();
            }
        });


    }

    private long lasttime = 0;

    private void updateStartX(float animatedValue) {
        long nowTime = System.currentTimeMillis();
        Log.e("updateStartX", "animatedValue：" + animatedValue + ":--timenow:" + (nowTime - lasttime));
        lasttime = nowTime;
        startX += 4;
        if (startX > 80) {
            startX -= 80;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mAnimator.start();//开启时间流
                break;
            case MotionEvent.ACTION_UP:
                mAnimator.pause();//暂停时间流
                break;
        }
        return true;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawColor(canvas);
        // drawGrid 绘制网格：release：
        HelpDraw.drawGrid(canvas, mWinSize, mGridPaint);
        // drawCoo 绘制坐标系:release：
        HelpDraw.drawCoo(canvas, mCoo, mWinSize, mGridPaint);

        drawPathSin(canvas);

//        drawPoint(canvas);
//        drawLines(canvas);
//        drawRect(canvas);


//        testStateSaveStore(canvas);
//        testStateSkew(canvas);
//        drawLikeCircle(canvas);

//        drawNStar(canvas, 5, 100, 50);

//        drawHexagon(canvas, 60, 40, 50, 70, 90, 100);

    }

    private void drawPathSin(Canvas canvas) {
        mPaint.setStrokeWidth(2);
        Path mPath = new Path();
        mPath.moveTo(startX - 80, 100);

        int size = 50;
        for (int i = 0; i < 20; i++) {
            //rQuadTo
            mPath.rQuadTo(20, size, 40, 0);
            mPath.rQuadTo(20, -size, 40, 0);
        }

        canvas.drawPath(mPath, mPaint);

    }

    private void drawHexagon(Canvas canvas, int one, int two, int three, int four, int five, int six) {

        canvas.getSaveCount();
        for (int i = 0; i < canvas.getSaveCount(); i++) {
            canvas.restore();
        }

        mPaint.setColor(Color.BLUE);

        canvas.translate(400, 500);
//        canvas.rotate(-90, 0, 0);

        Path path = new Path();
        path.moveTo(100, 0);


        float radius = 100;

        float degree = 360f / 6f;

        for (int i = 0; i < 6; i++) {

            float x = (float) (radius * Math.cos(Math.toRadians(degree * i)));
            float y = (float) (radius * Math.sin(Math.toRadians(degree * i)));
            LogUtils.e("degree:,x:" + x + ",y:" + y);

            path.lineTo(x, y);

        }

        path.close();

        canvas.drawPath(path, mPaint);
        canvas.save();
    }

    private void drawNStar(Canvas canvas, int num, float rOut, float rIn) {
        canvas.save();
        canvas.translate(100, 100);
        canvas.translate(rOut, rOut);

        canvas.rotate(-90, 0, 0);

        Path path = new Path();
        path.moveTo(rOut, 0);

        float degre = 360f / 2 / num;
        for (int i = 0; i < num; i++) {
            float degre2 = (float) Math.toRadians(degre * i * 2);
            float x = (float) (rOut * Math.cos(degre2));
            float y = (float) (rOut * Math.sin(degre2));
            LogUtils.e("degre2:" + degre2 + ",x:" + x + ",y:" + y);
            path.lineTo(x, y);

            float degre3 = (float) Math.toRadians(degre * (i * 2 + 1));
            float xIn = (float) (rIn * Math.cos(degre3));
            float yIn = (float) (rIn * Math.sin(degre3));
            LogUtils.e("degre3:" + degre3 + ",xIn:" + xIn + ",yIn:" + yIn);
            path.lineTo(xIn, yIn);

        }
        path.close();

        canvas.drawPath(path, mPaint);
        canvas.save();
    }


    private void testStateSkew(Canvas canvas) {

        canvas.drawRect(100, 100, 300, 250, mPaint);

        canvas.save();

        mPaint.setColor(Color.parseColor("#880fb5fd"));

//        对于skew(float sx,float sy),sx和sy分别表示将画布在x和y方向上倾斜相应的角度对应的tan值
        canvas.skew(0, 1);

        canvas.drawRect(100, 100, 300, 250, mPaint);
    }

    private void testStateSaveStore(Canvas canvas) {

        canvas.drawLine(500, 200, 900, 400, mPaint);

        canvas.drawRect(100, 100, 300, 200, mPaint);

        canvas.save();//保存canvas状态

        //(角度,中心点x,中心点y)
        canvas.rotate(45, 100, 100);

        mPaint.setColor(Color.parseColor("#880FB5FD"));

        int left = 100;
        int top = 100;
        canvas.drawRect(left, top, left + 200, top + 100, mPaint);

        canvas.restore();//图层向下合并
        canvas.save();//保存canvas状态


//        canvas.rotate(45, 400, 400);

        left = 400;
        top = 400;
        canvas.drawRect(left, top, left + 200, top + 100, mPaint);

        canvas.save();//保存canvas状态

//        canvas.restore();//图层向下合并

        canvas.rotate(45, 400, 800);

        left = 500;
        top = 800;

        canvas.drawRect(left, top, left + 200, top + 100, mPaint);

        canvas.restore();//图层向下合并


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
        mPaint.setColor(Color.RED);
        //255值最大
        mPaint.setAlpha(160);
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
