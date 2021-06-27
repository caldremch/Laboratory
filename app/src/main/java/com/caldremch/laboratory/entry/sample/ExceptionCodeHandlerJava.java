package com.caldremch.laboratory.entry.sample;

import android.content.Context;
import android.util.Log;
import android.view.View;

import com.caldremch.dialog.TipDialog;

/**
 * @auther Caldremch
 * @email finishmo@qq.com
 * @date 2/25/21 09:39
 * @description
 */
public class ExceptionCodeHandlerJava {

    public static boolean isLogin = true;

    public static void handleMulti(Context context) {
        if (isLogin) {
            isLogin = false;
            Log.d("MultiEntry", "handleMulti: 执行次数");
            new TipDialog.Builder(context).setDescText("202弹窗")
                    .setLeftText("好的")
                    .setCancelOutSide(false)
                    .setOnLeftBtnListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                        }
                    })
                    .build()
                    .show();
        }
    }

}
