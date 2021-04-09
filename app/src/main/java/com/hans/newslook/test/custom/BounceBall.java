package com.hans.newslook.test.custom;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.hans.newslook.utils.baseutils.LogUtils;

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
    private float defaultF = 0.95f;

    private float mWidth = 0;
    private float mHeight = 0;


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
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();

        if (mWidth > 0) {
            mWidth = mWidth - mCoo.x * 2;
        }


        LogUtils.e("mWidth:" + mWidth + ",:mHeight:" + mHeight);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawARGB(88, 0, 155, 0);
        canvas.save();
        canvas.translate(mCoo.x, mCoo.y);
        drawBall(canvas, mBall);
        canvas.restore();
    }


    private void updateBall() {
        if (mBall.x > mWidth) {
            mBall.vX = -mBall.vX;
        } else if (mBall.x < 0) {
            mBall.vX = -mBall.vX;
        }

        mBall.vY += mBall.ay;
        mBall.y += mBall.vY;

        if (mBall.y > 860 && mBall.vY > 0) {
            mBall.vY = -mBall.vY;
            mBall.y += mBall.vY;
            mBall.vY = mBall.vY * defaultF;
        }


        mBall.x += mBall.vX;
        LogUtils.d("updateBall", "mBall.x:" + mBall.x + ",:mBall.y:" + mBall.y + ",mBall.vY:" + mBall.vY);
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
