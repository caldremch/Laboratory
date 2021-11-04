import com.caldremch.android.version.Deps
plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("com.caldremch.android.version")
}

ext{
    set("myLibraryVersion", "1.0.1")
    set("myBintrayName", "widget")
    set("myArtifactId", "widget")
    set("myLibraryName", "widget for android base develop")
    set("myLibraryDescription", "widget for android base develop")
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

//apply(from = "https://gitee.com/caldrem/gradle-maven-kotlin-dsl/raw/master/bintray-with-maven-publish.gradle")
//apply(from="https://raw.githubusercontent.com/caldremch/gradle-maven-kotlin-dsl/master/bintray-with-maven-publish.gradle")
