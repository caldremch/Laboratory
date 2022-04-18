package com.caldremch.floatingwindow

import android.content.res.Resources
import android.util.Log
import android.view.MotionEvent
import android.view.View

/**
 * @author wanglikun
 * touch 事件代理 解决点击和触摸事件的冲突
 */
 class TouchProxy(private var mEventListener: OnTouchEventListener?) {


    private var mLastX = 0
    private var mLastY = 0
    private var mStartX = 0
    private var mStartY = 0
    private var mState = TouchState.STATE_STOP

    fun dp2px(dpValue: Float): Int {
        val scale = Resources.getSystem().displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

    fun setEventListener(eventListener: OnTouchEventListener?) {
        mEventListener = eventListener
    }

    private enum class TouchState {
        STATE_MOVE, STATE_STOP
    }

    fun onTouchEvent(v: View, event: MotionEvent): Boolean {
        val distance = dp2px(1f) * MIN_DISTANCE_MOVE
        val x = event.rawX.toInt()
        val y = event.rawY.toInt()
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                mStartX = x
                mStartY = y
                mLastY = y
                mLastX = x
                if (mEventListener != null) {
                    mEventListener!!.onDown(x, y)
                }
            }
            MotionEvent.ACTION_MOVE -> {
                if (Math.abs(x - mStartX) < distance
                    && Math.abs(y - mStartY) < distance
                ) {
                    if (mState == TouchState.STATE_STOP) {
                        return true
                    }
                } else if (mState != TouchState.STATE_MOVE) {
                    mState = TouchState.STATE_MOVE
                }
                if (mEventListener != null) {
                    mEventListener!!.onMove(mLastX, mLastY, x - mLastX, y - mLastY)
                }
                mLastY = y
                mLastX = x
                mState = TouchState.STATE_MOVE
            }
            MotionEvent.ACTION_UP -> {
                if (mEventListener != null) {
                    mEventListener!!.onUp(x, y)
                }
                if (mState != TouchState.STATE_MOVE
                    && event.eventTime - event.downTime < MIN_TAP_TIME
                ) {
                    v.performClick()
                    Log.d("performCreate", "performCreate: 回到用这里的???")
                }
                mState = TouchState.STATE_STOP
            }
            else -> {}
        }
        return false
    }

    interface OnTouchEventListener {
        fun onMove(x: Int, y: Int, dx: Int, dy: Int)
        fun onUp(x: Int, y: Int)
        fun onDown(x: Int, y: Int)
        fun onItemClick(){

        }
    }

    companion object {
        private const val MIN_DISTANCE_MOVE = 4
        private const val MIN_TAP_TIME = 1000
    }
}