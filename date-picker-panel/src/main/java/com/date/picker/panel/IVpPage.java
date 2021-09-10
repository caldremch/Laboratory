package com.date.picker.panel;

import android.text.TextUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Caldremch
 * @email finishmo@qq.com
 * @date 2021/9/8 12:26
 * @description
 */
public interface IVpPage {

    int viewType();

    IVpPage CALENDAR_PAGE = new CalendarPage();
    IVpPage HM_PAGE = new CalendarPage();
    IVpPage APM_PAGE = new Apm();

    String getTitle();

    int CALENDAR = 0;
    int HM = 1;
    int APM = 2;

    class CalendarPage implements IVpPage {

        public String title;

        @Override
        public int viewType() {
            return CALENDAR;
        }

        @Override
        public String getTitle() {
            if (!TextUtils.isEmpty(title)) {
                return title;
            }
            Date date = new Date();
            SimpleDateFormat format = new SimpleDateFormat("yyyy日M月d日");
            return format.format(date);
        }
    }

    class HmPage implements IVpPage {
        @Override
        public int viewType() {
            return HM;
        }

        @Override
        public String getTitle() {
            return null;
        }
    }

    class Apm implements IVpPage {
        @Override
        public int viewType() {
            return APM;
        }

        @Override
        public String getTitle() {
            return null;
        }
    }


}
