package com.caldremch.laboratory.fragment

import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.caldremch.laboratory.R
import com.caldremch.laboratory.base.LaboratoryFragment
import com.caldremch.laboratory.databinding.FragmentBannerBinding
import com.youth.banner.adapter.BannerAdapter
import com.youth.banner.indicator.CircleIndicator
import java.net.Inet4Address

/**
 * @author Caldremch
 * @date  2020/7/9
 * @email caldremch@163.com
 * @describe
 *
 **/
class TextViewFragment : LaboratoryFragment() {


    override fun getTitle(): String {
        return "textview-demo"
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_textview
    }

    override fun initView() {
        val tv1 = findViewById<TextView>(R.id.tv1)
        val tv2 = findViewById<TextView>(R.id.tv2)

        tv1.setOnClickListener {
            Toast.makeText(requireContext(), "type提示", Toast.LENGTH_SHORT).show()
        }
        
//        tv1.text = "第一Typẽ\n第二将ẽ"
        tv1.post {
            Log.d(TAG, "高度: ${tv1.height}")
            val fm = Paint.FontMetrics()
             tv1.paint.getFontMetrics(fm)
            Log.d(TAG, "top=${fm.top}, ascent=${fm.ascent}, descent=${fm.descent}, bottom=${fm.bottom}, leading=${fm.leading}")
            Log.d(TAG, "baseline.y = ${fm.bottom+fm.top-fm.descent}") //-142.04297
            //top=-145.74902, ascent=-128.02734, descent=33.691406, bottom=37.39746, leading=0.0
            //height=162 , ascent+decent = 161.71



        }

        tv2.post {
            Log.d(TAG, "高度: ${tv2.height}")
        }

    }


}