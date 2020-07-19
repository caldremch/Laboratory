package com.caldremch.laboratory

import android.graphics.Color
import android.graphics.Rect
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.caldremch.dialog.tipDialog
import com.caldremch.laboratory.entry.entry.IEntry
import com.caldremch.utils.KBObserver
import com.caldremch.widget.single.*
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import dalvik.system.DexFile
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        KBObserver.init(this)
        initSingleView()
        hackInit()
    }

    private lateinit var singleAdapter: SingleAdapter
    private fun initSingleView() {

        ssrv.layoutManager = GridLayoutManager(this, 3)
        val stringList =
            arrayListOf<StringItem>(
                StringItem("标题1"),
                StringItem("标题2"),
                StringItem("标题3")
            )
        stringList[0].isSelect = true
        singleAdapter = SingleAdapter(stringList, ssrv)
        singleAdapter.selectedPos = 0
        ssrv.addItemDecoration(SingleItemDecoration())
        ssrv.adapter = singleAdapter

        singleAdapter.interruptISelectListener = object : ISelectListener.OnInterrupt {

            override fun onInterrupt(
                selectedPos: Int,
                holder: RecyclerView.ViewHolder,
                currentPosition: Int,
                cacheOperation: CacheOperation<*, *>
            ) {
                tipDialog {
                    titleText = "我是标题"
                    titleColorRes = R.color.colorPrimary
                    descText = "我是内容啊"
                    descColorStr = "#3282EF"
                    descBold = true
                    descSize = 20f
                    leftText = "取消"
                    leftBold = true
                    leftColorStr = "#3282EF"
                    rightText = "确定啊"
                    rightColorRes = R.color.colorAccent
                    gravity = Gravity.CENTER
                    widthScale = 0.74f
                    cancelOutSide = false
                    leftClick {

                    }
                    rightClick {
                        cacheOperation.goOn()
                        Toast.makeText(this@MainActivity, "点击右边了", Toast.LENGTH_SHORT).show()
                    }
                }
            }

        }
    }

    /**
     * 初始化所有继承IEntry的类到列表中
     * 功能入口列表初始化
     */
    fun hackInit() {
        val list = arrayListOf<IEntry>()
        val flagInterface = IEntry::class.java
        if (flagInterface.isInterface) {
            val packName = flagInterface.`package`!!.name
            val dexFile = DexFile(packageCodePath)
            val enumeration = dexFile.entries()
            while (enumeration.hasMoreElements()) {
                val className = enumeration.nextElement();
                if (className.contains(packName) && className != flagInterface.name) {
                    val clz = Class.forName(className)
                    list.add(clz.newInstance() as IEntry)
                }
            }
        }
        rv.layoutManager = LinearLayoutManager(this)
        rv.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                outRect.top = 40
            }
        })
        rv.adapter = object : BaseQuickAdapter<IEntry, BaseViewHolder>(0, list) {
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
        (rv.adapter as BaseQuickAdapter<*, *>).setOnItemClickListener { adapter, view, position ->
            ((rv.adapter as BaseQuickAdapter<*, *>).data[position] as IEntry).onClick(context = this)
        }
    }

}
