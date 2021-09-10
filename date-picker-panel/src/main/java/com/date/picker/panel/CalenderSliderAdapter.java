package com.date.picker.panel;

import android.util.Log;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.date.picker.panel.entity.DatePanelData;
import com.date.picker.panel.entity.DayData;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Caldremch
 * @email finishmo@qq.com
 * @date 2021/9/8 11:44
 * @description
 */
public class CalenderSliderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private String TAG = "CalenderSliderAdapter";

    private List<DatePanelData> data = new ArrayList<>();
    private int rvHeight;
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public CalenderSliderAdapter() {

    }

    public void setData(List<DatePanelData> data) {
        this.data.clear();
        this.data.addAll(data);
        notifyDataSetChanged();
    }


    int last = 0;

    @NonNull
    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        RecyclerView recyclerView = new RecyclerView(parent.getContext());
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        recyclerView.setLayoutParams(params);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(parent.getContext(), DatePickerPanelUtils.WEEX_DAYS));
        InnerAdapter innerAdapter = new InnerAdapter(parent.getContext(), recyclerView);
        BaseHolder holder = new BaseHolder(recyclerView);
        innerAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull @NotNull RecyclerView.Adapter adapter, DayData dayData) {

                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(adapter, dayData);
                }

                if (last != dayData.day) {
                    last = dayData.day;
                    DatePickerPanelUtils.setCurrentSelectedDay(data, dayData.day);
                    int pos = holder.getAdapterPosition();
                    if (pos > 0) {
                        notifyItemRangeChanged(0, pos);
                    }
                    if (pos < data.size()) {
                        notifyItemRangeChanged(pos + 1, data.size() - (pos + 1));
                    }
                }

            }
        });
        recyclerView.setAdapter(innerAdapter);
        return holder;
    }


    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, int position) {
        RecyclerView recyclerView = (RecyclerView) holder.itemView;
        InnerAdapter adapter = (InnerAdapter) recyclerView.getAdapter();
        DatePanelData datePanelData = data.get(position);
        Log.d(TAG, "onBindViewHolder: " + datePanelData.panelSelectedDay);
        adapter.setData(datePanelData.dayDataList, datePanelData.panelSelectedDay);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setItemRvHeight(int rvHeight) {
        this.rvHeight = rvHeight;
    }

    public DatePanelData getData(int position) {
        return data.get(position);
    }

    public void initCurrentDay(int currentDay) {
    }
}
