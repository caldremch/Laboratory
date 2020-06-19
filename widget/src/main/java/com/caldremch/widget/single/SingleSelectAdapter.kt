package com.caldremch.widget.single

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 *
 * @author: Caldremch
 *
 * @date: 2020/6/19
 *
 * @describe: 单选(可拦截)rv套装
 *
 **/

//单选bean父类
open class SelectItem(var isSelect: Boolean = false)

//单选ViewHolder
open class SingleSelectHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun <T : View> getView(id: Int): T {
        return itemView.findViewById(id)
    }
}

//单选adapter
open abstract class SingleSelectAdapter<T : SelectItem, D : SingleSelectHolder>(
    var data: List<T>, //数据源
    var rv: RecyclerView, //列表
    var selectedPos: Int = 0, //选中位置
    var layoutId: Int //item布局
) : RecyclerView.Adapter<D>() {

    //操作缓存
    var cacheOperation: CacheOperation<T, D>? = null

    //拦截回调
    @Deprecated(message = "使用回调OnInterrupt中的cacheOperation")
    private val interruptCallback: ISelectListener.SelectInterruptCallback =
        object : ISelectListener.SelectInterruptCallback {
            override fun onConsume(goOn: Boolean) {
                if (goOn) {
                    cacheOperation?.goOn()
                    cacheOperation = null
                }
            }
        }

    //拦截监听
    var interruptISelectListener: ISelectListener.OnInterrupt? = null


    //操作缓存类


    //Holder创建
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): D {
        return createViewHolder(
            LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return data.size
    }

    //holder处理
    override fun onBindViewHolder(holder: D, position: Int) {

        bindViewHolder(holder, position, data[position])

        holder.itemView.setOnClickListener {

            //todo 可添加取消选中功能
            if (selectedPos == position) {
                return@setOnClickListener
            }

            //缓存操作, 缓存起来, 由回调处理(一般来说, 缓存后列表是不可以滚动, 因为拦截处理会中断选中逻辑\
            // , 而中断后必须有一个处理结果, 是继续选中, 还是取消选中处理)
            cacheOperation = CacheOperation(rv, this, selectedPos, holder, position)

            //选中之前, 有业务逻辑需要处理, 等业务逻辑处理后, 再决定是否选中
            if (!selectInterrupt()) {
                //没有拦截时, 直接执行操作
                cacheOperation?.goOn()
            } else {
                interruptISelectListener?.onInterrupt(
                    selectedPos,
                    holder,
                    position,
                    cacheOperation!!
                )
            }
        }
    }


    /**
     * 选中与未选中处理
     */

    fun handleSelect(lastSelectedHolder: D?, currentHolder: D, currentPosition: Int) {
        if (lastSelectedHolder != null) {
            onUnSelectHolder(lastSelectedHolder)
        } else {
            notifyItemChanged(selectedPos)
        }
        data[selectedPos].isSelect = false
        selectedPos = currentPosition
        data[selectedPos].isSelect = true
        onSelectHolder(currentHolder)
    }

    /**
     * 取消选中操作
     */
    abstract fun onUnSelectHolder(holder: D)

    /**
     * 选中操作
     */
    abstract fun onSelectHolder(holder: D)

    /**
     * 视图绑定
     */
    abstract fun bindViewHolder(holder: D, position: Int, data: T)

    /**
     * viewHolder创建
     */
    abstract fun createViewHolder(view: View): D

    /**
     * 选中前的拦截
     * 拦截后,是否消费
     * return true: 消费事件
     * return false: 执行选中, 并回调onSelectHolder后续操作
     */
    open fun selectInterrupt(): Boolean {
        return false
    }

}


