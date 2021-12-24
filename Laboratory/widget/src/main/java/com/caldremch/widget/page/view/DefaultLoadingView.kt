package com.caldremch.widget.page.view

import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.animation.ValueAnimator.AnimatorUpdateListener
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import com.caldremch.widget.page.protocol.IPageLoading

/**
 *
 * @author: Caldremch
 *
 * @date: 2020/7/10
 *
 * @describe:
 *
 **/

class DefaultLoadingView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr),
    IPageLoading {

    private var mWidth = 0f
    private var mPadding = 0f
    private var mStartAngle = 0f
    private var mPaint: Paint? = null

    init {
        initPaint()
    }

    private fun initPaint() {
        mPaint = Paint()
        mPaint!!.isAntiAlias = true
        mPaint!!.style = Paint.Style.STROKE
        mPaint!!.color = Color.WHITE
        mPaint!!.strokeWidth = 3.5f
    }

    fun setColor(@ColorRes color: Int) {
        mPaint!!.color = ContextCompat.getColor(context, color)
        invalidate()
    }


    fun startAnim() {
        stopAnim()
        startViewAnim(0f, 1f, 1000)
    }

    fun stopAnim() {
        if (mValueAnimator != null) {
            clearAnimation()
            mValueAnimator!!.repeatCount = 1
            mValueAnimator!!.cancel()
            mValueAnimator!!.end()
        }
    }

    fun isRunning(): Boolean {
        return mValueAnimator?.isRunning ?: false
    }

    private var mValueAnimator: ValueAnimator? = null

    private fun startViewAnim(
        startF: Float,
        endF: Float,
        time: Long
    ): ValueAnimator? {
        mValueAnimator = ValueAnimator.ofFloat(startF, endF)
        mValueAnimator?.apply {
            duration = time
            interpolator = LinearInterpolator()
            repeatCount = ValueAnimator.INFINITE //无限循环
            repeatMode = ValueAnimator.RESTART //
            addUpdateListener(AnimatorUpdateListener { valueAnimator ->
                val value = valueAnimator.animatedValue as Float
                mStartAngle = 360 * value
                invalidate()
            })
            addListener(object : AnimatorListenerAdapter() {
            })
            if (!isRunning) {
                start()
            }
        }

        return mValueAnimator
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        mWidth = if (measuredWidth > height) {
            measuredHeight.toFloat()
        } else {
            measuredWidth.toFloat()
        }
        mPadding = 5f
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        mPaint!!.color = Color.parseColor("#E6E6E6")
        canvas.drawCircle(mWidth / 2, mWidth / 2, mWidth / 2 - mPadding, mPaint!!)
        mPaint!!.color = Color.parseColor("#000000")
        val rectF = RectF(mPadding, mPadding, mWidth - mPadding, mWidth - mPadding)
        canvas.drawArc(rectF, mStartAngle, 100f, false, mPaint!!) //第四个参数是否显示半径
    }

    override fun startLoading() {
        startAnim()
    }

    override fun stopLoading() {
        stopAnim()
    }

    override fun loadingView(): View {
        return this
    }

}