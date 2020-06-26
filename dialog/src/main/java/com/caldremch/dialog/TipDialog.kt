package com.caldremch.dialog

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.text.TextUtils
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.Guideline
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

/**
 *
 *Created by Caldremch on 2020/6/17.
 *
 * 提示弹窗 标题/文本内容/左边按钮/右边按钮
 *
 * 所有属性均可定制
 *
 * 样式详见蓝湖地址
 * https://lanhuapp.com/web/#/item/project/board?type=share_mark&pid=0be60e73-b1a4-472b-88c1-39163e02a89c&activeSectionId=&teamId=42c29bb8-6a41-48fd-90eb-2843ef8d1e5b&param=none
 **/


//Activity  dsl调用
inline fun AppCompatActivity.tipDialog(dsl: TipDialog.() -> Unit): TipDialog {
    val dialog = TipDialog(this)
    dialog.apply(dsl)
    dialog.show(this.supportFragmentManager, "tipDialog")
    return dialog
}

//fragment dsl调用
inline fun Fragment.tipDialog(dsl: TipDialog.() -> Unit): TipDialog {
    val dialog = TipDialog(this.context!!)
    dialog.apply(dsl)
    dialog.show(this.childFragmentManager, "tipDialog")
    return dialog
}

//Context dsl调用
inline fun tipDialog(context: Context, dsl: TipDialog.() -> Unit): TipDialog {
    val dialog = TipDialog(context)
    dialog.apply(dsl)
    if (context is AppCompatActivity) {
        dialog.show(context.supportFragmentManager, "tipDialog")
    } else if (context is Fragment) {
        dialog.show((context as Fragment).childFragmentManager, "tipDialog")
    } else {
        throw RuntimeException("啥也不是")
    }
    return dialog
}

open class TipDialog(context: Context) : BaseDialog(context, "tipsDilaog") {

    //标题文本
    var titleText: String? = null
    var titleSize: Float? = null
    var titleColorStr: String? = null
    var titleColorRes: Int? = null  //R.color.xxx类型
    var titleBold = true

    //中间内容文本
    var descText: String? = null
    var descSize: Float? = null
    var descColorStr: String? = null
    var descColorRes: Int? = null
    var descBold = false

    //左边文本
    var leftText: String? = null
    var leftColorStr: String? = null
    var leftColorRes: Int? = null
    var leftSize: Float? = null
    var leftBold = false

    //右边文本
    var rightText: String? = null
    var rightColorStr: String? = null
    var rightColorRes: Int? = null
    var rightSize: Float? = null
    var rightBold = false

    //点击事件
    var leftBtnListener: (() -> Unit)? = null
    var rightBtnListener: (() -> Unit)? = null

    private lateinit var tvTitle: TextView
    private lateinit var tvDesc: TextView
    private lateinit var tvNegative: TextView
    private lateinit var vHorizontalDivider: View
    private lateinit var vVerticalDivider: View
    private lateinit var tvPositive: TextView
    private lateinit var guideline: Guideline

    fun leftClick(left: () -> Unit) {
        leftBtnListener = left
    }


    fun rightClick(right: () -> Unit) {
        rightBtnListener = right
    }

    override fun getLayoutId(): Int {
        return R.layout.tips_dialog
    }

    override fun initView(rootView: View) {
        guideline = rootView.findViewById(R.id.g)
        tvTitle = rootView.findViewById(R.id.tv_title)
        tvDesc = rootView.findViewById(R.id.tv_desc)
        tvPositive = rootView.findViewById(R.id.tv_positive)
        tvNegative = rootView.findViewById(R.id.tv_negative)
        vHorizontalDivider = rootView.findViewById(R.id.v_horization)
        vVerticalDivider = rootView.findViewById(R.id.v_vertical)

        tvStyle(
            tv = tvNegative,
            textStr = leftText,
            size = leftSize,
            colorStr = leftColorStr,
            colorRes = leftColorRes,
            isBold = leftBold
        )

        tvStyle(
            tv = tvPositive,
            textStr = rightText,
            size = rightSize,
            colorStr = rightColorStr,
            colorRes = rightColorRes,
            isBold = rightBold
        )


        tvStyle(
            tv = tvTitle,
            textStr = titleText,
            size = titleSize,
            colorStr = titleColorStr,
            colorRes = titleColorRes,
            isBold = titleBold
        )

        tvStyle(
            tv = tvDesc,
            textStr = descText,
            size = descSize,
            colorStr = descColorStr,
            colorRes = descColorRes,
            isBold = rightBold
        )

        tvNegative.setOnClickListener {
            dismiss()
            leftBtnListener?.let { clickInstance ->
                clickInstance()
            }
        }

        tvPositive.setOnClickListener {
            dismiss()
            rightBtnListener?.let { clickInstance ->
                clickInstance()
            }
        }

        if (TextUtils.isEmpty(leftText)) {
            guideline.setGuidelinePercent(0f)
        }

        if (TextUtils.isEmpty(rightText)) {
            guideline.setGuidelinePercent(1f)
        }

    }

    //设置tv样式
    fun tvStyle(tv: TextView, textStr: String?, size: Float?, colorStr: String?, colorRes: Int?, isBold:Boolean) {

        textStr?.let {
            tv.text = it
            tv.visibility = View.VISIBLE
            if (colorRes != null) {
                tv.setTextColor(ContextCompat.getColor(tv.context, colorRes))
            } else if (colorStr != null) {
                tv.setTextColor(Color.parseColor(colorStr))
            }
            size?.let { tv.textSize = it }

            if (isBold){
                tv.typeface = Typeface.DEFAULT_BOLD
            }else{
                tv.typeface = Typeface.DEFAULT
            }
        }

    }

}