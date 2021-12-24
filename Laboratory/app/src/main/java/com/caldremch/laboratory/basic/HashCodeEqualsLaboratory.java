package com.caldremch.laboratory.basic;

import android.util.Log;

/**
 * @author Caldremch
 * @date 2021/12/22
 * @email caldremch@163.com
 * @describe
 **/
public class HashCodeEqualsLaboratory {

    private final static String TAG = "HashCodeEqualsLaboratory";

    public void a(){
        //hashcode  是散列表里面的一种所以方式, 通过hash函数生成hashcode, 最终确定在散列表中的位置,
        //所以对象一样, 那么hashcode肯定一样, 但是hashcode一样, 对象就不一定一样为什么? 因为hash函数的计算是按照一定规则
        //进行的, 如果条件刚好吻合, 那么计算出来的hashcode也自然一致, 所以就会有散列表表的碰撞, 碰撞后就会进行一定规则继续散列
        //比如, 拉链法, 所以就不能说明某个hashcode就是对应某个对象 , 常见的场景数据结构, hashmap

        //equals和hashcode, 刚好就能证明解决两个对象是否一致, 只要equal是可以重写人为的去改动, hash函数的规则也是,
        //equals默认是对比两个对象的地址的也就是==, //https://github.com/Moosphan/Android-Daily-Interview/issues/51
    }

    public void b(){

    }

}
