plugins {
    java
}

ext {
    this[BintrayConst.myLibraryVersion] = "1.1.0.2"
    this[BintrayConst.myBintrayName] = "entry-annotation"
    this[BintrayConst.myArtifactId] = this[BintrayConst.myBintrayName]
    this[BintrayConst.myLibraryName] = "entry annotation api"
    this[BintrayConst.myLibraryDescription] = "annotation api for entrys"
}


apply(from = "../uploadCenter.gradle.kts")

