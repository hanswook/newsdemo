package com.hans.newslook.ui.behavior;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
//import  androidx.annotation.NonNull;
//import androidx.coordinatorlayout.widget.CoordinatorLayout;
//import com.google.android.material.floatingactionbutton.FloatingActionButton;
//import androidx.recyclerview.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.hans.newslook.utils.baseutils.LogUtils;

public class FabFollowListBehavior extends CoordinatorLayout.Behavior<FloatingActionButton> {

    private static final int MIN_DY = 10;

    public FabFollowListBehavior() {
    }

    public FabFollowListBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public void onNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull FloatingActionButton child, @NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type);

        //平移隐现
        if (dyConsumed > MIN_DY) {//上滑：消失
            showOrNot(coordinatorLayout, child, false).start();
//            child.hide();
        } else if (dyConsumed < -MIN_DY) {//下滑滑：显示
            showOrNot(coordinatorLayout, child, true).start();
//            child.show();
        }
    }

    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull FloatingActionButton child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
        return true;
    }

    private Animator showOrNot(CoordinatorLayout coordinatorLayout, final View fab, boolean show) {
        //获取fab头顶的高度
        int hatHeight = coordinatorLayout.getBottom() - fab.getBottom() + fab.getHeight();
        int end = show ? 0 : hatHeight;
        float start = fab.getTranslationY();
        ValueAnimator animator = ValueAnimator.ofFloat(start, end);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                fab.setTranslationY((Float) valueAnimator.getAnimatedValue());

            }
        });
        return animator;
    }

}
