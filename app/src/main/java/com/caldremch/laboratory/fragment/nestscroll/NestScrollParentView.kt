package com.caldremch.laboratory.fragment.nestscroll

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import androidx.core.view.NestedScrollingParent
import androidx.core.view.NestedScrollingParentHelper

/**
 *
 * @auther Caldremch
 *
 * @email finishmo@qq.com
 *
 * @date 2021/8/11 15:31
 *
 * @description https://www.jianshu.com/p/e333f11f29aa
 *
 *
 */
class NestScrollParentView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), NestedScrollingParent {

    private val nestedScrollingParentHelper = NestedScrollingParentHelper(this)


    private val TAG = this.javaClass.simpleName

    /**
     *
     * 有嵌套滑动到来了，问下该父View是否接受嵌套滑动
     * 是否接受嵌套滑动
     * @param child          嵌套滑动对应的父类的子类(因为嵌套滑动对于的父View不一定是一级就能找到的，可能挑了两级父View的父View，child的辈分>=target)
     * @param target           具体嵌套滑动的那个子类
     * @param axes 支持嵌套滚动轴。水平方向，垂直方向，或者不指定
     * @return 是否接受该嵌套滑动
     */
    override fun onStartNestedScroll(child: View, target: View, axes: Int): Boolean {
        return true
    }


    /**
     * 该父View接受了嵌套滑动的请求该函数调用。onStartNestedScroll返回true该函数会被调用。
     * 参数和onStartNestedScroll一样
     * View.java源码
     *  public boolean startNestedScroll(int axes) {
    if (hasNestedScrollingParent()) {
    // Already in progress
    return true;
    }
    if (isNestedScrollingEnabled()) {
    ViewParent p = getParent();
    View child = this;
    while (p != null) {
    try {
    if (p.onStartNestedScroll(child, this, axes)) {
    mNestedScrollingParent = p;
    p.onNestedScrollAccepted(child, this, axes);
    return true;
    }
     */
    override fun onNestedScrollAccepted(child: View, target: View, axes: Int) {
        nestedScrollingParentHelper.onNestedScrollAccepted(child, target, axes)
    }

    override fun getNestedScrollAxes(): Int {
        return nestedScrollingParentHelper.nestedScrollAxes
    }

    override fun onStopNestedScroll(child: View) {
        nestedScrollingParentHelper.onStopNestedScroll(child)
    }

    /**
     * 嵌套滑动的子View在滑动之后报告过来的滑动情况
     *
     * @param target       具体嵌套滑动的那个子类
     * @param dxConsumed   水平方向嵌套滑动的子View滑动的距离(消耗的距离)
     * @param dyConsumed   垂直方向嵌套滑动的子View滑动的距离(消耗的距离)
     * @param dxUnconsumed 水平方向嵌套滑动的子View未滑动的距离(未消耗的距离)
     * @param dyUnconsumed 垂直方向嵌套滑动的子View未滑动的距离(未消耗的距离)
     */
    override fun onNestedScroll(
        target: View,
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int
    ) {

    }


    /**
     * 在嵌套滑动的子View未滑动之前告诉过来的准备滑动的情况
     *
     * @param target   具体嵌套滑动的那个子类
     * @param dx       水平方向嵌套滑动的子View想要变化的距离
     * @param dy       垂直方向嵌套滑动的子View想要变化的距离
     * @param consumed 这个参数要我们在实现这个函数的时候指定，回头告诉子View当前父View消耗的距离
     * consumed[0] 水平消耗的距离，consumed[1] 垂直消耗的距离 好让子view做出相应的调整
     */
    override fun onNestedPreScroll(target: View, dx: Int, dy: Int, consumed: IntArray) {

        Log.d(TAG, "[${target.right}, $dx, $dy, $width]")
        if (dx > 0) {
            if ((target.right + dx) > width) {
                val currentConsumedDX = target.right + dx - width //多出来的
                offsetLeftAndRight(currentConsumedDX)
                consumed[0] += dx
            }
        } else {
            if (target.left + dx < 0) {
                val currentConsumedDX = target.left + dx //多出来的
                offsetLeftAndRight(currentConsumedDX)
                consumed[0] += dx
            }
        }

    }

    /**
     * 嵌套滑动的子View在fling之后报告过来的fling情况
     *
     * @param target    具体嵌套滑动的那个子类
     * @param velocityX 水平方向速度
     * @param velocityY 垂直方向速度
     * @param consumed  子view是否fling了
     * @return true 父View是否消耗了fling
     */
    override fun onNestedFling(
        target: View,
        velocityX: Float,
        velocityY: Float,
        consumed: Boolean
    ): Boolean {
        return false
    }

    /**
     * 在嵌套滑动的子View未fling之前告诉过来的准备fling的情况
     *
     * @param target    具体嵌套滑动的那个子类
     * @param velocityX 水平方向速度
     * @param velocityY 垂直方向速度
     * @return true 父View是否消耗了fling
     */
    override fun onNestedPreFling(target: View, velocityX: Float, velocityY: Float): Boolean {
        return false
    }

}