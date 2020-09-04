package com.caldremch.laboratory.entry.entry

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import com.caldremch.android.annotation.entry.Entry
import com.caldremch.android.annotation.entry.IEntry
import com.caldremch.image.ImageTools
import com.caldremch.laboratory.R

/**
 *
 * @author Caldremch
 *
 * @date 2020-07-19 14:42
 *
 * @email caldremch@163.com
 *
 * @describe 图片计算分析 https://juejin.im/post/5bc406b9f265da0aa664ea1e
 *
 **/
@Entry
class ImageEntry : IEntry {
    override fun getTitle(): String {
        return "calculate image size"
    }

    override fun onClick(any: Context) {

        val context = any as Context

        //Bitmap.Config.ARGB_8888 每个像素 4 个字节

        //通过计算, 我们平常资源文件中使用的 bitmap格式为RGB_565

        val pair =
            ImageTools.getFileSize(context, R.mipmap.ic_test_size, Bitmap.Config.ARGB_8888)
        val pair2 = ImageTools.getFileSize(context, R.mipmap.ic_test_size, Bitmap.Config.RGB_565)
        val pair3 =
            ImageTools.getFileSize(context, R.mipmap.ic_test_size, Bitmap.Config.ARGB_4444)
        val pair4 = ImageTools.getFileSize(context, R.mipmap.ic_test_size, Bitmap.Config.ALPHA_8)
        print(pair, 4)
        print(pair2, 2)
        print(pair3, 2)
        print(pair4, 1)

    }

    private fun print(pair: Pair<Int, Bitmap>, byteCount: Int) {

        Log.d("tag", "size =  ${pair.first * byteCount / 1024} kb")
        Log.d("tag", "bitmap alloc memory =  ${pair.second.allocationByteCount / 1024f} kb")
        Log.d("tag", "bitmap byteCount =  ${pair.second.byteCount * byteCount} byte(字节)")
        Log.d("tag", "----------------------------------------------------------------------------")

    }
}