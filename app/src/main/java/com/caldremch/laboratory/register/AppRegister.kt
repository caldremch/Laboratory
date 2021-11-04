//package com.caldremch.laboratory.register
//
//import android.app.Application
//import android.content.Context
//import android.content.Intent
//import android.net.Uri
//import android.os.Build
//import android.os.Handler
//import android.os.Looper
//import android.provider.Settings
//import com.caldremch.utils.ActivityDelegate
//import com.tencent.matrix.Matrix
//import com.tencent.matrix.trace.TracePlugin
//import com.tencent.matrix.trace.config.TraceConfig
//import com.tencent.matrix.trace.constants.Constants
//import com.tencent.matrix.trace.listeners.IDoFrameListener
//import com.tencent.matrix.trace.view.FrameDecorator
//import com.tencent.matrix.util.MatrixLog
//import java.util.concurrent.Executor
//
///**
// *
// * @author Caldremch
// *
// * @date 2021-02-04 10:56
// *
// * @email caldremch@163.com
// *
// * @describe
// *
// **/
//object AppRegister {
//
//
//    private lateinit var decorator: FrameDecorator
//
//    private fun canDrawOverlays(context: Context): Boolean {
//        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            Settings.canDrawOverlays(context)
//        } else {
//            true
//        }
//    }
//
//    private fun requestWindowPermission(context: Context) {
//        Handler(Looper.getMainLooper()).postDelayed(Runnable {
//            if (canDrawOverlays(application).not()) {
//                val intent = Intent(
//                    Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
//                    Uri.parse("package:" + application.packageName)
//                )
//                ActivityDelegate.with().start(intent) {
//                    if (it) {
//                        decorator = FrameDecorator.getInstance(application)
//                        if (decorator.isShowing.not()) {
//                            decorator.show()
//                        }
//                    }
//                }
//            }
//        }, 1000)
//    }
//
//    private lateinit var application: Application
//
//    fun initMatrix(application: Application) {
//        this.application = application
//
//
//        val builder: Matrix.Builder = Matrix.Builder(application) // build matrix
//
//        builder.patchListener(TestPluginListener(application)) // add general pluginListener
//
//        val dynamicConfig = DynamicConfigImplDemo() // dynamic config
//        val traceConfig = TraceConfig.Builder()
//            .dynamicConfig(dynamicConfig)
//            .enableFPS(true)
//            .enableAnrTrace(true)
//            .enableStartup(true)
//            .enableEvilMethodTrace(true)
//            .isDebug(true)
//            .isDevEnv(false)
//            .build()
//
//        val trancePlugin = TracePlugin(traceConfig)
//
//
//        //add to matrix
//        builder.plugin(trancePlugin)
//
//        //init matrix
//
//        //init matrix
//        Matrix.init(builder.build())
//
//        // start plugin
//
//        startFpsDetector()
//
//        if (!canDrawOverlays(AppRegister.application)) {
//            requestWindowPermission(AppRegister.application)
//        } else {
//            decorator = FrameDecorator.getInstance(AppRegister.application)
//            decorator.show()
//        }
//    }
//
//
//    private val mDoFrameListener: IDoFrameListener = object : IDoFrameListener(object : Executor {
//        var handler: Handler =
//            Handler(Looper.getMainLooper())
//
//        override fun execute(command: Runnable) {
//            handler.post(command)
//        }
//    }) {
//        override fun doFrameAsync(
//            focusedActivity: String,
//            startNs: Long,
//            endNs: Long,
//            dropFrame: Int,
//            isVsyncFrame: Boolean,
//            intendedFrameTimeNs: Long,
//            inputCostNs: Long,
//            animationCostNs: Long,
//            traversalCostNs: Long
//        ) {
//            super.doFrameAsync(
//                focusedActivity,
//                startNs,
//                endNs,
//                dropFrame,
//                isVsyncFrame,
//                intendedFrameTimeNs,
//                inputCostNs,
//                animationCostNs,
//                traversalCostNs
//            )
//            MatrixLog.i(
//                " Matrix",
//                "[doFrameAsync]" + " costMs=" + (endNs - intendedFrameTimeNs) / Constants.TIME_MILLIS_TO_NANO + " dropFrame=" + dropFrame + " isVsyncFrame=" + isVsyncFrame + " offsetVsync=" + (startNs - intendedFrameTimeNs) / Constants.TIME_MILLIS_TO_NANO + " [%s:%s:%s]",
//                inputCostNs,
//                animationCostNs,
//                traversalCostNs
//            )
//        }
//    }
//
//    private fun startFpsDetector() {
////        Matrix.with().getPluginByClass(TracePlugin::class.java).frameTracer.onStartTrace()
//        Matrix.with().startAllPlugins()
////        Matrix.with().getPluginByClass(TracePlugin::class.java).frameTracer.addListener(mDoFrameListener)
//    }
//}