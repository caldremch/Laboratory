package com.caldremch.utils.networklistener

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkInfo

/**
 *
 * @author Caldremch
 *
 * @date 2020-07-27
 *
 * @email caldremch@163.com
 *
 * @describe 网络监听结果信息
 *
 **/
open class ConnectInfo {
    var state: NetworkInfo.State = NetworkInfo.State.DISCONNECTED
    var type: Int? = null //wifi 3G 4G
    var typeName: String? = null //wifi 3G 4G

    companion object {
        fun handleNetInfo(context: Context, listenerType: ListenerType = ListenerType.ALL): ConnectInfo {
            val connectInfo = ConnectInfo()
            if (listenerType == ListenerType.MOBILE_DATA){
                connectInfo.type = NetworkCapabilities.TRANSPORT_CELLULAR
                connectInfo.typeName = "MOBILE_DATA"
            }else if (listenerType == ListenerType.MOBILE_DATA){
                connectInfo.type = NetworkCapabilities.TRANSPORT_WIFI
                connectInfo.typeName = "WIFI"
            }

            val cm = getConnectivityManager(context)
            // TODO: 2020/7/25 断开网络后   cm?.activeNetworkInfo?为 null
            cm?.activeNetworkInfo?.apply {
                connectInfo.state = state
                connectInfo.type = type
                connectInfo.typeName = typeName
            }
            return connectInfo
        }

        private fun getConnectivityManager(context: Context): ConnectivityManager? {
            val service = Context.CONNECTIVITY_SERVICE
            return context.getSystemService(service) as ConnectivityManager
        }
    }

    override fun toString(): String {
        return "ConnectInfo(state=${state.name}, type=$type, typeName=$typeName)"
    }


}