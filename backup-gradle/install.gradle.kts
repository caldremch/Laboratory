plugins.apply(MavenPublishPlugin::class)
//plugins.apply(org.gradle.api.plugins.AndroidMavenPlugin::class)
//声明当前gradle 所需的资源
buildscript {
    repositories {
        Deps.addDefaultRepo(this)
    }
    //添加dependencies, 不然会提示无法站到BintrayExtension等插件
    dependencies {
        classpath("com.android.tools.build:gradle:4.0.1")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${Deps.kotlin_version}")
    }
}


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
val publishedGroupId: String? by project
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

println("publishedGroupId=$publishedGroupId")
println("artifact=$artifact")

//group = publishedGroupId
//version = libraryVersion

//val sourcesJar by tasks

val isAndroid = project.hasProperty("android")
val pkgType = if (isAndroid) "aar" else "jar"
//user guide
//https://docs.gradle.org/current/userguide/publishing_maven.html

tasks.register("install") {
    configure<PublishingExtension>() {

        repositories {

        }

        publications {

            create<MavenPublication>("maven") {

//            artifact(sourcesJar)
//            artifact(sourcesJar)

                pom {

                    packaging = pkgType
                    groupId = publishedGroupId
                    artifactId = artifact

                    name.set(libraryName)
                    description.set(libraryDescription)
                    url.set(siteUrl)

                    licenses {
                        license {
                            name.set(licenseName)
                            url.set(licenseUrl)
                        }
                    }

                    developers {
                        developer {
                            id.set(developerId)
                            name.set(developerName)
                            email.set(developerEmail)
                        }
                    }

                    scm {
                        connection.set(myGitUrl)
                        developerConnection.set(myGitUrl)
                        url.set(siteUrl)
                    }

                }
            }
        }
    }
}


//val publishing by tasks
//tasks.getByName("publish") {
//
//    repositories {

//        withGroovyBuilder {
//            "maven"{
//            }
//        }

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

//    withGroovyBuilder {
//        "publications"{
//
//        }
//    }

//}



