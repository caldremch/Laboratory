package com.caldremch.image

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import java.lang.RuntimeException

/**
 * @author Caldremch
 * @date  2020/7/5
 * @email caldremch@163.com
 * @describe
 *
 **/
class ImageTool : IImage {

    companion object {
        val instance: ImageTool by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            ImageTool()
        }
    }

    override fun load(url: String?, iv: ImageView?) {
        url ?: return
        iv ?: return
        Glide.with(iv).load(url).into(iv)
    }


}