package com.caldremch.laboratory.widget

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.util.SparseArray
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.marginLeft
import androidx.core.view.marginRight
import com.caldremch.laboratory.R
import java.lang.Math.abs

/**
 *
 * @auther Caldremch
 *
 * @email finishmo@qq.com
 *
 * @date 2021/1/26 15:23
 *
 * @description 自定义 View, item 摆放及折叠
 *
 */
open class SearchHistoryFlowLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) : ViewGroup(context, attrs, defStyle) {

    private val TAG = "SearchHistoryFlowLayout"
    private val measureFlexLines = SparseArray<List<ViewInfo>>()
    private var viewInfoList = arrayListOf<ViewInfo>()
    private var isAddFoldView = false //是否已经添加了
    var foldMaxLine = 2 //折叠行数
    private val targetItemViewInfo = ViewInfo(-1, -1) //折叠 View 对象
    private var fold = true //是否折叠
    private var itemClickListener: ItemClickListener? = null
    private val updateViewWidthCache = hashMapOf<View, Int>() //更新过宽度的 View
    private val updateWidthFactor = 2 //什么时候决定更新被插队的宽度

    protected var mAllViews = arrayListOf<ArrayList<View>>()
    protected var mLineHeight = arrayListOf<Int>()
    protected var mLineWidth = arrayListOf<Int>()
    private var lineViews = arrayListOf<View>()

    //内部使用, View 便签
    private class ViewInfo(
            var index: Int,
            var childWidth: Int,
            var debugTitle: String? = null //debug 观察 title
    )

    private fun createFoldView(): View {
        val foldView = inflate(context, R.layout.house_item_history_fold, null)
        isAddFoldView = true
        foldView.setOnClickListener {
            fold = false
            isAddFoldView = false
            //如果之前有缓存的改变 view宽度的, 重置回当初的宽度
            updateViewWidthCache.forEach {
                it.key.layoutParams.width = it.value
            }
            updateViewWidthCache.clear()
            removeView(foldView)
            requestLayout()
        }
        return foldView
    }

    fun setItemClickListener(listener: ItemClickListener) {
        this.itemClickListener = listener
    }

    fun setList(data: List<String>) {
        fold = true
        isAddFoldView = false
        removeAllViews()
        viewInfoList.clear()

        data.forEachIndexed { index, item ->
            val info = ViewInfo(index, 0, item)
            viewInfoList.add(info)
            val itemView = View.inflate(context, R.layout.house_item_history_tag, null)
            itemView.setOnClickListener {
                itemClickListener?.onItemClick(item, index)
            }
            val tv = itemView.findViewById<TextView>(R.id.tv_title)
            tv.text = item
            val lp = itemLayoutParams()
            addView(itemView, lp)
        }

    }

    interface ItemClickListener {
        fun onItemClick(condition: String, index: Int)
    }

