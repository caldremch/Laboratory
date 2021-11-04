plugins {
    id("org.jetbrains.kotlin.jvm") version "1.5.31"
    id("java-gradle-plugin")
}
buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.31")
    }
}

repositories {
    google()
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.5.31")
}


gradlePlugin {
    plugins {
        register("version") {
            id = "com.caldremch.android.version"
            implementationClass = "com.caldremch.android.version.VersionPlugin"
        }
    }
}