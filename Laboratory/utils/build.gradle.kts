import com.caldremch.android.version.Deps

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("com.caldremch.android.version")
}

ext {
    set("myGitUrl", "https://github.com/android-module/utils.git")
    set("myLibraryVersion", "1.0.0")
    set("myArtifactId", "utils")
    set("myLibraryName", "utils")
    set("myLibraryDescription", "utils for android base develop")
    set("mySiteUrl", "https://github.com/android-module/utils")
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
    implementation(Deps.appcompat)
    implementation(Deps.ktx)
    implementation(Deps.coroutines)
    implementation(Deps.lifecycle)
    implementation(Deps.gson)
    implementation(Deps.activity)
    implementation(Deps.fragment)
}

apply(from = "../gradle-maven-kotlin-dsl/mavencentral-with-maven-publish.gradle")
