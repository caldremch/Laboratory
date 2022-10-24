package com.caldremch.common.base

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

/**
 *
 * @author Caldremch
 *
 * @date 2020-06-28 22:49
 *
 * @email caldremch@163.com
 *
 * @describe
 *
 **/
open class LifeCycleLogFragment : Fragment() {

    object DebugUtils {
        fun buildShortClassTag(cls: Any?): String {
            val out = StringBuilder()
            if (cls == null) {
                out.append("null")
            } else {
                var simpleName = cls.javaClass.simpleName
                if (simpleName == null || simpleName.length <= 0) {
                    simpleName = cls.javaClass.name
                    val end = simpleName.lastIndexOf('.')
                    if (end > 0) {
                        simpleName = simpleName.substring(end + 1)
                    }
                }
                out.append(simpleName)
                out.append('{')
                out.append(Integer.toHexString(System.identityHashCode(cls)))
                out.append('}')
            }

            return out.toString()
        }
    }

    private var shadowInstanceString: String? = null

    init {
        shadowInstanceString = DebugUtils.buildShortClassTag(this)
    }

    private val TAG = "LifeCycleLogFragment"

    protected fun isPrintLifeCycle(): Boolean {
        return true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        d("onDestroyView: ")
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        d("onCreateView: ")
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        d("onViewCreated: ")
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        d("onActivityCreated: ")
    }

    private fun d(log: String) {
        if (isPrintLifeCycle()) {
            Log.d(TAG, "${shadowInstanceString}-->" + log)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        d("onCreate: ")
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        d("onConfigurationChanged: ")
    }

    override fun onStart() {
        super.onStart()
        d("onStart: ")
    }

    override fun onStop() {
        super.onStop()
        d("onStop: ")
    }

    override fun onDestroy() {
        super.onDestroy()
        d("onDestroy: ")
    }

    override fun onPause() {
        super.onPause()
        d("onPause: ")
    }

    override fun onResume() {
        super.onResume()
        d("onResume: ")
    }

}