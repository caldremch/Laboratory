package com.caldremch.laboratory.action

import com.caldremch.dialog.action_sheet.IData

/**
 * @author: Caldremch
 * @date: 2020/7/10
 * @describe:
 */
class TestBundleData(
    var title: String = "1",
    var title2: String = "2",
    var title3: String = "我是标题3啊"
) : IData {
    override fun sheetType(): Int {
        return 2;
    }
}