    private fun itemLayoutParams(): MarginLayoutParams {
        val lp = MarginLayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT)
        lp.setMargins(dip2px(context, 0f),
                dip2px(context, 5f),
                dip2px(context, 5f),
                dip2px(context, 5f))
        return lp
    }

    private fun dip2px(context: Context, dpValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

    //获取行宽
    private fun getLineTotalWidth(list: List<ViewInfo>): Int {
        var lineTotalWidth = 0
        list.forEach {
            lineTotalWidth += it.childWidth
        }
        return lineTotalWidth
    }

    //第一个item的高度
    private var dependencyHeight = 0
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        measureFlexLines.clear()

        val sizeWidth = MeasureSpec.getSize(widthMeasureSpec)
        val modeWidth = MeasureSpec.getMode(widthMeasureSpec)
        val sizeHeight = MeasureSpec.getSize(heightMeasureSpec)
        val modeHeight = MeasureSpec.getMode(heightMeasureSpec)

        // wrap_content
        var width = 0
        var height = 0
        var lineHeight = 0
        val cCount = childCount

        //行统计
        var tempLine = 0
        var tempLineWidth = 0

        for (i in 0 until cCount) {
            //子 View 数据获取
            val child = getChildAt(i)
            measureChild(child, widthMeasureSpec, heightMeasureSpec)
            val lp = child.layoutParams as MarginLayoutParams
            var childWidth = (child.measuredWidth + lp.leftMargin + lp.rightMargin)
            val childHeight = (child.measuredHeight + lp.topMargin + lp.bottomMargin)

            if (dependencyHeight == 0) {
                dependencyHeight = childHeight
            }

            val maxWidth = sizeWidth - paddingLeft - paddingRight
            val currentViewInfo = viewInfoList[i]

            //如果测量结果大于 最大宽度, 那么宽度设置为最大宽度
            if (childWidth > maxWidth) {
                childWidth = maxWidth
            }

            currentViewInfo.childWidth = childWidth

            //开始统计行数
            if (tempLineWidth + childWidth > maxWidth) {
                if (tempLineWidth != 0) {
                    tempLine++
                }
                var list = measureFlexLines[tempLine] as ArrayList?
                if (list == null) {
                    list = arrayListOf<ViewInfo>()
                    measureFlexLines.put(tempLine, list)
                }
                list.add(currentViewInfo)
                tempLineWidth = childWidth
                width = Math.max(width, tempLineWidth)
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
                lineHeight = Math.max(lineHeight, childHeight)
            }

            //折叠逻辑
            if (fold && isAddFoldView.not() && measureFlexLines.size() > foldMaxLine) {
                val lineViewInfoListFlag = measureFlexLines[foldMaxLine - 1]
                Log.d(TAG, "onMeasure: 操作行宽度${
                    lineViewInfoListFlag.joinToString(separator = ",", transform = {
                        "${it.childWidth}, maxSize: ${maxWidth} sizeWidth= ${sizeWidth}"
                    })
                }")
                val foldViewLp = itemLayoutParams()
                val foldView = createFoldView()
                foldView.layoutParams = foldViewLp
                measureChild(foldView, widthMeasureSpec, heightMeasureSpec)
                //计算累加宽度
                val lineTotalWidth = getLineTotalWidth(lineViewInfoListFlag)
                //这一行中还剩余的大小
                val deltaWidth = abs(maxWidth - lineTotalWidth)
                //插队View的宽度
                val foldWidth = foldView.measuredWidth + foldViewLp.leftMargin + foldViewLp.rightMargin
                var insertIndex = -1
                if (foldWidth < deltaWidth) {
                    //直接+在当前行的最后一个 item 后面
                    insertIndex = lineViewInfoListFlag.last().index + 1
                } else {
                    //寻找插队位置
                    var decreaseFactor = 0
                    for (j in (lineViewInfoListFlag.size - 1) downTo 0) {
                        val data = lineViewInfoListFlag[j]
                        decreaseFactor += data.childWidth
                        val tempWidth = lineTotalWidth - decreaseFactor + foldWidth + deltaWidth
                        if (tempWidth <= maxWidth) {
                            insertIndex = data.index
                            //如果被插队的 View 的宽度比较长, 可以减少它宽度然后加在后面, 而不是插队
                            if (data.childWidth > updateWidthFactor * foldWidth) {
                                val tempView = getChildAt(data.index)
                                //存储改变宽度的 view
                                updateViewWidthCache[tempView] = tempView.measuredWidth
                                val debugWidth = tempView.measuredWidth
                                val tempLp = tempView.layoutParams as MarginLayoutParams
                                //更新后的宽度 = 总宽度 - (除当前 View 之外的Line 宽度) - 行留空宽度 - 插队View宽度 - 前View的 margin
                                tempView.layoutParams.width = maxWidth - (lineTotalWidth - data.childWidth) - deltaWidth - foldWidth - tempLp.leftMargin - tempLp.rightMargin
                                insertIndex++ //+在后面
                                Log.d(TAG, "onMeasure: 改变了宽度...$debugWidth --to-->${tempView.layoutParams.width} 其他信息: lineTotalWidth=${lineTotalWidth} data.childWidth = ${data.childWidth} 剩余宽度=$deltaWidth , 折叠view宽度=$foldWidth ")
                            }
                            break
                        }
                    }

                    //如果cutIndex的 item 宽度 < deltaWidth,那么cutIndex的View 会放在插队 View 的方面, 所以设置 deltaWidthmargin, 将cutIndex的View挤掉
                    val beCutView = viewInfoList[insertIndex]
                    if (beCutView.childWidth < deltaWidth) {
                        Log.d(TAG, "onMeasure: 改变了折叠 view 的 rightmargin = $deltaWidth")
                        foldViewLp.rightMargin = deltaWidth
                    } else {
                        Log.d(TAG, "onMeasure: 不需要 改变折叠 view 的 rightmargin")
                    }
                }
                //viewInfoList 添加-- 以防 index 错误
                targetItemViewInfo.index = insertIndex
                targetItemViewInfo.childWidth = foldWidth
                viewInfoList.add(insertIndex, targetItemViewInfo)
                //添加 view
                addView(foldView, insertIndex, foldViewLp)
            }
            if (i == cCount - 1) {
                width = Math.max(tempLineWidth, width)
                height += lineHeight
            }

        }

        if (fold) {
            height = foldMaxLine * dependencyHeight
        }

        setMeasuredDimension(
                if (modeWidth == MeasureSpec.EXACTLY) sizeWidth else width + paddingLeft + paddingRight,
                if (modeHeight == MeasureSpec.EXACTLY) sizeHeight else height + paddingTop + paddingBottom
        )
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        mAllViews.clear()
        mLineHeight.clear()
        mLineWidth.clear()
        lineViews.clear()
        val width = width
        var lineWidth = 0
        var lineHeight = 0
        var finalLineHeight = 0
        val cCount = childCount
        for (i in 0 until cCount) {
            val child = getChildAt(i)
            if (child.visibility == GONE) continue
            val lp = child
                    .layoutParams as MarginLayoutParams
            val childWidth = child.measuredWidth
            val childHeight = child.measuredHeight
            if (childWidth + lineWidth + lp.leftMargin + lp.rightMargin > width - paddingLeft - paddingRight) {
                mLineHeight.add(lineHeight)
                mAllViews.add(lineViews)
                mLineWidth.add(lineWidth)
                lineWidth = 0
                lineHeight = childHeight + lp.topMargin + lp.bottomMargin
                lineViews = ArrayList()
            }
            lineWidth += childWidth + lp.leftMargin + lp.rightMargin
            lineHeight = Math.max(lineHeight, childHeight + lp.topMargin
                    + lp.bottomMargin)
            lineViews.add(child)
        }
        mLineHeight.add(lineHeight)
        mLineWidth.add(lineWidth)
        mAllViews.add(lineViews)
        var left = paddingLeft
        var top = paddingTop
        val lineNum = mAllViews.size


        for (i in 0 until lineNum) {
            lineViews = mAllViews[i]
            lineHeight = mLineHeight[i]

            // set gravity
            val currentLineWidth = mLineWidth[i]
            left = paddingLeft
            for (j in lineViews.indices) {
                val child = lineViews[j]
                if (child.visibility == GONE) {
                    continue
                }
                val lp = child
                        .layoutParams as MarginLayoutParams
                val lc = left + lp.leftMargin
                val tc = top + lp.topMargin
                val rc = lc + child.measuredWidth
                val bc = tc + child.measuredHeight
                child.layout(lc, tc, rc, bc)
                left += (child.measuredWidth + lp.leftMargin
                        + lp.rightMargin)
            }
            top += lineHeight
        }
    }

    fun clear() {
        removeAllViews()
        fold = true
        isAddFoldView = false
    }
}
