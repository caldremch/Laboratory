package com.caldremch.laboratory.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class MetricsFontTextView  extends androidx.appcompat.widget.AppCompatTextView {
    public MetricsFontTextView(Context context) {
        super(context);
    }

    public MetricsFontTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MetricsFontTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int baseLineY = getBaseline();
        int baseLineX = 0 ;
        Paint paint = getPaint();
        //计算各线在位置
        Paint.FontMetrics fm = paint.getFontMetrics();
        float ascent = baseLineY + fm.ascent;
        float descent = baseLineY + fm.descent;
        float top = baseLineY + fm.top;
        float bottom = baseLineY + fm.bottom;
        //画基线
        paint.setColor(Color.GREEN);
        canvas.drawLine(baseLineX, baseLineY, getMeasuredWidth(), baseLineY, paint);
        //画top
        paint.setColor(Color.RED);
        canvas.drawLine(baseLineX, top, getMeasuredWidth(), top, paint);
        //画ascent
        paint.setColor(Color.BLUE);
        canvas.drawLine(baseLineX, ascent, getMeasuredWidth(), ascent, paint);
        //画descent
        paint.setColor(Color.BLUE);
        canvas.drawLine(baseLineX, descent, getMeasuredWidth(), descent, paint);
        //画bottom
        paint.setColor(Color.RED);
        canvas.drawLine(baseLineX, bottom, getMeasuredWidth(), bottom, paint);
    }

}
