package com.caldremch.laboratory.basic;

import android.util.Log;

/**
 * @author Caldremch
 * @date 2021/12/22
 * @email caldremch@163.com
 * @describe
 **/
public class StringLaboratory {

    private final static String TAG = "StringLaboratory";

    public void a(){
        String a = "first";
        Log.d(TAG, System.identityHashCode(a)+"");//1
        a = "first";//String对象不可变, 重新复制, 会生成新的对象 //编译器优化, 如果赋值的相等, 不会重新生成对象
        Log.d(TAG, System.identityHashCode(a)+"");//2 此时1==2
        a = "second";//String对象不可变, 重新复制, 会生成新的对象
        Log.d(TAG, System.identityHashCode(a)+"\n\n");//3
    }

    public void b(){
        Log.d(TAG, "-------------b-------------b");
        String a = "first";
        Log.d(TAG, System.identityHashCode(a)+"");//1
        a = "second";//String对象不可变, 重新复制, 会生成新的对象
        Log.d(TAG, System.identityHashCode(a)+"");//2
        a = "first";//String对象不可变, 重新复制, 会生成新的对象 //编译器优化, 如果已经存在, 不会重新生成对象
        Log.d(TAG, System.identityHashCode(a)+"");//3 此时 1==3
    }

}
