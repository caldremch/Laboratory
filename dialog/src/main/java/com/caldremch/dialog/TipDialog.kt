package com.caldremch.dialog

import android.graphics.Color
import android.text.TextUtils
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.Guideline
import androidx.fragment.app.FragmentManager

/**
 *
 *Created by Caldremch on 2020/6/17.
 *
 **/


inline fun AppCompatActivity.tipDialog(dsl: TipDialog.() -> Unit): TipDialog {
    val dialog = TipDialog()
    dialog.apply(dsl)
    dialog.show(this.supportFragmentManager, "tipDialog")
    return dialog
}

open class TipDialog : BaseDialog() {

    var titleText: String? = null
    var descText: String? = null
    var negativeText: String? = null
    var negativeColor: String? = null
    var positiveText: String? = null
    var positiveColor: String? = null

    //int color todo 更多属性定制
//    var negativeColor: String? = null
//    var positiveColor: String? = null

    var positiveListener: View.OnClickListener? = null
    var negativeListener: View.OnClickListener? = null

    private lateinit var tvTitle: TextView
    private lateinit var tvDesc: TextView
    private lateinit var tvNegative: TextView
    private lateinit var vHorizontalDivider: View
    private lateinit var vVerticalDivider: View
    private lateinit var tvPositive: TextView
    private lateinit var guideline: Guideline


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

        negativeText?.let {
            tvNegative.text = it
            tvNegative.visibility = View.VISIBLE
            negativeColor?.let {
                tvNegative.setTextColor(Color.parseColor(it))
            }
        }

        positiveText?.let {
            tvPositive.text = it
            tvPositive.visibility = View.VISIBLE
            positiveColor?.let {
                tvPositive.setTextColor(Color.parseColor(it))
            }
        }

        titleText?.let {
            tvTitle.text = it
            tvTitle.visibility = View.VISIBLE
        }

        descText?.let {
            tvDesc.text = it
        }

        tvNegative.setOnClickListener {
            negativeListener?.onClick(it)
        }

        tvPositive.setOnClickListener {
            positiveListener?.onClick(it)
        }

        if (TextUtils.isEmpty(negativeText)) {
            tvNegative.visibility = View.GONE
            guideline.setGuidelinePercent(0f)

        }

        if (TextUtils.isEmpty(positiveText)) {
            tvPositive.visibility = View.GONE
            guideline.setGuidelinePercent(1f)

        }
    }

}