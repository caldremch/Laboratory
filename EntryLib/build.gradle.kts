plugins {
    id(Plugin.library)
    id(Plugin.kotlin_android)
    id(Plugin.kotlin_android_extensions)
}

ext {
    this[BintrayConst.libraryVersion] = "1.0.0"
    this[BintrayConst.bintrayName] = "entry"
    this[BintrayConst.artifact] = "entry"
    this[BintrayConst.libraryName] = "entry for list"
    this[BintrayConst.libraryDescription] = "auto config entrys"
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
    compileOnly(Deps.kotlin_stdlib)
    compileOnly(Deps.appcompat)
    compileOnly(Deps.ktx)
    compileOnly(Deps.recyclerview)
    compileOnly(Deps.BaseRecyclerViewAdapterHelper)
    api(Deps.entry_api)
}

apply(from = "../bintray.gradle.kts")
