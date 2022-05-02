package com.caldremch.android.version

import org.gradle.api.Project

/**
 *
 * @author Caldremch
 *
 * @date 2020-05-29 14:28
 *
 * @email caldremch@163.com
 *
 * @describe 依赖库管理
 *
 **/

object Plugin {
    const val application = "com.android.application"
    const val library = "com.android.library"
    const val kotlin_android = "kotlin-android"
    const val kotlin_kapt = "kotlin-kapt"
    const val kotlin = "kotlin"
    const val java = "java"
}

object BintrayConst {
    const val myBintrayName = "myBintrayName"
    const val myArtifactId = "myArtifactId"
    const val myLibraryName = "myLibraryName"
    const val myLibraryDescription = "myLibraryDescription"
    const val myLibraryVersion = "myLibraryVersion"
}

object Deps {
    //version
    const val minSdk = 21
    const val compileSdk = 31
    const val targetSdk = 31
    const val versionCode = 1
}

