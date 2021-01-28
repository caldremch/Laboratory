package com.caldremch.laboratory.widget

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import androidx.core.util.forEach
import androidx.core.view.marginLeft
import androidx.core.view.marginRight
import kotlin.math.abs

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
            val maxWidth = sizeWidth - paddingLeft - paddingRight - marginLeft - marginRight
            val currentViewInfo = viewInfoList[i]
            currentViewInfo.childWidth = childWidth
            currentViewInfo.childHeight = childHeight

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


//            measureFlexLines.forEach { key, value ->
//                if (key == 1){
//                    Log.d(TAG, "onMeasure: measureFlexLines.size()= ${measureFlexLines.size()} measureFlexLines==> ${value.size} : ${value.joinToString(separator = ",",transform = {
//                        it.debugTitle!!
//                    })}")
//                }
//            }

            if (fold && isAddFoldView.not() && measureFlexLines.size() > FOLD_MAX_LINE) {
                val lineViewInfoListFlag = measureFlexLines[FOLD_MAX_LINE - 1]
                measureFlexLines.forEach { key, value ->
                    Log.d(TAG, "onMeasure: key=$key ${value.size}  --> ${
                        value.joinToString(separator = ",", transform = {
                            it.debugTitle!!
                        })
                    }")
                }
                val foldViewLp = neededLp()
                val foldView = createFoldView(0, foldViewLp, widthMeasureSpec, heightMeasureSpec)
                foldView.layoutParams = foldViewLp
                measureChild(foldView, widthMeasureSpec, heightMeasureSpec)
                //计算累加宽度
                val lineTotalWidth = FlowLabelUtils.getLineTotalWidth(lineViewInfoListFlag)
                //这一行中还剩余的大小
                var deltaWidth = abs(maxWidth - lineTotalWidth)
                //插队View的宽度
                val foldWidth = foldView.measuredWidth + foldViewLp.leftMargin + foldViewLp.rightMargin
                var insertIndex = -1
                if (foldWidth < deltaWidth) {
                    //直接+在当前行的最后一个 item 后面
                    insertIndex = lineViewInfoListFlag.last().index + 1
                } else {
                    var decreaseFactor = 0
                    for (j in (lineViewInfoListFlag.size - 1) downTo 0) {
                        val data = lineViewInfoListFlag[j]
                        decreaseFactor += data.childWidth
                        val tempWidth = lineTotalWidth - decreaseFactor + foldWidth + deltaWidth
                        Log.d(TAG, "onMeasure: ${data.index}--${data.debugTitle} 被插队")
                        if (tempWidth <= maxWidth) {
                            Log.d(TAG, "onMeasure: 剩余空间=$deltaWidth index=${data.index}")
                            insertIndex = data.index
                            //如果被插队的 View 的宽度比较长, 可以减少它宽度然后加在后面, 而不是插队
                            if (data.childWidth > 2 * foldWidth) {
                                val tempView = getChildAt(data.index)
                                val tempLayoutParams = tempView.layoutParams as MarginLayoutParams
                                //更新后的宽度 = 总宽度 - (除当前 View 之外的Line 宽度) - 行留空宽度 - 插队View宽度
                                tempView.layoutParams.width = maxWidth - (lineTotalWidth - data.childWidth) - deltaWidth - foldWidth
                                insertIndex++ //+在后面
                            }
                            break
                        }
                    }

                    //如果cutIndex的 item 宽度 < deltaWidth,那么cutIndex的View 会放在插队 View 的方面, 所以设置 deltaWidthmargin, 将cutIndex的View挤掉
                    val beCutView = viewInfoList[insertIndex]
                    if (beCutView.childWidth < deltaWidth) {
                        foldViewLp.rightMargin = deltaWidth
                    }
                }
                viewInfoList.add(insertIndex, ViewInfo(insertIndex, foldWidth, 0))
                addView(foldView, insertIndex, foldViewLp)
            }
            if (i == cCount - 1) {
                width = Math.max(tempLineWidth, width)
                height += lineHeight
            }
        }


        setMeasuredDimension( //
                if (modeWidth == MeasureSpec.EXACTLY) sizeWidth else width + paddingLeft + paddingRight,
                if (modeHeight == MeasureSpec.EXACTLY) sizeHeight else height + paddingTop + paddingBottom //
        )
    }
}
