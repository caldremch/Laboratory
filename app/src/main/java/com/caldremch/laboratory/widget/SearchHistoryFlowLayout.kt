package com.caldremch.laboratory.widget

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.util.SparseArray
import android.util.SparseIntArray
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.util.forEach
import androidx.core.view.marginLeft
import androidx.core.view.marginRight
import com.caldremch.laboratory.R

/**
 *
 * @auther Caldremch
 *
 * @email finishmo@qq.com
 *
 * @date 2021/1/26 15:23
 *
 * @description
 *
 *
 */
open class SearchHistoryFlowLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) : ViewGroup(context, attrs, defStyle) {

    protected var mAllViews: MutableList<MutableList<View>> = ArrayList()
    protected var mLineHeight: MutableList<Int> = ArrayList()
    private var lineViews: MutableList<View> = ArrayList()
    protected var viewInfoList = arrayListOf<ViewInfo>()
    private val measureLines = SparseIntArray()
    private val measureLinesWidthsForFold = SparseIntArray() //专门给折叠使用的临时记录
    protected val measureFlexLines = SparseArray<List<ViewInfo>>()
    protected var isAddFoldView = false
    protected val FOLD_MAX_LINE = 2

    class ViewInfo(
            var index: Int,
            var childWidth: Int,
            var childHeight: Int,
            var debugTitle: String? = null
    ) {
//        fun getChildWidth():Int{
//            val lp = view.layoutParams as MarginLayoutParams
//            return  (view.measuredWidth + lp.leftMargin + lp.rightMargin)
//        }
//
//        fun getChildHeight():Int{
//            val lp = view.layoutParams as MarginLayoutParams
//            return  (view.measuredHeight + lp.topMargin + lp.bottomMargin)
//        }

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        measureFlexLines.clear()
        val sizeWidth = MeasureSpec.getSize(widthMeasureSpec)
        val modeWidth = MeasureSpec.getMode(widthMeasureSpec)
        val sizeHeight = MeasureSpec.getSize(heightMeasureSpec)
        val modeHeight = MeasureSpec.getMode(heightMeasureSpec)

        // wrap_content
        var width = 0
        var height = 0
        var lineWidth = 0
        var lineHeight = 0
        val cCount = childCount

        //行统计
        var tempLine = 0
        var tempLineWidth = 0
        var flexLineCount = 0

        for (i in 0 until cCount) {
            //子 View 数据获取
            val child = getChildAt(i)
            measureChild(child, widthMeasureSpec, heightMeasureSpec)
            val lp = child.layoutParams as MarginLayoutParams
            val childWidth = (child.measuredWidth + lp.leftMargin + lp.rightMargin)
            val childHeight = (child.measuredHeight + lp.topMargin + lp.bottomMargin)
            val maxWidth = sizeWidth - paddingLeft - paddingRight
            val currentViewInfo = viewInfoList[i]
            currentViewInfo.childWidth = childWidth
            currentViewInfo.childHeight = childHeight
            if (tempLineWidth + childWidth > maxWidth) {
                tempLine++
                var list = measureFlexLines[tempLine] as ArrayList?
                if (list == null) {
                    list = arrayListOf<ViewInfo>()
                    measureFlexLines.put(tempLine, list)
                }
                list.add(currentViewInfo)
                tempLineWidth = childWidth
                lineWidth = childWidth
                width = Math.max(width, lineWidth)
                height += lineHeight
                lineHeight = childHeight
            } else {
                var list = measureFlexLines[tempLine] as ArrayList?
                if (list == null) {
                    list = arrayListOf()
                    measureFlexLines.put(tempLine, list)
                }
                list.add(currentViewInfo)
                tempLineWidth += childWidth
                lineWidth += childWidth
                lineHeight = Math.max(lineHeight, childHeight)
            }

            if (fold && isAddFoldView.not() && measureFlexLines.size() > FOLD_MAX_LINE) {
                val lineViewInfoListFlag = measureFlexLines[FOLD_MAX_LINE - 1]
                val foldViewLp = neededLp()
                val foldView = createFoldView(0, foldViewLp, widthMeasureSpec, heightMeasureSpec)
                foldView.layoutParams = foldViewLp
                measureChild(foldView, widthMeasureSpec, heightMeasureSpec)

                if (lineViewInfoListFlag.size == 1) {
                    //行---单item
                    val itemViewInfo = lineViewInfoListFlag.first()
                    //直接修改当前 ItemView 的宽度
                    val itemViewIndex = itemViewInfo.index
                    val insertIndex = itemViewIndex + 1
                    addView(foldView, insertIndex, foldViewLp)
                    viewInfoList.add(itemViewIndex, ViewInfo(insertIndex, foldView.measuredWidth + foldViewLp.leftMargin + foldViewLp.rightMargin, 0))
                    val itemView = getChildAt(itemViewIndex)
                    val itemViewLayoutParams = itemView.layoutParams as MarginLayoutParams
                    //改变前面一个 item 的宽度
                    itemView.layoutParams.width = maxWidth -
                            (marginLeft + marginRight) -
                            (foldView.measuredWidth + foldViewLp.leftMargin + foldViewLp.rightMargin) -
                            (itemViewLayoutParams.leftMargin + itemViewLayoutParams.rightMargin)

                } else {
                    //行---多item
                    val itemViewInfo = lineViewInfoListFlag.last()
                    val itemViewIndex = itemViewInfo.index
                    var insertIndex = -1
                    val foldWidth = foldView.measuredWidth + foldViewLp.leftMargin + foldViewLp.rightMargin

                    //是否需要更改前一个的 Item 宽度
                    val needToUpdatePreItemWidth = itemViewInfo.childWidth / 2 > foldWidth

                    Log.d(TAG, "onMeasure: foldWidth=$foldWidth ,itemViewInfo.childWidth / 2=${itemViewInfo.childWidth / 2}")

                    //计算累加宽度
                    var lineTotalWidth = 0
                    lineViewInfoListFlag.forEach {
                        lineTotalWidth += it.childWidth
                    }

                    if (needToUpdatePreItemWidth) {
                        //修改最后一个 item 的宽度, 并加折叠加到最后面,
                        insertIndex = itemViewIndex + 1
                        Log.d(TAG, "onMeasure: 压缩 pre-item 宽度 $insertIndex")
                        val itemView = getChildAt(itemViewInfo.index)
                        val itemViewLayoutParams = itemView.layoutParams as MarginLayoutParams
                        val blank = maxWidth - marginLeft - marginRight - lineTotalWidth
                        itemView.layoutParams.width = maxWidth - (lineTotalWidth - (itemViewInfo.childWidth - itemViewLayoutParams.leftMargin - itemViewLayoutParams.rightMargin) + blank + foldWidth + marginLeft + marginRight)
                    } else if (foldWidth < itemViewInfo.childWidth) {
                        insertIndex = itemViewIndex
                        Log.d(TAG, "onMeasure: 足够摆放 targetItemView, 直接加载后面 $insertIndex")
                    } else {
                        // 该循环判断, + 直到能找到足够宽度, 放下目标 itemView
                        val size = lineViewInfoListFlag.size
                        var tempNeedWidth = 0
                        for (j in (size - 1) downTo 0) {
                            val data = lineViewInfoListFlag[j]
                            if (foldWidth <= (tempNeedWidth + data.childWidth)) {
                                insertIndex = data.index
                                break
                            } else {
                                tempNeedWidth += data.childWidth
                            }
                        }

                    }
                    Log.d(TAG, "onMeasure: 寻找能摆放的位置, 直接加载后面 $insertIndex")
                    viewInfoList.add(itemViewIndex, ViewInfo(insertIndex, foldWidth, 0))
                    addView(foldView, insertIndex, foldViewLp)
                }

            }
            if (i == cCount - 1) {
                width = Math.max(lineWidth, width)
                height += lineHeight
            }
        }

        measureFlexLines.forEach { key, value ->
//            Log.d(TAG, "onMeasure: $key --> ${value.size}")
        }

//        Log.d(TAG, "onMeasure: 最终的高度: $lineHeight ---  $flexLineCount")

        setMeasuredDimension( //
                if (modeWidth == MeasureSpec.EXACTLY) sizeWidth else width + paddingLeft + paddingRight,
                if (modeHeight == MeasureSpec.EXACTLY) sizeHeight else height + paddingTop + paddingBottom //
        )
    }

