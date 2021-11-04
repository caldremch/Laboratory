pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
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
includeBuild("./version-plugin")
