package com.caldremch.floatingwindow

import android.app.Application
import com.caldremch.floatingwindow.schedule.FloatingViewThreadFactory
import java.util.concurrent.Executors

/**
 *
 * @author Caldremch
 *
 * @email finishmo@qq.com
 *
 * @date 2022/4/2 11:21
 *
 * @description
 *
 *
 */
class FloatingViewInitializer {

    class Builder(private val app: Application) {
        fun build() {
            //开始初始化逻辑
            InternalFloatingViewInitializer.init(app)
        }
    }

}