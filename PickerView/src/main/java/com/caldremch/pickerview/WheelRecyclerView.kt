package com.caldremch.pickerview

import android.content.Context
import android.graphics.*
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.sin

/**
 *
 * @author Caldremch
 *
 * @date 2020-05-27 20:05
 *
 * @email caldremch@163.com
 *
 * @describe 使用 matrix 和 camera 实现 3D 效果
 *
 * 基于 https://github.com/youxiaochen/WheelView2-3d 用 Kotlin重写
 * 并添加了详细注释 以及时间选择器的扩展
 *
 **/

class WheelRecyclerView(
    context: Context,
    val gravity: Int, //对齐方式
    val myItemCount: Int, //显示的item数量, 多处的 item 数量
    val itemSize: Int, //每个item大小,  垂直布局时为item的高度, 水平布局时为item的宽度
    val orientation: Int, //布局方向
    val dividerColor: Int, //分割线颜色
    var dividerSize: Int //分割线宽度

) : RecyclerView(context) {


    /**
     * 阴影遮罩颜色渐变
     */
    private val CENTER_COLOR = Color.parseColor("#00ffffff")
    private val EDGE_COLOR = Color.parseColor("#ffffffff")

    //限制的旋转角度
    val RIGHTANGLE = 90f

    /**
     * 此参数影响左右旋转对齐时的效果,系数越大,越明显,自己体会......(0-1之间)
     */
    val DEF_SCALE = 0.75f

    /**
     * 此参数影响偏离中心item时效果(1-3)
     */
    var DEF_COFFICIENT = 3

    /**
     * 每个item平均下来后对应的旋转角度
     * 根据中间分割线上下item和中间总数量计算每个item对应的旋转角度
     */
    var itemDegree = 0f

    /**
     * 滑动轴的半径
     */
    var wheelRadio = 0f

    /**
     * 自身中心点
     */
    var centerX = 0f
    var centerY = 0f

    /**
     * 3D旋转
     */
    var camera: Camera
    var myMatrix: Matrix

    /**
     * 分割线画笔
     */
    var dividerPaint: Paint
    var myLayoutManager: LinearLayoutManager

    /**
     * 渐变画笔
     */
    var topOrLeftPaint: Paint
    var rightOrBottomPaint: Paint
    var topOrLeftGradient: LinearGradient? = null
    var rightOrBottomGradient: LinearGradient? = null

    init {
        //itemCount * 2 + 1 可见 item 的度数, 比如 itemCount 3 , 上边显示 3, 下边显示 3 个 +1 加上本身自己
        itemDegree = 180f / (myItemCount * 2 + 1)
        wheelRadio = WheelUtils.radianToRadius(itemSize, itemDegree).toFloat()
        camera = Camera()
        myMatrix = Matrix()
        camera.setLocation(0f, 0f, -8f * DEF_COFFICIENT);
        dividerPaint = Paint()
        dividerPaint.isAntiAlias = true
        dividerPaint.color = dividerColor
        topOrLeftPaint = Paint()
        rightOrBottomPaint = Paint()

        myLayoutManager = LinearLayoutManager(context)
        myLayoutManager.orientation = orientation
        layoutManager = myLayoutManager
    }

    fun logg(str: String) {
        Log.d("tag", str)
    }

    //调用顺序 onDraw --> dispatchDraw --> drawChild
    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)
        //中心点位置
        centerX = (l + r) * 0.5f
        centerY = (t + b) * 0.5f
    }

    //画分割线与遮罩层
    override fun dispatchDraw(c: Canvas) {
        super.dispatchDraw(c)

        if (orientation == LinearLayoutManager.VERTICAL) {
            //两条横线
            val dividerOff = (height - dividerSize) / 2.0f
            val firstY = top + dividerOff
            c.drawLine(left.toFloat(), firstY, right.toFloat(), firstY, dividerPaint)
            //也是dividerOff + dividerSize
            val secondY = bottom - dividerOff
            c.drawLine(left.toFloat(), secondY, right.toFloat(), secondY, dividerPaint)

            //渐变阴影
            //阴影部分: 中间item 以上部分和以下部分
            val rectTop = firstY - wheelRadio
            val rectBottom = secondY + wheelRadio
            if (topOrLeftGradient == null) {
                topOrLeftGradient = LinearGradient(
                    0f,
                    firstY,
                    0f,
                    rectTop,
                    CENTER_COLOR,
                    EDGE_COLOR,
                    Shader.TileMode.MIRROR
                )
                topOrLeftPaint.shader = topOrLeftGradient
            }

            if (rightOrBottomGradient == null) {
                rightOrBottomGradient = LinearGradient(
                    0f,
                    secondY,
                    0f,
                    rectBottom,
                    CENTER_COLOR,
                    EDGE_COLOR,
                    Shader.TileMode.MIRROR
                )
                rightOrBottomPaint.shader = rightOrBottomGradient
            }
            c.drawRect(left.toFloat(), rectTop, right.toFloat(), firstY, topOrLeftPaint)
            c.drawRect(left.toFloat(), secondY, right.toFloat(), rectBottom, rightOrBottomPaint)
        }
    }

    override fun drawChild(canvas: Canvas, child: View, drawingTime: Long): Boolean {

        canvas.save()
        if (orientation == LinearLayoutManager.VERTICAL) {
            verticalCanvasForDrawChild(canvas, child, myTranslateX(centerX))
        }
        val drawChild = super.drawChild(canvas, child, drawingTime)
        canvas.restore()
        return drawChild

    }

    fun verticalCanvasForDrawChild(c: Canvas, child: View, translateX: Float) {
        //获取子 View 中心点 Y 坐标
        val itemCenterY = (child.top + child.bottom) * 0.5f
        // View 中心点 Y 距离 recyclerView 中心点的距离
        val scrollOffY = itemCenterY - centerY
        //垂直布局时要以X轴为中心旋转, 获取到当前 View 距离中心点旋转了多少度, 限制最多不能高于 90 度
        //比如 itemcout 4 , 那么 180/(2*4+1) = 20, 每个 item 旋转 20 度
        //为什么不能超过 90? 为 180 分两半, 一般最多有 90 可以旋转, 超过的这个度数, 就看不见了
        val rotateDegreeX =
            rotateLimitRightAngle(scrollOffY * itemDegree / itemSize) //垂直布局时要以X轴为中心旋转

        //1度=π/180弧度
        //角度A1转换弧度A2：A2=A1*π/180
        //Math.toRadians(rotateDegreeX.toDouble()) --->计算出了角度对应的弧度

        //为什么要计算出rotateOffY? 因为在子 View 基于中心点x轴旋转后, 会有[一段距离]空出,效果体现为距离顶部的距离有一段空白, 所以
        //要将画布上移[一段距离],, 而这里就是求出这段距离

        //根据旋转角度求出弧度 sin(弧度) = 垂直于半径的直线/半径, 垂直于半径的直线--> 旋转后距离中心点的距离
        val rotateSinX = sin(Math.toRadians(rotateDegreeX.toDouble())).toFloat()
        //scrollOffY:原先距离中心店的距离-旋转后距离中心点的距离
        //scrollOffY:原先距离中心店的距离-旋转后距离中心点的距离
        val rotateOffY = scrollOffY - wheelRadio * rotateSinX //因旋转导致界面视角的偏移
        c.translate(0f, -rotateOffY)
        camera.save()


        //上面是计算相关距离, 然后平移画布, 接下来开始进行 camera视角的旋转
        //旋转后的子 View, 跟原来的坐标相比, z 值发生了改变, 通过三角函数算出偏移后的 cos值, 然后用半径去这个长度就是 z坐标偏移的长度(负方向进行),
        //算出来后, 相机位置也要跟着同步,不然图像会变大, 因为距离相机源点近了(要清楚 camera 3D 坐标系, 不然这点无法理解))

        //旋转时离视角的z轴方向也会变化,先移动Z轴再旋转
        //旋转后的子 View, 跟原来的坐标相比, z 值发生了改变, 通过三角函数算出偏移后的 cos值, 然后用半径去这个长度就是 z坐标偏移的长度(负方向进行),
        //算出来后, 相机位置也要跟着同步,不然图像会变大, 因为距离相机源点近了(要清楚 camera 3D 坐标系, 不然这点无法理解))
        val z = (wheelRadio * (1 - abs(cos(Math.toRadians(rotateDegreeX.toDouble()))))).toFloat()
        camera.translate(0f, 0f, z)

        //根据 x 旋转
        camera.rotateX(-rotateDegreeX)
        camera.getMatrix(myMatrix)
        //回复 camera 坐标系
        camera.restore()

        //中心点一直是 0,0
        //为了成像展示正确, 将矩阵视图对准相机,矩阵移动为2D 坐标系, 通过中心点移动刚好对准相机
        myMatrix.preTranslate(-translateX, -itemCenterY)
        //恢复到中心点 0,0
        myMatrix.postTranslate(translateX, itemCenterY)
        c.concat(myMatrix)
    }

    /**
     * 根据对齐方式,计算出垂直布局时X轴移动的位置, 视察效果
     */
    private fun myTranslateX(parentCenterX: Float): Float {
        when (gravity) {
            GRAVITY_LEFT -> return parentCenterX * (1 + DEF_SCALE)
            GRAVITY_RIGHT -> return parentCenterX * (1 - DEF_SCALE)
        }
        return parentCenterX
    }

    /**
     * 旋转的角度绝对值不能大于90度
     */
    fun rotateLimitRightAngle(degree: Float): Float {
        if (degree >= RIGHTANGLE) return RIGHTANGLE
        return if (degree <= -RIGHTANGLE) -RIGHTANGLE else degree
    }

    /**
     * 获取中心点位置
     * @return
     */
    fun findCenterItemPosition(): Int {
        if (adapter == null || centerY == 0f || centerX == 0f) return -1
        val centerView = findChildViewUnder(centerX, centerY)
        if (centerView != null) {
            val adapterPosition: Int = getChildAdapterPosition(centerView) - myItemCount
            if (adapterPosition >= 0) {
                return adapterPosition
            }
        }
        return -1
    }

    /**
     * 垂直布局时的靠左,居中,靠右立体效果, 影响矩阵的移动
     */
    companion object {
        val GRAVITY_LEFT = 1
        val GRAVITY_CENTER = 2
        val GRAVITY_RIGHT = 3
    }


}