package com.caldremch.widget

import android.content.Context
import android.graphics.Color
import android.text.TextUtils
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

    val DEFAULT_SCORE_FLAG = "60,80"
    val DEFAULT_SCORE_FLAG_DESC = "达标(60),优秀(80)"
    val DEFAULT_FULL_SCORE = 104f
    val DEFAULT_FULL_ANGLE = 250f

    //满分 默认 104 分
    var svFullScore: Float = DEFAULT_FULL_SCORE

    //默认总角度为 250度
    var svFullAngle: Float = DEFAULT_FULL_ANGLE


    //分数在圆弧上的标志的颜色
    var svScoreFlagLineColor: Int = Color.WHITE

    //颜色
    var svScoreTextColor: Int = Color.parseColor("#9699A0")

    //文字大小
    var svScoreTextSize: Float = dp2px(12)

    //分数刻线 60,80 等
    var svScoreFlag: String? = DEFAULT_SCORE_FLAG

    //分数标志描述, 比如 60及格, 80分优秀, 用逗号隔开: 60及格,80分优秀
    var svScoreFlagDesc: String? = DEFAULT_SCORE_FLAG_DESC

    var scoreInfoList = mutableListOf<ScoreInfo>()


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
    var svFlagLineHeight: Float = dp2px(2)

    private var svCurrentScore: Float = 0f //当前分数
    var svCurrentScoreToAngle: Float = 0f //当前分数对应的角度


    fun dp2px(dp: Int): Float {
        val displayMetrics = context.resources
            .displayMetrics
        return (dp * displayMetrics.density + 0.5).toFloat()
    }

    fun initAttr(attrs: AttributeSet?) {
        attrs?.let {
            val ta = context.obtainStyledAttributes(it, R.styleable.ScoreView)
            svFullScore = ta.getFloat(R.styleable.ScoreView_sv_full_score, DEFAULT_FULL_SCORE)
            svScoreFlag = ta.getString(R.styleable.ScoreView_sv_score_flag)
            svScoreFlagDesc = ta.getString(R.styleable.ScoreView_sv_score_flag_desc)
            svCurrentScore = ta.getFloat(R.styleable.ScoreView_sv_current_score, 0f)
            svCurrentScoreToAngle = scoreToAngle(svCurrentScore)
            handleScoreAndDesc(svScoreFlag, svScoreFlagDesc)
            ta.recycle()
        }
    }

     fun handleScoreAndDesc(svScoreFlag:String?, svScoreFlagDesc:String?) {


        if (TextUtils.isEmpty(svScoreFlag) || TextUtils.isEmpty(svScoreFlagDesc)) {
            return
        }
        val scoreFlags = svScoreFlag!!.split(",")

        val scoreFlagDescs = svScoreFlagDesc!!.split(",")

        if (scoreFlags.size != scoreFlagDescs.size) {
            return
        }

        //分割线, 左边向左边旋转, 右边向右边旋转
        val middleAngle = svFullAngle / 2

        for (x in scoreFlags.indices) {
            val scoreInfo = ScoreInfo()
            scoreInfo.score = scoreFlags[x].toFloat()
            scoreInfo.scoreDesc = scoreFlagDescs[x]
            val angle = scoreToAngle(scoreInfo.score)
            scoreInfo.scoreAngle = angle - middleAngle
            scoreInfoList.add(scoreInfo)
        }

    }

    fun scoreToAngle(score: Float): Float {
        return (score / svFullScore) * svFullAngle
    }

}

class ScoreInfo() {
    //分数
    var score: Float = 0f

    //分数描述
    var scoreDesc = ""

    //分数所在的位置, 对应的角度
    var scoreAngle = 0f

}