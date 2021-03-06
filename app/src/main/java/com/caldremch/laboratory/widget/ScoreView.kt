package com.caldremch.laboratory.widget

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.text.TextUtils
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.animation.DecelerateInterpolator
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent

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
) : View(context, attrs, defStyleAttr), LifecycleObserver {

    //View 容器宽度
    private var viewSize: Float = 0f
    private var viewHeight: Float = 0f

    private val paint = Paint()
    private val scorePaint = Paint() //分数画笔
    private var rectF = RectF()

    //圆弧距离顶部边距
    private var circleMarginTop = dp2px(20)

    private val titleTextBound = Rect()
    private val subTitleTextBound = Rect()
    private val scoreRectBounds = Rect()

    //圆弧参数
    private var arcWidth: Float = 0f //圆弧的宽度
    private var arcRadius: Float = 0f //圆弧半径

    //圆弧信息
    private val arcInfo = ArcInfo()
    private val helperPaint = Paint() //辅助画笔(开发阶段)

    //自定义 View 属性
    private val scoreViewAttr =
        ScoreViewAttrDelegate(context)

    private val cameraMatrix = Matrix()
    private val camera = Camera()

    private var cameraRotateX = 0f
    private var cameraRotateY = 0f

    /* camera旋转的最大角度 */
    private val mMaxCameraRotate = 10f

    //描述文本的 margin
    private val descTextMargin = dp2px(10)

    //中间字标题最大宽度
    private var maxFlagScoreDescTextSize: Float = 0f

    //中间标题最大宽度
    private var maxCenterTitleSize: Float = 0f

    //动画
    private var objectAnimator: ValueAnimator? = null

    init {

        //监听声明周期
        if (context is LifecycleOwner) {
            (context as LifecycleOwner).lifecycle.addObserver(this)
        }

        //处理属性
        scoreViewAttr.initAttr(attrs)
        initHelper()
        paint.isAntiAlias = true
    }

    private fun initHelper() {
//        if (BuildConfig.DEBUG) {
//            helperPaint.color = Color.RED
//            helperPaint.textSize = 12f
//            helperPaint.style = Paint.Style.STROKE
//            helperPaint.isAntiAlias = true
//        }
    }

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
        scorePaint.color = scoreViewAttr.svScoreTextColor

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
                    2 * dp2px(20) +
                    2 * scoreViewAttr.svFlagLineHeight +
                    2 * dp2px(10) + //刻线距离圆弧的margin
                    2 * scoreViewAttr.svArcLineWidth +
                    2 * maxFlagScoreDescTextSize +
                    2 * descTextMargin + scoreRectBounds.width() / 4
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
//        Log.d("tag", "screenWidth=$screenWidth,viewSize=$viewSize")
        if (viewSize > screenWidth) {
            viewSize = screenWidth.toFloat()
        }

        arcWidth = viewSize - 2 * maxFlagScoreDescTextSize - 2 * descTextMargin
        arcRadius = arcWidth / 2

        viewHeight = arcRadius + (scoreRectBounds.bottom - scoreRectBounds.top) + circleMarginTop+ arcRadius * 3.8f/4

        Log.d("tag", "viewSize=$viewSize,viewHeight=$viewHeight")

        setMeasuredDimension(viewSize.toInt(), viewHeight.toInt())

    }

    override fun onDraw(canvas: Canvas) {
        this.canvas = canvas
//        cameraRotate()

        //圆弧矩形
        rectF.left = maxFlagScoreDescTextSize + descTextMargin
        rectF.top =
            circleMarginTop + scoreViewAttr.svArcLineWidth / 2 + (scoreRectBounds.bottom - scoreRectBounds.top)
        rectF.right = rectF.left + arcWidth
        rectF.bottom = rectF.top + arcWidth


        arcInfo.handleInfo(rectF)

//        Log.d("tag",  rectF.toShortString())

//        if (BuildConfig.DEBUG) {
//            helperPaint.style = Paint.Style.STROKE
//            helperPaint.color = Color.RED
//            canvas.drawRect(rectF, helperPaint)
//        }

        //设置圆弧画笔
        paint.color = Color.BLUE
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = scoreViewAttr.svArcLineWidth

        //圆弧下
        paint.color = scoreViewAttr.svArcBackColor
        canvas.drawArc(rectF, 145f, 250f, false, paint)

        //圆弧上
        paint.color = scoreViewAttr.svArcFrontColor
        canvas.drawArc(rectF, 145f, scoreViewAttr.svCurrentScoreToAngle, false, paint)

        //圆弧中心画分数
        paint.color = scoreViewAttr.svCenterTextColor
        paint.textSize = scoreViewAttr.svCenterTextSize
        paint.style = Paint.Style.FILL
        paint.getTextBounds(
            scoreViewAttr.svCenterText,
            0,
            scoreViewAttr.svCenterText.length,
            titleTextBound
        )
        val titleWidth = paint.measureText(scoreViewAttr.svCenterText)
        val titleY = arcInfo.centerY + titleTextBound.height() / 2
        canvas.drawText(scoreViewAttr.svCenterText, arcInfo.centerX - titleWidth / 2, titleY, paint)

        //今日得分
        paint.color = scoreViewAttr.svCenterSubTextColor
        paint.textSize = scoreViewAttr.svCenterSubTextSize
        paint.getTextBounds(
            scoreViewAttr.svCenterSubText,
            0,
            scoreViewAttr.svCenterSubText.length,
            subTitleTextBound
        )
        val subTitleMarginTop = dp2px(10)
        canvas.drawText(
            scoreViewAttr.svCenterSubText,
            arcInfo.centerX - subTitleTextBound.width() / 2,
            titleY + subTitleTextBound.height() + subTitleMarginTop,
            paint
        )

        //圆弧内的小短线
        drawShortLine(canvas)

        //分数和描述
        drawScoreAndDesc(canvas)

//        if (BuildConfig.DEBUG) {
//            //画出辅助线,居中辅助线
//            canvas.drawLine(arcInfo.centerX, 0f, arcInfo.centerX, height.toFloat(), helperPaint)
//            canvas.drawLine(0f, arcInfo.centerY, width.toFloat(), arcInfo.centerY, helperPaint)
//        }


    }

    private lateinit var canvas: Canvas
    private fun drawShortLine(canvas: Canvas) {
        paint.color = scoreViewAttr.svFlagLineBackColor
        paint.strokeWidth = scoreViewAttr.svFlagLineWidth
        //刻线边距
        val smallLineMarigin = dp2px(5)
        //刻线长度
        val smallLineSize = scoreViewAttr.svArcLineWidth*0.7f
        //刻线 startY
        val smallLineStartY = rectF.top + scoreViewAttr.svArcLineWidth / 2 + smallLineMarigin
        //画刻线, 根据设计图, 一共有 35根, 顶部 1 根, 左右两边旋转 17 跟 , 每根旋转 250/35 个度数
        //旋转度数
        val rotateLineAngle = scoreViewAttr.svFullAngle / 35

        paint.color = scoreViewAttr.svFlagLineBackColor
        canvas.drawLine(
            arcInfo.centerX, smallLineStartY,
            arcInfo.centerX, smallLineStartY + smallLineSize, paint
        )
        canvas.save()
        //-rotateLineAngle/2 误差度数
        canvas.rotate(
            -scoreViewAttr.svFullAngle / 2 - rotateLineAngle / 2,
            arcInfo.centerX,
            arcInfo.centerY
        )
        //正方向旋转画刻线
        drawLineByAngle(canvas, rotateLineAngle, smallLineStartY, smallLineSize)
        canvas.restore()
    }

    private fun drawScoreAndDesc(canvas: Canvas) {
//        Log.d("tag", "分数标志数量:${scoreViewAttr.scoreInfoList.size}")

        paint.color = scoreViewAttr.svScoreFlagLineColor
        paint.strokeWidth = scoreViewAttr.svFlagLineWidth
        paint.style = Paint.Style.FILL
        helperPaint.style = Paint.Style.FILL

        for (scoreInfo in scoreViewAttr.scoreInfoList) {

            val measureTextWidth = scorePaint.measureText(scoreInfo.scoreDesc)

//            Log.d("tag", "start drawing....")
            canvas.save()
            /************画文字描述*************/

            /************画文刻度线*************/
            // rectF边线切在圆弧线宽的中间

            val startY = rectF.top - scoreViewAttr.svArcLineWidth / 2
//            Log.d("tag", "startY:$startY")
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
            canvas.save()
            canvas.rotate(scoreInfo.scoreAngle, arcInfo.centerX, arcInfo.centerY)


//            if (BuildConfig.DEBUG) {
//                helperPaint.color = Color.RED
//                val rotateY = circleMarginTop - (scoreRectBounds.bottom - scoreRectBounds.top) / 2
//                //辅助原点
//                canvas.drawCircle(arcInfo.centerX, rotateY, dp2px(2), helperPaint)
//            }

            //根据文字中心旋转文字
            val rotateY = circleMarginTop - (scoreRectBounds.bottom - scoreRectBounds.top) / 2
            canvas.rotate(-scoreInfo.scoreAngle, arcInfo.centerX, rotateY)

            //移动长度, 文字宽度的 1/4
            var translateX = (measureTextWidth / 4).toFloat()
            if (scoreInfo.scoreAngle < 0) {
                translateX = -translateX
            }
            canvas.translate(translateX, 0f)
            canvas.drawText(
                scoreInfo.scoreDesc,
                arcInfo.centerX - measureTextWidth / 2,
                circleMarginTop,
                scorePaint
            )
            canvas.restore()
        }

    }

    //画短线
    private fun drawLineByAngle(
        canvas: Canvas,
        rotateLineAngle: Float,
        smallLineStartY: Float,
        smallLineSize: Float
    ) {

        var collectAngles = 0f
        for (i in 0 until 35) {

            if (scoreViewAttr.svCurrentScoreToAngle > 0) {
                if (collectAngles < scoreViewAttr.svCurrentScoreToAngle) {
                    paint.color = scoreViewAttr.svFlagLineFrontColor
                } else {
                    paint.color = scoreViewAttr.svFlagLineBackColor
                }
            }

            collectAngles += rotateLineAngle
            canvas.rotate(rotateLineAngle, arcInfo.centerX, arcInfo.centerY)
            canvas.drawLine(
                arcInfo.centerX, smallLineStartY,
                arcInfo.centerX, smallLineStartY + smallLineSize, paint
            )
        }
    }


    private fun dp2px(dp: Int): Float {
        val displayMetrics = context.resources
            .displayMetrics
        return (dp * displayMetrics.density + 0.5).toFloat()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        objectAnimator?.let {
            if (it.isRunning) {
                //取消动画
                it.cancel()
            }
        }
    }

    //开始动画
    fun startAnim(currentScore: Float) {

        var checkScore = 0f

        if (currentScore>scoreViewAttr.svFullScore){
            checkScore = scoreViewAttr.svFullScore
        }else{
            checkScore = currentScore
        }

        if (objectAnimator == null) {
            objectAnimator = ObjectAnimator.ofFloat(checkScore)
        }

        if (objectAnimator!!.isRunning) {
            return
        }

        objectAnimator!!.addUpdateListener {
            scoreViewAttr.svCurrentScoreToAngle =
                scoreViewAttr.scoreToAngle(it.animatedValue as Float)
            postInvalidate()
        }
        objectAnimator!!.duration = 3000
        objectAnimator!!.interpolator = DecelerateInterpolator()
        objectAnimator!!.start()

    }

    private fun cameraRotate() {
        cameraMatrix.reset()
        camera.save()
        camera.rotateX(cameraRotateX)
        camera.rotateY(cameraRotateY)
        camera.getMatrix(cameraMatrix)
        camera.restore()
        //修改旋转中心点
        cameraMatrix.preTranslate(-arcInfo.centerX, -arcInfo.centerY)
        cameraMatrix.postTranslate(arcInfo.centerX, arcInfo.centerY)
        canvas.concat(cameraMatrix)
    }

