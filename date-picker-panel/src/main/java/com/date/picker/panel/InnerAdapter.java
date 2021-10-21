package com.date.picker.panel;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.date.picker.panel.entity.DayData;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author Caldremch
 * @email finishmo@qq.com
 * @date 2021/9/9 11:30
 * @description
 */
class InnerAdapter extends RecyclerView.Adapter<InnerAdapter.ViewHolder> {

    private final List<DayData> data = new ArrayList<>();
    private final int notCurrentMonthColor = Color.parseColor("#C4C9CF");
    private final int currentMonthColor = Color.parseColor("#21272A");
    private final int cDayColor = Color.parseColor("#257BF4");
    private Drawable selectedBg;
    private long cMills;
    private long cMillsUtil00;
    private int selectedPos = -1;
    private RecyclerView rv;

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public InnerAdapter(Context context, RecyclerView rv) {
        Calendar calendar = Calendar.getInstance();
        DatePickerPanelUtils.clearHMS(calendar);
        cMills = calendar.getTimeInMillis();
        calendar.add(Calendar.DATE, 1);
        cMillsUtil00 = calendar.getTimeInMillis();

        selectedBg = ContextCompat.getDrawable(context, R.drawable.dpp_day_selected);
        this.rv = rv;
    }

    public void setData(List<DayData> data, int cD) {
        this.data.clear();
        this.data.addAll(data);
        this.cD = cD;
        for (int i = 0; i < data.size(); i++) {
            DayData d = data.get(i);
            if (d.day == cD && !d.isNotCurrentMonth) {
                selectedPos = i;
            }
        }
        notifyDataSetChanged();
    }

    public void setData(List<DayData> data) {
        this.data.clear();
        this.data.addAll(data);
        for (int i = 0; i < data.size(); i++) {
            DayData d = data.get(i);
            if (d.mills == DatePickerPanelVpAdapter.InnerData.selectedDate && !d.isNotCurrentMonth) {
                selectedPos = i;
            }
        }
        notifyDataSetChanged();
    }

    private LayoutInflater layoutInflater;
    private int cD = -1;

    public void setCurrentSelectedDay(int cD) {
        this.cD = cD;

    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTitle;
        public View vBg;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            vBg = itemView.findViewById(R.id.v_bg);
        }
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        View view = layoutInflater.inflate(R.layout.day_item, null);
        ViewHolder holder = new ViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = holder.getAdapterPosition();
                DayData dayData = data.get(pos);
                if (DatePickerPanelVpAdapter.InnerData.limitType == 1 && dayData.mills >= cMillsUtil00) {
                    return;
                }
                DatePickerPanelVpAdapter.InnerData.selectedDate = dayData.mills;
                //设置当前选中的day
                cD = dayData.day;

                //通知原先选中的改变样式
                if (selectedPos != -1) {
                    DayData selectedDay = data.get(selectedPos);
                    ViewHolder selectedHolder = (ViewHolder) rv.findViewHolderForAdapterPosition(selectedPos);
                    if (selectedHolder == null) {
                        notifyItemChanged(selectedPos);
                    } else {
                        handleTitleStyle(selectedHolder, selectedDay);
                    }
                }
                //当前选中的改变样式
                selectedPos = pos;
                notifyItemChanged(pos);

                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(InnerAdapter.this, dayData);
                }

            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        DayData dayData = data.get(position);
        handleTitleStyle(holder, dayData);
        holder.tvTitle.setText(data.get(position).getShowTitle());
    }

    private void handleTitleStyle(@NotNull ViewHolder holder, DayData dayData) {


        if (DatePickerPanelVpAdapter.InnerData.selectedDate == dayData.mills && !dayData.isNotCurrentMonth) {
            //选中的颜色
            holder.vBg.setVisibility(View.VISIBLE);
            holder.tvTitle.setTextColor(Color.WHITE);
            holder.tvTitle.setTypeface(Typeface.DEFAULT_BOLD);
        } else if (dayData.mills == cMills) {
            //当日的颜色
            holder.tvTitle.setTypeface(Typeface.DEFAULT_BOLD);
            holder.tvTitle.setTextColor(cDayColor);
            holder.vBg.setVisibility(View.GONE);
        } else if (dayData.isNotCurrentMonth || (DatePickerPanelVpAdapter.InnerData.limitType == 1 && dayData.mills >= cMillsUtil00)) {
            //非当月颜色
            holder.tvTitle.setTypeface(Typeface.DEFAULT);
            holder.tvTitle.setTextColor(notCurrentMonthColor);
            holder.vBg.setVisibility(View.GONE);
        } else {
            //当月颜色
            holder.tvTitle.setTypeface(Typeface.DEFAULT);
            holder.tvTitle.setTextColor(currentMonthColor);
            holder.vBg.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
