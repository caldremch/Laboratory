package com.caldremch.android.test.demo

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.Rect
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder


class MainActivity : AppCompatActivity() {


    class Item(val index: Int, val avgWidth: Int)

    private val TAG = this.javaClass.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val rv = findViewById<RecyclerView>(R.id.rv)
        rv.layoutManager = GridLayoutManager(this, 3)
        val adapter = object : BaseQuickAdapter<Item, BaseViewHolder>(R.layout.activity_item) {
            @SuppressLint("SetTextI18n")
            override fun convert(holder: BaseViewHolder, item: Item) {
                holder.itemView.setBackgroundColor(
                    when (item.index) {
                        0 -> Color.BLUE
                        1 -> Color.GREEN
                        2 -> Color.RED
                        else -> Color.GRAY
                    }
                )

                holder.itemView.post {
                    val tv = holder.getViewOrNull<TextView>(R.id.tv)
                    val tv2 = holder.getViewOrNull<TextView>(R.id.tv2)
                    tv?.text = "item宽度:${holder.itemView.measuredWidth}"
                    tv2?.text = "均分宽度:${item.avgWidth.toString()}"
                }
            }

        }

        val spanCount = 3
        val margin = (15f).dp2px().toInt()
        val itemGap = (3f).dp2px().toInt()
        val sw = getScreenWidth()
        val avgWidth = ((sw - margin * 2 - itemGap * 2) / spanCount) //327
        val screenWidthBySpan = sw / 3


        rv.adapter = adapter
        rv.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                val pos = parent.getChildAdapterPosition(view)
                val data = adapter.getItem(pos)
                val imageTotalCount = 3
                val index = data.index
                val lp = view.layoutParams
                val gridManagerGap = ((sw - 3 * data.avgWidth) / 3)
                val designGap = 3f.dp2px().toInt()

                //继续平摊宽度
                val avgGain = (gridManagerGap * 3 - margin * 2 - designGap * 2) / 3
                val settleWidth = avgGain + data.avgWidth
                val leftGap = getScreenWidth() - 3 * settleWidth
                val perLeftGap = leftGap / 3

                //还可以继续平摊的宽度
                val canGainW = leftGap - margin * 2 - designGap * 2

                Log.d(
                    TAG,
                    "sw=$sw avgWidth=$avgWidth canGainW =$canGainW leftGap=$leftGap $gridManagerGap dp15=${margin} dp3=$designGap"
                )
                when (index) {
                    0 -> {
                        if (imageTotalCount == 2) {
                            //2张图片的时候
                            outRect.left = margin
                        } else if (imageTotalCount == 1) {
                            //1张图片的时候
                        } else {
                            lp.width = screenWidthBySpan - gridManagerGap
                            outRect.left = gridManagerGap

                        }

                    }
                    1 -> {
                        outRect.left = gridManagerGap / 2
                        lp.width = screenWidthBySpan - gridManagerGap
                    }

                    2 -> {
                        outRect.right = gridManagerGap
                        lp.width = screenWidthBySpan - gridManagerGap
                    }
                }
                view.layoutParams = lp

            }
        })

        adapter.setList(arrayListOf(Item(0, avgWidth), Item(1, avgWidth), Item(2, avgWidth)))
    }


    fun getScreenWidth(): Int {
        val paramContext: Context = this
        val localWindowManager = paramContext.getSystemService(
            WINDOW_SERVICE
        ) as WindowManager
        val localDisplayMetrics = DisplayMetrics()
        localWindowManager.defaultDisplay
            .getMetrics(localDisplayMetrics)
        return localDisplayMetrics.widthPixels
    }

    fun Float.dp2px(): Float {
        val displayMetrics = resources.displayMetrics
        return (this * displayMetrics.density + 0.5).toFloat()
    }
}