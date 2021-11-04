import com.caldremch.android.version.Deps

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.caldremch.android.version")
    id("kotlin-kapt")
}

//plugins {
//    id 'com.caldremch.android.version'
//    id 'kotlin-kapt'
////    id 'com.tencent.matrix-plugin'
//    id 'kotlin-parcelize'
//}
//id 'com.caldremch.android.version'
//id 'org.jetbrains.kotlin.android'
//id 'kotlin-kapt'
////    id 'com.tencent.matrix-plugin'
android {
    compileSdk = Deps.compileSdk
    defaultConfig {
        applicationId  = Deps.applicationId
        minSdk = Deps.minSdk
        targetSdk = Deps.targetSdk
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }


    signingConfigs {
        create("config"){
            keyAlias  = "laboratory"
            keyPassword = "123456"
            storeFile  = project.rootProject.projectDir.resolve("key.jks")
            storePassword  = "123456"
            enableV1Signing = true
            enableV2Signing = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            signingConfig = signingConfigs.getByName("config")

        }

        debug{
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            signingConfig = signingConfigs.getByName("config")
        }
    }

    buildFeatures {
        viewBinding = true
    }
    compileOptions {
        sourceCompatibility  = JavaVersion.VERSION_1_8
        targetCompatibility  = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}


dependencies {
    implementation("com.yanzhenjie:permission:2.0.3")
    implementation(Deps.kotlin_stdlib)
    implementation(Deps.appcompat)
    implementation(Deps.ktx)
    implementation(Deps.constraintlayout)
    implementation(Deps.recyclerview)
    implementation(Deps.BaseRecyclerViewAdapterHelper)
    implementation(Deps.cardview)
    implementation(Deps.design)
    implementation(Deps.glide)
    implementation(Deps.leakcanary)
    implementation(Deps.coroutines)
    implementation(Deps.viewbinding)
//    implementation(project(":PickerView"))
    implementation(project(":dialog"))
//    implementation(project(":matrix-android-lib"))
//    implementation(project(":matrix-trace-canary"))
    implementation(project(":widget"))
    implementation(project(":utils"))
    implementation(project(":common"))
    implementation(project(":image-core"))
//    implementation(project(":native-logger"))
    implementation(Deps.entry)
    implementation(Deps.eventbus)
    implementation("com.caldremch.android:http:1.0.0")
    kapt(Deps.entry_compiler)
    implementation(Deps.coroutines)

    testImplementation(Deps.junit)
    androidTestImplementation(Deps.junit_ext)
    androidTestImplementation(Deps.espresso_core)

}

//ext.unusedResourcesSet = new HashSet<String>();

//apply plugin: com.tencent.matrix.trace.MatrixPluginEx
//
//matrix {
//    trace {
//        enable = true
//        baseMethodMapFile = "${project.projectDir}/matrixTrace/methodMapping.txt"
//        blackListFile = "${project.projectDir}/matrixTrace/blackMethodList.txt"
//    }
//}

