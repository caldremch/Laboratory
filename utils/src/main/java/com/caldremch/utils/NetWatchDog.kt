package com.caldremch.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi

/**
 *
 * @author Caldremch
 *
 * @date 2020-07-03 11:49
 *
 * @email caldremch@163.com
 *
 * @describe 监听狗
 *
 **/
@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
class NetWatchDog(var context: Context) : ConnectivityManager.NetworkCallback() {

    private val TAG = "NetWatchDog"

    private lateinit var networkRequest: NetworkRequest
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    init {
        networkRequest = NetworkRequest.Builder()
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .build()
    }

    override fun onAvailable(network: Network) {
        Log.d(TAG, "onAvailable: ")
    }

    override fun onUnavailable() {
        Log.d(TAG, "onUnavailable: ")
    }

    fun start() {
        connectivityManager.registerNetworkCallback(networkRequest, this)
    }

    fun stop() {
        connectivityManager.unregisterNetworkCallback(this)
    }

}