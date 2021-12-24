#git rm -r --cached common
#git  submodule add git@github.com:android-module/common.git
#git  submodule add git@github.com:android-module/dialog.git
git rm --cache -r common
git rm --cache -r dialog
git rm --cache -r data-core
git rm --cache -r image-core
git rm --cache -r picker-view
git rm --cache -r utils
git rm --cache -r version-plugin
git rm --cache -r widget
git add .
git commit -m"rm cache all"