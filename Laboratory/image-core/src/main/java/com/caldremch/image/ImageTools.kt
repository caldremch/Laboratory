package com.caldremch.image

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.annotation.DrawableRes

/**
 *
 * @author Caldremch
 *
 * @date 2020-07-20 22:49
 *
 * @email caldremch@163.com
 *
 * @describe
 *
 **/
object ImageTools {

    /**
     * cal  size of bitmap
     */
    fun getFileSize(context: Context, @DrawableRes resId: Int, config: Bitmap.Config): Pair<Int, Bitmap> {
        val option = BitmapFactory.Options()
        option.inPreferredConfig = config
        option.inJustDecodeBounds = false
        val bitmap = BitmapFactory.decodeResource(context.resources, resId, option)
        val width = option.outWidth
        val height = option.outHeight
        Log.d("TAG", "getFileSize: width=$width, height=$height")
        val pair = Pair<Int, Bitmap>(width * height, bitmap)
        return pair
    }


}