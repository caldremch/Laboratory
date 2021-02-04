package com.caldremch.common.util

import java.lang.reflect.ParameterizedType

/**
 *
 * @author Caldremch
 *
 * @date 2020-07-02 13:56
 *
 * @email caldremch@163.com
 *
 * @describe
 *
 **/
object Utils {


    fun <T> getType(o: Any): Class<T> {
        var errorMsg: String? = null
        try {
            //项目特殊原因, 分页的时候, 将会有两个泛型
            val type = o.javaClass.genericSuperclass
                ?: throw RuntimeException("o.javaClass.genericSuperclass == null")
            var parameterizedType: ParameterizedType? = null
            if (type is ParameterizedType) {
                parameterizedType = type
            }
            if (parameterizedType == null) {
                throw RuntimeException("parameterizedType == null")
            }
            val types =
                parameterizedType.actualTypeArguments
            if (types.isEmpty()) {
                throw RuntimeException(" parameterizedType.actualTypeArguments isEmpty")
            }
            val realType = parameterizedType.actualTypeArguments[0]
            if (types.size == 2) {
                val secondType = parameterizedType.actualTypeArguments[1]
            }
            return realType as Class<T>
        } catch (e: InstantiationException) {
            e.printStackTrace()
            errorMsg = e.message
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
            errorMsg = e.message
        }
        throw RuntimeException(" error $errorMsg")
    }

    fun <T> getInstance(o: Any): T? {
        try {
            //项目特殊原因, 分页的时候, 将会有两个泛型
            val type = o.javaClass.genericSuperclass ?: return null
            var parameterizedType: ParameterizedType? = null
            if (type is ParameterizedType) {
                parameterizedType = type
            }
            if (parameterizedType == null) {
                return null
            }
            val types =
                parameterizedType.actualTypeArguments
            if (types.isEmpty()) {
                return null
            }
            val realType = parameterizedType.actualTypeArguments[0]
            if (types.size == 2) {
                val secondType = parameterizedType.actualTypeArguments[1]
            }
            val clz = realType as Class<T>
            return clz.newInstance()
        } catch (e: InstantiationException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }
        return null
    }

}