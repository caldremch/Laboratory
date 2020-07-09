package com.caldremch.laboratory.activity

import android.content.Context
import com.caldremch.widget.page.IPageDelegate
import com.caldremch.widget.page.PageWrapperView

/**
 * @author Caldremch
 * @date  2020/7/10
 * @email caldremch@163.com
 * @describe
 *
 **/
class PageManagerView<T>(var mContext: Context,
                         var pageDelegate: IPageDelegate<T>,
                         var loadingEnable: Boolean = true): PageWrapperView<T>(mContext, pageDelegate, loadingEnable) {

}