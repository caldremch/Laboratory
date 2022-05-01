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
    ndkVersion = "24.0.7956693 rc2"
}


dependencies {
    implementation("com.yanzhenjie:permission:2.0.3")
    implementation(Deps.kotlin_stdlib)
    implementation(Deps.appcompat)
    implementation(Deps.ktx)
    implementation("androidx.activity:activity-ktx:1.3.1")
    implementation("androidx.fragment:fragment-ktx:1.4.0")
    implementation(Deps.constraintlayout)
    implementation(Deps.recyclerview)
    implementation(Deps.BaseRecyclerViewAdapterHelper)
    implementation(Deps.cardview)
    implementation(Deps.design)
    implementation(Deps.glide)
    implementation(Deps.leakcanary)
    implementation(Deps.coroutines)
    implementation(Deps.viewbinding)
    implementation(projects.dialog)
    implementation(projects.widget)
    implementation(projects.utils)
    implementation(projects.common)
    implementation(projects.imageCore)
    implementation(projects.floatingWindow)
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

