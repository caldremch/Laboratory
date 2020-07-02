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
//todo 点击事件定位到具体的View
//单选bean父类
open class SelectItem(var isSelect: Boolean = false)

//单选adapter
abstract class SingleSelectAdapter<T : SelectItem, D : RecyclerView.ViewHolder>(
    var mData: List<T>, //数据源
    var rv: RecyclerView, //列表
    var selectedPos: Int = NONE, //选中位置
    var isSupportUnSelect: Boolean = false
) : RecyclerView.Adapter<D>() {

    companion object {
        const val NONE = -1; //表示未选中任何一个
    }

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

    fun hasSelected(): Boolean {
        return selectedPos != NONE
    }

    fun getSelectData(): T? {
        if (selectedPos != NONE && selectedPos < mData.size) {
            return mData[selectedPos]
        }
        return null
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    //holder处理
    override fun onBindViewHolder(holder: D, position: Int) {

        bindViewHolder(holder, position, mData[position])

        holder.itemView.setOnClickListener {

            if (selectedPos == position && !isSupportUnSelect) {
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

        //优先处理点击同一个
        if (isSupportUnSelect) {
            if (lastSelectedHolder == currentHolder && selectedPos != NONE) {
                //取消选中
                mData[selectedPos].isSelect = false
                onUnSelectHolder(currentHolder, selectedPos, mData[selectedPos])
                selectedPos = NONE
                return
            }

            if (lastSelectedHolder == null && selectedPos == NONE) {
                //没有任何一个被选中
                selectedPos = currentPosition
                mData[selectedPos].isSelect = true
                onSelectHolder(currentHolder, selectedPos, mData[selectedPos])
                return
            }
        }

        if (selectedPos == NONE) {
            return
        }

        if (lastSelectedHolder != null) {
            onUnSelectHolder(lastSelectedHolder, selectedPos, mData[selectedPos])
        } else {
            notifyItemChanged(selectedPos)
        }

        //当前为取消选中
        mData[selectedPos].isSelect = false
        selectedPos = currentPosition
        mData[selectedPos].isSelect = true
        onSelectHolder(currentHolder, selectedPos, mData[selectedPos])
    }

    /**
     * 清除选中, 取消当前选中
     *
     */
    fun clearSelected() {
        if (selectedPos != NONE) {
            mData[selectedPos].isSelect = false
            val targetHolder = rv.findViewHolderForAdapterPosition(selectedPos)
            if (targetHolder == null) {
                notifyItemChanged(selectedPos)
            } else {
                onUnSelectHolder(targetHolder as D, selectedPos, mData[selectedPos])
            }
            selectedPos = NONE
        }
    }

    protected fun inflateView(parent: ViewGroup, layoutRes: Int): View =
        LayoutInflater.from(parent.context).inflate(layoutRes, parent, false)


    /**
     * 取消选中操作
     * holder: 取消选中的holder
     * position: 取消选中选中的位置
     * item: 选中的数据
     */
    abstract fun onUnSelectHolder(holder: D, position: Int, item: T)

    /**
     * 选中操作
     * holder: 选中的holder
     * position: 选中的位置
     * item: 选中的数据
     */
    abstract fun onSelectHolder(holder: D, position: Int, item: T)

    /**
     * 视图绑定
     */
    abstract fun bindViewHolder(holder: D, position: Int, data: T)


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


