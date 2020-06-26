package com.caldremch.laboratory

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.caldremch.date.DatePickDialog
import com.caldremch.date.OnDateSelectedListener
import com.caldremch.date.StringPickDialog
import com.caldremch.dialog.TestDialog
import com.caldremch.dialog.owner.ownerDialog
import com.caldremch.dialog.tipDialog
import com.caldremch.laboratory.adapter.MenuListAdapter
import com.caldremch.laboratory.bean.MenuData
import com.caldremch.pickerview.callback.OnItemSelectedListener
import com.caldremch.wheel.StringAdapter
import com.caldremch.widget.SingleSelectAdapter
import com.caldremch.widget.single.*
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {

    private var simpleDateFormat = SimpleDateFormat("YYYY-MM-dd HH:mm:ss", Locale.CHINA)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val context = this
        initMenuList()
        initSingleView()



        wv.post {

            val stringList = mutableListOf<String>(
                "超强",
                "我只看优秀",
                "只看中等",
                "我啥都要",
                "不可能不可能不可能",
                "超强",
                "我只看优秀",
                "只看中等",
                "我啥都要",
                "不可能不可能不可能"
            )
            stringPickDialog.setData(stringList)
            stringPickDialog.listener = object : OnItemSelectedListener {
                override fun onItemSelected(index: Int) {
                    Log.d("tag", "index=$index")
                }

            }
            val adapter = StringAdapter()
            adapter.data.clear()
            adapter.data.addAll(stringList)
            wv.setAdapter(adapter)
        }

        wv.listener = object : OnItemSelectedListener {
            override fun onItemSelected(index: Int) {
                Toast.makeText(context, "index = $index", Toast.LENGTH_SHORT).show()
            }
        }

        dpv.listener = object : OnDateSelectedListener {
            override fun onItemSelected(year: Int, month: Int, day: Int) {
//                tvDate.text = year
            }

        }
    }

    private fun initMenuList() {
        val adapter = MenuListAdapter()
        rvMenu.layoutManager = LinearLayoutManager(this)
        rvMenu.adapter = adapter
        adapter.setOnItemClickListener { adapter, view, position ->
            (adapter.data[position] as MenuData).runnable?.run()
        }
        setMenuData()
    }

    private fun setMenuData() {
        val menuList = arrayListOf<MenuData>()
        ConfigMenuUtils.setSetMenuData(this, menuList)
        (rvMenu.adapter as BaseQuickAdapter<MenuData, *>).setList(menuList)

    }

    private lateinit var singleAdapter: SingleAdapter
    private fun initSingleView() {

        ssrv.layoutManager = GridLayoutManager(this, 3)
        val stringList =
            arrayListOf<StringItem>(StringItem("出租"), StringItem("出售"), StringItem("租售"))
        stringList[0].isSelect = true
        singleAdapter = SingleAdapter(stringList, ssrv)
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

    val dialog by lazy {
        DatePickDialog(this)
    }
    val testDialg by lazy {
        TestDialog(this)
    }

    val stringPickDialog by lazy {
        StringPickDialog(this)
    }

    var i = 1;


    fun start(view: View) {
    }

    fun tost(view: View) {
    }

    fun setDatess(view: View) {
        var stringPickDialog = StringPickDialog(this)
        if (!stringPickDialog.isShowing) {
            stringPickDialog.show()
        }
    }


}
