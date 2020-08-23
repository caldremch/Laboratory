import com.jfrog.bintray.gradle.BintrayExtension
import com.jfrog.bintray.gradle.BintrayPlugin

buildscript {
    repositories {
        Deps.addDefaultRepo(this)
    }
    dependencies {
        //添加dependencies, 不然会提示无法站到BintrayExtension等插件
        classpath("com.jfrog.bintray.gradle:gradle-bintray-plugin:${Deps.bintray_plugin_version}")
        classpath("com.android.tools.build:gradle:4.0.1")
    }
}
plugins.apply(BintrayPlugin::class)
plugins.apply(MavenPublishPlugin::class)


var userName: String? = Deps.findInLocalProperties(project, "bintray.user")
var apiKey: String? = Deps.findInLocalProperties(project, "bintray.apiKey")

val myBintrayName: String? by project
val myLibraryVersion: String? by project
val myBintrayRepo: String? by project
val myLibraryDescription: String? by project
val myGitUrl: String? by project
val myAllLicenses: String? by project
val myPublishedGroupId: String? by project
val myLibraryName: String? by project
val myArtifactId: String? by project
val myLicenseName: String? by project
val myLicenseUrl: String? by project
val myDeveloperId: String? by project
val myDeveloperName: String? by project
val myDeveloperEmail: String? by project
val mySiteUrl: String? by project

val isAndroid = project.hasProperty("android")

val myMavenUrl = Deps.findInLocalProperties(project, "myMavenUrl")
val myMavenUserName = Deps.findInLocalProperties(project, "myMavenUserName")
val myMavenPassword = Deps.findInLocalProperties(project, "myMavenPassword")

fun println(log: String) {
    kotlin.io.println("maven-publish-bintray > $log")
}

/**
 * 默认打包方式不会生成 java doc
 * 我们自定义[MavenArtifact]-->artifact方法可以接受多种输入
 *
 */

//var sourcesJar: TaskProvider<Jar>
if (project.hasProperty("android")) {
    val android = project.extensions["android"] as com.android.build.gradle.BaseExtension
    val sourcesJar = tasks.register("sourcesJar", Jar::class) {
        classifier = "sources"
        from(android.sourceSets.getByName("main").java.srcDirs)
    }

    tasks.register("javadoc", Javadoc::class) {
        setSource(android.sourceSets.getByName("main").java.srcDirs)
        classpath += project.files(android.bootClasspath.joinToString(File.pathSeparator))
    }

} else {

    configure<JavaPluginExtension> {
        withSourcesJar()
        withJavadocJar()
    }

//    sourcesJar = tasks.register("sourcesJar", Jar::class) {
//        val classes by tasks
//        dependsOn(classes)
//        val javaOrKotlinExtends = project.extensions["sourceSets"] as SourceSetContainer
//        from(javaOrKotlinExtends.getByName("main").allSource)
//    }
}

//artifacts {
//    add("archives", sourcesJar)
//}

configure<PublishingExtension> {

    publications {

        create<MavenPublication>(Deps.a_name_whatever_you_want) {

            //https://docs.gradle.org/current/userguide/publishing_maven.html
            //官方规定必须加上afterEvaluate,否则上传出现unspecified
            afterEvaluate {
                groupId = myPublishedGroupId
                artifactId = myArtifactId
                version = myLibraryVersion ?: "unspecified-version"


                //使用我们自定义的sourcesJar task 所打包出来的产物
                if (isAndroid) {
                    if (components.size > 0) {
                        val sourcesJar by tasks
                        artifact(sourcesJar)
                        from(components["release"])
                    }
                } else {
//                    artifact(sourcesJar)
                    println("components.size = ${components.size}")
                    println("components: ${components.names.toString()}")
                    from(components["java"])
                }
            }

            //modify by yours
            pom {
                name.set(myLibraryName)
                description.set(myLibraryDescription)
                url.set(mySiteUrl)
                licenses {
                    license {
                        name.set(myLicenseName)
                        url.set(myLicenseUrl)
                    }
                }
                developers {
                    developer {
                        id.set(myDeveloperId)
                        name.set(myDeveloperName)
                        email.set(myDeveloperEmail)
                    }
                }
                scm {
                    connection.set(myGitUrl)
                    developerConnection.set(myGitUrl)
                    url.set(mySiteUrl)
                }
            }

        }
    }

}
//}


afterEvaluate {
    configure<BintrayExtension> {
        user = userName
        key = apiKey
        //设置maven-publish 配置好的 Publication
        setPublications(Deps.a_name_whatever_you_want)
        //有相同版本-则覆盖
        override = true
        pkg.apply {
            repo = myBintrayRepo
            name = myBintrayName
            desc = myLibraryDescription
            websiteUrl = mySiteUrl
            vcsUrl = myGitUrl
            setLicenses(myAllLicenses)
            publicDownloadNumbers = true
            publish = true
            version.apply {
                desc = myLibraryDescription
            }
        }
    }

}
