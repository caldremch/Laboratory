package com.caldremch.laboratory

import android.app.Application
import com.caldremch.common.util.CommonLog
import com.caldremch.common.util.DefaultLogger
import com.caldremch.floatingwindow.FloatingViewInitializer
import com.caldremch.floatingwindow.callback.OnFloatingViewShow
import com.caldremch.laboratory.util.FloatingViewType
import com.caldremch.utils.ActivityDelegate
import com.caldremch.utils.FileUtils
import com.caldremch.utils.ToastUtils
import com.caldremch.utils.Utils

/**
 *
 * @author Caldremch
 *
 * @date 2020-07-12 17:14
 *
 * @email caldremch@163.com
 *
 * @describe
 *
 **/
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        FloatingViewInitializer.Builder(this)
//            .setOnShow(object : OnFloatingViewShow {
//                override fun onSound(type: Int): Int {
//                    return if (type == FloatingViewType.TYPE2) R.raw.type_2 else R.raw.type_1
//                }
//            })
            .build()
        ToastUtils.init(this)
//        CrashHandler.instance.init(this)
        FileUtils.init(this)
        Utils.init(this)
        CommonLog.logger = DefaultLogger()
        CommonLog.d { "好的" }
        ActivityDelegate.init(this)
//
//        ViewBindingDelegate.iViewBinding  = object : IViewBinding {
//            override fun <F, T> getValue(
//                vbFactory: (View) -> T,
//                viewProvider: (F) -> View
//            ): ReadOnlyProperty<F, T> {
//                return viewBinding(vbFactory. viewProvider)
//            }
//
//        }
//        AppRegister.initMatrix(this)
//        ThreadStackUtils.init(this)


//       val target = filesDir.resolve("dex").resolve("1.dex")
//        if(target.exists().not()){
//            android.os.FileUtils.copy(resources.assets.open("1.dex"), FileOutputStream(target))
//        }
//        val dexClassLoader = DexClassLoader(target.absolutePath, )
    }

}