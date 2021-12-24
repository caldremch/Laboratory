package com.caldremch.utils

import android.app.Activity
import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.FrameLayout

/**
 *
 * @author Caldremch
 *
 * @date 2020-06-28 11:52
 *
 * @email caldremch@163.com
 *
 * @describe  自动监听键盘, 点击键盘以外的地方收起键盘
 *
 **/
object KBObserver {

    fun init(target: Context) {

        if (target is Activity) {
            val root: View = target.window.decorView
            if (root is ViewGroup) {
                val decorView = root
                if (decorView.childCount > 0) {
                    //将contentView 放到 KBObserverLayout中
                    val firstChild = decorView.getChildAt(0)
                    decorView.removeAllViews()
                    val params: ViewGroup.LayoutParams = firstChild.layoutParams
                    val kbObserverLayout = KBObserverLayout(target.getApplicationContext())
                    kbObserverLayout.addView(firstChild, params)
                    val lp = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                    decorView.addView(kbObserverLayout)
                }
            }
        }
    }

    private class KBObserverLayout @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
    ) : FrameLayout(context, attrs, defStyleAttr) {
        private var mEditTexts: MutableList<EditText>? = null

        //收集 EditText
        override fun onAttachedToWindow() {
            super.onAttachedToWindow()
            collectEditText(this)
        }

        //清除
        override fun onDetachedFromWindow() {
            clearEditText()
            super.onDetachedFromWindow()
        }

        override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
            if (ev.action == MotionEvent.ACTION_DOWN && shouldHideKeyboard(ev)) {
                hideKeyBoard()
            }
            return super.onInterceptTouchEvent(ev)
        }

        //隐藏键盘
        private fun hideKeyBoard() {
            val context = context.applicationContext
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(windowToken, 0)
        }

        //是否隐藏键盘
        private fun shouldHideKeyboard(ev: MotionEvent): Boolean {
            if (mEditTexts == null || mEditTexts!!.isEmpty()) {
                return false
            }
            val x = ev.x.toInt()
            val y = ev.y.toInt()
            val r = Rect()

            //如果点击的是某个 EditText, 也不隐藏
            mEditTexts?.forEach {
                it.getGlobalVisibleRect(r)
                if (r.contains(x, y)) {
                    return false
                }
            }
            return true
        }

        private fun collectEditText(child: View) {
            if (null == mEditTexts) {
                mEditTexts = mutableListOf()
            }
            if (child is ViewGroup) {
                val parent = child
                val childCount = parent.childCount

                for (i in 0 until childCount) {
                    val childView = parent.getChildAt(i)
                    collectEditText(childView)
                }
            } else if (child is EditText) {
                val editText = child
                if (!mEditTexts!!.contains(editText)) {
                    mEditTexts!!.add(editText)
                }
            }
        }

        private fun clearEditText() {
            if (null != mEditTexts) {
                mEditTexts!!.clear()
                mEditTexts = null
            }
        }


    }
}