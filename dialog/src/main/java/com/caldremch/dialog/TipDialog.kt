package com.caldremch.dialog

import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.Typeface
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
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
 **/


//Activity  dsl调用
inline fun AppCompatActivity.tipDialog(dsl: TipDialog.() -> Unit): TipDialog {
    val dialog = TipDialog(this)
    dialog.apply(dsl)
    dialog.show()
    return dialog
}

//fragment dsl调用
inline fun tipDialog(fragment: androidx.fragment.app.Fragment, dsl: TipDialog.() -> Unit): TipDialog {
    val dialog = TipDialog(fragment)
    dialog.apply(dsl)
    dialog.show()
    return dialog
}

//Context dsl调用
inline fun tipDialog(context: Context, dsl: TipDialog.() -> Unit): TipDialog {
    val dialog = TipDialog(context)
    dialog.apply(dsl)
    dialog.show()
    return dialog
}

open class TipDialog : BaseDialog {

    constructor(context: Context) : super(context) {
    }

    constructor(fragment: Fragment) : super(fragment) {
    }

    //标题文本
    var titleText: String? = null
    var titleTextRes: Int? = null
    var titleSize: Float? = null
    var titleColorStr: String? = null
    var titleColorRes: Int? = null  //R.color.xxx类型
    var titleBold = true

    //中间内容文本
    var descText: String? = null
    var descTextRes: Int? = null
    var descSize: Float? = null
    var descColorStr: String? = null
    var descColorRes: Int? = null
    var descBold = false

    //左边文本
    var leftText: String? = null
    var leftTextRes: Int? = null
    var leftColorStr: String? = null
    var leftColorRes: Int? = null
    var leftSize: Float? = null
    var leftBold = false

    //右边文本
    var rightText: String? = null
    var rightTextRes: Int? = null
    var rightColorStr: String? = null
    var rightColorRes: Int? = null
    var rightSize: Float? = null
    var rightBold = true

    //点击事件 kotlin
    private var leftBtnListener: (() -> Unit)? = null
    private var rightBtnListener: (() -> Unit)? = null

    //点击事件, java
    var onLeftBtnListener: View.OnClickListener? = null
    var onRightBtnListener: View.OnClickListener? = null

