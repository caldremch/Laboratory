package com.caldremch.floatingwindow

import android.app.Application
import com.caldremch.floatingwindow.callback.OnFloatingViewShow
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
        private var onShow: OnFloatingViewShow? = null

        fun disableFloatingViewIn(clzName: String): Builder {
            FloatingViewManager.disableFloatingViewIn(clzName)
            return this
        }

        fun setOnShow(show: OnFloatingViewShow?): Builder {
            this.onShow = show
            return this
        }


        fun build() {
            InternalFloatingViewInitializer.onShow = onShow
            //开始初始化逻辑
            InternalFloatingViewInitializer.init(app)
        }
    }

}