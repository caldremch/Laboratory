import com.caldremch.android.version.Deps
plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("com.caldremch.android.version")
}

ext{
    set("myLibraryVersion", "1.0.1")
    set("myBintrayName", "picker-view")
    set("myArtifactId", "picker-view")
    set("myLibraryName", "picker-view for android base develop")
    set("myLibraryDescription", "picker-view for android base develop")
}


android {
    compileSdk = com.caldremch.android.version.Deps.compileSdk
    defaultConfig {
        minSdk = com.caldremch.android.version.Deps.minSdk
        targetSdk = com.caldremch.android.version.Deps.targetSdk
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles.add(File("consumer-rules.pro"))
    }


    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile ("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    buildFeatures {
        viewBinding = true
    }

    compileOptions{
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}


dependencies {
     implementation(libs.kotlin.stdlib.jdk8)
    implementation(libs.androidx.appcompat)
    implementation(libs.kotlin.ktx)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.recyclerview)
    implementation(libs.immersionbar)
    implementation(libs.eventbus)
    implementation(libs.androidx.lifecycle)
    implementation(libs.androidx.lifecycle.extensions)
    implementation(libs.viewbinding)
    implementation(libs.koin.android)

}

//apply(from = "https://gitee.com/caldrem/gradle-maven-kotlin-dsl/raw/master/bintray-with-maven-publish.gradle")
//apply(from="https://raw.githubusercontent.com/caldremch/gradle-maven-kotlin-dsl/master/bintray-with-maven-publish.gradle")
