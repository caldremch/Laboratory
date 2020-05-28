package com.caldremch.date

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.caldremch.laboratory.R

/**
 *
 * @author Caldremch
 *
 * @date 2020-05-28 01:42
 *
 * @email caldremch@163.com
 *
 * @describe
 *
 **/

class DateViewHolder(var myItemView: View) : RecyclerView.ViewHolder(myItemView) {

    var textView: TextView

    init {
        textView = myItemView.findViewById(R.id.tv_name)
    }

}