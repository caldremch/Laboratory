plugins {
    kotlin("jvm")
}

kotlin {

}

java {

}

ext {
    this[BintrayConst.myLibraryVersion] = "1.0.8"
    this[BintrayConst.myBintrayName] = "maven-publish-example"
    this[BintrayConst.myArtifactId] = this[BintrayConst.myBintrayName]
    this[BintrayConst.myLibraryName] = "maven-publish-example"
    this[BintrayConst.myLibraryDescription] = "maven-publish-example "
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib:${Deps.kotlin_version}")
    testImplementation("junit:junit:4.13")
    testImplementation("com.caldremch.android:maven-publish-example:1.0.8")

}

//apply(from = "../bintray_publish.gradle.kts") upload to bintray
//apply(from = "../maven-publish.gradle.kts") upload to your own repo


//publish to bintray
//apply(from = "../bintray-with-maven-publish.gradle.kts")