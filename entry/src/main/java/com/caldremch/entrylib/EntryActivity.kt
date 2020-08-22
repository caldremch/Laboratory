package com.caldremch.entrylib

import android.graphics.Color
import android.graphics.Rect
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.caldremch.annotation.const.EntryConst
import com.caldremch.annotation.entry.IEntry
import com.caldremch.annotation.entry.IEntryCollection
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder

/**
 *
 * @author Caldremch
 *
 * @date 2020-08-20
 *
 * @email caldremch@163.com
 *
 * @describe 入口列表界面
 *
 **/
class EntryActivity : AppCompatActivity() {

    private lateinit var entryRv: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        entryRv = RecyclerView(this)
        setContentView(entryRv)
        hackInit()
    }

    /**
     * 初始化所有继承IEntry的类到列表中
     * 功能入口列表初始化
     */
    fun hackInit() {
        val list = arrayListOf<IEntry>()
        try {
            val clz = Class.forName(EntryConst.ENTRY_NAME_SIGN)
            val instance = clz.getConstructor().newInstance()
            if (instance is IEntryCollection) {
                instance.load(list)
            }
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        }
        entryRv.layoutManager = LinearLayoutManager(this)
        entryRv.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                outRect.top = 40
            }
        })

        entryRv.adapter = EntryAdapter(list)
        (entryRv.adapter as BaseQuickAdapter<*, *>).setOnItemClickListener { adapter, view, position ->
            ((entryRv.adapter as BaseQuickAdapter<*, *>).data[position] as IEntry).onClick(context = this)
        }
    }

}

open class EntryAdapter(list: ArrayList<IEntry>) :
    BaseQuickAdapter<IEntry, BaseViewHolder>(0, list) {
    override fun onCreateDefViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val tv = TextView(parent.context)
        tv.setPadding(38, 36, 36, 36)
        tv.setBackgroundColor(Color.BLUE)
        tv.setTextColor(Color.WHITE)
        return BaseViewHolder(tv)
    }

    override fun convert(holder: BaseViewHolder, item: IEntry) {
        (holder.itemView as TextView).text = item.title
    }
}