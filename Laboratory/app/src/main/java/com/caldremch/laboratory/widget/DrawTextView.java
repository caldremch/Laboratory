package com.caldremch.laboratory.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

public class DrawTextView extends View {

    Paint paint;
    String text = "第一个Type";
    int textHeight = 0;
    int textHeightMiddle = 0;
    int mHeight = 0;
    private float textTop;
    private float textBottom;
    private String TAG = "DrawTextView";

    private void init() {
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(12);
        paint.setTextSize(100);

        Paint.FontMetrics fm = paint.getFontMetrics();
        textTop = fm.top;
        textBottom = fm.bottom;
        textBaseLine = (int) Math.abs(textTop);

        Log.d(TAG, ": textTop:" + textTop + "," + "textBottom:" + textBottom);
    }

    public DrawTextView(Context context) {
        super(context);
        init();
    }

    public DrawTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DrawTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        textHeight = (int) (Math.abs(textTop) + textBottom);
        textHeightMiddle = textHeight/2;
        mHeight = MeasureSpec.getSize(heightMeasureSpec);
        heightMiddleLine = mHeight / 2f;
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), mHeight);
    }

    int textStartX = 0;
    float heightMiddleLine = 0;
    int textBaseLine = 0;
    int fixedBaseLine = 0;

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawText(text, textStartX, textBaseLine, paint);
        //画两条线标记位置
        paint.setStrokeWidth(4);

        paint.setColor(Color.RED);
        canvas.drawLine(0, heightMiddleLine, 2000, heightMiddleLine, paint);

        paint.setColor(Color.GREEN);
        canvas.drawLine(0, textHeightMiddle, 2000, textHeightMiddle, paint);


        paint.setColor(Color.BLUE);
        canvas.drawLine(200, 0, 200, 2000, paint);


        //修复居中
        canvas.drawText(text, 300, textBaseLine+textHeightMiddle, paint);

    }

}