    private lateinit var tvTitle: TextView
    private lateinit var tvDesc: TextView
    private lateinit var tvNegative: TextView
    private lateinit var vHorizontalDivider: View
    private lateinit var vVerticalDivider: View
    private lateinit var tvPositive: TextView
    private lateinit var guideline: Guideline
    var customView: View? = null //扩展布局, 任意View

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
                tv = tvTitle,
                textStr = titleText,
                textRes = titleTextRes,
                size = titleSize,
                colorStr = titleColorStr,
                colorRes = titleColorRes,
                isBold = titleBold
        )


        if (customView != null) {
            if (rootView is ViewGroup) {
                val tvDescLayoutParams = tvDesc.layoutParams
                customView!!.id = tvDesc.id
                rootView.removeView(tvDesc)
                rootView.addView(customView, tvDescLayoutParams)
            }
        } else {
            tvStyle(
                    tv = tvDesc,
                    textStr = descText,
                    textRes = descTextRes,
                    size = descSize,
                    colorStr = descColorStr,
                    colorRes = descColorRes,
                    isBold = descBold
            )
        }

        tvStyle(
                tv = tvNegative,
                textStr = leftText,
                textRes = leftTextRes,
                size = leftSize,
                colorStr = leftColorStr,
                colorRes = leftColorRes,
                isBold = leftBold
        )

        tvStyle(
                tv = tvPositive,
                textStr = rightText,
                textRes = rightTextRes,
                size = rightSize,
                colorStr = rightColorStr,
                colorRes = rightColorRes,
                isBold = rightBold
        )


        tvNegative.setOnClickListener {
            dismiss()

            if (onLeftBtnListener != null) {
                onLeftBtnListener!!.onClick(it)
                return@setOnClickListener
            }

            leftBtnListener?.let { clickInstance ->
                clickInstance()
            }
        }

        tvPositive.setOnClickListener {
            dismiss()

            if (onRightBtnListener != null) {
                onRightBtnListener!!.onClick(it)
                return@setOnClickListener
            }

            rightBtnListener?.let { clickInstance ->
                clickInstance()
            }
        }

        if (TextUtils.isEmpty(leftText) && leftTextRes == null) {
            guideline.setGuidelinePercent(0f)
        }

        if (TextUtils.isEmpty(rightText) && rightTextRes == null) {
            guideline.setGuidelinePercent(1f)
        }

    }

    override fun initEvent() {
        dialog?.setOnShowListener {

        }
    }

    //设置tv样式
    fun tvStyle(tv: TextView, textStr: String?, textRes: Int?, size: Float?, colorStr: String?, colorRes: Int?, isBold: Boolean) {

        var str: String? = null
        if (textRes != null) {
            str = context?.resources?.getString(textRes)
        }
        if (TextUtils.isEmpty(str)) {
            str = textStr
        }

        str?.let {
            tv.text = it
            tv.visibility = View.VISIBLE
            if (colorRes != null) {
                tv.setTextColor(ContextCompat.getColor(tv.context, colorRes))
            } else if (colorStr != null) {
                tv.setTextColor(Color.parseColor(colorStr))
            }
            size?.let { tv.textSize = it }

            if (isBold) {
                tv.typeface = Typeface.DEFAULT_BOLD
            } else {
                tv.typeface = Typeface.DEFAULT
            }
        }

    }

    class Builder private constructor() {
        private var centerView: View? = null
        private var titleText: String? = null
        private var titleTextRes: Int? = null
        private var titleSize: Float? = null
        private var titleColorStr: String? = null
        private var titleColorRes: Int? = null
        private var titleBold: Boolean = true
        private var descText: String? = null
        private var descTextRes: Int? = null
        private var descSize: Float? = null
        private var descColorStr: String? = null
        private var descColorRes: Int? = null
        private var descBold = false
        private var leftText: String? = null
        private var leftTextRes: Int? = null
        private var leftColorStr: String? = null
        private var leftColorRes: Int? = null
        private var leftSize: Float? = null
        private var leftBold = false
        private var rightText: String? = null
        private var rightTextRes: Int? = null
        private var rightColorStr: String? = null
        private var rightColorRes: Int? = null
        private var rightSize: Float? = null
        private var rightBold: Boolean = true
        private var backPressDisable: Boolean = false //禁用后退键

        private var gravity: Int = Gravity.CENTER //dialog位置
        private var widthScale: Float = 0.75f //宽占(屏幕宽度)比
        private var cancelOutSide: Boolean = true //点击弹窗以外区域是否关闭
        private var isAllowingStateLoss = true //commit 是否允许状态丢失
        private var anim = DialogAnim.NONE //弹窗动画

        private var onLeftBtnListener: View.OnClickListener? = null
        private var onRightBtnListener: View.OnClickListener? = null
        private var onShowListener: DialogInterface.OnShowListener? = null
        private var dismissListener: DialogInterface.OnDismissListener? = null

        private var context: Context? = null
        private var fragment: Fragment? = null

        constructor(context: Context) : this() {
            this.context = context
        }

        constructor(fragment: Fragment) : this() {
            this.fragment = fragment
        }

        fun setCustomView(view: View): Builder {
            centerView = view
            return this
        }

        fun backPressDisable(): Builder {
            backPressDisable = true
            cancelOutSide = false
            return this
        }

        fun setTitleText(var1: String?): Builder {
            titleText = var1
            return this
        }

        fun setTitleTextRes(var1: Int?): Builder {
            titleTextRes = var1
            return this
        }

        fun setTitleSize(var1: Float?): Builder {
            titleSize = var1
            return this
        }

        fun setTitleColorStr(var1: String?): Builder {
            titleColorStr = var1
            return this
        }

        fun setTitleColorRes(var1: Int?): Builder {
            titleColorRes = var1
            return this
        }

        fun setTitleBold(var1: Boolean): Builder {
            titleBold = var1
            return this
        }

        fun setDescText(var1: String?): Builder {
            descText = var1
            return this
        }

        fun setDescTextRes(var1: Int?): Builder {
            descTextRes = var1
            return this
        }

        fun setDescSize(var1: Float?): Builder {
            descSize = var1
            return this
        }

        fun setDescColorStr(var1: String?): Builder {
            descColorStr = var1
            return this
        }

        fun setDescColorRes(var1: Int?): Builder {
            descColorRes = var1
            return this
        }

        fun setDescBold(var1: Boolean): Builder {
            descBold = var1
            return this
        }

        fun setLeftText(var1: String?): Builder {
            leftText = var1
            return this
        }

        fun setLeftTextRes(var1: Int?): Builder {
            leftTextRes = var1
            return this
        }

        fun setLeftColorStr(var1: String?): Builder {
            leftColorStr = var1
            return this
        }

        fun setLeftColorRes(var1: Int?): Builder {
            leftColorRes = var1
            return this
        }

        fun setLeftSize(var1: Float?): Builder {
            leftSize = var1
            return this
        }

        fun setLeftBold(var1: Boolean): Builder {
            leftBold = var1
            return this
        }

        fun setRightText(var1: String?): Builder {
            rightText = var1
            return this
        }

        fun setRightTextRes(var1: Int?): Builder {
            rightTextRes = var1
            return this
        }

        fun setRightColorStr(var1: String?): Builder {
            rightColorStr = var1
            return this
        }

        fun setRightColorRes(var1: Int?): Builder {
            rightColorRes = var1
            return this
        }

        fun setRightSize(var1: Float?): Builder {
            rightSize = var1
            return this
        }

        fun setRightBold(var1: Boolean): Builder {
            rightBold = var1
            return this
        }

        fun setOnLeftBtnListener(var1: View.OnClickListener?): Builder {
            onLeftBtnListener = var1
            return this
        }

        fun setOnRightBtnListener(var1: View.OnClickListener?): Builder {
            onRightBtnListener = var1
            return this
        }

        fun defaultButton(): Builder {
            leftText = "取消"
            rightText = "确定"
            return this
        }

        fun defaultSingleRight(): Builder {
            rightText = "确定"
            return this
        }

        fun defaultSingleLeft(): Builder {
            leftText = "取消"
            return this
        }

        fun singleRight(text: String?): Builder {
            rightText = text
            return this
        }

        fun singleLeft(text: String?): Builder {
            leftText = text
            return this
        }

        fun singleRight(textRes: Int?): Builder {
            rightTextRes = textRes
            return this
        }

        fun singleLeft(textRes: Int?): Builder {
            leftTextRes = textRes
            return this
        }


        fun setGravity(gravity: Int): Builder {
            this.gravity = gravity
            return this
        }

        fun setWidthScale(widthScale: Float): Builder {
            this.widthScale = widthScale
            return this
        }

        fun setCancelOutSide(cancelOutSide: Boolean): Builder {
            this.cancelOutSide = cancelOutSide
            return this
        }

        fun setAllowingStateLoss(allowingStateLoss: Boolean): Builder {
            this.isAllowingStateLoss = allowingStateLoss
            return this
        }

        fun setOnDismissListener(listener: DialogInterface.OnDismissListener?): Builder {
            this.dismissListener = listener
            return this
        }

        fun setOnShowListener(listener: DialogInterface.OnShowListener?): Builder {
            this.onShowListener = listener
            return this
        }

        fun build(): TipDialog {

            val tipDialog: TipDialog = when {
                context != null -> {
                    TipDialog(context!!)
                }
                fragment != null -> {
                    TipDialog(fragment!!)
                }
                else -> {
                    throw RuntimeException("unknow error")
                }
            }

            //标题
            tipDialog.titleBold = titleBold
            tipDialog.titleColorRes = titleColorRes
            tipDialog.titleColorStr = titleColorStr
            tipDialog.titleSize = titleSize
            tipDialog.titleText = titleText
            tipDialog.titleTextRes = titleTextRes

            //文本内容
            tipDialog.descBold = descBold
            tipDialog.descSize = descSize
            tipDialog.descText = descText
            tipDialog.descTextRes = descTextRes
            tipDialog.descColorRes = descColorRes
            tipDialog.descColorStr = descColorStr

            //左边按钮
            tipDialog.leftBold = leftBold
            tipDialog.leftSize = leftSize
            tipDialog.leftText = leftText
            tipDialog.leftTextRes = leftTextRes
            tipDialog.leftColorRes = leftColorRes
            tipDialog.leftColorStr = leftColorStr

            //右边按钮
            tipDialog.rightText = rightText
            tipDialog.rightTextRes = rightTextRes
            tipDialog.rightColorStr = rightColorStr
            tipDialog.rightColorRes = rightColorRes
            tipDialog.rightSize = rightSize
            tipDialog.rightBold = rightBold

            //监听事件
            tipDialog.onLeftBtnListener = onLeftBtnListener
            tipDialog.onRightBtnListener = onRightBtnListener

            //设置自定义View
            tipDialog.customView = centerView
            tipDialog.backPressDisable = backPressDisable

            //基类属性
            tipDialog.cancelOutSide = cancelOutSide
            tipDialog.widthScale = widthScale
            tipDialog.gravity = gravity
            tipDialog.isAllowingStateLoss = isAllowingStateLoss
            tipDialog.anim = anim
            tipDialog.onShowListener = onShowListener
            tipDialog.dismissListener = dismissListener

            return tipDialog
        }
    }
}