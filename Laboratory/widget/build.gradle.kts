import com.caldremch.android.version.Deps
plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("com.caldremch.android.version")
}

ext{
    set("myGitUrl", "https://github.com/android-module/widget.git")
    set("myLibraryVersion", "1.0.0")
    set("myArtifactId", "widget")
    set("myLibraryName", "widget")
    set("myLibraryDescription", "widget for android base develop")
    set("mySiteUrl", "https://github.com/android-module/widget")
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

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(Deps.kotlin_stdlib)
    implementation(Deps.appcompat)
    implementation(Deps.ktx)
    implementation(Deps.constraintlayout)
    implementation(Deps.recyclerview)
    api(Deps.banner)
    implementation(Deps.BaseRecyclerViewAdapterHelper)
    api(Deps.refresh)
    api(Deps.refresh_header_classics)
    api(Deps.refresh_footer_classics)
    api(Deps.refresh_header_falsify)
    implementation(Deps.MagicIndicator)
    implementation(Deps.viewbinding)
}

apply(from="../gradle-maven-kotlin-dsl/mavencentral-with-maven-publish.gradle")
