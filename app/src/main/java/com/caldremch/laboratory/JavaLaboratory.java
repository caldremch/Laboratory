package com.caldremch.laboratory;

import com.caldremch.dialog.TipDialog;

/**
 * @author: Caldremch
 * @date: 2020/7/6
 * @describe:
 **/

class JavaLaboratory {

    public static void showTipDialog(Object parent) {
        new TipDialog.Builder(parent)
                .setTitleText("标题")
                .setDescText("哈哈哈")
                .singleLeft(R.string.app_name)
                .singleRight(R.string.app_name)
                .defaultButton()
                .defaultSingleLeft()
                .defaultSingleRight()
                .setDescColorRes(null)
                .setRightText(null)
                .setOnLeftBtnListener(null)
                //.setXXX
                .build()
                .show();

    }

}
