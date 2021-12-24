package com.caldremch.dialog

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment

/**
 * @author Caldremch
 * @date 2020-06-26 15:58
 * @email caldremch@163.com
 * @describe
 */
open class LifeDialogFragment : DialogFragment() {

    val TAG = "LifeDialogFragment"

    private fun log(tagString: String, string: String) {
        if (BuildConfig.DEBUG) {
            Log.d(tagString, string)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        log(TAG, "onAttach: ")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        log(TAG, "onCreate: ")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        log(TAG, "onCreateView: ")
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        log(TAG, "onViewCreated: ")
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        log(TAG, "onActivityCreated: ")
    }

    override fun onStart() {
        super.onStart()
        log(TAG, "onStart: ")
    }

    override fun onResume() {
        super.onResume()
        log(TAG, "onResume: ")
    }

    override fun onPause() {
        super.onPause()
        log(TAG, "onPause: ")
    }

    override fun onStop() {
        super.onStop()
        log(TAG, "onStop: ")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        log(TAG, "onDestroyView: ")
    }

    override fun onDestroy() {
        super.onDestroy()
        log(TAG, "onDestroy: ")
    }

    override fun onDetach() {
        super.onDetach()
        log(TAG, "onDetach: ")
    }
}