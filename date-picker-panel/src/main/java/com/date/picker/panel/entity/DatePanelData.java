package com.date.picker.panel.entity;

import com.date.picker.panel.DatePickerPanelUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author Caldremch
 * @email finishmo@qq.com
 * @date 2021/9/8 17:38
 * @description
 */
public class DatePanelData {
    public static final int DATE_PANEL_MAX_COUNT = 42;
    //面板展示的日期 共36个数
    public List<DayData> dayDataList = new ArrayList<>(DATE_PANEL_MAX_COUNT);
    public int index;
    public int panelSelectedDay;//当前面板选中的哪一天
    public int monthMaxDays;
    public int year;
    public int showMonth;
    public int frontOffset = 0; //面板每月1号的偏移

    public long getPanelMills() {
        Calendar calendar = Calendar.getInstance();
        DatePickerPanelUtils.clearHMS(calendar);
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, showMonth);
        calendar.set(Calendar.DAY_OF_MONTH, panelSelectedDay);
        return calendar.getTimeInMillis();
    }
}
