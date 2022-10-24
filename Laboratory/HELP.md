# [删除github子模块](https://blog.csdn.net/guotianqing/article/details/82391665)

1. rm -rf 子模块目录
2. vi .gitmodules 删除项目目录下.gitmodules文件中子模块相关条目
3. vi .git/config 删除配置项中子模块相关条目
4. rm .git/module/* 删除模块下的子模块目录，每个子模块对应一个目录，注意只删除对应的子模块目录即可