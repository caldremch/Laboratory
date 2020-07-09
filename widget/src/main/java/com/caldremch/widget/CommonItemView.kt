package com.caldremch.widget

import android.content.Context
import android.graphics.Color
import android.text.TextUtils
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
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

    var civ_left_title: String? = null
        set(value) {
            field = value
            if (!TextUtils.isEmpty(field)) {

                val targetId = R.id.civ_tv_left_title

                if (isExist(targetId)) {
                    setExistTvText(targetId, field)
                    return
                }
                val tv = TextView(context)
                tv.text = value
                tv.id = targetId
                addView(tv)
                val set = ConstraintSet()
                set.constrainHeight(tv.id, ConstraintSet.WRAP_CONTENT)
                set.constrainWidth(tv.id, ConstraintSet.WRAP_CONTENT)
                set.connect(
                    tv.id,
                    ConstraintSet.START,
                    tv_title.id,
                    ConstraintSet.END
                )
                set.connect(
                    tv.id,
                    ConstraintSet.TOP,
                    tv_title.id,
                    ConstraintSet.TOP
                )
                set.connect(
                    tv.id,
                    ConstraintSet.BOTTOM,
                    tv_title.id,
                    ConstraintSet.BOTTOM
                )
                set.setMargin(tv.id, 6, 15)
                set.applyTo(this)
            }

        }


    var civ_right_title: String? = null
        set(value) {
            field = value
            if (!TextUtils.isEmpty(field)) {
                if (isExist(R.id.civ_tv_right_title)) {
                    setExistTvText(R.id.civ_tv_right_title, field)
                    return
                }
                val rightTv = TextView(context)
                rightTv.text = value
                rightTv.id = R.id.civ_tv_right_title
                addView(rightTv)
                val set = ConstraintSet()
                set.constrainHeight(rightTv.id, ConstraintSet.WRAP_CONTENT)
                set.constrainWidth(rightTv.id, ConstraintSet.WRAP_CONTENT)

                if (isExist(R.id.civ_iv_right)) {
                    set.connect(
                        rightTv.id,
                        ConstraintSet.END,
                        R.id.civ_iv_right,
                        ConstraintSet.START
                    )
                } else {
                    set.connect(
                        rightTv.id,
                        ConstraintSet.END,
                        ConstraintSet.PARENT_ID,
                        ConstraintSet.END
                    )
                }

                set.connect(
                    rightTv.id,
                    ConstraintSet.TOP,
                    tv_title.id,
                    ConstraintSet.TOP
                )
                set.connect(
                    rightTv.id,
                    ConstraintSet.BOTTOM,
                    tv_title.id,
                    ConstraintSet.BOTTOM
                )
                set.applyTo(this)
            }

        }


    var show_bottom_line: Boolean = true
        set(value) {
            field = value
            if (field) {
                val set = ConstraintSet()
                val lineView = View(context)
                addView(lineView)
                lineView.setBackgroundColor(Color.BLACK)
                lineView.id = R.id.civ_v_bottom_line
                set.constrainWidth(lineView.id, 0)
                set.constrainHeight(lineView.id, 1)
                set.connect(lineView.id, ConstraintSet.LEFT, tv_title.id, ConstraintSet.LEFT)
                set.connect(
                    lineView.id,
                    ConstraintSet.BOTTOM,
                    ConstraintSet.PARENT_ID,
                    ConstraintSet.BOTTOM
                )
                set.applyTo(this)


            }
        }
    var show_right_arrow: Boolean = true
        set(value) {
            field = value
            if (field) {
                val set = ConstraintSet()
                val view = ImageView(context)
                view.setImageResource(R.drawable.ic_arrow_right)
                addView(view)
                view.id = R.id.civ_iv_right
                set.constrainWidth(view.id, ConstraintSet.WRAP_CONTENT)
                set.constrainHeight(view.id, ConstraintSet.WRAP_CONTENT)
                set.connect(
                    view.id,
                    ConstraintSet.END,
                    ConstraintSet.PARENT_ID,
                    ConstraintSet.END
                )
                set.connect(
                    view.id,
                    ConstraintSet.TOP,
                    tv_title.id,
                    ConstraintSet.TOP
                )
                set.connect(
                    view.id,
                    ConstraintSet.BOTTOM,
                    tv_title.id,
                    ConstraintSet.BOTTOM
                )
                set.setMargin(view.id, 7, 20)
                set.applyTo(this)

                //由于字段设置的先后顺序问题, 这里需要矫正一下右边标题的约束
                if (isExist(R.id.civ_tv_right_title)) {
                    val rightTv = findViewById<View>(R.id.civ_tv_right_title)
                    val set2 = ConstraintSet()
                    set2.clone(this)
                    set2.connect(rightTv.id, ConstraintSet.END, view.id, ConstraintSet.START)
                    set.setMargin(rightTv.id, 7, 20)
                    set2.applyTo(this)
                }

            }
        }

    /**
     * 根据id, 设置内容
     */
    private fun setExistTvText(id: Int, text: String?) {
        findViewById<TextView>(id).text = text
    }

    /**
     * 添加是否安全, 不存在的话, 则添加安全
     */
    private fun isExist(id: Int): Boolean {
        return findViewById<View>(id) != null
    }

    init {
        View.inflate(context, R.layout.common_item_view, this)
        val a = context.obtainStyledAttributes(attrs, R.styleable.CommonItemView, defStyleAttr, 0)
        this.title = a.getString(R.styleable.CommonItemView_civ_title)
        this.civ_right_title = a.getString(R.styleable.CommonItemView_civ_right_title)
        this.civ_left_title = a.getString(R.styleable.CommonItemView_civ_left_title)
        this.show_bottom_line = a.getBoolean(R.styleable.CommonItemView_civ_show_bottom_line, true)
        this.show_right_arrow = a.getBoolean(R.styleable.CommonItemView_civ_show_right_arrow, true)
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