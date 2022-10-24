pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}

buildscript{
    extra.apply {
//        set("myPublishedGroupId", "io.github.caldremch")
//        set("myPublishedGroupId1", "io.github.caldremch1")
    }
}



dependencyResolutionManagement {

    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenCentral()
        google()
        jcenter()
        maven {
            setUrl("https://www.jitpack.io")
        }
    }
}
//rootProject.name = "Laboratory"
include(":app")
include(":widget")
include(":dialog")
include(":image-core")
include(":data-core")
include(":utils")
include(":picker-view")
includeBuild("../version-plugin")
//include(":native-laboratory")
//include(":nl-server")
//include(":nl-client")
//include(":nl-client:native")
//include(":nl-server:native")
include(":data-struct-helper")
include(":floating-window")
enableFeaturePreview("VERSION_CATALOGS")
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
include(":lib-startup-a")
project(":lib-startup-a").projectDir = file("./demo/startup/lib-startup-a")
include(":lib-basic-web")
