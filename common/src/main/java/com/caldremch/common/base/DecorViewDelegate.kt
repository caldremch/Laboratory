package com.caldremch.common.base

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import com.caldremch.common.R
import com.caldremch.common.widget.status.IStatusView
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
class DecorViewDelegate @JvmOverloads constructor(
    context: Context,
    private val isUseLoading: Boolean = false,
    private val contentViewId: Int = 0,
    private val titleViewId: Int = 0,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), IStatusView {


    private var statusView: StatusView? = null

    fun proxySetContentView(): ViewGroup {

        val childRootView = LayoutInflater.from(context).inflate(contentViewId, null)

        //选寻找布局中的 titleId
        val titleView = childRootView.findViewById<View>(R.id.android_common_title_view_id)
        //看看是不是单独社设置了
        if (titleView == null) {
            //跟布局中没有 titleView, 看看 Activity 或者 Fragment 中是否设置了, titleViewId ,
            // 如果还是没有, 那么直接添加进入跟布局,没有 titleView
            //todo [也可设置标志, 是否需要包装状态也]
            if (titleViewId == 0) {
                if (context is Activity) {
                    (context as Activity).setContentView(childRootView)
                    return childRootView as ViewGroup
                }
            } else {
                //在 Activity 中设置了 titleId, 或者 设置了 TitleView
                val autoAddTitleView: View =
                    LayoutInflater.from(context).inflate(titleViewId, this, false)
                addView(autoAddTitleView)


                val titleParams = autoAddTitleView.layoutParams as LayoutParams
                titleParams.startToStart = LayoutParams.PARENT_ID
                titleParams.endToEnd = LayoutParams.PARENT_ID
                titleParams.topToTop = LayoutParams.PARENT_ID
                autoAddTitleView.layoutParams = titleParams
                if (autoAddTitleView.id == View.NO_ID) {
                    autoAddTitleView.id = R.id.android_common_title_view_id
                }

                val contentLayoutParams =
                    LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_CONSTRAINT)
                contentLayoutParams.topToBottom = autoAddTitleView.id
                contentLayoutParams.startToStart = LayoutParams.PARENT_ID
                contentLayoutParams.endToEnd = LayoutParams.PARENT_ID
                contentLayoutParams.bottomToBottom = LayoutParams.PARENT_ID

                //将 StatusView 添加到 title 的底下
                if (isUseLoading) {
                    statusView = StatusView(context, contentViewId)
                    //将 statusView 添加到 title 底下
                    addView(statusView, contentLayoutParams)
                } else {
                    addView(childRootView, contentLayoutParams)
                }

                if (context is Activity) {
                    (context as Activity).setContentView(this)
                    return this
                }
            }

        } else {
            //仅支持ConstraintLayout形式的布局
            //title类型在childRootView中
            if (childRootView is ConstraintLayout) {
                //将 StatusView 添加到 title 的底下, statusView 金包含错误状态, 加载状态

                if (isUseLoading) {
                    addView(childRootView)
                    statusView = StatusView(context)
                    val stateViewLayoutParams = LayoutParams(
                        LayoutParams.MATCH_PARENT,
                        LayoutParams.MATCH_CONSTRAINT
                    )
                    stateViewLayoutParams.topToBottom = titleView.id
                    stateViewLayoutParams.startToStart = LayoutParams.PARENT_ID
                    stateViewLayoutParams.endToEnd = LayoutParams.PARENT_ID
                    stateViewLayoutParams.bottomToBottom = LayoutParams.PARENT_ID
                    addView(statusView, stateViewLayoutParams)
                    if (context is Activity) {
                        (context as Activity).setContentView(this)
                        return this
                    }
                    statusView?.setViewStatus(StatusView.VIEW_STATE_ERROR)
                } else {
                    if (context is Activity) {
                        (context as Activity).setContentView(childRootView)
                        return childRootView
                    }
                }

            } else {
                //如果不是ConstraintLayout
                if (context is Activity) {
                    (context as Activity).setContentView(childRootView)
                    return childRootView as ViewGroup
                }
            }
        }

        return this
    }

    override fun setViewStatus(status: Int) {
        if (isUseLoading) {
            statusView?.setViewStatus(status)
        }
    }

}