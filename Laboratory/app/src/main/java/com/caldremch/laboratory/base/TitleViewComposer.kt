package com.caldremch.android.common.sample.biz

import com.caldremch.common.base.ITitleInit
import com.hjq.bar.OnTitleBarListener
import com.hjq.bar.TitleBar

/**
 * Created by Leon on 2022/8/20
 */
object TitleViewComposer {
    fun initTitle(titleBar: TitleBar, iTitleInit: ITitleInit) {
        iTitleInit.titleBackground?.let { titleBar.setBackgroundColor(it) }
        iTitleInit.rightTitle?.let { titleBar.rightTitle = it }
        iTitleInit.leftIcon?.let { titleBar.setLeftIcon(it) }
        iTitleInit.titleColor?.let { titleBar.setTitleColor(it) }
        iTitleInit.titleBarTitle.let { titleBar.setTitle(it) }
        titleBar.setOnTitleBarListener(object : OnTitleBarListener {
            override fun onLeftClick(titleBar: TitleBar) {
                iTitleInit.onLeftClick(titleBar)
            }

            override fun onRightClick(titleBar: TitleBar) {
                iTitleInit.onRightClick(titleBar)
            }
        })
    }
}