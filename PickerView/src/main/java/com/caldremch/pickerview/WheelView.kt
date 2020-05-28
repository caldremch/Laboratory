package com.caldremch.pickerview

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.caldremch.pickerview.adapter.SimpleWheelAdapter
import com.caldremch.pickerview.adapter.WheelAdapter

/**
 *
 * @author Caldremch
 *
 * @date 2020-05-28
 *
 * @email caldremch@163.com
 *
 * @describe 3D 滑动 View 容器 , 暂不支持横向滚轮
 *
 **/
class WheelView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ViewGroup(context, attrs, defStyleAttr) {

    /**
     * 垂直与水平布局两种状态
     */

    companion object {
        val WHEEL_VERTICAL: Int = androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
        val WHEEL_HORIZONTAL: Int = androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL

        //类型, 时间选择器类型, 字符串选择其类型
        val SELECT_TYPE_DATE = 1
        val SELECT_TYPE_STRING = 0

        //年月日
        val SELECT_TYPE_DATE_YEAR = 0x00000224
        val SELECT_TYPE_DATE_MONTH = 0x00000225
        val SELECT_TYPE_DATE_DAY = 0x00000226

    }

    /**
     * 布局方向
     */
    private var orientation: Int = WHEEL_VERTICAL

    /**
     * item大小, 具体为测量文字的大小
     */
    private var itemSize = 90  //垂直布局中, itemSize 为高度

    private var itemWidth = 0f //宽度, 用于设置时间选择器的宽度限制,其他选择器类型不适用

    /**
     * 分割线颜色
     */
    private var dividerColor = Color.BLACK

    /**
     * item数量
     */
    private var itemCount = 3

    /**
     * 分割线之间距离
     */
    private var dividerSize = 90

    /**
     * 对齐方式
     */
    private var gravity: Int = WheelRecyclerView.GRAVITY_LEFT

    private lateinit var myRecyclerView: WheelRecyclerView

    //1:时间选择
    //2.普通文字列表选择
    var selectType = SELECT_TYPE_STRING

    init {

        attrs?.let {
            val a = context.obtainStyledAttributes(attrs, R.styleable.WheelView)
            itemCount = a.getInt(R.styleable.WheelView_wheelItemCount, itemCount)
            dividerColor = a.getColor(R.styleable.WheelView_dividerColor, dividerColor)
            itemSize = a.getDimensionPixelOffset(R.styleable.WheelView_wheelItemSize, itemSize)
            dividerSize =
                a.getDimensionPixelOffset(R.styleable.WheelView_wheelDividerSize, dividerSize)
            orientation = a.getInt(R.styleable.WheelView_wheelOrientation, orientation)
            gravity = a.getInt(R.styleable.WheelView_wheelGravity, gravity)
            selectType = a.getInt(R.styleable.WheelView_selectType, selectType)
            a.recycle()
        }

        //时间选择器, 宽高设置
        if (selectType == SELECT_TYPE_DATE) {
            val paint = Paint()
            paint.isAntiAlias = true
            paint.textSize = Utils.dp2px(context, 14)
            val year = "2019年"
            itemWidth = paint.measureText(year)
            val textRect = Rect()
            paint.getTextBounds(year, 0, year.length, textRect)
            val fontMer = Paint.FontMetrics()
            paint.getFontMetrics(fontMer)
            val padding = Utils.dp2px(context, 5)

            itemSize = ((fontMer.bottom - fontMer.top).toInt() + 2 * padding).toInt()
            dividerSize = (itemSize + Utils.dp2px(context, 2)).toInt()
        }

        initRecyclerView()
    }

    private fun initRecyclerView() {


        myRecyclerView = WheelRecyclerView(
            context = context,
            gravity = gravity,
            itemCount = itemCount,
            itemSize = itemSize,
            orientation = orientation,
            dividerColor = dividerColor,
            dividerSize = dividerSize
        )
        myRecyclerView.setBackgroundColor(Color.RED)

        myRecyclerView.overScrollMode = View.OVER_SCROLL_NEVER
        val totalItemSize = (itemCount * 2 + 1) * itemSize
        LinearSnapHelper().attachToRecyclerView(myRecyclerView) //让滑动结束时都能定到中心位置

        var layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, totalItemSize)
        //字符串选择, MATCH_PARENT
        if (selectType == SELECT_TYPE_STRING) {
            layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, totalItemSize)
        }

        addView(myRecyclerView, layoutParams)

        myRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (newState != RecyclerView.SCROLL_STATE_IDLE) return

            }
        })
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        if (childCount <= 0) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
            return
        }

        //测量子 View
        measureChildren(widthMeasureSpec, heightMeasureSpec)

        if (orientation == WHEEL_VERTICAL) {
            measureVertical(widthMeasureSpec, heightMeasureSpec)
        }

    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {

        if (childCount <= 0) return

        val child = getChildAt(0)
        val childWidth = child.measuredWidth
        val childHeight = child.measuredHeight
        var left: Int = 0
        var top: Int = 0

        if (orientation == WHEEL_VERTICAL) {
            //recyclerView 中心点
            val centerHeight = height - paddingTop - paddingBottom - childHeight shr 1
            left = paddingLeft
            top = paddingTop + centerHeight
        }

        //主要为了处理横向, 否则无需这么麻烦的写
        child.layout(left, top, left + childWidth, top + childHeight)
    }


    private fun measureVertical(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        var myWidth: Int = 0
        var myHeight: Int = 0

        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)

        //垂直方向EXACTLY 意义不大
        val child = getChildAt(0)
        myHeight = child.measuredHeight + paddingTop + paddingBottom

        if (selectType == SELECT_TYPE_DATE) {
            //时间选择器宽度固定
            myWidth = (itemWidth + paddingLeft + paddingRight).toInt()
        } else {

            myWidth = if (widthMode == MeasureSpec.EXACTLY) {
                MeasureSpec.getSize(widthMeasureSpec)
            } else {
                //默认 itemsize
                itemSize + paddingLeft + paddingRight
            }
            itemWidth = myWidth.toFloat()
        }
        setMeasuredDimension(myWidth, myHeight)
    }


    fun <T : RecyclerView.ViewHolder> setAdapter(adapter: WheelAdapter<T>?) {
        if (adapter == null) {
            myRecyclerView.adapter = null
            return
        }
        val wheelAdapter = SimpleWheelAdapter(adapter, orientation, itemSize, itemCount, itemWidth)
        adapter.adapter = wheelAdapter
        myRecyclerView.adapter = wheelAdapter
        adapter.notifyDataSetChanged()
    }


}