//    override fun onTouchEvent(event: MotionEvent): Boolean {
//
//        when (event.action) {
//            MotionEvent.ACTION_DOWN -> {
//                getCameraRotate(event)
//                invalidate()
//            }
//            MotionEvent.ACTION_MOVE -> {
//                getCameraRotate(event)
//                invalidate()
//            }
//        }
//        return true
//    }

    private fun getCameraRotate(event: MotionEvent) {

        val rotateX = -(event.y - height / 2)
        val rotateY = event.x - width / 2
        var percentX: Float = rotateX / arcWidth
        var percentY: Float = rotateY / arcWidth
        if (percentX > 1) {
            percentX = 1f
        } else if (percentX < -1) {
            percentX = -1f
        }
        if (percentY > 1) {
            percentY = 1f
        } else if (percentY < -1) {
            percentY = -1f
        }
        cameraRotateX = percentX * mMaxCameraRotate
        cameraRotateY = percentY * mMaxCameraRotate
//        Log.d("tag", "cameraRotateX=$cameraRotateX")
        cameraRotate()
    }

    /**
     * 设置标志刻度
     */
    fun setScoreAndDesc(fullScore: Float, svScoreFlag: String?, svScoreFlagDesc: String?) {
        scoreViewAttr.handleScoreAndDesc(fullScore, svScoreFlag, svScoreFlagDesc)
        invalidate()
    }

    fun setTitle(title: String?, subTitle: String?) {
        if (!TextUtils.isEmpty(subTitle)) {
            scoreViewAttr.svCenterSubText = subTitle!!
        }

        if (!TextUtils.isEmpty(title)) {
            scoreViewAttr.svCenterText = title!!
        }
        invalidate()

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