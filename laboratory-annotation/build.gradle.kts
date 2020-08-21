plugins {
    kotlin("jvm")
}

ext {
    this[BintrayConst.libraryVersion] = "1.0.0"
    this[BintrayConst.bintrayName] = "annotation-api"
    this[BintrayConst.artifact] = this[BintrayConst.bintrayName]
    this[BintrayConst.libraryName] = "Laboratory annotation api"
    this[BintrayConst.libraryDescription] = "annotation for entrys"
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

apply(from = "../bintray.gradle.kts")