package com.caldremch.widget

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.caldremch.laboratory.BuildConfig

/**
 *
 * @author Caldremch
 *
 * @date 2020-05-22 17:58
 *
 * @email caldremch@163.com
 *
 * @describe  几个信息:
 *
 * 1.满分:104 分
 * 2.60 分
 * 3.80 分刻线
 * 4.圆弧总度数:250(缺口110度)
 * 5.圆弧开始角度: 90+110/2 = 145
 *
 * 计算 60 分:所在的度数是多少: (60/104)*250+145 ≈ 299
 * 计算 80 分:所在的度数是多少: 60分角度+(20/104)*250 = 347  (20/104)*250:48 度
 *
 * 开始分数:0 位于 145 度
 * 满分 104 位于: 90-55
 *
 **/
class ScoreView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    //最小宽度 170+2*50
    val MIN_SIZE = dp2px(170 + 2 * 50)
    var viewSize: Float = 0f

    val paint = Paint()
    val scorePaint = Paint() //分数画笔
    var rectF = RectF()
    var lineWidth: Float = 0f
    var lineHeight: Float = 0f
    var circleMarginTop = dp2px(20)
    val titleTextSize = dp2px(40)
    val subTitleTextSize = dp2px(12)
    val titleTextColor = Color.parseColor("#191919")
    val subTitleTextColor = Color.parseColor("#4C4C4C")
    val scoreTextColor = Color.parseColor("#9699A0")

    val backColor = Color.parseColor("#E5E9ED")
    val frontColor = Color.parseColor("#257BF4")
    val titleTextBound = Rect()
    val subTitleTextBound = Rect()
    val text = "8.8"
    val subText = "今日得分"
    val score60 = "达标(60)"
    val score80 = "优秀(80)"
    val score0 = "0"
    val score104 = "104"
    val scoreRectBounds = Rect()

    var textFlagWidth: Float = 0f //分数刻度的宽度
    var textFlagHeight: Float = 0f //分数刻度的高度

    //圆弧参数
    var arcWidth: Float = 0f //圆弧的宽度
    var arcRadius: Float = 0f //圆弧半径
    var arcHeight: Float = 0f //圆弧的高度

    //圆弧信息
    val arcInfo = ArcInfo()

    val helperPaint = Paint() //测试用的

    val scoreViewAttr = ScoreViewAttrDelegate(context)

    init {

        //处理属性
        scoreViewAttr.initAttr(attrs)

        initHelper()

        paint.isAntiAlias = true
        scoreViewAttr.svArcLineWidth = dp2px(20)
        lineWidth = dp2px(2)
        lineHeight = dp2px(30)

        paint.color = Color.BLACK
        paint.textSize = titleTextSize
        paint.style = Paint.Style.FILL

        //分数刻度
        scorePaint.color = scoreTextColor
        scorePaint.textSize = subTitleTextSize
        scorePaint.style = Paint.Style.FILL

        scorePaint.getTextBounds(score80, 0, score80.length, scoreRectBounds)
        textFlagWidth = scoreRectBounds.width().toFloat()
        textFlagHeight = scoreRectBounds.height().toFloat()

    }

    private fun initHelper() {
        if (BuildConfig.DEBUG) {
            helperPaint.color = Color.RED
            helperPaint.textSize = 12f
            helperPaint.style = Paint.Style.STROKE
            helperPaint.isAntiAlias = true
        }
    }


    //todo padding 未算
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        val specMode = MeasureSpec.getMode(widthMeasureSpec); //获取测量模式
        val specSize = MeasureSpec.getSize(widthMeasureSpec); //获取测量的宽度

        //宽度能小于最小宽度
        when (specMode) {

            MeasureSpec.EXACTLY -> {
                if (specSize < MIN_SIZE) {
                    viewSize = MIN_SIZE
                } else {
                    viewSize = specSize.toFloat()
                }
                arcWidth = viewSize - 2 * scoreViewAttr.svArcLineWidth - 2 * textFlagWidth
                arcRadius = arcWidth / 2
            }

            MeasureSpec.AT_MOST, MeasureSpec.UNSPECIFIED -> {
                viewSize = 2 * textFlagWidth + dp2px(170)
            }
        }

        setMeasuredDimension(viewSize.toInt(), viewSize.toInt())

    }

    override fun onDraw(canvas: Canvas) {
        canvas.save()
        paint.color = Color.BLUE
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = scoreViewAttr.svArcLineWidth
        //dp2px(3) 文字和圆弧的间隔,
        rectF.left = textFlagWidth + dp2px(3) + scoreViewAttr.svArcLineWidth
        rectF.top = circleMarginTop + scoreViewAttr.svArcLineWidth / 2 + textFlagHeight
        rectF.right = rectF.left + arcWidth
        rectF.bottom = rectF.top + arcWidth

        arcInfo.handleInfo(rectF)

        Log.d("tag", "right-left:" + (rectF.right - rectF.left))
        Log.d("tag", "bottom-top:" + (rectF.bottom - rectF.top))

        //圆弧下
        paint.color = backColor
        canvas.drawArc(rectF, 145f, 360f, false, paint)
//        canvas.drawArc(rectF, 145f, 250f, false, paint)

        //圆弧上
        paint.color = frontColor
        canvas.drawArc(rectF, 145f, 100.5f, false, paint)

        if (BuildConfig.DEBUG) {
            canvas.drawRect(rectF, helperPaint)
        }

        paint.strokeWidth = lineWidth
        //刻线边距
        val smallLineMarigin = dp2px(10)
        //刻线长度
        val smallLineSize = scoreViewAttr.svArcLineWidth
        //刻线 startY
        val smallLineStartY = rectF.top + scoreViewAttr.svArcLineWidth / 2 + smallLineMarigin
        //画刻线, 根据设计图, 一共有 35根, 顶部 1 根, 左右两边旋转 17 跟 , 每根旋转 250/35 个度数
        //旋转度数
        val rotateLineAngle = 250f/35
        canvas.save()
        canvas.drawLine(
            arcInfo.centerX, smallLineStartY,
            arcInfo.centerX, smallLineStartY + smallLineSize, paint
        )
        //正方向旋转画刻线
        drawLineByAngle(canvas, rotateLineAngle, smallLineStartY, smallLineSize)
        canvas.restore()
        canvas.save()
        //逆方向旋转画刻线
        drawLineByAngle(canvas, -rotateLineAngle, smallLineStartY, smallLineSize)
        canvas.restore()
        canvas.restore()


        canvas.save()

        //60 分刻线 与文字描述
        //旋转度数299-270 = 29
        scorePaint.getTextBounds(score60, 0, score60.length, scoreRectBounds)
        canvas.rotate(29f, (width / 2).toFloat(), (height / 2).toFloat())
        canvas.drawText(score60, (width / 2).toFloat(), 0f, scorePaint)
        paint.color = Color.WHITE
        paint.strokeWidth = lineWidth
        paint.style = Paint.Style.FILL
        canvas.drawLine(
            (width / 2).toFloat(),
            circleMarginTop - scoreViewAttr.svArcLineWidth / 2,
            (width / 2).toFloat(),
            circleMarginTop + scoreViewAttr.svArcLineWidth / 2,
            paint
        )

        //80 分刻线 与文字描述
        // 在 60 刻度基础上, 再旋转48 度
        scorePaint.getTextBounds(score80, 0, score80.length, scoreRectBounds)
        canvas.rotate(48f, (width / 2).toFloat(), (height / 2).toFloat())
        canvas.drawText(score80, (width / 2).toFloat(), 0f, scorePaint)
        canvas.drawLine(
            (width / 2).toFloat(),
            circleMarginTop - scoreViewAttr.svArcLineWidth / 2,
            (width / 2).toFloat(),
            circleMarginTop + scoreViewAttr.svArcLineWidth / 2,
            paint
        )

        canvas.rotate(48f, (width / 2).toFloat(), (height / 2).toFloat())
        canvas.drawText(score104, (width / 2).toFloat(), 0f, scorePaint)

        canvas.rotate(110f, (width / 2).toFloat(), (height / 2).toFloat())
        canvas.drawText(score0, (width / 2).toFloat(), 0f, scorePaint)
        canvas.restore()

        if (BuildConfig.DEBUG) {

            val rotateAngle = 65f
            //圆弧顶部文字
            canvas.drawText(
                score80,
                arcInfo.centerX - scoreRectBounds.width() / 2,
                circleMarginTop,
                scorePaint
            )
            canvas.save()
            //画出文字中心点
//            canvas.drawCircle(arcInfo.centerX, 0f, dp2px(2), helperPaint)
            //旋转画布
            canvas.rotate(-rotateAngle, arcInfo.centerX, arcInfo.centerY)
            //旋转后的文字中心点, 中心点应该向左移动文字宽度一半的距离, 因为文字平放会比较长
            canvas.drawCircle(arcInfo.centerX, 0f, dp2px(2), helperPaint)
            //根据文字的中心点旋转文字
            canvas.rotate(rotateAngle, arcInfo.centerX, 0f)
            scorePaint.color = Color.RED
            canvas.drawText(score80, arcInfo.centerX - scoreRectBounds.width() / 2, 0f, scorePaint)
            canvas.restore()

            //画出辅助线,居中辅助线
            canvas.drawLine(arcInfo.centerX, 0f, arcInfo.centerX, height.toFloat(), helperPaint)
            canvas.drawLine(0f, arcInfo.centerY, width.toFloat(), arcInfo.centerY, helperPaint)
        }


        //圆弧中心画分数
        paint.color = Color.RED
        paint.textSize = titleTextSize
        paint.style = Paint.Style.FILL
        paint.getTextBounds(text, 0, text.length, titleTextBound)
        val titleY = arcInfo.centerY + titleTextBound.height() / 2
        canvas.drawText(text, arcInfo.centerX - titleTextBound.width() / 2, titleY, paint)
        //今日得分
        paint.color = subTitleTextColor
        paint.textSize = subTitleTextSize
        paint.getTextBounds(subText, 0, subText.length, subTitleTextBound)
        val subTitleMarginTop = dp2px(10)
        canvas.drawText(
            subText, arcInfo.centerX - subTitleTextBound.width() / 2,
            titleY + subTitleTextBound.height() + subTitleMarginTop,
            paint
        )


        //圆 helper
        paint.strokeWidth = 10f
        paint.color = Color.GREEN
        paint.style = Paint.Style.STROKE
        val helperCircleHeight = circleMarginTop + scoreRectBounds.height() + arcWidth / 2
        canvas.drawCircle(
            (rectF.right - rectF.left) / 2 + rectF.left,
            (rectF.bottom - rectF.top) / 2 + rectF.top,
            (rectF.bottom - rectF.top) / 2,
            paint
        )

    }

    private fun drawLineByAngle(
        canvas: Canvas,
        rotateLineAngle: Float,
        smallLineStartY: Float,
        smallLineSize: Float
    ) {
        for (i in 0 until 17) {
            canvas.rotate(rotateLineAngle, arcInfo.centerX, arcInfo.centerY)
            canvas.drawLine(
                arcInfo.centerX, smallLineStartY,
                arcInfo.centerX, smallLineStartY + smallLineSize, paint
            )
        }
    }


    fun dp2px(dp: Int): Float {
        val displayMetrics = context.resources
            .displayMetrics
        return (dp * displayMetrics.density + 0.5).toFloat()
    }

}

/**
 * 圆弧信息
 */
data class ArcInfo(
    var centerX: Float = 0f,
    var centerY: Float = 0f
) {
    fun handleInfo(rectF: RectF) {
        centerX = (rectF.right - rectF.left) / 2 + rectF.left
        centerY = (rectF.bottom - rectF.top) / 2 + rectF.top
    }
}