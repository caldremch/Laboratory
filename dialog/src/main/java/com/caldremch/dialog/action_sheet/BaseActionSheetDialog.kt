package com.caldremch.dialog.action_sheet

import android.annotation.SuppressLint
import android.app.Service
import android.content.Context
import android.graphics.Rect
import android.os.Vibrator
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
import com.caldremch.utils.UiUtils
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

abstract class BaseActionSheetDialog(context: Context) : BaseDialog(context) {

    protected var adapterTop: ActionSheetAdapter? = null
    protected var adapterBottom: ActionSheetAdapter? = null
    protected var mTitleView: View? = null
    private var actionHeader: IActionHeader? = null
    private lateinit var rvTop: RecyclerView
    private lateinit var rvBottom: RecyclerView
    private lateinit var topGroup: Group
    private lateinit var tvCancel: TextView
    protected var dragEnable: Boolean = false

    /**
     * 拖拽监听
     */
    protected var dragListener: ActionSheetDragListener? = null

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
        return R.layout.dialog_base_action_sheet
    }

    private class ItemDecor : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            parent.adapter?.apply {
                if (itemCount - 1 == parent.getChildLayoutPosition(view)) {
                    outRect.right = UiUtils.dp2px(16f)
                }
            }
        }
    }

    override fun initView(rootView: View) {
        rvTop = rootView.findViewById(R.id.rv_top)
        rvBottom = rootView.findViewById(R.id.rv_bottom)
        tvCancel = rootView.findViewById(R.id.tv_cancel)
        topGroup = rootView.findViewById(R.id.g)

        rvBottom.addItemDecoration(ItemDecor())
        rvTop.addItemDecoration(ItemDecor())

        //初始化Header
        actionHeader = getActionHeader()
        actionHeader?.apply {
            mTitleView = getHeaderView()
            mTitleView?.apply {
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
        actionHeader?.initData(getData())
        mTitleView?.apply { initTitleView(this) }

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

    //点击事件, 优先action, 而后onClick
    private fun handleClick(adapter: BaseQuickAdapter<*, *>, position: Int) {
        val data = adapter.data[position] as BaseActionData
        if (data.getData().action != null) {
            data.getData().action!!.run()
        } else {
            data.onClick(mContext, getData())
        }
        dismiss()
    }

    //初始化穿件adapter
    private fun initSmart(
        rv: RecyclerView,
        data: MutableList<BaseActionData>,
        isBottom: Boolean
    ) {

        val tempAdapter = ActionSheetAdapter(data)
        //拖拽功能
        if (dragEnable) {
            var startPos = 0
            val dragListener = object : OnItemDragListener {
                override fun onItemDragMoving(
                    source: RecyclerView.ViewHolder?,
                    from: Int,
                    target: RecyclerView.ViewHolder?,
                    to: Int
                ) {
                }

                @SuppressLint("MissingPermission")
                override fun onItemDragStart(viewHolder: RecyclerView.ViewHolder?, pos: Int) {
                    val vibrator = mContext.getSystemService(Service.VIBRATOR_SERVICE) as Vibrator
                    vibrator.vibrate(100)
                    startPos = pos
                }

                override fun onItemDragEnd(viewHolder: RecyclerView.ViewHolder?, pos: Int) {
                    if (startPos == pos) {
                        return
                    }
                    val row = if (isBottom) 1 else 0
                    dragListener?.onDragEnd(startPos, pos, tempAdapter.data, row)
                }

            }
            tempAdapter.draggableModule.isDragEnabled = true
            tempAdapter.draggableModule.setOnItemDragListener(dragListener)
        }

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

    /**
     * 获取titleView布局
     */
    protected open fun getActionHeader(): IActionHeader? {
        return null
    }

    /**
     * 重写处理titleView
     */
    protected open fun initTitleView(titleView: View) {

    }

    /**
     * 点击事件所用数据
     */
    abstract fun getData(): IData?

}