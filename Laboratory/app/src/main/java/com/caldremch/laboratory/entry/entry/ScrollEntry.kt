package com.caldremch.laboratory.entry.entry

import android.content.Context
import android.graphics.Point
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.NestedScrollingParent
import androidx.core.view.ViewCompat
import com.caldremch.android.annotation.entry.Entry
import com.caldremch.android.annotation.entry.IEntry
import com.caldremch.dialog.BaseDialog
import com.caldremch.laboratory.R
import com.google.android.material.bottomsheet.BottomSheetBehavior

/**
 *
 * @author Caldremch
 *
 * @date 2020-09-28 14:58
 *
 * @email caldremch@163.com
 *
 * @describe
 *
 **/
@Entry
class ScrollEntry : IEntry {
    override fun getTitle(): String {
        return "滑动"
    }

    override fun onClick(context: Context) {
        ScrollDialog(context).show()
    }
}

class ScrollDialog(context: Context) : BaseDialog(context) {

    init {
        widthScale = 1f
        gravity = Gravity.BOTTOM
    }

    override fun initView(rootView: View) {
//        val view = rootView.findViewById<MaxHeightCoordinatorLayout>(R.id.rootView)
        val v3 = rootView.findViewById<View>(R.id.v3)
        val h = getRealScreenHeight(context!!)
        val hD = h - 100;
//        view.setMaxHeight(hD)

        rootView.findViewById<View>(R.id.rootView).setOnClickListener { dismiss() }

        val b = BottomSheetBehavior.from(v3)
        b.peekHeight = (h * 0.5).toInt()


    }

    override fun getLayoutId(): Int {
        return R.layout.dialog_scoller
    }

    fun getRealScreenHeight(context: Context): Int {
        val localWindowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val defaultDisplay = localWindowManager.defaultDisplay
        val outPoint = Point()
        defaultDisplay.getRealSize(outPoint)
        return outPoint.y
    }
}


class MaxHeightCoordinatorLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : CoordinatorLayout(context, attrs, defStyleAttr) {

    var mMaxHeight = 0


    fun setMaxHeight(maxHeight: Int) {
        mMaxHeight = maxHeight
        requestLayout()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        if (mMaxHeight > 0) {
            val heightMode = MeasureSpec.getMode(heightMeasureSpec)
            var heightSize = MeasureSpec.getSize(heightMeasureSpec)

            if (heightMode == MeasureSpec.EXACTLY) {
                heightSize = if (heightSize <= mMaxHeight) heightSize else mMaxHeight
            }

            if (heightMode == MeasureSpec.UNSPECIFIED) {
                heightSize = if (heightSize <= mMaxHeight) heightSize else mMaxHeight
            }
            if (heightMode == MeasureSpec.AT_MOST) {
                heightSize = if (heightSize <= mMaxHeight) heightSize else mMaxHeight
            }
            val maxHeightMeasureSpec = MeasureSpec.makeMeasureSpec(heightSize, heightMode)

            super.onMeasure(widthMeasureSpec, maxHeightMeasureSpec)
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        }
    }

}

class GesLayout : NestedScrollingParent {

    override fun onStartNestedScroll(child: View, target: View, axes: Int): Boolean {
        return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL
    }

    override fun onNestedScrollAccepted(child: View, target: View, axes: Int) {
    }

    override fun onStopNestedScroll(target: View) {
    }

    override fun onNestedScroll(
        target: View,
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int
    ) {
    }

    override fun onNestedPreScroll(target: View, dx: Int, dy: Int, consumed: IntArray) {
    }

    override fun onNestedFling(
        target: View,
        velocityX: Float,
        velocityY: Float,
        consumed: Boolean
    ): Boolean {
        return false
    }

    override fun onNestedPreFling(target: View, velocityX: Float, velocityY: Float): Boolean {
        return false
    }

    override fun getNestedScrollAxes(): Int {
        return ViewCompat.SCROLL_AXIS_VERTICAL
    }

}