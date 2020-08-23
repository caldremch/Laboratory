plugins {
    id(Plugin.library)
}

ext {
    this[BintrayConst.myLibraryVersion] = "1.0.1"
    this[BintrayConst.myBintrayName] = "entry"
    this[BintrayConst.myArtifactId] = this[BintrayConst.myBintrayName]
    this[BintrayConst.myLibraryName] = "entry for list"
    this[BintrayConst.myLibraryDescription] = "auto config entrys"
}

android {
    compileSdkVersion(Deps.compileSdkVersion)
    buildToolsVersion(Deps.buildToolsVersion)
    defaultConfig {
        minSdkVersion(Deps.minSdkVersion)
        targetSdkVersion(Deps.targetSdkVersion)
        versionCode = Deps.versionCode
        versionName = Deps.versionName
        consumerProguardFiles("consumer-rules.pro")
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
    api(project(mapOf("path" to ":laboratory-annotation")))
    compileOnly(Deps.appcompat)
}
//apply(from = "../bintray_publish.gradle.kts") upload to bintray
//apply(from = "../maven-publish.gradle.kts") upload to your own repo

apply(from = "../bintray-with-maven-publish.gradle.kts")