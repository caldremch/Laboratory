plugins {
    kotlin("jvm")
    `maven-publish`
}

ext {
    this[BintrayConst.myLibraryVersion] = "1.0.3"
    this[BintrayConst.myBintrayName] = "your-name"
    this[BintrayConst.myArtifactId] = this[BintrayConst.myBintrayName]
    this[BintrayConst.myLibraryName] = "maven-publish-example"
    this[BintrayConst.myLibraryDescription] = "maven-publish-example "
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib:${Deps.kotlin_version}")
}

apply(from = "maven-publish.gradle.kts")

