package com.caldremch.data

import android.annotation.SuppressLint
import android.content.Context
import com.tencent.mmkv.MMKV


/**
 *
 * @author Caldremch
 *
 * @date 2020-07-03 13:53
 *
 * @email caldremch@163.com
 *
 * @describe
 *
 **/
class DataTool private constructor(var context: Context) : IData {

    companion object {

        @SuppressLint("StaticFieldLeak")
        private var sContext: Context? = null

        val instance: DataTool by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            sContext ?: throw RuntimeException("please init")
            DataTool(sContext!!)
        }

        fun init(context: Context) {
            sContext = context.applicationContext
        }
    }

    init {
        val rootDir: String = MMKV.initialize(context)
    }

    private fun getInstance(): MMKV {
        return MMKV.defaultMMKV()
    }

    override fun get(key: String, defaultValue: String): String? {
        return getInstance().getString(key, defaultValue)
    }

    override fun get(key: String, defaultValue: Int): Int {
        return getInstance().getInt(key, defaultValue)
    }

    override fun get(key: String, defaultValue: Long): Long {
        return getInstance().getLong(key, defaultValue)
    }

    override fun get(key: String, defaultValue: Float): Float {
        return getInstance().getFloat(key, defaultValue)
    }

    override fun get(key: String, defaultValue: Boolean): Boolean {
        return getInstance().getBoolean(key, defaultValue)
    }

    override fun put(key: String, value: String?): IData {
        getInstance().putString(key, value)
        return this
    }

    override fun put(key: String, values: Set<String>): IData {
        getInstance().putStringSet(key, values)
        return this
    }

    override fun put(key: String, value: Int): IData {
        getInstance().putInt(key, value)
        return this
    }

    override fun put(key: String, value: Long): IData {
        getInstance().putLong(key, value)
        return this
    }

    override fun put(key: String, value: Float): IData {
        getInstance().putFloat(key, value)
        return this
    }

    override fun put(key: String, value: Boolean): IData {
        getInstance().putBoolean(key, value)
        return this
    }

    override fun remove(key: String?): IData {
        getInstance().remove(key)
        return this
    }

    override fun clear(): IData {
        getInstance().clearAll()
        return this
    }

}