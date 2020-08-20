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
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${Deps.kotlin_version}")
    }
}

allprojects {
    repositories {
        Deps.addDefaultRepo(this)
    }
}

val clean by tasks.creating(Delete::class){
    delete(rootProject.buildDir)
}

