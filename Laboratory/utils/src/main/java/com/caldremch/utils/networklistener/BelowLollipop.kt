package com.caldremch.utils.networklistener

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

/**
 *
 * @author Caldremch
 *
 * @date 2020-07-27
 *
 * @email caldremch@163.com
 *
 * @describe 5.0 以下
 *
 * todo 4.4版本未测试
 *
 **/
class BelowLollipop(
    val context: Context,
    var listener: NetListener?,
    var filter: ListenerType = ListenerType.ALL
) : INetWorkStrategy {

    var mainScope : CoroutineScope? = null

    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            mainScope?.launch {
                listener?.onStatus(ConnectInfo.handleNetInfo(context, filter))
            }
        }
    }

    private val intentFilter = IntentFilter().apply {
        addAction(ConnectivityManager.CONNECTIVITY_ACTION)
    }


    init {
//        register()
    }

    override fun unRegister() {
        mainScope?.cancel()
        context.unregisterReceiver(receiver)
    }

    override fun register() {
        mainScope = MainScope()
        context.registerReceiver(receiver, intentFilter)
    }
}