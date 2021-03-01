package com.caldremch.laboratory.fragment

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.caldremch.laboratory.R
import com.caldremch.laboratory.base.LaboratoryFragment
import com.caldremch.laboratory.databinding.FragmentBannerBinding
import com.youth.banner.adapter.BannerAdapter
import com.youth.banner.indicator.CircleIndicator

/**
 * @author Caldremch
 * @date  2020/7/9
 * @email caldremch@163.com
 * @describe
 *
 **/
class BannerFragment : LaboratoryFragment() {

    private val binding by viewBinding(FragmentBannerBinding::bind)

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
        binding.banner.addBannerLifecycleObserver(this)
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