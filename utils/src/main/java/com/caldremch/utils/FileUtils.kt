package com.caldremch.utils

import android.app.Application
import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log

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
object FileUtils {

    private val TAG = "FileUtils"

    private lateinit var app_public_dir: String
    private val isAndroidRAbove = Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q
    private lateinit var contentResolver: ContentResolver

    private lateinit var applicationContext: Context

    fun init(application: Application, appPublicDir: String? = null) {
        applicationContext = application
        contentResolver = application.contentResolver
        if (appPublicDir.isNullOrEmpty()) {
            app_public_dir = application.packageName.replace(".", "_") + "_public"
        }
    }

    fun savePublicPicture(bitmap: Bitmap, fileName: String) {
        if (isAndroidRAbove) {
            val values = ContentValues()
            values.put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
            values.put(MediaStore.MediaColumns.TITLE, fileName)
            values.put(
                MediaStore.MediaColumns.RELATIVE_PATH,
                "${Environment.DIRECTORY_DOCUMENTS}/$app_public_dir"
            )
            val uriEx = MediaStore.Files.getContentUri("external")
            Log.d(TAG, "savePublicPicture: ${uriEx.toString()}")
            val uri = contentResolver.insert(uriEx, values)
            uri?.let {
                val out = contentResolver.openOutputStream(it)
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
                Log.d(TAG, "savePublicPicture: 图片保存成功")
            }
        }
    }

//    private fun isAndroidRAbove():Boolean{
//        return
//    }

}