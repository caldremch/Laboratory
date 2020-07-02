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