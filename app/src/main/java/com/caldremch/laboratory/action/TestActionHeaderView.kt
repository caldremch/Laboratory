package com.caldremch.laboratory.action

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import com.caldremch.dialog.action_sheet.IActionHeader
import com.caldremch.dialog.action_sheet.IData
import com.caldremch.laboratory.R

/**
 *
 * @author: Caldremch
 *
 * @date: 2020/7/10
 *
 * @describe:
 *
 **/

class TestActionHeaderView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr), IActionHeader {

    init {
        View.inflate(context, R.layout.action_sheet_title, this)
    }

    override fun initData(data: IData?) {
        if (data is TestBundleData) {
            val bundleData = data as TestBundleData
            findViewById<Button>(R.id.btn_view).text = bundleData.title3
        }
    }

    override fun getHeaderView(): View {
        return this
    }
}