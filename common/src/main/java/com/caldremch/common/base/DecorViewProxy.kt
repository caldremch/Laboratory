package com.caldremch.common.base

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.caldremch.common.R
import com.caldremch.common.widget.status.StatusView

/**
 *
 * @author Caldremch
 *
 * @date 2020-06-29 00:20
 *
 * @email caldremch@163.com
 *
 * @describe 承载 View
 *
 **/
class DecorViewProxy {

    private lateinit var context: Context
    private var isActivity: Boolean = true
    private var isUseLoading: Boolean = false
    private var contentViewId: Int = -1
    private var titleViewId: Int = -1
    private var container: ViewGroup? = null //fragment 使用
    private var statusView: StatusView? = null
    private var inflater: LayoutInflater? = null
    var titleView: View? = null


    class Builder {

        private lateinit var activity: Activity
        private lateinit var fragment: Fragment
        private var context: Context
        private var isActivity: Boolean = true
        private var isUseLoading: Boolean = false
        private var contentViewId: Int = -1
        private var titleViewId: Int = -1
        private var container: ViewGroup? = null //fragment 使用
        private var inflater: LayoutInflater? = null
        private var titleView: View? = null

        constructor(fragment: Fragment, inflater: LayoutInflater, container: ViewGroup?) {
            this.fragment = fragment
            context = fragment.context!!
            this.container = container
            isActivity = false
            this.inflater = inflater
        }

        constructor(activity: Activity) {
            this.activity = activity
            context = activity
            isActivity = true
        }

        fun isUseLoading(isUseLoading: Boolean): Builder {
            this.isUseLoading = isUseLoading
            return this
        }

        fun setContentViewId(contentViewId: Int): Builder {
            this.contentViewId = contentViewId
            return this
        }

        fun setTitleViewId(titleViewId: Int): Builder {
            this.titleViewId = titleViewId
            return this
        }

        fun setTitleView(titleView: View?): Builder {
            this.titleView = titleView
            return this
        }

        fun build(): DecorViewProxy {
            val proxy = DecorViewProxy()
            proxy.isUseLoading = isUseLoading
            proxy.isActivity = isActivity
            proxy.contentViewId = contentViewId
            proxy.titleViewId = titleViewId
            proxy.inflater = inflater
            proxy.context = context
            proxy.titleView = titleView
            return proxy
        }

    }


    //返回 contentView
    fun proxySetContentView(): View {

        //创建对应的contentView
        val childRootView: View
        if (isActivity) {
            childRootView = LayoutInflater.from(context).inflate(contentViewId, null)
        } else {
            childRootView = inflater!!.inflate(contentViewId, container, false)
        }

        var targetTitleView: View? = null
        var isSellConfigTitleView = false
        if (titleView == null) {
            //选寻找布局中的 titleId
            targetTitleView = childRootView.findViewById(R.id.android_common_title_view_id)
        } else {
            isSellConfigTitleView = true
            targetTitleView = titleView!!
        }


        //看看是不是单独社设置了
        if (targetTitleView == null) {
            //跟布局中没有 titleView, 看看 Activity 或者 Fragment 中是否设置了, titleViewId ,
            // 如果还是没有, 那么直接添加进入跟布局,没有 titleView
            //todo [也可设置标志, 是否需要包装状态也]
            if (titleViewId == 0) {
                return childRootView
            } else {
                //在 Activity 中设置了 titleId, 或者 设置了 TitleView
                val parentLayout = ConstraintLayout(context)

                val autoAddTitleView: View =
                    LayoutInflater.from(context).inflate(titleViewId, parentLayout, false)
                parentLayout.addView(autoAddTitleView)

                val titleParams = autoAddTitleView.layoutParams as ConstraintLayout.LayoutParams
                titleParams.startToStart = ConstraintLayout.LayoutParams.PARENT_ID
                titleParams.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
                titleParams.topToTop = ConstraintLayout.LayoutParams.PARENT_ID
                autoAddTitleView.layoutParams = titleParams
                if (autoAddTitleView.id == View.NO_ID) {
                    autoAddTitleView.id = R.id.android_common_title_view_id
                }

                val contentLayoutParams =
                    ConstraintLayout.LayoutParams(
                        ConstraintLayout.LayoutParams.MATCH_PARENT,
                        ConstraintLayout.LayoutParams.MATCH_CONSTRAINT
                    )
                contentLayoutParams.topToBottom = autoAddTitleView.id
                contentLayoutParams.startToStart = ConstraintLayout.LayoutParams.PARENT_ID
                contentLayoutParams.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
                contentLayoutParams.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID

                //将 StatusView 添加到 title 的底下
                if (isUseLoading) {
                    statusView = StatusView(context, contentViewId)
                    //将 statusView 添加到 title 底下
                    parentLayout.addView(statusView, contentLayoutParams)
                } else {
                    parentLayout.addView(childRootView, contentLayoutParams)
                }
                return parentLayout
            }

        } else {
            //仅支持ConstraintLayout形式的布局
            //title类型在childRootView中
            if (!isSellConfigTitleView) {
                if (childRootView is ConstraintLayout) {
                    //将 StatusView 添加到 title 的底下, statusView 金包含错误状态, 加载状态
                    if (isUseLoading) {
                        statusView = StatusView(context)
                        val stateViewLayoutParams = ConstraintLayout.LayoutParams(
                            ConstraintLayout.LayoutParams.MATCH_PARENT,
                            ConstraintLayout.LayoutParams.MATCH_CONSTRAINT
                        )
                        stateViewLayoutParams.topToBottom = targetTitleView.id
                        stateViewLayoutParams.startToStart = ConstraintLayout.LayoutParams.PARENT_ID
                        stateViewLayoutParams.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
                        stateViewLayoutParams.bottomToBottom =
                            ConstraintLayout.LayoutParams.PARENT_ID
                        childRootView.addView(statusView, stateViewLayoutParams)
                        return childRootView
                    } else {
                        if (context is Activity) {
                            (context as Activity).setContentView(childRootView)
                            return childRootView
                        }
                    }

                } else {
                    //如果不是ConstraintLayout
                    return childRootView
                }
            } else {
                //titleView 自己设置
                val parentLayout = ConstraintLayout(context)
                parentLayout.addView(targetTitleView)
                val titleParams = targetTitleView.layoutParams as ConstraintLayout.LayoutParams
                titleParams.width = ConstraintLayout.LayoutParams.MATCH_PARENT
                titleParams.startToStart = ConstraintLayout.LayoutParams.PARENT_ID
                titleParams.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
                titleParams.topToTop = ConstraintLayout.LayoutParams.PARENT_ID
                targetTitleView.layoutParams = titleParams
                if (targetTitleView.id == View.NO_ID) {
                    targetTitleView.id = R.id.android_common_title_view_id
                }

                val contentLayoutParams =
                    ConstraintLayout.LayoutParams(
                        ConstraintLayout.LayoutParams.MATCH_PARENT,
                        ConstraintLayout.LayoutParams.MATCH_CONSTRAINT
                    )
                contentLayoutParams.topToBottom = targetTitleView.id
                contentLayoutParams.startToStart = ConstraintLayout.LayoutParams.PARENT_ID
                contentLayoutParams.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
                contentLayoutParams.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID

                //将 StatusView 添加到 title 的底下
                if (isUseLoading) {
                    statusView = StatusView(context, contentViewId)
                    //将 statusView 添加到 title 底下
                    parentLayout.addView(statusView, contentLayoutParams)
                } else {
                    parentLayout.addView(childRootView, contentLayoutParams)
                }
                return parentLayout

            }
        }

        return ConstraintLayout(context)
    }


}