package com.date.picker.panel;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Gravity;

import androidx.annotation.Nullable;

/**
 * @author Caldremch
 * @email finishmo@qq.com
 * @date 2021/9/8 12:17
 * @description
 */
public class DayTextView extends androidx.appcompat.widget.AppCompatTextView {
    public DayTextView(Context context) {
        super(context);
        init();
    }


    public DayTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DayTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        int dp12 = dp2IntPx(12);
        setPadding(0, dp12, 0, dp12);
        setGravity(Gravity.CENTER);
        setTextSize(12);
        setTextColor(Color.parseColor("#767E85"));
    }

    private float dp2px(int dp) {
        DisplayMetrics displayMetrics = getContext().getResources()
                .getDisplayMetrics();
        return (float) (dp * displayMetrics.density + 0.5);
    }

    private int dp2IntPx(int dp) {
        DisplayMetrics displayMetrics = getContext().getResources()
                .getDisplayMetrics();
        return (int) (dp * displayMetrics.density + 0.5);
    }
}
