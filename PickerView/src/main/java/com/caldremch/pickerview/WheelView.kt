package com.caldremch.pickerview

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.caldremch.pickerview.adapter.SimpleWheelAdapter
import com.caldremch.pickerview.adapter.WheelAdapter
import com.caldremch.pickerview.callback.OnItemSelectedListener

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

        /**
         * 无效的位置
         */
        const val IDLE_POSITION = -1

        //滚轮方向
        val WHEEL_VERTICAL: Int = androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
        val WHEEL_HORIZONTAL: Int = androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL

        //类型, 时间选择器类型, 字符串选择其类型
        val SELECT_TYPE_DATE = 1
        val SELECT_TYPE_STRING = 0

        //年月日
        val SELECT_TYPE_DATE_YEAR = 1
        val SELECT_TYPE_DATE_MONTH = 2
        val SELECT_TYPE_DATE_DAY = 3

    }

    /**
     * 布局方向
     */
    private var orientation: Int = WHEEL_VERTICAL

    /**
     * item大小, 具体为测量文字的大小
     */
    private var itemSize = 90  //垂直布局中, itemSize 为高度

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
     * item 文字大小-->跟ViewHolder 对应的文字大小一致
     */
    private var stringSelectTextSize: Float = 0f

    /**
     * 对齐方式
     */
    private var gravity: Int = WheelRecyclerView.GRAVITY_CENTER

    private lateinit var myRecyclerView: WheelRecyclerView

    //1:时间选择
    //2.普通文字列表选择
    var selectType = SELECT_TYPE_STRING

    var listener: OnItemSelectedListener? = null

    //选中位置
    private var lastSelectedPosition = IDLE_POSITION
    private var selectedPosition = IDLE_POSITION

    //时间选择器的类型: 年月日
    private var datePickerType = SELECT_TYPE_DATE_YEAR

    init {

        stringSelectTextSize = Utils.dp2px(context, 14) //默认 14

        attrs?.let {
            val a = context.obtainStyledAttributes(attrs, R.styleable.WheelView)
            itemCount = a.getInt(R.styleable.WheelView_wv_wheelItemCount, itemCount)
            dividerColor = a.getColor(R.styleable.WheelView_wv_dividerColor, dividerColor)
            itemSize = a.getDimensionPixelOffset(R.styleable.WheelView_wv_wheelItemSize, itemSize)
            dividerSize =
                a.getDimensionPixelOffset(R.styleable.WheelView_wv_wheelDividerSize, dividerSize)
            orientation = a.getInt(R.styleable.WheelView_wv_wheelOrientation, orientation)
            gravity = a.getInt(R.styleable.WheelView_wv_wheelGravity, gravity)
            selectType = a.getInt(R.styleable.WheelView_wv_selectType, selectType)
            stringSelectTextSize =
                a.getDimension(
                    R.styleable.WheelView_wv_item_text_size,
                    stringSelectTextSize
                )

            datePickerType = a.getInt(R.styleable.WheelView_wv_date_type, SELECT_TYPE_DATE_YEAR)

            a.recycle()
        }

        val paint = Paint()
        paint.isAntiAlias = true
        paint.textSize = stringSelectTextSize

        //时间选择器, 宽高设置, 这里设置的高度, 决定了 ViewHolder 的高度
        //根据字体测量出宽度
        var measureText = "十"
        if (selectType == SELECT_TYPE_DATE) {
            when (datePickerType) {
                SELECT_TYPE_DATE_YEAR -> {
                    measureText = "2020年"
                }
                SELECT_TYPE_DATE_MONTH -> {
                    measureText = "12月"
                }
                SELECT_TYPE_DATE_DAY -> {
                    measureText = "30日"
                }
            }
        }

        //文字padding
        val padding = Utils.dp2px(context, 5)

        //测量文字高度
        val textRect = Rect()
        paint.getTextBounds(measureText, 0, measureText.length, textRect)
        val fontMer = Paint.FontMetrics()
        paint.getFontMetrics(fontMer)

        //设置 item 的高度
        itemSize = ((fontMer.bottom - fontMer.top).toInt() + 2 * padding).toInt()
        //两条分割线之间的距离 > item 的高度
//        dividerSize = (itemSize + Utils.dp2px(context, 2)).toInt()
        dividerSize = itemSize

        initRecyclerView()
    }

    private fun initRecyclerView() {


        myRecyclerView = WheelRecyclerView(
            context = context,
            gravity = gravity,
            myItemCount = itemCount,
            itemSize = itemSize,
            orientation = orientation,
            dividerColor = dividerColor,
            dividerSize = dividerSize
        )

//        myRecyclerView.setBackgroundColor(Color.RED)
        myRecyclerView.overScrollMode = View.OVER_SCROLL_NEVER
        val totalItemSize = (itemCount * 2 + 1) * itemSize
        LinearSnapHelper().attachToRecyclerView(myRecyclerView) //让滑动结束时都能定到中心位置

        //设置 RecyclerView的布局
        val layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, totalItemSize)
        addView(myRecyclerView, layoutParams)

        //添加选中监听
        myRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (newState != RecyclerView.SCROLL_STATE_IDLE) {
                    return
                }
                val centerItemPosition: Int = myRecyclerView.findCenterItemPosition()
                if (centerItemPosition == IDLE_POSITION) {
                    return
                }
                selectedPosition = centerItemPosition
                if (selectedPosition != lastSelectedPosition) {
                    listener?.onItemSelected(centerItemPosition)
                    lastSelectedPosition = selectedPosition
                }
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

        myHeight = if (heightMode == MeasureSpec.EXACTLY) {
            MeasureSpec.getSize(heightMeasureSpec)
        } else {
            val child = getChildAt(0)
            child.measuredHeight + paddingTop + paddingBottom
        }
        myWidth = if (widthMode == MeasureSpec.EXACTLY) {
            MeasureSpec.getSize(widthMeasureSpec)
        } else {
            itemSize + paddingLeft + paddingRight
        }


        Log.d("tag", "myWidth-->$datePickerType-->$myWidth")
        setMeasuredDimension(myWidth, myHeight)
    }


    /**
     * 设置适配器
     */
    fun <T : RecyclerView.ViewHolder> setAdapter(adapter: WheelAdapter<T>?) {
        if (adapter == null) {
            myRecyclerView.adapter = null
            return
        }

        val wheelAdapter = SimpleWheelAdapter(adapter, orientation, itemSize, itemCount)
        adapter.adapter = wheelAdapter
        myRecyclerView.adapter = wheelAdapter
        adapter.notifyDataSetChanged()
    }

    fun setCurrentPos(indexOfCurrentYear: Int) {
        myRecyclerView.post {
            myRecyclerView.scrollToPosition(itemCount * 2 + indexOfCurrentYear)
        }
    }


}