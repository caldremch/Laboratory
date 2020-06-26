package com.caldremch.dialog.utils

/**
 *
 * @author Caldremch
 *
 * @date 2020-06-27 00:28
 *
 * @email caldremch@163.com
 *
 * @describe
 *
 **/
object PhoneCheckUtils {

    /**
     * 手机号码
     * 转为 xxx********
     *
     * @param
     * @return
     */
    fun getMaskPhone(phone: String): String? {
        val sb = StringBuilder()
        val startStr = ""
        if (phone.length > 3) {
            sb.append(phone.substring(0, 3))
        }
        for (i in 0 until phone.length - 3) {
            sb.append("*")
        }
        return sb.toString()
    }

}