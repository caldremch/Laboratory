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

    //View大小 正方形 todo 最小为主
    var viewSize: Float = 0f

    val paint = Paint()
    val scorePaint = Paint() //分数画笔
    var rectF = RectF()
    var lineHeight: Float = 0f
    var circleMarginTop = dp2px(20)
    val titleTextSize = dp2px(40)
    val subTitleTextSize = dp2px(12)

    val titleTextBound = Rect()
    val subTitleTextBound = Rect()
    val text = "8.8"
    val subText = "今日得分"
    val score60 = "达标(60)"
    val score80 = "优秀(80)"
    val score0 = "0"
    val score104 = "104"
    val scoreRectBounds = Rect()

    var textFlagWidth: Float = 0f //分数刻度描述文字的宽度
    var textFlagHeight: Float = 0f //分数刻度描述文字的高度

    //圆弧参数
    var arcWidth: Float = 0f //圆弧的宽度
    var arcRadius: Float = 0f //圆弧半径

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
        lineHeight = dp2px(30)

        paint.color = Color.BLACK
        paint.textSize = titleTextSize
        paint.style = Paint.Style.FILL

        //分数刻度
        scorePaint.color = scoreViewAttr.svScoreTextColor
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


    val descTextMargin = dp2px(10)
    var maxFlagScoreDescTextSize: Float = 0f
    var maxCenterTitleSize: Float = 0f

    //todo padding 未算
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        val specMode = MeasureSpec.getMode(widthMeasureSpec); //获取测量模式
        val specWidthSize = MeasureSpec.getSize(widthMeasureSpec); //获取测量的宽度
        val specHeightSize = MeasureSpec.getSize(heightMeasureSpec); //获取测量的高度


        /*************计算最小尺寸**************/
        val maxText = "10.8"
        paint.textSize = scoreViewAttr.svCenterTextSize
        paint.style = Paint.Style.FILL
        paint.isAntiAlias = true
        maxCenterTitleSize = paint.measureText(maxText)

        val maxFlagScoreDescText = "104(优秀)"
        scorePaint.textSize = scoreViewAttr.svScoreTextSize
        scorePaint.style = Paint.Style.FILL
        scorePaint.isAntiAlias = true
        maxFlagScoreDescTextSize = scorePaint.measureText(maxFlagScoreDescText)
        scorePaint.getTextBounds(
            maxFlagScoreDescText,
            0,
            maxFlagScoreDescText.length,
            scoreRectBounds
        )

        //中间标题宽度+距离刻线两边边距+2*刻线宽度+2*刻线距离圆弧的margin+2*圆弧线宽度+侧边刻度描述文字的宽度+ 描述文字的边距+ 描述文字宽度/4 的宽度(调整位置,移动文字)
        val minViewSize =
            maxCenterTitleSize +
                    2 * dp2px(40) +
                    2 * scoreViewAttr.svFlagLineHeight +
                    2 * dp2px(10) + //刻线距离圆弧的margin
                    2 * scoreViewAttr.svArcLineWidth +
                    2 * maxFlagScoreDescTextSize +
                    2 * descTextMargin+ scoreRectBounds.width()/4
        /*************计算最小尺寸**************/


        //宽度能小于最小宽度
        when (specMode) {

            MeasureSpec.EXACTLY -> {

                if (specWidthSize < specHeightSize) {
                    viewSize = specWidthSize.toFloat()
                } else {
                    viewSize = specHeightSize.toFloat()
                }

                if (viewSize < minViewSize) {
                    viewSize = minViewSize
                }
            }

            MeasureSpec.AT_MOST, MeasureSpec.UNSPECIFIED -> {
                viewSize = minViewSize

            }
        }

        //如果大于屏幕宽度处理
        val screenWidth = resources.displayMetrics.widthPixels
        Log.d("tag", "screenWidth=$screenWidth,viewSize=$viewSize")
        if (viewSize > screenWidth) {
            viewSize = screenWidth.toFloat()
        }

        arcWidth = viewSize - 2 * maxFlagScoreDescTextSize - 2 * descTextMargin
        arcRadius = arcWidth / 2

        val viewHeight =
            viewSize.toInt() - 2 * maxFlagScoreDescTextSize + 2 * scoreRectBounds.height() + 2 * circleMarginTop

        setMeasuredDimension(viewSize.toInt(), viewHeight.toInt())

    }

    override fun onDraw(canvas: Canvas) {
        paint.color = Color.BLUE
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = scoreViewAttr.svArcLineWidth
        //dp2px(3) 文字和圆弧的间隔,
        rectF.left = maxFlagScoreDescTextSize + descTextMargin
        rectF.top = circleMarginTop + scoreViewAttr.svArcLineWidth / 2 + textFlagHeight
        rectF.right = rectF.left + arcWidth
        rectF.bottom = rectF.top + arcWidth

        arcInfo.handleInfo(rectF)

        if (BuildConfig.DEBUG) {
            helperPaint.color = Color.RED
            canvas.drawRect(rectF, helperPaint)
        }



        Log.d("tag", "right-left:" + (rectF.right - rectF.left))
        Log.d("tag", "bottom-top:" + (rectF.bottom - rectF.top))

        //圆弧下
        paint.color = scoreViewAttr.svArcBackColor
        canvas.drawArc(rectF, 145f, 360f, false, paint)

        //圆弧上
        paint.color = scoreViewAttr.svArcFrontColor
        canvas.drawArc(rectF, 145f, 100.5f, false, paint)



        paint.strokeWidth = scoreViewAttr.svFlagLineWidth
        //刻线边距
        val smallLineMarigin = dp2px(10)
        //刻线长度
        val smallLineSize = scoreViewAttr.svArcLineWidth
        //刻线 startY
        val smallLineStartY = rectF.top + scoreViewAttr.svArcLineWidth / 2 + smallLineMarigin
        //画刻线, 根据设计图, 一共有 35根, 顶部 1 根, 左右两边旋转 17 跟 , 每根旋转 250/35 个度数
        //旋转度数
        val rotateLineAngle = 250f / 35

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

        drawScoreAndDesc(canvas)

        if (true) {

            val rotateAngle = 65f
            //圆弧顶部文字
//            canvas.drawText(
//                score80,
//                arcInfo.centerX - scoreRectBounds.width() / 2,
//                circleMarginTop,
//                scorePaint
//            )
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
        paint.color = scoreViewAttr.svCenterTextColor
        paint.textSize = titleTextSize
        paint.style = Paint.Style.FILL
        paint.getTextBounds(text, 0, text.length, titleTextBound)
        val titleY = arcInfo.centerY + titleTextBound.height() / 2
        canvas.drawText(text, arcInfo.centerX - titleTextBound.width() / 2, titleY, paint)
        //今日得分
        paint.color = scoreViewAttr.svCenterSubTextColor
        paint.textSize = subTitleTextSize
        paint.getTextBounds(subText, 0, subText.length, subTitleTextBound)
        val subTitleMarginTop = dp2px(10)
        canvas.drawText(
            subText, arcInfo.centerX - subTitleTextBound.width() / 2,
            titleY + subTitleTextBound.height() + subTitleMarginTop,
            paint
        )


        //圆 helper
        if (false) {
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

    }

    private fun drawScoreAndDesc(canvas: Canvas) {
        Log.d("tag", "刻线数量:${scoreViewAttr.scoreInfoList.size}")
        paint.color = Color.GREEN
        paint.strokeWidth = scoreViewAttr.svFlagLineWidth
        paint.style = Paint.Style.FILL
        helperPaint.style = Paint.Style.FILL
        for (scoreInfo in scoreViewAttr.scoreInfoList) {

//
//            if (true){
//                canvas.save()
//                helperPaint.color = Color.BLUE
//                helperPaint.style = Paint.Style.FILL
//                canvas.rotate(scoreInfo.scoreAngle, arcInfo.centerX, arcInfo.centerY)
//                canvas.drawCircle(arcInfo.centerX, scoreRectBounds.height().toFloat(), dp2px(2), helperPaint)
//                canvas.restore()
//            }

            scorePaint.getTextBounds(
                scoreInfo.scoreDesc,
                0,
                scoreInfo.scoreDesc.length,
                scoreRectBounds
            )

            Log.d("tag", "start drawing....")
            canvas.save()
            /************画文字描述*************/

            /************画文刻度线*************/
            // rectF边线切在圆弧线宽的中间

            val startY = rectF.top - scoreViewAttr.svArcLineWidth / 2
            Log.d("tag", "startY:$startY")
            canvas.rotate(scoreInfo.scoreAngle, arcInfo.centerX, arcInfo.centerY)
            canvas.drawLine(
                arcInfo.centerX,
                rectF.top - scoreViewAttr.svArcLineWidth / 2,
                arcInfo.centerX,
                rectF.top - scoreViewAttr.svArcLineWidth / 2 + scoreViewAttr.svArcLineWidth,
                paint
            )

            canvas.restore()
            //注意文字旋转画布的中心
            //为什么旋转画布以后, 文字中心店往外靠了? todo , 因为两次旋转了, 但是画布没有回归
            canvas.save()
//            canvas.rotate(-scoreInfo.scoreAngle, arcInfo.centerX, 0f)
//            canvas.rotate(scoreInfo.scoreAngle, arcInfo.centerX, scoreRectBounds.height().toFloat())
            canvas.rotate(scoreInfo.scoreAngle, arcInfo.centerX, arcInfo.centerY)
            helperPaint.color = Color.RED

            val rotateY = circleMarginTop - scoreRectBounds.height() / 2
            canvas.drawCircle(arcInfo.centerX, rotateY, dp2px(2), helperPaint)
            canvas.rotate(-scoreInfo.scoreAngle, arcInfo.centerX, rotateY)
            //移动长度, 文字宽度的 1/4
            var translateX = (scoreRectBounds.width() / 4).toFloat()
            if (scoreInfo.scoreAngle < 0) {
                translateX = -translateX
            }
            canvas.translate(translateX, 0f)
            canvas.drawText(
                scoreInfo.scoreDesc,
                arcInfo.centerX - scoreRectBounds.width() / 2,
                circleMarginTop,
                scorePaint
            )
            canvas.restore()
        }

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