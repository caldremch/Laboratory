package com.caldremch.laboratory;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.caldremch.dialog.TipDialog;

/**
 * @author: Caldremch
 * @date: 2020/7/6
 * @describe:
 **/

public class JavaLaboratory {

    public static Boolean aBoolean = null;

    public static void showTipDialog(Context context) {

        ImageView imageView = new ImageView(context);
        imageView.setImageResource(R.mipmap.ic_launcher);

        View view = LayoutInflater.from(context).inflate(R.layout.test_title, null);

        new TipDialog.Builder(context)
                .setTitleText("标题")
                .setDescText("哈哈哈")
                .singleLeft(R.string.app_name)
                .singleRight(R.string.app_name)
                .defaultButton()
                .defaultSingleLeft()
                .setCustomView(view)
                .defaultSingleRight()
                .setDescColorRes(null)
                .setRightText(null)
                .setRightColorRes(R.color.colorPrimary)
                .setOnLeftBtnListener(null)
                //.setXXX
                .build()
                .show();

    }

}
