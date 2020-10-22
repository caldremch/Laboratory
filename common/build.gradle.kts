plugins {
    id(Plugin.library)
    id(Plugin.kotlin_android)
    id(Plugin.kotlin_android_extensions)
}

ext {
    this["myLibraryVersion"] = "1.0.0"
    this["myBintrayName"] = "common"
    this["myArtifactId"] = "common"
    this["myLibraryName"] = "common for android base develop"
    this["myLibraryDescription"] = "common for android base develop"
}



android {
    compileSdkVersion(Deps.compileSdkVersion)
    buildToolsVersion(Deps.buildToolsVersion)
    defaultConfig {
        minSdkVersion(Deps.minSdkVersion)
        targetSdkVersion(Deps.targetSdkVersion)
        versionCode = Deps.versionCode
        versionName = Deps.versionName
        resourcePrefix("cm_")
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
    implementation(Deps.constraintlayout)
    implementation(Deps.recyclerview)
    implementation(Deps.immersionbar)
    implementation(Deps.eventbus)
    implementation(Deps.lifecycle)
}
//apply(from="https://raw.githubusercontent.com/caldremch/gradle-maven-kotlin-dsl/master/bintray-with-maven-publish.gradle")
