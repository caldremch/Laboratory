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

    public  synchronized static void handleMulti(Context context) {

//        String sb = "thread=" + Thread.currentThread().getId() +
//                ", isLogin =" + isLogin;
//        Log.d(
//                "MultiEntry",
//                sb
//        );

        if (isLogin) {
            String sb2 = "thread=" + Thread.currentThread().getId() +
                    ", isLogin =" + isLogin;
            Log.d(
                    "MultiEntry",
                    sb2
            );
            Log.d("MultiEntry", "handleMulti: 执行次数");
            new TipDialog.Builder(context).setDescText("202弹窗")
                    .setLeftText("好的")
                    .setCancelOutSide(false)
                    .setOnLeftBtnListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            isLogin = false;
                        }
                    })
                    .build()
                    .show();
        }
    }

}
