apply(plugin="com.jfrog.bintray")
//
////val version = libraryVersion
//if (project.hasProperty("android")) {
//
//    tasks {
//        this.create("sourcesJar", Jar::class) {
//            classifier = "sources"
////            from(android.getByName("main").java.srcDirs)
//        }
//    }
//}


//artifacts {
//    archives("sourcesJar")
//}

val properties = java.util.Properties()
properties.load(project.rootProject.file("local.properties").inputStream())

//bintray{
//
//}

