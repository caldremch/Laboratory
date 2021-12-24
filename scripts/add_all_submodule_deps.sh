###
 # @Author: Caldrech
 # @Date: 2021-12-25 00:11:03
 # @LastEditors: Caldrech
 # @LastEditTime: 2021-12-25 00:46:04
 # @Description: 
### 
pushd ../
pwd


git  submodule add git@github.com:caldremch/gradle-maven-kotlin-dsl.git Laboratory/gradle-maven-kotlin-dsl
git  submodule add git@github.com:android-module/common.git Laboratory/common
git  submodule add git@github.com:android-module/dialog.git  Laboratory/dialog
git  submodule add git@github.com:android-module/utils.git Laboratory/utils
git  submodule add git@github.com:android-module/image-core.git Laboratory/image-core
git  submodule add git@github.com:android-module/widget.git Laboratory/widget
git  submodule add git@github.com:android-module/picker-view.git Laboratory/picker-view
git  submodule add git@github.com:android-module/data-core.git Laboratory/data-core

popd || exit



#问题记录 fatal: 'Laboratory/xxxx' already exists in the inde
#git ls-files 命令: https://cloud.tencent.com/developer/section/1138777
#fatal: 'Laboratory/xxxx' already exists in the index解决: https://cloud.tencent.com/developer/ask/71104
