package com.caldremch.laboratory.entry.entry

import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.RadioGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.caldremch.android.annotation.entry.Entry
import com.caldremch.android.annotation.entry.IEntry
import com.caldremch.common.base.BaseFragment
import com.caldremch.laboratory.R
import com.caldremch.laboratory.base.LaboratoryFragment
import kotlinx.android.synthetic.main.fragment_vp_vp2.*

/**
 *
 * @author Caldremch
 *
 * @date 2020-09-29 14:21
 *
 * @email caldremch@163.com
 *
 * @describe
 *
 **/
@Entry
class VpAndVp2Entry : IEntry {
    override fun getTitle(): String {
        return "vp2 & vp"
    }

    override fun onClick(context: Context) {
        context.startActivity(Intent(context, ActTest::class.java))
    }
}

class Vp2AndVpFragment : LaboratoryFragment() {


    override fun getLayoutId(): Int {
        return R.layout.fragment_vp_vp2
    }

    override fun getTitle(): String {
        return "vp和vp2测试"
    }

    override fun initView() {
        val vp = findViewById<ViewPager>(R.id.vp)
        val rg = findViewById<RadioGroup>(R.id.rg)
        rg.setOnCheckedChangeListener { radioGroup, checkedId ->
            when (checkedId) {
                R.id.radioButton1 -> {
                    vp.currentItem = 0
                }
                R.id.radioButton2 -> {
                    vp.currentItem = 1
                }
                R.id.radioButton3 -> {
                    vp.currentItem = 2
                }
            }
        }

        vp.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> radioButton1.isChecked = true
                    1 -> radioButton2.isChecked = true
                    2 -> radioButton3.isChecked = true
                }
            }


            override fun onPageScrollStateChanged(state: Int) {
            }

        })
        val list = arrayListOf<Fragment>()
        list.add(SubVpFragment())
        list.add(SubVpFragment())
        list.add(SubVpFragment())
        vp.offscreenPageLimit = 3
        val fpa = object : FragmentStatePagerAdapter(
            childFragmentManager,
            BEHAVIOR_SET_USER_VISIBLE_HINT
        ) {
            override fun getCount(): Int {
                return list.size
            }

            override fun getItem(position: Int): Fragment {
                return list[position]
            }

        }
        vp.adapter = fpa
    }
}

class TitleFragment() : BaseFragment<Any>() {

    override fun initView() {

    }

    override fun getLayoutView(): View {
        val tv = TextView(context!!)
        tv.text = "标题"
        return tv
    }
}

class SubVpFragment() : BaseFragment<Any>() {


    override fun getLayoutId(): Int {
        return R.layout.fragment_vp_vp2_sub
    }

    override fun initView() {
        val vp2 = view?.findViewById<ViewPager2>(R.id.okok)
        val adapter2 = object : FragmentStateAdapter(childFragmentManager, lifecycle) {
            override fun getItemCount(): Int {
                return 4
            }

            override fun createFragment(position: Int): Fragment {
                return TitleFragment()
            }
        }
        vp2?.adapter = adapter2
        val vp2Rv = vp2?.getChildAt(0) as RecyclerView
        vp2Rv.layoutManager?.isItemPrefetchEnabled = false
        vp2Rv.setItemViewCacheSize(adapter2.itemCount)
    }

//    override fun getLayoutId(): Int {
//        return R.layout.fragment_vp_vp2_sub
//    }
//
//    override fun initView() {
//        val vp2  = view?.findViewById<ViewPager2>(R.id.vp2)
//        val adapter2 = object : FragmentStateAdapter(childFragmentManager, lifecycle) {
//            override fun getItemCount(): Int {
//                return 1
//            }
//
//            override fun createFragment(position: Int): Fragment {
//                return SubVpFragment("$title 我是页面$position")
//            }
//        }
//        vp2?.adapter = adapter2
//        val vp2Rv = vp2.getChildAt(0) as RecyclerView
//        vp2Rv.setItemViewCacheSize(adapter2.itemCount)
//    }

}