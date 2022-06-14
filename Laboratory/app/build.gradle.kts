import com.caldremch.android.version.Deps

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.caldremch.android.version")
    id("kotlin-kapt")
}

android {
    compileSdk = Deps.compileSdk
    defaultConfig {
        applicationId  = libs.versions.applicationId.get()
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

    dataBinding{
        isEnabled = true
    }

    compileOptions {
        sourceCompatibility  = JavaVersion.VERSION_1_8
        targetCompatibility  = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    ndkVersion = "24.0.7956693 rc2"
}


dependencies {
    implementation(libs.kotlin.stdlib.jdk8)
    implementation(libs.kotlin.coroutines.android)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.recyclerview)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.fragment)
    implementation(libs.androidx.cardview)
    implementation(libs.androidx.design)
    implementation(libs.androidx.junit)
    implementation(libs.androidx.junit.ext)
    implementation(libs.androidx.espresso.core)
    implementation(libs.kotlin.ktx)
    implementation(libs.baseRecyclerViewAdapterHelper)
    implementation(libs.glide)
    implementation(libs.leakcanary)
    implementation(libs.viewbinding)
    implementation(libs.permission)
    implementation(projects.dialog)
    implementation(projects.widget)
    implementation(projects.utils)
    implementation(projects.common)
    implementation(projects.imageCore)
    implementation(projects.floatingWindow)
    implementation(libs.caldremch.entry)
    implementation(libs.eventbus)
    implementation(libs.navigation.fragment.ktx)
    implementation(libs.navigation.ui.ktx)
    implementation(libs.caldremch.http)
    implementation(libs.koin.android)
    implementation(libs.koin.android.compat)
    implementation(libs.koin.android.navigation)
    kapt(libs.caldremch.entry.compiler)
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

