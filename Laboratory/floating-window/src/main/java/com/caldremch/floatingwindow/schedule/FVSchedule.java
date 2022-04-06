package com.caldremch.floatingwindow.schedule;

import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.util.Log;

import com.caldremch.floatingwindow.FloatingIntent;
import com.caldremch.floatingwindow.FloatingViewLauncher;

import java.util.LinkedList;

/**
 * @author Caldremch
 * @email finishmo@qq.com
 * @date 2022/4/2 17:39
 * @description
 */
public class FVSchedule {

    private volatile LinkedList<FloatingIntent> messageQueue = new LinkedList<>();
    public final static Handler ANDROID_MAIN_EXECUTOR = new Handler(Looper.getMainLooper());

    private final static String TAG = "FloatingViewTAG";

    public FVSchedule() {
        FloatingViewThreadFactory factory = new FloatingViewThreadFactory();
        Thread thread = factory.newThread(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, ": 浮窗消息线程启动: " + Thread.currentThread().getName());
                for (; ; ) {
                    if (messageQueue.isEmpty()) {
                        continue;
                    }
                    FloatingIntent message = messageQueue.peek();
                    if (message == null) continue;
//                    long now = SystemClock.uptimeMillis();
//                    if (now < message.getWhen()) {
//                        try {
//                            Thread.sleep(message.getWhen());
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                    }
                    //阻塞超时后, 继续执行
                    Log.d(TAG, "run: 执行展示弹窗");
                    ANDROID_MAIN_EXECUTOR.post(new Runnable() {
                        @Override
                        public void run() {
                            FloatingViewLauncher.INSTANCE.launchFloating(message.getTargetClass(), message.getBundle());
                        }
                    });
                    //出队
                    messageQueue.poll();
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.start();
    }

    public void enqueue(FloatingIntent intent) {
        messageQueue.offer(intent);
        Log.d(TAG, "enqueue: " + messageQueue.size());
    }
}
