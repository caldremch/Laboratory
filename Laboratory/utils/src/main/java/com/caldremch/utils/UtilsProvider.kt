package com.caldremch.utils

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.content.pm.ProviderInfo
import android.database.Cursor
import android.net.Uri
import androidx.core.content.FileProvider
import java.security.Provider

/**
 *
 * @author Caldremch
 *
 * @date 2020-07-19 16:45
 *
 * @email caldremch@163.com
 *
 * @describe
 *
 *
 * java.lang.IllegalArgumentException: Missing android.support.FILE_PROVIDER_PATHS meta-data
 *
 *
 **/
class UtilsProvider : FileProvider(){
    override fun attachInfo(context: Context, info: ProviderInfo) {
        super.attachInfo(context, info)
        Utils.init(context)
    }
}