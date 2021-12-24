package com.caldremch.laboratory.basic;

/**
 * @author Caldremch
 * @date 2021/12/24
 * @email caldremch@163.com
 * @describe
 **/
public class ThreadBlockLaboratory {

    private int a = 1;

    synchronized void b(){
        a=2;
    }


    void a(){
        try {
            /**
             * sleep并不会释放monitor
             */
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
