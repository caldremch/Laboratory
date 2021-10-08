package com.date.picker.panel;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.date.picker.panel.entity.DatePanelData;
import com.date.picker.panel.entity.DayData;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * @author Caldremch
 * @email finishmo@qq.com
 * @date 2021/9/8 11:43
 * @description
 */
public class CalenderSliderView extends FrameLayout {


    private LinearLayout llWeek;
    private ViewPager2 rvCalendar;
    private CalenderSliderAdapter calenderSliderAdapter;
    private OnItemClickListener onItemClickListener;
    private Handler mHandler = new Handler(Looper.getMainLooper());

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
        if (calenderSliderAdapter != null) {
            calenderSliderAdapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(@NonNull @NotNull RecyclerView.Adapter adapter, DayData dayData) {
                    lastSelectedDay = dayData.day;

                    //滑动操作
                    mHandler.post(() -> {
                        if (dayData.front) {
                            //下滑
                            int cIndex = rvCalendar.getCurrentItem();
                            if (cIndex > 0) {
                                rvCalendar.setCurrentItem(cIndex - 1);
                            }
                        } else if (dayData.behind) {
                            //上划
                            int cIndex = rvCalendar.getCurrentItem();
                            if (cIndex < calenderSliderAdapter.getItemCount()) {
                                rvCalendar.setCurrentItem(cIndex + 1);
                            }
                        }
                    });

                    if (!dayData.isNotCurrentMonth) {
                        if (onItemClickListener != null) {
                            onItemClickListener.onItemClick(adapter, dayData);
                        }
                    }
                }
            });
        }
    }

    private int calendarItemHeight;

    public CalenderSliderView(@NonNull Context context, int calendarItemHeight) {
        super(context);
        this.calendarItemHeight = calendarItemHeight;
        init();
    }

    public CalenderSliderView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CalenderSliderView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private int getCurrentDay() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.DAY_OF_MONTH);

    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.calender_slider_view, this, true);
        llWeek = findViewById(R.id.ll_week);
        rvCalendar = findViewById(R.id.rv_calendar);
        rvCalendar.setOrientation(ViewPager2.ORIENTATION_VERTICAL);
        rvCalendar.setOffscreenPageLimit(1);
        ((RecyclerView) rvCalendar.getChildAt(0)).setHasFixedSize(true);
        calenderSliderAdapter = new CalenderSliderAdapter();
        calenderSliderAdapter.initCurrentDay(getCurrentDay());
        rvCalendar.setAdapter(calenderSliderAdapter);
        setInfiniteLoop();
        initWeek();
        initDays();
        initEvent();
    }

    private int lastSelectedDay; //如果不一样, 则需要更新

    private void initEvent() {
        //不做自动选中
//        rvCalendar.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
//            @Override
//            public void onPageSelected(int position) {
//                DatePanelData datePanelData = calenderSliderAdapter.getData(position);
//                if (onItemClickListener != null) {
//                    DayData dayData = new DayData(datePanelData.year, datePanelData.showMonth, datePanelData.panelSelectedDay);
//                    dayData.mills = DatePickerPanelUtils.trans2Mills(dayData);
//                    //取出当前选中的
//                    onItemClickListener.onItemClick(calenderSliderAdapter, dayData);
//                }
//            }
//        });
    }

    private void setInfiniteLoop() {

    }

    private List<DatePanelData> datePanelDataList;

    //初始化面板数据
    private void initDays() {
        datePanelDataList = DatePickerPanelUtils.generateRecent12YearsData();
        calenderSliderAdapter.setData(datePanelDataList);
        //初始页面
        rvCalendar.setCurrentItem(datePanelDataList.size() / 2, false);
        Log.d("CalenderSliderView", "initDays: " + datePanelDataList.size());
    }

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日");

    /**
     * 设置当前选中的数据
     *
     * @param timeStamp 目标时间戳  [注意:无时分秒]
     *                  对比年月即可
     */
    public void setSelectedDate(long timeStamp) {
        //计算出面板指定位置
        if (datePanelDataList != null && timeStamp > 0) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(timeStamp);
            int y = calendar.get(Calendar.YEAR);
            int m = calendar.get(Calendar.MONTH);

            Calendar elementCalendar = Calendar.getInstance();
            //6*7 = 42 12*11
            for (int i = 0; i < datePanelDataList.size(); i++) {
                DatePanelData datePanelData = datePanelDataList.get(i);
                if (datePanelData.dayDataList.size() > datePanelData.frontOffset) {
                    DayData dayData = datePanelData.dayDataList.get(datePanelData.frontOffset);
                    elementCalendar.setTimeInMillis(dayData.mills);
                    int y1 = elementCalendar.get(Calendar.YEAR);
                    int m1 = elementCalendar.get(Calendar.MONTH);
                    if (y == y1 && m == m1) {
                        rvCalendar.setCurrentItem(i, false);
                        break;
                    }
                }
            }
            calenderSliderAdapter.setSelectedDate(timeStamp);
        }
    }


    private void initWeek() {
        String[] weeks = new String[]{"一", "二", "三", "四", "五", "六", "日"}; //星期
        ViewGroup.LayoutParams llWeekParams = llWeek.getLayoutParams();
        llWeekParams.height = calendarItemHeight;
        llWeek.setLayoutParams(llWeekParams);
        llWeek.removeAllViews();
        for (String week : weeks) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            params.weight = 1;
            DayTextView dayTextView = new DayTextView(getContext());
            dayTextView.setText(week);
            llWeek.addView(dayTextView, params);
        }

    }

}
