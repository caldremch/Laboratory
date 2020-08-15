package com.caldremch.utils.networklistener

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner

/**
 *
 * @author Caldremch
 *
 * @date 2020-07-27
 *
 * @email caldremch@163.com
 *
 * @describe
 *
 **/
open class NetWatchDog private constructor() : INetWorkStrategy {

    private val TAG = "NetWatchDog"
    private lateinit var strategy: INetWorkStrategy

    private var listener: NetListener? = null
    private var filter: ListenerType = ListenerType.ALL
    private lateinit var context: Context

    constructor(
        context: Context,
        filter: ListenerType = ListenerType.ALL,
                listener: NetListener?
    ) : this() {
        this.context = context
        this.listener = listener
        this.filter = filter
        initStrategy()
        //如果不是LifecycleOwner, 需要手动解除监听
        if (context is LifecycleOwner) {
            bindLifeCycle(context)
        }
    }

    /**
     * 如果只用Fragment的LifecycleOwner, 在 fragment 销毁的时候, 不会调用 ON_DESTROY 事件
     */
    constructor(
        fragment: Fragment,
        viewLifecycleOwner: LifecycleOwner, //通过fragment 的getViewLifecycleOwner()获取
        filter: ListenerType = ListenerType.ALL,
        listener: NetListener?
    ) : this() {
        this.context = fragment.context!!
        this.listener = listener
        this.filter = filter
        initStrategy()
        bindLifeCycle(viewLifecycleOwner)
    }

    private fun bindLifeCycle(owner: LifecycleOwner) {
        //生命周期监听
        owner.lifecycle.addObserver(object : DefaultLifecycleObserver {
            override fun onDestroy(owner: LifecycleOwner) {
                Log.d(TAG, "onDestroy: 取消网络监听")
                strategy.unRegister()
            }

            override fun onResume(owner: LifecycleOwner) {
            }
        })
    }

    private fun initStrategy() {
        //5.0以上不用广播
        strategy =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                AtLeastLollipop(context, listener, filter)
            } else {
                BelowLollipop(context, listener, filter)
            }
    }

    override fun unRegister() {
        Log.d(TAG, "unRegister: 取消监听")
        strategy.unRegister()
    }

    override fun register() {
        Log.d(TAG, "register: 开始监听...")
        strategy.register()
    }

}