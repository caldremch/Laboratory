plugins {
    java
}


ext {
    this[BintrayConst.myLibraryVersion] = "1.0.4"
    this[BintrayConst.myBintrayName] = "annotation-api"
    this[BintrayConst.myArtifactId] = this[BintrayConst.myBintrayName]
    this[BintrayConst.myLibraryName] = "Laboratory annotation api"
    this[BintrayConst.myLibraryDescription] = "annotation for entrys"
}


//apply(from = "../bintray_publish.gradle.kts")

