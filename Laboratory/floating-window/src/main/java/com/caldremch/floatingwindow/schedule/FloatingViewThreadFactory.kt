package com.caldremch.floatingwindow.schedule

import java.util.concurrent.ThreadFactory

/**
 *
 * @author Caldremch
 *
 * @email finishmo@qq.com
 *
 * @date 2022/4/2 16:53
 *
 * @description
 *
 *
 */
class FloatingViewThreadFactory : ThreadFactory {
    override fun newThread(p0: Runnable?): Thread {
        val t  = Thread(p0, "pool-floating-schedule-thread-")
        return t
    }
}