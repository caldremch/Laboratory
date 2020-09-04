buildscript {
    repositories {
        Deps.addDefaultRepo(this)
    }
    dependencies {
        classpath("com.android.tools.build:gradle:4.0.1")
        classpath("com.jfrog.bintray.gradle:gradle-bintray-plugin:${Deps.bintray_plugin_version}")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${Deps.kotlin_version}")
    }
}

allprojects {
    repositories {
        Deps.addDefaultRepo(this)
        configMyTestUrl(this)
    }

    //如果是kotlin项目,请添加此项,纯Java项目请忽略
    tasks.withType(Javadoc::class).all { enabled = false }
}

val clean by tasks.creating(Delete::class) {
    delete(rootProject.buildDir)
}

//for my maven repository test
fun configMyTestUrl(repositoryHandler: RepositoryHandler) {
    val myMavenUrl = Deps.findInLocalProperties(project, "myMavenUrl")
    if (myMavenUrl.isNullOrEmpty().not()) {
        val myMavenUserName = Deps.findInLocalProperties(project, "myMavenUserName")
        val myMavenPassword = Deps.findInLocalProperties(project, "myMavenPassword")
        repositoryHandler.maven(url = myMavenUrl!!) {
            credentials {
                username = myMavenUserName
                password = myMavenPassword
            }
        }
    }

}
