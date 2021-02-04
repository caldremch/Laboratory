package com.caldremch.utils

import java.lang.reflect.Field
import java.lang.reflect.Method
import java.util.*

/**
 *
 * @auther Caldremch
 *
 * @email finishmo@qq.com
 *
 * @date 1/20/21 15:13
 *
 * @description
 *
 *
 */
object ReflectUtils {

    /**
     * Locates a given field anywhere in the class inheritance hierarchy.
     *
     * @param instance an object to search the field into.
     * @param name field name
     * @return a field object
     * @throws NoSuchFieldException if the field cannot be located
     */
    @Throws(NoSuchFieldException::class)
    private fun findField(instance: Any, name: String): Field? {
        var clazz: Class<*>? = instance.javaClass
        while (clazz != null) {
            try {
                val field = clazz.getDeclaredField(name)
                if (!field.isAccessible) {
                    field.isAccessible = true
                }
                return field
            } catch (e: NoSuchFieldException) {
                // ignore and search next
            }
            clazz = clazz.superclass
        }
        throw NoSuchFieldException("Field " + name + " not found in " + instance.javaClass)
    }

    /**
     * Locates a given method anywhere in the class inheritance hierarchy.
     *
     * @param instance an object to search the method into.
     * @param name method name
     * @param parameterTypes method parameter types
     * @return a method object
     * @throws NoSuchMethodException if the method cannot be located
     */
    @Throws(NoSuchMethodException::class)
    private fun findMethod(instance: Any, name: String, vararg parameterTypes: Class<*>): Method? {
        var clazz: Class<*>? = instance.javaClass
        while (clazz != null) {
            try {
                val method = clazz.getDeclaredMethod(name, *parameterTypes)
                if (!method.isAccessible) {
                    method.isAccessible = true
                }
                return method
            } catch (e: NoSuchMethodException) {
                // ignore and search next
            }
            clazz = clazz.superclass
        }
        throw NoSuchMethodException(
            "Method " + name + " with parameters " +
                    Arrays.asList(*parameterTypes) + " not found in " + instance.javaClass
        )
    }
}