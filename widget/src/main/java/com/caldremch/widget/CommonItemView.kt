package com.caldremch.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.android.synthetic.main.common_item_view.view.*

/**
 *
 * @author: Caldremch
 *
 * @date: 2020/7/8
 *
 * @describe: 按需添加所有View
 *
 * 功能:
 * [左边标题]
 * [子标题][左右]
 *  [红点][左右]
 * [消息数目][左右]
 * [toggle]
 * [底部横线]
 * 三种情况
 * 1.[全部][full width]
 * 2.[开始于左边标题-end2endOf rightView[arrow/标题]]
 * 3.[start2start parent--->end2endOf rightView]
 *
 **/

class CommonItemView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    var title: String? = null
        set(value) {
            field = value
            tv_title.text = value
        }


    init {
        View.inflate(context, R.layout.common_item_view, this)
        val a = context.obtainStyledAttributes(attrs, R.styleable.CommonItemView, defStyleAttr, 0)
        this.title = a.getString(R.styleable.CommonItemView_civ_title)
        a.recycle()

    }


//    var redCount: String? = null
//        set(value) {
//            field = value
//            if (TextUtils.isEmpty(value)){
//                tv_count.text = null
//            }else{
//                tv_count.text = value
//            }
//        }

//    var leftTitle: String? = null
//        set(value) {
//            field = value
//            tv_left.text = value
//        }

//    var rightTitle: String? = null
//        set(value) {
//            field = value
//            tv_right.text = value
//        }

}