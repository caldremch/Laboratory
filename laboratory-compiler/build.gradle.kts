plugins {
    kotlin("jvm")
    kotlin("kapt")
}
dependencies {
    kapt(Deps.auto_service)
    implementation(Deps.auto_service)
    implementation(Deps.javapoet)
    implementation(Deps.kotlin_stdlib)
    implementation(project(":laboratory-annotation"))
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