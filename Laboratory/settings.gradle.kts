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
        google()
        jcenter()
        mavenCentral()
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
include(":common")
include(":utils")
include(":picker-view")
includeBuild("../version-plugin")
include(":native-laboratory")
include(":nl-server")
include(":nl-client")
include(":nl-client:native")
include(":nl-server:native")
include(":data-struct-helper")
include(":floating-window")
