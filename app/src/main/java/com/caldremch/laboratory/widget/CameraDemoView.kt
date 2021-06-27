package com.caldremch.laboratory.widget

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

/**
 *
 * @author Caldremch
 *
 * @date 2020-05-27 13:07
 *
 * @email caldremch@163.com
 *
 * @describe
 *
 **/
class CameraDemoView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    val camera = Camera()
    val myMatrix = Matrix()
    private lateinit var bitmap: Bitmap
    val paint = Paint()

    init {
        //R.mipmap.ic_launche是一个 drawable , 不是 bitmap, 所以BitmapFactory.decodeResourc返回 null
//        val drawable = ContextCompat.getDrawable(context, R.mipmap.ic_launcher)!!
//        drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
//        bitmap = Bitmap.createBitmap(
//            drawable.intrinsicWidth,
//            drawable.intrinsicHeight,
//            Bitmap.Config.ARGB_8888
//        )
//
//        val canvas = Canvas(bitmap)
//        drawable.setBounds(0, 0, canvas.width, canvas.height)
//        drawable.draw(canvas)

    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        canvas.drawBitmap(bitmap, myMatrix, paint)
        camera.save()
        camera.translate(0f, 0F, 0f)
//        可以向 x,y,z 旋转
//        camera.rotateY(-80f)
//        camera.rotateZ(30f)

        //设置相机的位置
        camera.setLocation(1f, 0f, -8f)

        camera.getMatrix(myMatrix)
        paint.color = Color.BLUE
        paint.style = Paint.Style.STROKE
        paint.textSize = 20f

        canvas.translate(100f, 1000f)
        canvas.drawRect(0f, 0f, 400f, 400f, paint)
        canvas.drawBitmap(bitmap, myMatrix, paint)
        camera.restore()
    }


}