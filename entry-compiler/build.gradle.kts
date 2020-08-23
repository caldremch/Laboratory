plugins {
    kotlin("jvm")
    kotlin("kapt")
}

ext {
    this[BintrayConst.myLibraryVersion] = "1.1.0.1"
    this[BintrayConst.myBintrayName] = "entry-compiler"
    this[BintrayConst.myArtifactId] = this[BintrayConst.myBintrayName]
    this[BintrayConst.myLibraryName] = "entry annotation compiler"
    this[BintrayConst.myLibraryDescription] = "compiler for entry annotaion"
}


dependencies {
    kapt(Deps.auto_service)
    implementation(Deps.auto_service)
    implementation(Deps.javapoet)
    implementation(Deps.kotlin_stdlib)
    implementation(Deps.entry_annotation)
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

apply(from = "../bintray-with-maven-publish.gradle.kts")

