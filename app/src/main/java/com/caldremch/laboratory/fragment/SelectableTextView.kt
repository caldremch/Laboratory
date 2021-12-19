package com.jixugou.ec.story.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ViewConfiguration
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView

/**
 *
 * @author Caldremch
 *
 * @email finishmo@qq.com
 *
 * @date 2021/12/1
 *
 * @description  fixed  android:textIsSelectable="true" 时第一次点击无效
 *
 *
 *
 */
class SelectableTextView : AppCompatTextView {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    // 记录按下时间
    private var mLastActionDownTime = 0L

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        var result = false
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> mLastActionDownTime = System.currentTimeMillis()
            MotionEvent.ACTION_MOVE -> {}
            MotionEvent.ACTION_UP -> {
                val actionUpTime = System.currentTimeMillis()
                result = if (actionUpTime - mLastActionDownTime < ViewConfiguration.getLongPressTimeout()) {
                    onVisibilityChanged(this, GONE)
                    callOnClick()
                    true
                }else {
                    //长按事件,即不处理点击事件
                    false
                }
            }
        }
        return if (!result) {
            // 如果没有处理  就走父类方法 使其支持复制粘贴功能
            super.onTouchEvent(event)
        } else {
            true
        }
    }
}
