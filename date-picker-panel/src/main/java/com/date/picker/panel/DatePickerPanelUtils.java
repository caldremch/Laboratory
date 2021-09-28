package com.date.picker.panel;

import com.date.picker.panel.entity.DatePanelData;
import com.date.picker.panel.entity.DayData;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * @author Caldremch
 * @email finishmo@qq.com
 * @date 2021/9/8 17:37
 * @description
 */
public class DatePickerPanelUtils {


    public static final int WEEX_DAYS = 7;
    public static final int DURATION = 6;
    public static final int DATE_PANEL_MAX_COUNT = 6 * 7;

    //日期滑动一共有3个Item, 循环滑动
    public static final int MAX_RECYCLE_COUNT = 3;

    public static List<DatePanelData> generateDefaultPanelData() {
        List<DatePanelData> datePanelDataList = new ArrayList<>(MAX_RECYCLE_COUNT);
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH);
        datePanelDataList.add(generateByMonth(getLastMonth(month)));
        datePanelDataList.add(generateByMonth(month));
        datePanelDataList.add(generateByMonth(getNextMonth(month)));
        return datePanelDataList;
    }

    public static int getMonthDiff(String d1, String d2) throws ParseException {
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        //将String日期转换成date
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date date1 = sdf.parse(d1);
        java.util.Date date2 = sdf.parse(d2);
        c1.setTime(date1);
        c2.setTime(date2);
        //判断两个日期的大小
        if (c2.getTimeInMillis() < c1.getTimeInMillis()) return 0;
        int year1 = c1.get(Calendar.YEAR);
        int year2 = c2.get(Calendar.YEAR);
        int month1 = c1.get(Calendar.MONTH);
        int month2 = c2.get(Calendar.MONTH);
        int day1 = c1.get(Calendar.DAY_OF_MONTH);
        int day2 = c2.get(Calendar.DAY_OF_MONTH); // 获取年的差值 假设 d1 = 2015-9-30   d2 = 2015-12-16
        int yearInterval = year2 - year1; // 如果 d2的 月-日 小于 d1的 月-日 那么 yearInterval-- 这样就得到了相差的年数

        if (month2 < month1 || month1 == month2 && day2 < day1) {
            yearInterval--;
        }

        if (month2 < month1) {
            yearInterval--; // 获取月数差值
        }
        int monthInterval = (month2 + 12) - month1;
        if (day2 > day1) {
            monthInterval++;
        }
        monthInterval %= 12;

        return yearInterval * 12 + monthInterval;
    }


    public static int getMonthDiffOfCalender(Calendar c1, Calendar c2) {
        //判断两个日期的大小
        if (c2.getTimeInMillis() < c1.getTimeInMillis()) return 0;
        int year1 = c1.get(Calendar.YEAR);
        int year2 = c2.get(Calendar.YEAR);
        int month1 = c1.get(Calendar.MONTH);
        int month2 = c2.get(Calendar.MONTH);
        int day1 = c1.get(Calendar.DAY_OF_MONTH);
        int day2 = c2.get(Calendar.DAY_OF_MONTH); // 获取年的差值 假设 d1 = 2015-9-30   d2 = 2015-12-16
        int yearInterval = year2 - year1; // 如果 d2的 月-日 小于 d1的 月-日 那么 yearInterval-- 这样就得到了相差的年数

        if (month2 < month1 || month1 == month2 && day2 < day1) {
            yearInterval--;
        }

        if (month2 < month1) {
            yearInterval--; // 获取月数差值
        }
        int monthInterval = (month2 + 12) - month1;
        if (day2 > day1) {
            monthInterval++;
        }
        monthInterval %= 12;

        return yearInterval * 12 + monthInterval;
    }

    private static Calendar getTargetCalendar(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        clearHMS(calendar);
        return calendar;
    }

    public static List<DatePanelData> generateRecent12YearsData() {

        List<DatePanelData> datePanelDataList = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        int cY = calendar.get(Calendar.YEAR);
        int cM = calendar.get(Calendar.MONTH);
        int cD = calendar.get(Calendar.DAY_OF_MONTH);
        int startY = cY - DURATION;
        int endY = cY + DURATION;
        startY = Math.max(startY, 1970);
        calendar.set(Calendar.YEAR, startY);
        calendar.set(Calendar.DAY_OF_MONTH, 1);

        //共有x月
        int monthCount = getMonthDiffOfCalender(getTargetCalendar(startY, cM), getTargetCalendar(endY, cM));

        clearHMS(calendar);
        for (int i = 0; i < monthCount; i++) {

            //copy一个
            Calendar itemC = (Calendar) calendar.clone();

            //算出面板展示的天数
            int maxMonthDays = itemC.getActualMaximum(Calendar.DAY_OF_MONTH);
            int m = itemC.get(Calendar.MONTH);
            int week = getWeekOfMonth(getTargetCalendarAndNoHms(m)); //1-7
            int lastMonthAddCount = week - 1;
            itemC.add(Calendar.DATE, -lastMonthAddCount); //减掉这么多天
            DatePanelData datePanelData = new DatePanelData();
            datePanelData.index = i;
            datePanelData.year = itemC.get(Calendar.YEAR);
            datePanelData.showMonth = m + 1;
            for (int j = 0; j < DATE_PANEL_MAX_COUNT; j++) {
                boolean front = j < lastMonthAddCount;
                boolean behind = j >= (maxMonthDays + lastMonthAddCount);
                DayData dayData = new DayData(itemC, front || behind);
                dayData.front = front;
                dayData.behind = behind;
                datePanelData.dayDataList.add(dayData);
                datePanelData.monthMaxDays = maxMonthDays;
                itemC.add(Calendar.DATE, 1); //+1
            }
            datePanelDataList.add(datePanelData);

            //递增1个月
            calendar.add(Calendar.MONTH, 1);
        }

        setCurrentSelectedDay(datePanelDataList, cD);

        return datePanelDataList;
    }

    public static void setCurrentSelectedDay(List<DatePanelData> datePanelDataList, int cD) {
        for (DatePanelData datePanelData : datePanelDataList) {
            if (cD > datePanelData.monthMaxDays) {
                datePanelData.panelSelectedDay = datePanelData.monthMaxDays;
            } else {
                datePanelData.panelSelectedDay = cD;
            }
        }
    }


    /**
     * @param month 0-11
     * @return
     */
    private static DatePanelData generateByMonth(int month) {

        DatePanelData datePanelData = new DatePanelData();
        int week = getWeekOfMonth(getTargetCalendarAndNoHms(month)); //1-7

        Calendar currentCalendar = getTargetCalendarAndNoHms(month);
        int maxDayOfCurrentMonth = currentCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        int lastMonthAddCount = week - 1;
        int nextMonthAddCount = DatePanelData.DATE_PANEL_MAX_COUNT - lastMonthAddCount - maxDayOfCurrentMonth;

        //上个月
        Calendar lastMonthCalendar = getTargetCalendarAndNoHms(getLastMonth(month));
        int maxDayOfLastMonth = currentCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        lastMonthCalendar.set(Calendar.DAY_OF_MONTH, maxDayOfLastMonth);
        for (int i = 0; i < lastMonthAddCount; i++) {
            DayData dayData = new DayData(lastMonthCalendar, true);
            datePanelData.dayDataList.add(dayData);
            lastMonthCalendar.add(Calendar.DAY_OF_MONTH, -1);
        }


        //当前月
        currentCalendar.set(Calendar.DAY_OF_MONTH, 1);
        for (int i = 0; i < maxDayOfCurrentMonth; i++) {
            DayData dayData = new DayData(currentCalendar);
            datePanelData.dayDataList.add(dayData);
            currentCalendar.add(Calendar.DAY_OF_MONTH, 1);
        }


        //下个月
        Calendar nextMonthCalendar = getTargetCalendarAndNoHms(getNextMonth(month));
        nextMonthCalendar.set(Calendar.DAY_OF_MONTH, 1);
        for (int i = 0; i < nextMonthAddCount; i++) {
            DayData dayData = new DayData(nextMonthCalendar, true);
            datePanelData.dayDataList.add(dayData);
            nextMonthCalendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        return datePanelData;
    }

    public static void clearHMS(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
    }

    private static int getLastMonth(int month) {
        if (month == 0) {
            return 11;
        }
        return month - 1;
    }

    private static int getNextMonth(int month) {
        if (month == 11) {
            return 0;
        }
        return month + 1;
    }


    /**
     * @param month 0-11
     * @return
     */
    private static Calendar getTargetCalendarAndNoHms(int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, month);
        clearHMS(calendar);
        return calendar;
    }


    /**
     * 获取某月第一天是星期几
     * 1-7
     * 代表星期1 到星期日
     */
    private static int getWeekOfMonth(Calendar calendar) {
        Calendar targetCalendar;
        if (calendar == null) {
            targetCalendar = Calendar.getInstance(Locale.CHINA);
        } else {
            targetCalendar = calendar;
        }
        //获取当前月的1号,判断是星期几
        targetCalendar.add(Calendar.MONTH, 0);
        targetCalendar.set(Calendar.DAY_OF_MONTH, 1);
        int x = targetCalendar.get(Calendar.DAY_OF_WEEK);
        return x == 1 ? 7 : (x - 1);
    }

    public static long trans2Mills(DayData dayData) {
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        clearHMS(calendar);
        calendar.set(Calendar.YEAR, dayData.year);
        calendar.set(Calendar.MONTH, dayData.month - 1);
        calendar.set(Calendar.DAY_OF_MONTH, dayData.day);
        return calendar.getTimeInMillis();
    }
}
