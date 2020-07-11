package com.caldremch.widget.page

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.view.View
import androidx.fragment.app.Fragment
import com.caldremch.widget.page.base.IPageOperator

/**
 * @author Caldremch
 * @date  2020/7/9
 * @email caldremch@163.com
 * @describe
 *
 **/
@SuppressLint("ViewConstructor")
class PageManager<T> private constructor(
    mContext: Context,
    pageDelegate: IPageDelegate<T>,
    loadingEnable: Boolean = false
) : PageWrapper<T>(mContext, pageDelegate, loadingEnable){

    //通过Builder约束
    class Builder<T> {

        private var activity: Activity? = null
        private var fragment: Fragment? = null
        private var loadingEnable: Boolean = false
        private var context: Context
        private var pageDelegate: IPageDelegate<T>

        constructor(activity: Activity) {
            if (activity !is IPageDelegate<*>) {
                throw RuntimeException("activity must be implement IPageDelegate")
            }
            this.activity = activity
            context = activity
            pageDelegate = activity as IPageDelegate<T>
        }

        constructor(fragment: Fragment) {
            if (fragment !is IPageDelegate<*>) {
                throw RuntimeException("fragment must be implement IPageDelegate")
            }
            this.fragment = fragment
            context = fragment.context!!
            pageDelegate = fragment as IPageDelegate<T>
        }


        fun setLoadEnable(enable: Boolean): Builder<T> {
            this.loadingEnable = enable
            return this
        }

        fun build(): IPageOperator<T> {
            val pageManager = PageManager(context, pageDelegate, loadingEnable)
            return pageManager
        }
    }

    init {

    }

    override fun getLoading(): IPageLoading? {
        return DefaultLoadingView(context)
    }

    override fun getStatusView(): View? {
        return DefaultEmptyView(context)
    }

}