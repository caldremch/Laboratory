
# databinding使用

0.在build.gradle中开启
```gradle
    dataBinding{
        isEnabled = true
    }
```
1. 在xml布局, 中选中根节点, option+return 转为 databinding的模式
2. 在data节点中, import对应的模型, 同时也可以定义别名
3. 在(data节点中的)variable节点中声明变量名字, 如果没有import, 需要写全路径
