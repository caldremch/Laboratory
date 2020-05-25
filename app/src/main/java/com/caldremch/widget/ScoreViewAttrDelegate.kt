package com.caldremch.widget

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import com.caldremch.laboratory.R

/**
 *
 * @author Caldremch
 *
 * @date 2020-05-25 17:00
 *
 * @email caldremch@163.com
 *
 * @describe  属性管理
 *
 **/
class ScoreViewAttrDelegate(val context: Context) {

    //满分 默认 104 分
    var svFullScore: Float = 104f

    //颜色
    var svScoreTextColor: Int = Color.parseColor("#9699A0")

    //文字大小
    var svScoreTextSize: Float = dp2px(12)

    //分数刻线 60,80 等
    var svScoreFlag = "60,80"

    //分数标志描述, 比如 60及格, 80分优秀, 用逗号隔开: 60及格,80分优秀
    var svScoreFlagDesc = "达标(60),优秀(80)"

    // 中间标题-相关
    var svCenterText = "8.8"
    var svCenterTextColor = Color.parseColor("#191919")
    var svCenterTextSize: Float = dp2px(40)

    //中间子标题-相关
    var svCenterSubText = "今日得分"
    var svCenterSubTextColor = Color.parseColor("#4C4C4C")
    var svCenterSubTextSize: Float = dp2px(12)

    //圆弧相关
    //圆弧线宽
    var svArcLineWidth: Float = dp2px(20)

    //圆弧底色
    var svArcBackColor = Color.parseColor("#E5E9ED")

    //圆弧高亮色
    var svArcFrontColor = Color.parseColor("#257BF4")


    //圆弧刻线相关
    //圆弧刻线底色
    var svFlagLineBackColor = Color.parseColor("#F5F5F7")

    //圆弧刻线高亮色
    var svFlagLineFrontColor = Color.parseColor("#257BF4")

    //圆弧刻线线宽
    var svFlagLineWidth: Float = dp2px(2)


    fun dp2px(dp: Int): Float {
        val displayMetrics = context.resources
            .displayMetrics
        return (dp * displayMetrics.density + 0.5).toFloat()
    }

    fun initAttr(attrs: AttributeSet?) {
        attrs?.let {
            val ta = context.obtainStyledAttributes(it, R.styleable.ScoreView)

            ta.recycle()
        }
    }

}