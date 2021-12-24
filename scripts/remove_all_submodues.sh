
###
 # @Author: Caldrech
 # @Date: 2021-12-25 00:22:45
 # @LastEditors: Caldrech
 # @LastEditTime: 2021-12-25 00:43:40
 # @Description: 
### 
# https://blog.csdn.net/guotianqing/article/details/82391665
#rm -rf 子模块目录 删除子模块目录及源码
#vi .gitmodules 删除项目目录下.gitmodules文件中子模块相关条目
#vi .git/config 删除配置项中子模块相关条目
#rm .git/module/* 删除模块下的子模块目录，每个子模块对应一个目录，注意只删除对应的子模块目录即可

pushd ../
pwd
rm -rf Laboratory/gradle-maven-kotlin-dsl
rm -rf Laboratory/common
rm -rf Laboratory/dialog
rm -rf Laboratory/utils
rm -rf Laboratory/image-core
rm -rf Laboratory/widget
rm -rf Laboratory/picker-view
rm -rf Laboratory/data-core

rm -rf .gitmodules
rm -rf .git/modules/*



git rm --cache Laboratory/gradle-maven-kotlin-dsl
git rm --cache Laboratory/common
git rm --cache Laboratory/dialog
git rm --cache Laboratory/utils
git rm --cache Laboratory/image-core
git rm --cache Laboratory/widget
git rm --cache Laboratory/picker-view
git rm --cache Laboratory/data-core


git add .
git commit -m"transparent commit"


popd || exit