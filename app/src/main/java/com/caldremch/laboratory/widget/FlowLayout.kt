package com.caldremch.laboratory.widget

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import androidx.core.util.forEach
import androidx.core.view.marginLeft
import androidx.core.view.marginRight

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
open class FlowLayout @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : SearchHistoryFlowLayout(context, attrs, defStyleAttr) {
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
                    Log.d(TAG, "onMeasure: itemViewIndex=$itemViewIndex")
                    var insertIndex = -1
                    val foldWidth = foldView.measuredWidth + foldViewLp.leftMargin + foldViewLp.rightMargin
                    //是否需要更改前一个的 Item 宽度
                    val needToUpdatePreItemWidth = itemViewInfo.childWidth / 2 >= (foldView.measuredWidth + foldViewLp.leftMargin + foldViewLp.rightMargin)

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

                        //这一行中还剩余的大小
                        val deltaWidth = maxWidth - lineTotalWidth - marginLeft - marginRight
                        // 该循环判断, + 直到能找到足够宽度, 放下目标 itemView
                        val size = lineViewInfoListFlag.size
                        //用户判断是否宽度够了
                        var tempNeedWidth = deltaWidth
                        //用于记录被插队的item
                        val cutInLineList = arrayListOf<ViewInfo>()
                        for (j in (size - 1) downTo 0) {
                            val data = lineViewInfoListFlag[j]
                            if (foldWidth <= (tempNeedWidth + data.childWidth)) {
                                Log.d(TAG, "onMeasure: data.index =  ${data.index}")
                                insertIndex = data.index - 1
                                //同时将最后一个被插队的 item ,宽度设置为最大, 使它无法在当前行继续摆放
                                val lastCutInItemView = getChildAt(insertIndex)
                                lastCutInItemView.layoutParams.width = maxWidth
                                break
                            } else {
                                tempNeedWidth += data.childWidth
                                cutInLineList.add(data)
                            }
                        }

                        //第一个被插队的 item, 不可能还能再当前行, 所以直接移除
                        //需要踢除被插队后的item, 通过插队 item 的 margin 挤掉被插队的 item
                        Log.d(TAG, "onMeasure: ${cutInLineList.size}个被插队")
                        var cutMargin = 0
                        if (cutInLineList.isNotEmpty()) {
                            cutInLineList.forEach {
                                cutMargin += it.childWidth
                            }
                        }
//                        foldViewLp.rightMargin = cutMargin
                    }
                    Log.d(TAG, "onMeasure: 寻找能摆放的位置, 直接加载后面 $insertIndex")
                    viewInfoList.add(itemViewIndex, ViewInfo(insertIndex, foldWidth, 0))
                    addView(foldView, insertIndex, foldViewLp)
                }

            }
            if (i == cCount - 1) {
                width = Math.max(tempLineWidth, width)
                height += lineHeight
            }
        }

        measureFlexLines.forEach { key, value ->
//            Log.d(TAG, "onMeasure: $key --> ${value.size}")
        }

        setMeasuredDimension( //
                if (modeWidth == MeasureSpec.EXACTLY) sizeWidth else width + paddingLeft + paddingRight,
                if (modeHeight == MeasureSpec.EXACTLY) sizeHeight else height + paddingTop + paddingBottom //
        )
    }
}
