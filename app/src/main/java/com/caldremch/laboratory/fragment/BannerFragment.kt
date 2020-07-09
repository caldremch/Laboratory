package com.caldremch.laboratory.fragment

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.caldremch.laboratory.R
import com.caldremch.laboratory.base.LaboratoryFragment
import com.youth.banner.adapter.BannerAdapter
import com.youth.banner.indicator.CircleIndicator
import kotlinx.android.synthetic.main.fragment_banner.*

/**
 * @author Caldremch
 * @date  2020/7/9
 * @email caldremch@163.com
 * @describe
 *
 **/
class BannerFragment : LaboratoryFragment() {

    override fun getTitle(): String {
        return "banner demo"
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_banner
    }

    override fun initView() {
        val datas = arrayListOf<Drawable>()
        datas.add(ColorDrawable(Color.RED))
        datas.add(ColorDrawable(Color.YELLOW))
        datas.add(ColorDrawable(Color.BLACK))
        banner.addBannerLifecycleObserver(this)
            .setAdapter(object : BannerAdapter<Drawable, BannerViewHolder>(datas) {
                override fun onCreateHolder(parent: ViewGroup?, viewType: Int): BannerViewHolder {
                    val imageView = ImageView(parent!!.context)
                    imageView.layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                    imageView.scaleType = ImageView.ScaleType.CENTER_CROP
                    return BannerViewHolder(imageView)
                }

                override fun onBindView(
                    holder: BannerViewHolder,
                    data: Drawable,
                    position: Int,
                    size: Int
                ) {
                    holder.iv.setImageDrawable(data)
                }

            }).setIndicator(CircleIndicator(context))
            .setBannerRound(20f)
            .start()
    }

    open class BannerViewHolder(var iv: ImageView) : RecyclerView.ViewHolder(iv) {

    }

}