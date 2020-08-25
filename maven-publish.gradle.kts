plugins.apply(MavenPublishPlugin::class)

//use guide
//https://s0docs0gradle0org.icopy.site/current/userguide/publishing_maven.html

//https://s0docs0gradle0org.icopy.site/current/dsl/org.gradle.api.publish.maven.MavenPublication.html#org.gradle.api.publish.maven.MavenPublication:artifact(java.lang.Object)
//self  MavenArtifact . artifact

buildscript {
    repositories {
        Deps.addDefaultRepo(this)
    }
    dependencies {
        classpath("com.android.tools.build:gradle:4.0.1")
    }
}

fun println(log: String) {
    kotlin.io.println("maven-publish > $log")
}

val myBintrayName: String? by project
val myLibraryVersion: String? by project
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
val pkgType = if (isAndroid) "aar" else "jar"


val myMavenUrl = Deps.findInLocalProperties(project, "myMavenUrl")
val myMavenUserName = Deps.findInLocalProperties(project, "myMavenUserName")
val myMavenPassword = Deps.findInLocalProperties(project, "myMavenPassword")

println("myMavenUrl:$myMavenUrl")
println("myMavenUserName:$myMavenUserName")
println("myMavenPassword:$myMavenPassword")

/**
 * 默认打包方式不会生成 java doc
 * 我们自定义[MavenArtifact]-->artifact方法可以接受多种输入
 *
 */
if (project.hasProperty("android")) {
    println("this project is android type")

    val android = project.extensions["android"] as com.android.build.gradle.BaseExtension

    //register sourcesJar for android
    val sourcesJar = tasks.register("sourcesJar", Jar::class) {
        archiveClassifier.set("sources")
        from(android.sourceSets.getByName("main").java.srcDirs)
    }

    //register task javadoc for android
    val javadoc = tasks.register("javadoc", Javadoc::class) {
        setSource(android.sourceSets.getByName("main").java.srcDirs)
        classpath += project.files(android.bootClasspath.joinToString(File.pathSeparator))
    }


    tasks.register("androidJavaDocsJar", Jar::class) {
        archiveClassifier.set("javadoc")
        dependsOn(javadoc)
        from(javadoc.get().destinationDir)
    }


} else {
    println("this project is java/kotlin type")

    //use gradle packing-->components["java]
    configure<JavaPluginExtension> {
        withSourcesJar()
        withJavadocJar()
    }

    /*
    this is a usual packing way
    sourcesJar = tasks.register("sourcesJar", Jar::class) {
        //通过tasks 寻找 task 名字为classes的 task, by---->provideDelegate 代理查找
        val classes by tasks
        println("task classes---> : ${classes.toString()}")
        dependsOn(classes)
        val javaOrKotlinExtends = project.extensions["sourceSets"] as SourceSetContainer
        val dirs = javaOrKotlinExtends.getByName("main").allSource.sourceDirectories
        println("sourceSet---> : ${dirs.asPath}")
        from(javaOrKotlinExtends.getByName("main").allSource)
    }*/
}

//https://developer.android.google.cn/studio/build/maven-publish-plugin?hl=zh-cn
// in Android, Because the components are created only during the afterEvaluate phase, you must
// configure your publications using the afterEvaluate() lifecycle method.
//afterEvaluate 在所有模块都配置完以后, 就会回调afterEvaluate, 为什么要在afterEvaluate 配置 publishing? 因为publishing 里面的 components 在配置完后才会创建
//可以测试一下, 不在afterEvaluate回调里面配置 publishing 在 sync 的时候, components.size = 0, 在afterEvaluate里面的话, components.size=3, 分别是
//debug, release, all


//configure use for plugin
afterEvaluate {
    configure<PublishingExtension> {

        repositories {
            maven {
//            url = uri("$buildDir/repo")
                myMavenUrl?.apply { url = uri(this) }
                /**
                 * [AuthenticationSupported]
                 */
                if (myMavenUserName.isNullOrEmpty().not() && myMavenPassword.isNullOrEmpty()
                        .not()
                ) {
                    credentials {
                        username = myMavenUserName
                        password = myMavenPassword
                    }
                }

            }
        }

        publications {
            //create for node
            //AnyName publication
            create<MavenPublication>("AnyName") {

                //使用默认的产物
                //from(components["java"])

                //使用我们自定义的sourcesJar task 所打包出来的代码
                if (isAndroid) {
                    //is ok to set the aar of build/output dir
                    //artifact("$buildDir/output/xx.aar")
                    println("check result is Android ${components.size}")
                    if (components.size > 0) {
                        //单纯的components["release"]是不包含javadoc和源码的
                        from(components["debug"])
                        //添加源码和Javadoc
                        val androidJavaDocsJar by tasks
                        val sourcesJar by tasks
                        artifact(sourcesJar)
                        //artifact(javadoc) wrong usage, solution: javadoc show cast to TaskProvider<JavaDoc>, and use javadoc.get(), and then ,artifact(javadoc.get().destinationDir
                        artifact(androidJavaDocsJar)
                    }
                } else {
                    from(components["java"])
                }
                //artifact 'my-file-name.jar' // Publish a file created outside of the build
                //show wrap with afterEvaluate
                groupId = myPublishedGroupId
                artifactId = myArtifactId
                version = myLibraryVersion

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
}
