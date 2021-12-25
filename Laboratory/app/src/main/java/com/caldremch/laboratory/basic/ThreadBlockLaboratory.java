package com.caldremch.laboratory.basic;

/**
 * @author Caldremch
 * @date 2021/12/24
 * @email caldremch@163.com
 * @describe  参考: https://www.cnblogs.com/aspirant/p/11470858.html
 **/
public class ThreadBlockLaboratory {

    private int a = 1;

    /**
     * 1.5之前, synchronized它还是重量级的锁, 1.6以后进行了优化,不在那么重
     * 优化、锁优化机制、锁的存储结构
     *
     * synchronized是什么: 可以实现线程同步操作
     * synchronized的作用:
     * 1.保证操作的原子性: 意思就是一旦进入操作, 知道操作结束, 其他线程都无法干预(进入), 直到操作完成, 也就是常说的互斥访问
     * 2.可见性: 在java的内存模型中, 每个线程(子线程)都有自己执行的内存副本, 而数据来源是主存(当前线程/主线程), 如果一个主线程中含有多个子线程
     * 对主线程中的数据进行操作的时候, 它们其实是操作自己 内存的副本, 为什么保证共享变量的修改都及时看到, 需要
     *
     * “对一个变量unlock操作之前，必须要同步到主内存中；如果对一个变量进行lock操作，则将会清空工作内存中此变量的值，在执行引擎使用此变量前，需要重新从主内存中load操作或assign操作初始化变量值”
     *
     * 3.有序性: 有效的解决重排序的问题(编译优化), 及即 “一个unlock操作先行发生(happen-before)于后面对同一个锁的lock操作”, 这点后面结合指定详细说明
     *
     *
     *synchronized void b();
     *     descriptor: ()V
     *     flags: ACC_SYNCHRONIZED //根据ACC_SYNCHRONIZED标识符判断是否设置了访问标志, 如果设置了了访问标识符, 那么就先获取monitor, 并且执行方法体, 完事后释放monitor , monitor内容在下面讲
     *     Code:
     *       stack=2, locals=1, args_size=1
     *          0: aload_0
     *          1: iconst_2
     *          2: putfield      #2                  // Field a:I
     *          5: return
     *       LineNumberTable:
     *         line 28: 0
     *         line 29: 5
     *       LocalVariableTable:
     *         Start  Length  Slot  Name   Signature
     *             0       6     0  this   Lcom/caldremch/laboratory/basic/ThreadBlockLaboratory;
     *
     *
     *
     *
     */
    synchronized void b(){
        a=2;
    }


    /**
     * 案例分析:
     * 此时c拿到了对象锁, 准备执行b, 但是b也需要对象锁, 所以b等待c释放锁,
     * 但是c必须执行完才能释放锁, 这就造成了死锁了, b永远也等不到c释放, 因为c中的b一直无法执行
     *
     */
    synchronized void c(){
        a=3;
        b();
    }


    /**
     * 基本知识: 可以把任意一个非null对象作为"锁", 在HotSpot JVM实现中, 锁有个住啊们的名字:
     * "对象监视器"(Object Monitor)
     * 同步原理:
     * synchronized在软件层面依赖JVM的指令实现, 而JVM的monitor指令依赖硬件层面特殊的CPU指令
     *
     *
     * d方法对应的JVM指令:
     *
     *
     *  public void d();
     *     descriptor: ()V
     *     flags: ACC_PUBLIC
     *     Code:
     *       stack=2, locals=3, args_size=1
     *          0: aload_0  //this装载到了操作数栈中
     *          1: dup    //复制栈顶数值，并压入栈顶
     *          2: astore_1 //对象中待处理的局部变量的位置: 1 , 把对象的第一个局部变量装载到操作数栈中
     *          //1.如果monitor的进入数为0，则该线程进入monitor，然后将进入数设置为1，该线程即为monitor的所有者
     *          //2.如果线程已经占有该monitor，只是重新进入，则进入monitor的进入数加1；
     *          //3.如果其他线程已经占用了monitor，则该线程进入阻塞状态，直到monitor的进入数为0，再重新尝试获取monitor的所有权；
     *          3: monitorenter
     *          4: aload_0 //对象this入栈
     *          5: iconst_4 //int型常量4进栈
     *          6: putfield      #2 //给对象的字段赋值
     *          9: aload_1  //	对象的第一个局部变量(a)入栈, 出栈时, 就是 aload_1 -> putfield -> 4 就是a=4的赋值操作了
     *         10: monitorexit //指令执行时，monitor的进入数减1，如果减1后进入数为0，那线程退出monitor，不再是这个monitor的所有者。其他被这个monitor阻塞的线程可以尝试去获取这个 monitor 的所有权。
     *         11: goto          19 //return
     *         14: astore_2  //对象的第2个局部变量入栈
     *         15: aload_1 //对象的第一个局部变量(a)入栈,
     *         16: monitorexit //该指令是用于处理异常时的锁退出机制
     *         17: aload_2 //对象的第2个局部变量入栈,用于
     *         18: athrow //抛异常指令入栈
     *         19: return
     *
     *
     */
    public void d(){
        synchronized (this){
            a=4;
        }
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
