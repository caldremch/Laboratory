plugins {
    `kotlin-dsl`
}

repositories {
    mavenLocal()
    mavenCentral()
    jcenter()
    google()
}

dependencies {
    implementation("com.android.tools.build:gradle:3.5.4")
    implementation(gradleApi())
}

