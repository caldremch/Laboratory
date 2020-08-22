import com.jfrog.bintray.gradle.BintrayExtension
import com.jfrog.bintray.gradle.BintrayPlugin

//set plugins
plugins.apply(BintrayPlugin::class)

//设置版本
val libraryVersion: String? by project
//version = libraryVersion
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

val bintrayRepo: String? by project
val bintrayName: String? by project
val libraryDescription: String? by project
val siteUrl: String? by project
val myGitUrl: String? by project
val allLicenses: String? by project

configure<BintrayExtension> {
    user = userName
    key = apiKey
    setConfigurations("archives")
    pkg.apply {
        repo = bintrayRepo
        name = bintrayName
        desc = libraryDescription
        websiteUrl = siteUrl
        vcsUrl = myGitUrl
        setLicenses(allLicenses)
        publicDownloadNumbers = true
        publish = true
        version.apply {
            desc = libraryDescription
        }
    }
}