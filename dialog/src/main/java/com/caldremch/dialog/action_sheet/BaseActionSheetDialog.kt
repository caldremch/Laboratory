package com.caldremch.dialog.action_sheet

import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.Group
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.caldremch.dialog.BaseDialog
import com.caldremch.dialog.DialogAnim
import com.caldremch.dialog.R
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemDragListener

/**
 *
 * @author: Caldremch
 *
 * @date: 2020/7/6
 *
 * @describe: action sheet 基类封装
 *
 * 功能:
 * 1.自定义操作sheet top style
 * 2.人性化 操作项封装 和 点击事件封装
 * 3.优秀的扩展能力和复用能力
 *
 **/

abstract class BaseActionSheetDialog(parent: Any) : BaseDialog(parent) {

    protected var adapterTop: ActionSheetAdapter? = null
    protected var adapterBottom: ActionSheetAdapter? = null
    protected var titleView: View? = null
    private lateinit var rvTop: RecyclerView
    private lateinit var rvBottom: RecyclerView
    private lateinit var topGroup: Group
    private lateinit var tvCancel: TextView
    private var iActionHeader: IActionHeader? = null

    abstract fun getData(): IData?

    var dragListener: ActionSheetDragListener? = null

    companion object {
        const val DATA_TOP = "DATA_TOP"
        const val DATA_BOTTOM = "DATA_BOTTOM"
    }

    init {
        widthScale = 1f
        gravity = Gravity.BOTTOM
        anim = DialogAnim.BOTTOM_IN_BOTTOM_OUT
    }

    override fun getLayoutId(): Int {
        return R.layout.dialog_action_sheet
    }

    override fun initView(rootView: View) {
        rvTop = rootView.findViewById(R.id.rv_top)
        rvBottom = rootView.findViewById(R.id.rv_bottom)
        tvCancel = rootView.findViewById(R.id.tv_cancel)
        topGroup = rootView.findViewById(R.id.g)
        iActionHeader = getTitleView()
        iActionHeader?.apply {
            titleView = getHeaderView()
            titleView?.apply {
                val titleLayoutParams = ConstraintLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                titleLayoutParams.bottomToTop = rvTop.id
                layoutParams = titleLayoutParams
                (rootView as ViewGroup).addView(this, titleLayoutParams)
            }
        }

    }

    override fun initData() {
        iActionHeader?.initData(getData())
        var dataTop: MutableList<BaseActionData>? = null
        var dataBottom: MutableList<BaseActionData>? = null
        arguments?.apply {
            dataTop = getParcelableArrayList(DATA_TOP)
            dataBottom = getParcelableArrayList(DATA_BOTTOM)
        }

        dataTop = dataTop ?: mutableListOf()
        dataBottom = dataBottom ?: mutableListOf()

        //如果只有一个为null, 只显示底部一个
        val hasEmpty = dataBottom!!.isEmpty() || dataTop!!.isEmpty()

        if (hasEmpty) {
            topGroup.visibility = View.GONE
            val validData = if (dataTop!!.isEmpty()) dataBottom!! else dataTop!!
            initSmart(rvBottom, validData, true)
        } else {
            initSmart(rvTop, dataTop!!, false)
            initSmart(rvBottom, dataBottom!!, true)
        }

        titleView?.apply { initTitleView(this) }
    }

    override fun initEvent() {

        tvCancel.setOnClickListener {
            dismiss()
        }

        adapterTop?.setOnItemClickListener { adapter, view, position ->
            handleClick(adapter, position)
        }

        adapterBottom?.setOnItemClickListener { adapter, view, position ->
            handleClick(adapter, position)
        }
    }


    private fun handleClick(adapter: BaseQuickAdapter<*, *>, position: Int) {
        val data = adapter.data[position] as BaseActionData
        if (data.getData().action != null) {
            data.getData().action!!.run()
        } else {
            data.onClick(mContext, getData())
        }
        dismiss()
    }

    private fun initSmart(
        rv: RecyclerView,
        data: MutableList<BaseActionData>,
        isBottom: Boolean
    ) {

        val tempAdapter = ActionSheetAdapter(data)
        //拖拽功能
        var startPos = 0
        val dragListener = object : OnItemDragListener {
            override fun onItemDragMoving(
                source: RecyclerView.ViewHolder?,
                from: Int,
                target: RecyclerView.ViewHolder?,
                to: Int
            ) {
            }

            override fun onItemDragStart(viewHolder: RecyclerView.ViewHolder?, pos: Int) {
                Log.d(TAG, "onItemDragStart: $pos")
                startPos = pos
            }

            override fun onItemDragEnd(viewHolder: RecyclerView.ViewHolder?, pos: Int) {
                Log.d(TAG, "onItemDragEnd: $pos")

                if (startPos == pos) {
                    return
                }

//                修改的是底部数据源还是顶部数据源
//                if (isBottom) {
//                    val endPos = pos
//                    dragListener?.onDragEnd(startPos, endPos, tempAdapter.data)
//                } else {
//
//                }
                dragListener?.onDragEnd(startPos, pos, tempAdapter.data)

            }

        }
        tempAdapter.draggableModule.isDragEnabled = true
        tempAdapter.draggableModule.setOnItemDragListener(dragListener)
        rv.layoutManager = getLayoutManager()
        rv.adapter = tempAdapter
        if (isBottom) {
            adapterBottom = tempAdapter
        } else {
            adapterTop = tempAdapter
        }
    }

    private fun getLayoutManager(): RecyclerView.LayoutManager? {
        val manager = LinearLayoutManager(mContext)
        manager.orientation = LinearLayoutManager.HORIZONTAL
        return manager
    }

    protected open fun getTitleView(): IActionHeader? {
        return null
    }

    protected open fun initTitleView(titleView: View) {

    }
}