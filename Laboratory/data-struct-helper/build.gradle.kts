plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_7
    targetCompatibility = JavaVersion.VERSION_1_7
}

dependencies {
    testImplementation("junit:junit:4.13.2")
}


ext{
    set("myGitUrl", "https://github.com/caldremch/Laboratory.git")
    set("myLibraryVersion", "0.0.1")
    set("myArtifactId", "data-struct-helper")
    set("myLibraryName", "data-struct-helper")
    set("myLibraryDescription", "data-struct-helper for  base develop")
    set("mySiteUrl", "https://github.com/caldremch/Laboratory")
}


apply(from="../gradle-maven-kotlin-dsl/mavencentral-with-maven-publish.gradle")