package com.caldremch.laboratory.floatingview

import android.content.Context
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import com.caldremch.floatingwindow.AbsFloatingView
import com.caldremch.floatingwindow.FloatingViewLayoutParams
import com.caldremch.laboratory.R

/**
 *
 * @author Caldremch
 *
 * @email finishmo@qq.com
 *
 * @date 2022/4/1 15:54
 *
 * @description
 *
 *
 */
class SimpleFloatView : AbsFloatingView() {
    var mWidth = 0
    var mHeight = 0
    var mDp50InPx = 0
    private lateinit var context:Context
//    private var mWindowManager: WindowManager? = null


    private  var tv_content:TextView? = null

    override fun onCreate(context: Context) {
        val outMetrics = DisplayMetrics()
        mWindowManager.defaultDisplay.getMetrics(outMetrics)
        mWidth = outMetrics.widthPixels - mDp50InPx
        mHeight = outMetrics.heightPixels - mDp50InPx
    }

    override fun onCreateView(context: Context, rootView: FrameLayout?): View {
        this.context = context
        return LayoutInflater.from(context)
            .inflate(R.layout.simple_floating_view, rootView, false)
    }

    override fun onViewCreated(rootView: FrameLayout?) {
        tv_content = rootView?.findViewById(R.id.tv_content)
//        tv_content?.setOnClickListener {
//            Toast.makeText(context, "点击了我", Toast.LENGTH_SHORT).show()
//        }

    }


    override fun onUpdate(bundle: Bundle?) {
        bundle?.let {
            tv_content?.text = it.getString("content")
        }
    }


    override fun initFloatingViewLayoutParams(params: FloatingViewLayoutParams) {
        params.width = FloatingViewLayoutParams.MATCH_PARENT
        params.height = FloatingViewLayoutParams.WRAP_CONTENT
        params.gravity = Gravity.TOP or Gravity.LEFT
    }


    override val justFloating: Array<Boolean>
        get() = arrayOf(true, true, true, true)


}