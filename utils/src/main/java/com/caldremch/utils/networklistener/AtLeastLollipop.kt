package com.caldremch.utils.networklistener

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
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
 * @describe 5.0 以上 网络监听
 *
 * 不监听蓝牙和 usb 网络,
 *
 **/
@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
class AtLeastLollipop(
    val context: Context,
    var listener: NetListener?,
    var filter: ListenerType = ListenerType.ALL
) : INetWorkStrategy {

    //监听请求
    private var request: NetworkRequest
    var mainScope: CoroutineScope? = null
    private val builder = NetworkRequest.Builder()
    private val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
    private var networkCallback: ConnectivityManager.NetworkCallback? = null //监听回调

    private fun createCallback(): ConnectivityManager.NetworkCallback {
        networkCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                Log.d("NetWatchDog", "onAvailable: ")
                mainScope?.launch {
                    listener?.onStatus(ConnectInfo.handleNetInfo(context, filter))
                }
            }

            override fun onLost(network: Network) {
                Log.d("NetWatchDog", "onLost: ")
                mainScope?.launch {
                    listener?.onStatus(ConnectInfo.handleNetInfo(context, filter))
                }
            }

        }

        return networkCallback!!
    }


    init {
        when (filter) {

            ListenerType.ALL -> {
                builder.addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
                builder.addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            }

            ListenerType.MOBILE_DATA -> {
                builder.addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            }

            ListenerType.WIFI -> {
                builder.addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            }
        }

        builder.addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .addCapability(NetworkCapabilities.NET_CAPABILITY_NOT_RESTRICTED)

        networkCallback = createCallback()
        request = builder.build()
    }

    override fun unRegister() {
        mainScope?.cancel()
        try {
            //NetworkCallback was already unregistered
            networkCallback?.apply {
                cm?.unregisterNetworkCallback(this)

            }
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
        }
    }

    override fun register() {
        mainScope = MainScope()
        networkCallback?.apply {
            cm?.registerNetworkCallback(request, this)
        }
    }

}