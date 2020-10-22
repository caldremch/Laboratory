plugins {
    id(Plugin.library)
    id(Plugin.kotlin_android)
    id(Plugin.kotlin_android_extensions)
}


ext {
    this["myLibraryVersion"] = "1.0.1"
    this["myBintrayName"] = "utils"
    this["myArtifactId"] = "utils"
    this["myLibraryName"] = "utils for android base develop"
    this["myLibraryDescription"] = "utils for android base develop"
}

android {
    compileSdkVersion(Deps.compileSdkVersion)
    buildToolsVersion(Deps.buildToolsVersion)
    defaultConfig {
        minSdkVersion(Deps.minSdkVersion)
        targetSdkVersion(Deps.targetSdkVersion)
        versionCode = Deps.versionCode
        versionName = Deps.versionName
    }

    buildTypes {
        getByName(com.android.builder.core.BuilderConstants.RELEASE) {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android.txt"),
                file("proguard-rules.pro")
            )
        }
    }

    compileOptions(Action<com.android.build.gradle.internal.CompileOptions> {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    })

}


dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(Deps.kotlin_stdlib)
    implementation(Deps.appcompat)
    implementation(Deps.ktx)
    implementation(Deps.coroutines)
    implementation(Deps.lifecycle)
}

apply(from = "https://gitee.com/caldrem/gradle-maven-kotlin-dsl/raw/master/bintray-with-maven-publish.gradle")
//apply(from="https://raw.githubusercontent.com/caldremch/gradle-maven-kotlin-dsl/master/bintray-with-maven-publish.gradle")
