plugins.apply(org.gradle.api.plugins.AndroidMavenPlugin::class)
//声明当前gradle 所需的资源
buildscript {
    repositories {
        Deps.addDefaultRepo(this)
    }
    //添加dependencies, 不然会提示无法站到BintrayExtension等插件
    dependencies {
        classpath("com.github.dcendents:android-maven-gradle-plugin:2.1")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${Deps.kotlin_version}")
    }
}
val publishedGroupId: String? by project
//group = publishedGroupId

//maven 自带install task, 在执行打包时, 会执行 install 任务
//val install by tasks
//install.apply {
//    repositories.getByName("mavenInstaller"){
//
//    }
//}
//tasks.register("install"){
//    repositories.getByName("mavenInstaller"){
//        println("mavenInstaller--->: $name")
//    }
//}

//{
//
//}
//publishToMavenLocal{
//
//}
//扩展属性
val libraryName: String? by project
val libraryVersion: String? by project
val artifact: String? by project
val libraryDescription: String? by project
val licenseName: String? by project
val licenseUrl: String? by project
val developerId: String? by project
val developerName: String? by project
val developerEmail: String? by project
val myGitUrl: String? by project
val siteUrl: String? by project

//val publishing by tasks
//tasks.getByName("publish") {
//
//    repositories {
//        withConvention(MavenRepositoryHandlerConvention::class) {
//
//            mavenDeployer {
//
//                pom.project {
//                    withGroovyBuilder {
//                        "parent"{
//                            "groupId"(publishedGroupId)
//                            "artifactId"(artifact)
//                            "version"(libraryVersion)
//                        }
//                    }
//                }
//
//            }
//
//        }
//    }
//
//}



