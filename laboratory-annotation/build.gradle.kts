plugins {
    kotlin("jvm")
}

ext {
    this[BintrayConst.myLibraryVersion] = "1.0.4"
    this[BintrayConst.myBintrayName] = "annotation-api"
    this[BintrayConst.myArtifactId] = this[BintrayConst.myBintrayName]
    this[BintrayConst.myLibraryName] = "Laboratory annotation api"
    this[BintrayConst.myLibraryDescription] = "annotation for entrys"
}

dependencies {
    implementation(Deps.kotlin_stdlib)
}
buildscript {
    repositories {
        Deps.addDefaultRepo(this)

    }
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${Deps.kotlin_version}")
    }
}
repositories {
    Deps.addDefaultRepo(this)
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
}

//apply(from = "../bintray_publish.gradle.kts")

