//package com.caldremch.laboratory.register
//
//import com.tencent.mrs.plugin.IDynamicConfig
//
///**
// *
// * @author Caldremch
// *
// * @date 2021-02-04 14:10
// *
// * @email caldremch@163.com
// *
// * @describe
// *
// **/
//class DynamicConfigImplDemo : IDynamicConfig {
//
//
//    fun isFPSEnable(): Boolean {
//        return true
//    }
//
//    fun isTraceEnable(): Boolean {
//        return true
//    }
//
//    fun isMatrixEnable(): Boolean {
//        return true
//    }
//
//    fun isDumpHprof(): Boolean {
//        return false
//    }
//
//    override fun get(key: String?, defStr: String?): String? {
//        return defStr
//    }
//
//    override fun get(key: String?, defInt: Int): Int {
//        return defInt
//    }
//
//    override fun get(key: String?, defLong: Long): Long {
//        return defLong
//    }
//
//    override fun get(key: String?, defBool: Boolean): Boolean {
//        return defBool
//    }
//
//    override fun get(key: String?, defFloat: Float): Float {
//        return defFloat
//    }
//}