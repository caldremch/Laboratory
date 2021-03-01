package com.caldremch.widget

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.text.TextUtils
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import by.kirich1409.viewbindingdelegate.viewBinding
import com.caldremch.widget.databinding.CommonItemViewBinding

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

    private  val binding by viewBinding(CommonItemViewBinding::bind)

    var title: String? = null
        set(value) {
            field = value
            binding.tvTitle.text = value
            if (isExist(R.id.civ_iv_left)) {
                val set = ConstraintSet()
                set.clone(this)
                set.connect(binding.tvTitle.id, ConstraintSet.START, R.id.civ_iv_left, ConstraintSet.END)
                set.applyTo(this)
            }
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
                        binding.tvTitle.id,
                    ConstraintSet.END
                )
                set.connect(
                    tv.id,
                    ConstraintSet.TOP,
                    binding.tvTitle.id,
                    ConstraintSet.TOP
                )
                set.connect(
                    tv.id,
                    ConstraintSet.BOTTOM,
                    binding.tvTitle.id,
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
                set.setMargin(rightTv.id, 7, 20)
                set.connect(
                    rightTv.id,
                    ConstraintSet.TOP,
                    binding.tvTitle.id,
                    ConstraintSet.TOP
                )
                set.connect(
                    rightTv.id,
                    ConstraintSet.BOTTOM,
                    binding.tvTitle.id,
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
                set.connect(lineView.id, ConstraintSet.LEFT, binding.tvTitle.id, ConstraintSet.LEFT)
                set.connect(
                    lineView.id,
                    ConstraintSet.BOTTOM,
                    ConstraintSet.PARENT_ID,
                    ConstraintSet.BOTTOM
                )
                set.applyTo(this)


            }
        }
    var civ_left_image: Drawable? = null
        set(value) {
            field = value
            if (field != null) {
                val view = ImageView(context)
                addView(view)
                view.setImageDrawable(field)
                view.id = R.id.civ_iv_left
                val set = ConstraintSet()
                set.constrainWidth(view.id, dp2px(24f))
                set.constrainHeight(view.id, dp2px(24f))
                set.connect(
                    view.id,
                    ConstraintSet.START,
                    ConstraintSet.PARENT_ID,
                    ConstraintSet.START
                )

                set.connect(
                    view.id,
                    ConstraintSet.TOP,
                    binding.tvTitle.id,
                    ConstraintSet.TOP
                )
                set.connect(
                    view.id,
                    ConstraintSet.BOTTOM,
                    binding.tvTitle.id,
                    ConstraintSet.BOTTOM
                )

                set.setMargin(view.id, 6, dp2px(20f))
                set.applyTo(this)

                if (isExist(R.id.tv_title)) {
                    val set2 = ConstraintSet()
                    set2.clone(this)
                    set2.connect(R.id.tv_title, ConstraintSet.START, view.id, ConstraintSet.END)
                    set2.setMargin(view.id, 6, dp2px(15f))
                    set2.applyTo(this)
                }
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
                    binding.tvTitle.id,
                    ConstraintSet.TOP
                )
                set.connect(
                    view.id,
                    ConstraintSet.BOTTOM,
                    binding.tvTitle.id,
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
                    set2.setMargin(rightTv.id, 7, 20)
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
        this.civ_left_image = a.getDrawable(R.styleable.CommonItemView_civ_left_image)
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

    fun dp2px(dpValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

    fun dp2px(dpValue: Int): Int {
        val scale = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }


}