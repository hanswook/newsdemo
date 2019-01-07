package com.hans.newslook.test.custom;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * @author Hans
 * @create 2019/1/7
 * @Describe
 */
public class BounceBall extends View {

    private Ball mBall;
    private ValueAnimator mAnimator;

    private Paint mPaint;
    private Point mCoo;

    private float defaultRadius = 20f;
    private float defaultVX = 10;
    private float defaultVY = 0;
    private int defaultColor = Color.RED;
    private float defaultA = 9.8f;


    public BounceBall(Context context) {
        this(context, null);
    }

    public BounceBall(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BounceBall(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mCoo = new Point(20, 20);

        mBall = new Ball();
        mBall.vX = defaultVX;
        mBall.vY = defaultVY;
        mBall.aX = 0;
        mBall.ay = defaultA;
        mBall.color = defaultColor;
        mBall.radius = defaultRadius;

        //初始画笔
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        //初始化时间流ValueAnimator
        mAnimator = ValueAnimator.ofFloat(0, 1);
        mAnimator.setRepeatCount(-1);
        mAnimator.setDuration(1000);
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                updateBall();//更新小球信息
                invalidate();
            }
        });


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawARGB(88,0,155,0);
        canvas.save();
        canvas.translate(mCoo.x, mCoo.y);
        drawBall(canvas, mBall);
        canvas.restore();
    }


    private void updateBall() {
        mBall.x += mBall.vX;
    }

    /**
     * 绘制小球
     *
     * @param canvas
     * @param ball
     */
    private void drawBall(Canvas canvas, Ball ball) {
        mPaint.setColor(ball.color);
        canvas.drawCircle(ball.x, ball.y, ball.radius, mPaint);
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

}
