package com.caldremch.laboratory.basic;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Caldremch
 * @date 2021/12/22
 * @email caldremch@163.com
 * @describe
 **/
public class GenericityL {

    public void a(){
        List<String> a = new ArrayList<>();
        a.add("a");
        List<GenericityL> b = new ArrayList<>();
        b.add(new GenericityL());
//        Log.d(TAG, ""+a.getClass() == b.getClass()); //编译器优化判断, 不能执行
        Log.d(TAG, ""+a.getClass()); //class java.util.ArrayList
        Log.d(TAG, ""+a.getClass()); //class java.util.ArrayList


        /**
         * 1.不能使用基本数据类型
         */
//        List<int> a = new ArrayList<>();


        /**
         * 不能使用 instanceof 判断一个含泛型的类型, 强制转换也会警告(以为最终都会认为是Object, 不知道类型是否正确)
         */
//        a instanceof List<String>


        /**
         * 不能把泛型用于数组创建
         */
//        class A<T>{}
//        A<String> c[] = new A<String>[10];

        /**
         * 不能实例化泛型
         */
//        class A<T>{
//            private T b;
//            void a(){
//                b = new T();
//            }
//        }

        /**
         * 泛型静态变量,无法定义
         */

//        class A<T>{
//            public static T a;
//        }


        /**
         * 泛型无法重启eqauls
         */

//        class A<T>{
//            @Override
//            public boolean equals(@Nullable T obj) {
//                return super.equals(obj);
//            }
//        }

    }

}
