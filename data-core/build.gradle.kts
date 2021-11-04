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
    compileSdk = Deps.compileSdk
    defaultConfig {
        minSdk = Deps.minSdk
        targetSdk = Deps.targetSdk
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles.add(File("consumer-rules.pro"))
    }


    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile ("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
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
    implementation(Deps.kotlin_stdlib)
    implementation(Deps.ktx)
    testImplementation(Deps.junit)
    androidTestImplementation (Deps.junit_ext)
    androidTestImplementation (Deps.espresso_core)
    implementation("com.tencent:mmkv-static:1.2.0")
}