plugins {
    `kotlin-dsl`
}

repositories {
    mavenLocal()
    maven { setUrl("http://maven.aliyun.com/nexus/content/groups/public/") }
    maven { setUrl(  "https://maven.aliyun.com/nexus/content/repositories/jcenter")}
    maven { setUrl(  "https://maven.aliyun.com/nexus/content/repositories/google")}
    mavenCentral()
    jcenter()
    google()
}

dependencies {
    implementation("com.android.tools.build:gradle:4.0.1")
    implementation(gradleApi())
}

