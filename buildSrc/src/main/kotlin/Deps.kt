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

object Plugin{
    const val application = "com.android.application"
    const val library = "com.android.library"
    const val kotlin_android = "kotlin-android"
    const val kotlin_android_extensions = "kotlin-android-extensions"
}

object Deps {

    //version
    const val minSdkVersion = 18
    const val compileSdkVersion = 30
    const val targetSdkVersion = 30
    const val versionCode = 1
    const val versionName = "1.0"
    const val buildToolsVersion = "30.0.2"

    const val kotlin_version = "1.3.72"
    //support
    const val appcompat = "androidx.appcompat:appcompat:1.1.0"
    const val constraintlayout = "androidx.constraintlayout:constraintlayout:1.1.3"
    const val cardview = "androidx.cardview:cardview:1.0.0"
    const val ktx = "androidx.core:core-ktx:1.3.1"
    const val kotlin_stdlib = "org.jetbrains.kotlin:kotlin-stdlib:$kotlin_version"
    const val lifecycle = "androidx.lifecycle:lifecycle-common-java8:2.2.0"

    //android dep
    const val recyclerview = "androidx.recyclerview:recyclerview:1.1.0"

    //third part
    const val glide = "com.github.bumptech.glide:glide:4.10.0"
    const val recyclerview_animators = "jp.wasabeef:recyclerview-animators:3.0.0"
    const val immersionbar = "com.gyf.immersionbar:immersionbar:3.0.0"
    const val BaseRecyclerViewAdapterHelper =
        "com.github.CymChad:BaseRecyclerViewAdapterHelper:3.0.4"
    const val lottie = "com.airbnb.android:lottie:3.4.1"
    const val eventbus = "org.greenrobot:eventbus:3.2.0"
    const val refresh = "com.scwang.smart:refresh-layout-kernel:2.0.1"
    const val refresh_header_falsify = "com.scwang.smart:refresh-header-falsify:2.0.1"
    const val refresh_footer_classics = "com.scwang.smart:refresh-footer-classics:2.0.1"
    const val refresh_header_classics = "com.scwang.smart:refresh-header-classics:2.0.1"
    const val banner = "com.youth.banner:banner:2.0.12"
    const val MagicIndicator = "com.github.hackware1993:MagicIndicator:1.6.0"
    const val leakcanary = "com.squareup.leakcanary:leakcanary-android:2.4"
    const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.5"
}

