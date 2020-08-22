import com.jfrog.bintray.gradle.BintrayExtension
import com.jfrog.bintray.gradle.BintrayPlugin
import org.gradle.api.plugins.AndroidMavenPlugin


plugins.apply(BintrayPlugin::class)
plugins.apply(AndroidMavenPlugin::class)

//所有属性
val myBintrayName: String by project
val myLibraryVersion: String by project
val myBintrayRepo: String by project
val myLibraryDescription: String by project
val myGitUrl: String by project
val myAllLicenses: String by project
val myPublishedGroupId: String by project
val myLibraryName: String by project
val myArtifactId: String by project
val myLicenseName: String by project
val myLicenseUrl: String by project
val myDeveloperId: String by project
val myDeveloperName: String by project
val myDeveloperEmail: String by project
val mySiteUrl: String by project

println("publishedGroupId=$myPublishedGroupId")
println("artifact=$myArtifactId")

version = myLibraryVersion
group = myPublishedGroupId

var sourcesJar: TaskProvider<Jar>
if (project.hasProperty("android")) {
    println("this project is android library")
    val android = project.extensions["android"] as com.android.build.gradle.BaseExtension
    sourcesJar = tasks.register("sourcesJar", Jar::class) {
        classifier = "sources"
        from(android.sourceSets.getByName("main").java.srcDirs)
    }

    tasks.register("javadoc", Javadoc::class) {

        setSource(android.sourceSets.getByName("main").java.srcDirs)
        classpath += project.files(android.bootClasspath.joinToString(File.pathSeparator))
        println("classpath--> ${classpath.asPath}")
    }

} else {
    println("this project is java/kotlin library")
    sourcesJar = tasks.register("sourcesJar", Jar::class) {
        //通过tasks 寻找 task 名字为classes的 task, by---->provideDelegate 代理查找
        val classes by tasks
        println("task classes---> : ${classes.toString()}")
        dependsOn(classes)
        val javaOrKotlinExtends = project.extensions["sourceSets"] as SourceSetContainer
        val dirs = javaOrKotlinExtends.getByName("main").allSource.sourceDirectories
        println("sourceSet---> : ${dirs.asPath}")
        from(javaOrKotlinExtends.getByName("main").allSource)
    }
}

tasks.register("javadocJar", Jar::class) {
    val javadoc by tasks
    classifier = "javadoc"
    val dirs = (javadoc as Javadoc).destinationDir
    println("javadoc.destinationDir-->${dirs?.absolutePath}")
    from(dirs)
}

/**
 * 理解 artifacts
 * https://juejin.im/post/6844904087675240461
 */
artifacts {
    add("archives", sourcesJar)
}

repositories {
    Deps.addDefaultRepo(this)
}

buildscript {
    repositories {
        Deps.addDefaultRepo(this)
    }
    //添加dependencies, 不然会提示无法站到BintrayExtension等插件
    dependencies {
        classpath("com.github.dcendents:android-maven-gradle-plugin:2.1")
        classpath("com.jfrog.bintray.gradle:gradle-bintray-plugin:${Deps.bintray_plugin_version}")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${Deps.kotlin_version}")
    }
}

//plugins.apply(com.jfrog.bintray.gradle.BintrayPlugin::class)
//加载配置信息 各种 ext 或者配置在 gradle.properties 中的属性
fun findProperties(key: String): String? {
    return project.findProperty(key)?.toString()
}

//加载 local.properties 中的属性
val properties = java.util.Properties()
properties.load(project.rootProject.file("local.properties").inputStream())

var userName: String? = properties.getProperty("bintray.user")
var apiKey: String? = properties.getProperty("bintray.apiKey")

if (userName.isNullOrEmpty()) {
    println("there is no bintray.user in local.properties")
} else {
    println("bintray.user found in local.properties--> $userName")
}

if (apiKey.isNullOrEmpty()) {
    println("there is no bintray.apiKey in local.properties")
} else {
    println("bintray.apiKey found in local.properties--> $apiKey   ")
}

configure<BintrayExtension> {
    user = userName
    key = apiKey
    setConfigurations("archives")
    pkg.apply {
        repo = myBintrayRepo
        name = myBintrayName
        desc = myLibraryDescription
        websiteUrl = mySiteUrl
        vcsUrl = myGitUrl
        setLicenses(myAllLicenses)
        publicDownloadNumbers = true
        publish = false
        version.apply {
            desc = myLibraryDescription
        }
    }
}
val aar: String = "aar"
"install".withGroovyBuilder {
    repositories {
        "mavenInstaller".withGroovyBuilder {
            "pom".withGroovyBuilder {
                "project".withGroovyBuilder {
                    "packaging" to aar
                    "groupId" to myPublishedGroupId
                    //https://github.com/dcendents/android-maven-gradle-plugin/issues/9
                    //对于 android项目, 我们自定义的artifactId是无效的, 这个是根据项目名字来[插件内部处理的], 所以这里传递
                    //我们设置的值是无效的, 如果需要修改的, 参照issue#9
                    "artifactId" to myArtifactId //only work for java/kotlin module/project
                    "name" to myLibraryName
                    "description" to myLibraryDescription
                    "url" to mySiteUrl

                    // Set your license
                    "licenses".withGroovyBuilder {
                        "license".withGroovyBuilder {
                            "name" to myLicenseName
                            "url" to myLicenseUrl
                        }
                    }
                    "developers".withGroovyBuilder {
                        "developer".withGroovyBuilder {
                            "id" to myDeveloperId
                            "name" to myDeveloperName
                            "email" to myDeveloperEmail
                        }
                    }
                    "scm".withGroovyBuilder {
                        "connection" to myGitUrl
                        "developerConnection" to myGitUrl
                        "url" to mySiteUrl
                    }
                }
            }
        }
    }

}