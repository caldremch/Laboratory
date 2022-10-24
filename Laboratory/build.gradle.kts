// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.0.4")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.7.0")
        classpath(libs.navigation.safe.args.gradle.plugin)
//        classpath "androidx.navigation:navigation-safe-args-gradle-plugin:$nav_version"
    }
}


//task clean(type: Delete) {
//    delete rootProject.buildDir
//}