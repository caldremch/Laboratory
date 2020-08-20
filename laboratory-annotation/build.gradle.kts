plugins {
    kotlin("jvm")
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
