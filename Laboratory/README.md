
# 项目启动必备

在.gradle中添加文件 gradle.properties
```gradle
signing.keyId=
signing.password=
signing.secretKeyRingFile=
ossrhUsername=
ossrhPassword=
myPublishedGroupId=
myDeveloperId=
myDeveloperName=
myDeveloperEmail=
myLicenseName=
myLicenseUrl=
```

代码来源参考
-----------------

## 一、Java


### JVM

1. [Jvm系列3—字节码指令(介绍java虚拟机的指令功能，至少能阅读java代码生成的字节码指令含义)](http://gityuan.com/2015/10/24/jvm-bytecode-grammar/)
2. [aload、aload_1、iload都是什么意思](https://blog.csdn.net/qq_27416233/article/details/90018541)
3. [盘一盘 synchronized （一）—— 从打印Java对象头说起](https://www.cnblogs.com/LemonFive/p/11246086.html)

### Thread

1. [深入分析Synchronized原理(介绍java虚拟机的指令功能，至少能阅读java代码生成的字节码指令含义)](https://www.cnblogs.com/aspirant/p/11470858.html)



## 二、Android
1. [jetpack-navigation](https://developer.android.google.cn/jetpack/androidx/releases/navigation)
2. [Android 屏幕适配终结者](https://blankj.com/2018/12/18/android-adapt-screen-killer/)

## 三、C/C++

1. [Linux IO模式及 select、poll、epoll详解](https://segmentfault.com/a/1190000003063859)


## 四、github项目参考

1. [IO-Multiplexing](https://github.com/Liu-YT/IO-Multiplexing)

## 五、工具

* Android Studio
* javap -p(Show all classes and members) -s(Print internal type signatures) -v(Print additional information) -c(Disassemble the code) -l(Print line number and local variable tables)