    protected fun createFoldView(index: Int, foldViewLp: LayoutParams, widthMeasureSpec: Int, heightMeasureSpec: Int): View {
        val foldView = inflate(context, R.layout.house_item_history_tag, null)
        foldView.findViewById<TextView>(R.id.tv_title).text = "箭头箭头"
        isAddFoldView = true
        return foldView
    }

    /**
     * 折叠
     */
    var fold = true

    private val containerMaxWidth
        get() = width - paddingLeft - paddingRight

    private val PRE_FOLD_ITEM_WIDTH = dip2px(context, 70f)
    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        mAllViews.clear()
        mLineHeight.clear()
        lineViews.clear()
        val width = width
        var lineWidth = 0
        var lineHeight = 0
        val cCount = childCount
        var flexLineCount = 0
        for (i in 0 until cCount) {
            val child = getChildAt(i)

            if (child.visibility == GONE) continue
            val lp = child.layoutParams as MarginLayoutParams
            val childWidth = child.measuredWidth
            val childHeight = child.measuredHeight
            //折叠的情况先, 显示箭头
            val isOverSize = childWidth + lineWidth + lp.leftMargin + lp.rightMargin > containerMaxWidth
            if (isOverSize) {
                //统计行数
                flexLineCount++
                //添加行高
                mLineHeight.add(lineHeight)
                //存储行高
                mAllViews.add(lineViews)
                //重置行宽度, 为下一行准备
                lineWidth = 0
                //下一行的高度
                lineHeight = childHeight + lp.topMargin + lp.bottomMargin
                //重新初始化行子 view 的集合
                lineViews = ArrayList()
            }
            //如果是第二行, 在累加的时候, 就开始判断是否 + 折叠item, 是否会超过当前maxWidth
            //如果超过了, 那么就不加当前View 了, 而是替换成 折叠 View, 特殊处理: 如果当前行只有一个 View 的时候, 缩短 View 的宽度
            //如果没有超过 按旧的逻辑处理
            lineWidth += childWidth + lp.leftMargin + lp.rightMargin
            lineHeight = Math.max(lineHeight, childHeight + lp.topMargin + lp.bottomMargin)
            lineViews.add(child)
        }
        mLineHeight.add(lineHeight)
        mAllViews.add(lineViews)
        var left = paddingLeft
        var top = paddingTop
        //遍历每行, 开始进行布局
        val lineNum = mAllViews.size
        for (i in 0 until lineNum) {
            //取出高度和子view
            lineViews = mAllViews[i]
//            Log.d(TAG, "onLayout: lineViews = ${lineViews.size}")
            lineHeight = mLineHeight[i]
            left = paddingLeft
            //行的宽度
            var lineContainerWidth = 0
            for (j in lineViews.indices) {
                val child = lineViews[j]
                val lp = child.layoutParams as MarginLayoutParams
                val lc = left + lp.leftMargin
                val tc = top + lp.topMargin
                val rc = lc + child.measuredWidth
                val bc = tc + child.measuredHeight
                val aChildWidth = child.measuredWidth + lp.leftMargin + lp.rightMargin
                lineContainerWidth += aChildWidth
                child.layout(lc, tc, rc, bc)
                left += (child.measuredWidth + lp.leftMargin + lp.rightMargin)
            }
            top += lineHeight
        }
    }

    override fun generateLayoutParams(attrs: AttributeSet?): LayoutParams {
        return MarginLayoutParams(context, attrs)
    }

    override fun generateDefaultLayoutParams(): LayoutParams {
        return MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
    }

    override fun generateLayoutParams(p: LayoutParams?): LayoutParams {
        return MarginLayoutParams(p)
    }

    companion object {
        const val TAG = "FlowLayout"
        const val LEFT = -1
    }

    fun setList(data: List<String>) {
        fold = true
        isAddFoldView = false
        removeAllViews()
        viewInfoList.clear()
        data.forEachIndexed { index, item ->
            val info = ViewInfo(index, 0, 0, item)
            viewInfoList.add(info)
            val itemView = View.inflate(context, R.layout.house_item_history_tag, null)
            val tv = itemView.findViewById<TextView>(R.id.tv_title)
            tv.text = item
            val lp = neededLp()
            addView(itemView, lp)
        }

    }

    protected fun neededLp(): MarginLayoutParams {
        val lp = MarginLayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT)
        lp.setMargins(dip2px(context, 0f),
                dip2px(context, 5f),
                dip2px(context, 5f),
                dip2px(context, 5f))
        return lp
    }

    open fun dip2px(context: Context, dpValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }
}
