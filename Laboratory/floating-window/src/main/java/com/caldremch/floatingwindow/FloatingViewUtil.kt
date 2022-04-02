package com.caldremch.floatingwindow

import android.app.Activity
import android.app.AppOpsManager
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Point
import android.net.Uri
import android.os.Build
import android.os.Process
import android.provider.Settings
import android.util.Log
import android.view.WindowManager
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method

/**
 *
 * @author Caldremch
 *
 * @email finishmo@qq.com
 *
 * @date 2022/4/1 15:44
 *
 * @description
 *
 *
 */
object FloatingViewUtil {

    private const val OP_SYSTEM_ALERT_WINDOW = 24

    /**
     * 判断是否具有悬浮窗权限
     * @param context
     * @return
     */
    fun canDrawOverlays(context: Context): Boolean {
        //android 6.0及以上的判断条件
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Settings.canDrawOverlays(context)
        } else checkOp(context, OP_SYSTEM_ALERT_WINDOW)
        //android 4.4~6.0的判断条件
    }

    private fun checkOp(context: Context, op: Int): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val manager: AppOpsManager =
                context.getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager
            val clazz: Class<*> = AppOpsManager::class.java
            try {
                val method: Method = clazz.getDeclaredMethod(
                    "checkOp",
                    Int::class.javaPrimitiveType,
                    Int::class.javaPrimitiveType,
                    String::class.java
                )
                return AppOpsManager.MODE_ALLOWED === method.invoke(
                    manager,
                    op,
                    Process.myUid(),
                    context.packageName
                ) as Int
            } catch (e: NoSuchMethodException) {
                e.printStackTrace()
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
            } catch (e: InvocationTargetException) {
                e.printStackTrace()
            }
        }
        return true
    }


    /**
     * 请求悬浮窗权限
     * @param context
     */
    fun requestDrawOverlays(context: Context) {
        val intent = Intent(
            "android.settings.action.MANAGE_OVERLAY_PERMISSION",
            Uri.parse("package:" + context.packageName)
        )
        if (context !is Activity) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        if (intent.resolveActivity(context.packageManager) != null) {
            context.startActivity(intent)
        } else {
            Log.e("FloatingViewPermissionUtil", "No activity to handle intent")
        }
    }


    /**
     * Return whether screen is landscape.
     *
     * @return `true`: yes<br></br>`false`: no
     */
    fun isLandscape(): Boolean {
        return (Utils.context.getResources().getConfiguration().orientation
                === Configuration.ORIENTATION_LANDSCAPE)
    }

    /**
     * Return whether screen is portrait.
     *
     * @return `true`: yes<br></br>`false`: no
     */
    fun isPortrait(): Boolean {
        return (Utils.context.getResources().getConfiguration().orientation
                === Configuration.ORIENTATION_PORTRAIT)
    }

    fun getAppScreenWidth(): Int {
        val wm = Utils.context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            ?: return -1
        val point = Point()
        wm.defaultDisplay.getSize(point)
        return point.x
    }


    /**
     * Return the application's height of screen, in pixel.
     *
     * @return the application's height of screen, in pixel
     */
    fun getAppScreenHeight(): Int {
        val wm = Utils.context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            ?: return -1
        val point = Point()
        wm.defaultDisplay.getSize(point)
        return point.y
    }

}