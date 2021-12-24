package com.caldremch.image

import android.widget.ImageView

/**
 * @author Caldremch
 * @date  2020/7/5
 * @email caldremch@163.com
 * @describe
 *
 **/
interface IImage {
    fun load(url:String?, iv:ImageView?)
}