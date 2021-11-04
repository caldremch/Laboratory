package com.caldremch.laboratory.util

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.caldremch.laboratory.R

/**
 *
 * @author Caldremch
 *
 * @date 2020-07-04 13:47
 *
 * @email caldremch@163.com
 *
 * @describe
 *
 **/
object FragmentUtil {

    fun add(context: Context, fragment: Fragment) {
        val activity = context as AppCompatActivity
        val manager = activity.supportFragmentManager
        val tran = manager.beginTransaction()
//        tran.setCustomAnimations(R.anim.cm_fragemnt_in, R.anim.cm_fragemnt_out)
        tran.add(android.R.id.content, fragment)
        tran.commit()
    }

    fun add(target: Fragment, fragment: Fragment) {
        val manager = target.childFragmentManager
        val tran = manager.beginTransaction()
        tran.add(fragment, fragment.javaClass.simpleName)
        tran.commit()
    }

    fun remove(context: Context, fragment: Fragment) {
        val manager = (context as AppCompatActivity).supportFragmentManager.beginTransaction()
//        manager.setCustomAnimations(R.anim.cm_fragemnt_in, R.anim.cm_fragemnt_out)
        manager.remove(fragment)
        manager.commit()
    }

}