package com.caldremch.common.widget.status;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import androidx.annotation.ColorRes;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

/**
 * @author Caldremch
 * @date 2019-02-28 14:26
 * @describe
 **/
public class LoadingCircleRing extends View {

    private float mWidth = 0f;
    private float mPadding = 0f;
    private float mStartAngle = 0f;
    private Paint mPaint;


    public LoadingCircleRing(Context context) {
        this(context, null);
    }

    public LoadingCircleRing(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingCircleRing(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }


    private void initPaint() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.WHITE);
        mPaint.setStrokeWidth(3.5f);
    }

    public void setColor(@ColorRes int color) {
        mPaint.setColor(ContextCompat.getColor(getContext(), color));
        invalidate();
    }


    public void startAnim() {
        stopAnim();
        startViewAnim(0f, 1f, 1000);
    }

    public void stopAnim() {
        if (mValueAnimator != null) {
            clearAnimation();
            mValueAnimator.setRepeatCount(1);
            mValueAnimator.cancel();
            mValueAnimator.end();
        }
    }

    public boolean isRunning() {
        if (mValueAnimator == null) return false;
        return mValueAnimator.isRunning();
    }

    ValueAnimator mValueAnimator;

    private ValueAnimator startViewAnim(float startF, final float endF, long time) {
        mValueAnimator = ValueAnimator.ofFloat(startF, endF);

        mValueAnimator.setDuration(time);
        mValueAnimator.setInterpolator(new LinearInterpolator());
        mValueAnimator.setRepeatCount(ValueAnimator.INFINITE); //无限循环
        mValueAnimator.setRepeatMode(ValueAnimator.RESTART); //

        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {

                float value = (float) valueAnimator.getAnimatedValue();
                mStartAngle = 360 * value;

                invalidate();
            }
        });
        mValueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }
        });
        if (!mValueAnimator.isRunning()) {
            mValueAnimator.start();
        }

        return mValueAnimator;
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if (getMeasuredWidth() > getHeight()) {
            mWidth = getMeasuredHeight();
        } else {
            mWidth = getMeasuredWidth();
        }
        mPadding = 5;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPaint.setColor(Color.parseColor("#E6E6E6"));
        canvas.drawCircle(mWidth / 2, mWidth / 2, mWidth / 2 - mPadding, mPaint);
        mPaint.setColor(Color.parseColor("#FF6B00"));
        RectF rectF = new RectF(mPadding, mPadding, mWidth - mPadding, mWidth - mPadding);
        canvas.drawArc(rectF, mStartAngle, 100, false, mPaint); //第四个参数是否显示半径

    }

}
