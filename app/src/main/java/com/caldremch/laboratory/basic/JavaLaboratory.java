package com.caldremch.laboratory.basic;

/**
 * @author Caldremch
 * @date 2021/12/21
 * @email caldremch@163.com
 * @describe
 **/
// TODO: 2021/12/21 查看字节码 synchronized的实现
public class JavaLaboratory {

    final int a = 1;//a不可修改
    static int b = 1;//b存放在方法区 ,属于类的资源范畴,

    //同步方法
    synchronized void a() {

    }

    JavaLaboratory instance = new JavaLaboratory();

    void b() {
        synchronized (JavaLaboratory.class) {
            System.out.println("synchronized avaLaboratory.class");
        }

        synchronized (instance) {
            System.out.println("synchronized instance");
        }
    }

}
