1.git submodule fatal: Needed a single revision使用
git submodule add --force xxx.git

2.添加enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
即可生成以来相关的东西


3.build.gradle不设置在setting.gradle的是情况下配置

```gradle
    dependencies {
        classpath "com.android.tools.build:gradle:7.0.4"
        classpath "org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.10"
```