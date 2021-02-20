package com.caldremch.laboratory.entry.entry

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.net.Uri
import android.util.Log
import androidx.core.content.FileProvider
import com.caldremch.android.annotation.entry.Entry
import com.caldremch.android.annotation.entry.IEntry
import java.io.File


/**
 *
 * @author Caldremch
 *
 * @date 2020-07-19 14:42
 *
 * @email caldremch@163.com
 *
 * @describe
 *
 **/
@Entry
class AudioEntry : IEntry {

    override fun getTitle(): String {
        return "播放音频"
    }

    override fun onClick(context: Context) {
        val recordIntent = Intent()
        val path = context.externalCacheDir?.absolutePath + "/bb.wav"
        val file = File(path)
        val contentUri: Uri = FileProvider.getUriForFile(
            context,
            "${context.packageName}.provider",file
        )
        recordIntent.action = Intent.ACTION_VIEW
        recordIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        recordIntent.setDataAndType(contentUri, "audio/*")
        context.startActivity(recordIntent)
//        val uri = Uri.parse("")
//        val mPlayer = MediaPlayer.create(context, contentUri)
//        mPlayer.start()



        val pManager = context.packageManager
        val mApps = pManager.queryIntentActivities(
            recordIntent,
            PackageManager.COMPONENT_ENABLED_STATE_DEFAULT
        )

        Log.d("AudioEntry", "onClick: ${mApps.size}")

    }
}