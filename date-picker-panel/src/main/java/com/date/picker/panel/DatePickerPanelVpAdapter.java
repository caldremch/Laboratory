package com.date.picker.panel;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Caldremch
 * @email finishmo@qq.com
 * @date 2021/9/8 12:21
 * @description
 */
public class DatePickerPanelVpAdapter extends RecyclerView.Adapter<BaseHolder> {

    private List<IVpPage> pages = new ArrayList<>();
    private OnItemClickListener onItemClickListener;
    private int calendarItemHeight;

    public static class InnerData {
        public static long selectedDate = -1;
        public static long limitType = 0;
    }


    public DatePickerPanelVpAdapter(int calendarItemHeight) {
        this.calendarItemHeight = calendarItemHeight;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @NotNull
    @Override
    public BaseHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case IVpPage.CALENDAR:
                CalenderSliderView view = new CalenderSliderView(parent.getContext(), calendarItemHeight);
                view.setOnItemClickListener(onItemClickListener);
                ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                view.setLayoutParams(lp);
                BaseHolder holder = new BaseHolder(view);
                return holder;
        }
        throw new IllegalArgumentException("不支持类型");
    }

    @Override
    public int getItemViewType(int position) {
        return pages.get(position).viewType();
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull BaseHolder holder, int position) {
        if (holder.itemView instanceof CalenderSliderView) {
            ((CalenderSliderView) holder.itemView).setSelectedDate();
        }
    }

    @Override
    public int getItemCount() {
        return pages.size();
    }

    public void addPage(IVpPage calendarPage) {
        pages.add(calendarPage);
    }

    public void setPages(List<IVpPage> pages) {
        this.pages = pages;
    }

}
