import com.caldremch.android.version.Deps

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("com.caldremch.android.version")
}

ext {
    set("myLibraryVersion", "1.0.1")
    set("myBintrayName", "dialog")
    set("myArtifactId", "dialog")
    set("myLibraryName", "dialog for android base develop")
    set("myLibraryDescription", "dialog for android base develop")
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
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(Deps.kotlin_stdlib)
    implementation(Deps.constraintlayout)
    implementation(Deps.ktx)
    implementation(Deps.appcompat)
    implementation(Deps.lifecycle)
    implementation(Deps.recyclerview_animators)
    implementation(Deps.BaseRecyclerViewAdapterHelper)
    implementation(project(":utils"))
    implementation(project(":common"))
}