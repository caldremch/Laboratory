#git rm -r --cached common
#git  submodule add git@github.com:android-module/common.git
#git  submodule add git@github.com:android-module/dialog.git
git rm --cache -r picker-view
git add .
git commit -m"rm cache picker-view"