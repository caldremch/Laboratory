package com.date.picker.panel.entity;

import java.util.Calendar;

/**
 * @author Caldremch
 * @email finishmo@qq.com
 * @date 2021/9/8 11:39
 * @description
 */
public class DayData {

    public DayData(Calendar calendar) {
        this(calendar, false);
    }

    public DayData(Calendar calendar, boolean isNotCurrentMonth) {
        this.mills = calendar.getTimeInMillis();
        this.day = calendar.get(Calendar.DAY_OF_MONTH);
        this.month = calendar.get(Calendar.MONTH) + 1;
        this.year = calendar.get(Calendar.YEAR);
        this.isNotCurrentMonth = isNotCurrentMonth;
    }

    public DayData(int year, int month, int panelSelectedDay) {
        this.year = year;
        this.month = month;
        this.day = panelSelectedDay;
    }

    public long mills;
    public int month;
    public int day;
    public int year;
    public boolean isNotCurrentMonth;
    public boolean front;
    public boolean behind;

    public String getShowTitle() {
        if (day == 1) {
            return month + "月";
        }

        return day + "";
    }
}
