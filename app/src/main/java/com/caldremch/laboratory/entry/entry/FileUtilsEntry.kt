package com.caldremch.laboratory.entry.entry

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import com.caldremch.android.annotation.entry.Entry
import com.caldremch.android.annotation.entry.IEntry
import com.caldremch.utils.FileUtils

/**
 *
 * @author Caldremch
 *
 * @date 2020-10-22
 *
 * @email caldremch@163.com
 *
 * @describe
 *
 **/
@Entry
class FileUtilsEntry : IEntry {
    override fun getTitle(): String {
        return "FileUtils"
    }

    override fun onClick(context: Context?) {
        val bitmap = Bitmap.createBitmap(400, 400, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        canvas.drawColor(Color.RED)
        FileUtils.savePublicPicture(bitmap, "androidQ")
    }


}