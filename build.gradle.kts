buildscript {
    val kotlin_version by extra("1.3.72")
    repositories {
        //本地仓库
        mavenLocal()
        //优先查找镜像地址
        maven { setUrl(Deps.maven_aliyun_public) }
        maven { setUrl(Deps.maven_aliyun_jcenter) }
        maven { setUrl(Deps.maven_aliyun_google) }
        //官方地址
        mavenCentral()
        google()
        jcenter()


    }
    dependencies {
        classpath("com.android.tools.build:gradle:4.0.1")
        classpath("com.jfrog.bintray.gradle:gradle-bintray-plugin:1.8.4")
        classpath("com.github.dcendents:android-maven-gradle-plugin:2.1")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${Deps.kotlin_version}")
    }
}

allprojects {
    repositories {
        Deps.addDefaultRepo(this)
    }

    //如果是kotlin项目,请添加此项,纯Java项目请忽略
    tasks.withType(Javadoc::class).all { enabled = false }
}

val clean by tasks.creating(Delete::class){
    delete(rootProject.buildDir)
}

