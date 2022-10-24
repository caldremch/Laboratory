package com.caldremch.laboratory.fragment

import android.util.Log
import android.widget.TextView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.caldremch.laboratory.R
import com.caldremch.laboratory.base.LaboratoryFragment
import com.caldremch.laboratory.databinding.FragmentViewEntryBinding
import com.caldremch.utils.UiUtils
import kotlinx.coroutines.*

/**
 *
 * @auther Caldremch
 *
 * @email finishmo@qq.com
 *
 * @date 2021/6/10 00:05
 *
 * @description
 *
2021-06-10 00:26:42.572 24681-24681/? D/BaseFragment: b.b.g.y{54c7f6f V.ED..... ......ID 0,0-0,0 #7f0801ae app:id/tv}==>: 0 dp100=275 layoutParams.width=275
2021-06-10 00:26:42.572 24681-24681/? D/BaseFragment: b.b.g.y{f40447c V.ED..... ......ID 0,0-0,0 #7f0801b0 app:id/tvWrapContent}==>: 0 dp100=275 layoutParams.width=-2
2021-06-10 00:26:42.572 24681-24681/? D/BaseFragment: b.b.g.y{7232105 V.ED..... ......ID 0,0-0,0 #7f0801af app:id/tvMatchParent}==>: 0 dp100=275 layoutParams.width=-1

2021-06-10 00:26:45.585 24681-24681/? D/BaseFragment: b.b.g.y{54c7f6f V.ED..... ........ 403,859-678,1162 #7f0801ae app:id/tv}==>: 275 dp100=275 layoutParams.width=275
2021-06-10 00:26:45.585 24681-24681/? D/BaseFragment: b.b.g.y{f40447c V.ED..... ........ 392,1564-689,1617 #7f0801b0 app:id/tvWrapContent}==>: 297 dp100=275 layoutParams.width=-2
2021-06-10 00:26:45.585 24681-24681/? D/BaseFragment: b.b.g.y{7232105 V.ED..... ........ 0,1792-1080,1845 #7f0801af app:id/tvMatchParent}==>: 1080 dp100=275 layoutParams.width=-1

通过例子可已得出的结论是, 当布局参数设置指定的宽高, 可以直接通过 layoutParams 去获取, 而不需要等待View 绘制完毕

而 layoutPramas 只有三种情况:
-2:wrap_content
-1:match_parent
具体的值: 设置了指定大小dp/px
 *
 *
 */
class ViewEntryFragment : LaboratoryFragment() {

    val binding by viewBinding(FragmentViewEntryBinding::bind)

    override val layoutId: Int
        get() = R.layout.fragment_view_entry

    override fun initView() {
        val delayTime: Long = 3000L
        printAllSizeInfo()
        GlobalScope.launch {
            delay(delayTime)
            withContext(Dispatchers.Main) {
                printAllSizeInfo()
            }
        }
    }


    private fun printAllSizeInfo() {
        with(binding) {
            printSizeInfo(tv)
            printSizeInfo(tvWrapContent)
            printSizeInfo(tvMatchParent)
        }


    }

    private fun printSizeInfo(tv: TextView) {
        val layoutParams = tv.layoutParams
        Log.d(
            TAG,
            "$tv==>: ${tv.width} dp100=${UiUtils.dp2px(100)} layoutParams.width=${layoutParams.width}"
        )
    }
}