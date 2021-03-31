项目解析

1.app-host:宿主项目
2.app-plugin-a: 项目a(插件 app)
3.app-plugin-b: 项目b(插件 app)
4.app-plugin-loader: 插件项目loader ---> 动态下发
5.app-plugin-runtime: 插件项目runtime ---> 动态下发
5.app-plugin-manager: 插件项目manager ---> 动态下发


shadow集成过程

宿主项目所在 project build.gradle 进行配置信息

1.配置 classpath
2.在插件项目如app-plugin-a, 执行  id 'com.tencent.shadow.plugin'

报错
1.javassist.NotFoundException: /Volumes/Caldremch/Library/Android/sdk/platforms/null/android.jar
找不到平台 android.jar
就是没有指定sdk 的版本, 比如android-30

答案: 是因为插件应用的地方不一样, 它应该放在 android{}后面进行 apply


2.在app-host中应用shadow 插件 , 报错, 错误就是各种类找不到,
答案: shadow的插件主要服务于插件app, 跟宿主 app 没什么关系, 它是用于对插件进行各种代码的处理, 然后打包成 shadow 规范的 apk 即可

3.打包loader, runtime, manager 代码, 分别以下载的形式进行加载(assets 测试)

4.宿主注册代理 Activity, 注册pps Service , 开始按照 demo 进行启动

5.检查 Activity (是继承于 Activity 而不是 AppCompatActivity注意)

6.开始执行启动插件 Activity
    java.lang.NullPointerException: Attempt to read from field 'android.content.pm.ApplicationInfo android.content.pm.PackageInfo.applicationInfo' on a null object reference
        at com.tencent.shadow.dynamic.host.ChangeApkContextWrapper.createResources(ChangeApkContextWrapper.java:51)
        at com.tencent.shadow.dynamic.host.ChangeApkContextWrapper.<init>(ChangeApkContextWrapper.java:45)
        at com.tencent.shadow.dynamic.host.ManagerImplLoader.load(ManagerImplLoader.java:53)
        at com.tencent.shadow.dynamic.host.DynamicPluginManager.updateManagerImpl(DynamicPluginManager.java:79)
        at com.tencent.shadow.dynamic.host.DynamicPluginManager.enter(DynamicPluginManager.java:56)
        at com.caldremch.android.dynamic.host.MainActivity$startAPlugin$1.run(MainActivity.kt:50)


