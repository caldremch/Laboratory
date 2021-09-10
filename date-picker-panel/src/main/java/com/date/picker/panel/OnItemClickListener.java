package com.date.picker.panel;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.date.picker.panel.entity.DayData;

/**
 * @author Caldremch
 * @email finishmo@qq.com
 * @date 2021/9/9 16:33
 * @description
 */
public interface OnItemClickListener {
    void onItemClick(@NonNull RecyclerView.Adapter adapter, DayData dayData);
}

