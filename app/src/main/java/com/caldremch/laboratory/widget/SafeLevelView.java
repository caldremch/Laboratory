package com.caldremch.laboratory.widget;/*
package com.caldremch.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.View.MeasureSpec;

import com.caldremch.laboratory.R;

*/
/**
 * @author Caldremch
 * @date 2020-05-22 16:56
 * @email caldremch@163.com
 * @describe
 **//*

public class SafeLevelView extends View {


    private Paint mPaintDescribeText;
    private float currentDegree = 0;


    */
/**
     * 控件的宽
     *//*

    private int mWidth;
    */
/**
     * 控件的高
     *//*

    private int mHeight;
    */
/**
     * 控件中的图片
     *//*

    private Bitmap mImage;

    */
/**
     * 箭头
     *//*

    private Bitmap mImage_arror;
    */
/**
     * 图片的缩放模式
     *//*

    private int mImageScale;
    private static final int IMAGE_SCALE_FITXY = 0;
    private static final int IMAGE_SCALE_CENTER = 1;
    */
/**
     * 图片的介绍
     *//*

    private String mLevel;
    */
/**
     * 字体的颜色
     *//*

    private int mTextColor;
    */
/**
     * 等级字体的大小
     *//*

    private int mTextSize;

    */
/**
     * 等级描述字体的大小
     *//*

    private int mDescribeTextSize;

    private Paint mPaint;


    private Rect mTextBound;

    */
/**
     * 描述文字的框
     *//*

    private Rect mDescribeTextBound;


    */
/**
     * 控制整体布局
     *//*

    private Rect rect;


    */
/**
     * 圆弧的的区域
     *//*

    private RectF rectF;
    public float currentX = 0;
    private int i;
    private int mcurrentLevel;
    private int currentLevelColor;
    private double distance;//圆心到最初始化画布中心的位置的坐标的距离
    private Canvas canvasArror;
    Rect rect1;

    public float getCurrentX() {
        return currentX;
    }


    */
/**
     * 管理圆
     *//*

    private CircleBean circleBean;

    */
/**
     * 等级描述字符
     *//*

    public String levelDescribeText;


    //    管理画布中心点
    public SafeLevelView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SafeLevelView(Context context) {
        this(context, null);
    }

    */
/**
     * 初始化所特有自定义类型
     *
     * @param context
     * @param attrs
     * @param defStyle
     *//*

    public SafeLevelView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.FYouView, defStyle, 0);

        int n = a.getIndexCount();

        for (int i = 0; i < n; i++) {
            int attr = a.getIndex(i);

            switch (attr) {
                case R.styleable.FYouView_image:
                    mImage = BitmapFactory.decodeResource(getResources(), a.getResourceId(attr, 0));
                    break;


                case R.styleable.FYouView_imagearror:
                    mImage_arror = BitmapFactory.decodeResource(getResources(), a.getResourceId(attr, 0));

                    break;

                case R.styleable.FYouView_imageScaleType:
                    mImageScale = a.getInt(attr, 0);
                    break;
                case R.styleable.FYouView_levelFlagText:
                    mLevel = a.getString(attr);
                    break;

                case R.styleable.FYouView_levelDescribeText:

                    levelDescribeText = a.getString(attr);
                    break;

                case R.styleable.FYouView_levelFlagTextColor:
                    mTextColor = a.getColor(attr, Color.BLACK);
                    break;
                case R.styleable.FYouView_levelFlagTextSize:
                    mTextSize = a.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                            16, getResources().getDisplayMetrics()));
                    break;

                case R.styleable.FYouView_levelDescribeTextSize:
                    mDescribeTextSize = a.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                            16, getResources().getDisplayMetrics()));
                    break;

            }
        }
        a.recycle();

        //箭头画布
        canvasArror = new Canvas();

        // 控制整体布局
        rect = new Rect();

        // 圆弧区域
        rectF = new RectF();
        mPaint = new Paint();
        mPaintDescribeText = new Paint();
        //文本区域定制
        mTextBound = new Rect();
        //描述文本区域定制
        mDescribeTextBound = new Rect();
        mPaintDescribeText.setTextSize(mDescribeTextSize);
        mPaintDescribeText.setAntiAlias(true);
        mPaintDescribeText.getTextBounds(levelDescribeText, 0, levelDescribeText.length(), mDescribeTextBound);
        imageAror_width = mImage_arror.getWidth();
        circleBean = new CircleBean();


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        */
/**
         * 设置宽度,
         *//*

        int specMode = View.MeasureSpec.getMode(widthMeasureSpec); //获取测量模式
        int specSize = View.MeasureSpec.getSize(widthMeasureSpec); //获取测量的宽度

        if (specMode == View.MeasureSpec.EXACTLY)// match_parent , accurate，如果模式是match——parent或者是写死的高度
        {
            mWidth = specSize; //将宽度的设置为预先测量到的高度
        } else {
            // 由图片决定的宽
            int desireByImg = getPaddingLeft() + getPaddingRight() + mImage.getWidth();
            // 由字体决定的宽
            int desireByTitle = getPaddingLeft() + getPaddingRight() + mTextBound.width();

            if (specMode == MeasureSpec.AT_MOST)// wrap_content
            {
                int desire = Math.max(desireByImg, desireByTitle);
                mWidth = Math.min(desire, specSize);
            }
        }

        */
/***
         * 设置高度
         *//*

        specMode = MeasureSpec.getMode(heightMeasureSpec);
        specSize = MeasureSpec.getSize(heightMeasureSpec);
        if (specMode == MeasureSpec.EXACTLY)// match_parent , accurate
        {
            mHeight = specSize;
        } else {
            int desire = getPaddingTop() + getPaddingBottom() + mImage.getHeight() + mTextBound.height();
            if (specMode == MeasureSpec.AT_MOST)// wrap_content
            {
                mHeight = Math.min(desire, specSize);
            }
        }

        setMeasuredDimension(mWidth, mHeight);

    }


    private float imageAror_width;

    public final static int HIGH = 217;
    public final static int LOW = 80;
    public final static int MIDDLE = 164;

    @Override
    protected void onDraw(Canvas canvas) {


        mPaint.setColor(currentLevelColor);
        mPaint.setStyle(Style.FILL);

        i = (int) currentDegree / 2;
        //绘画文字时， 字体大小的设置
        mPaint.setTextSize(mTextSize);

        mLevel = i + "";
        // 计算了描绘字体需要的范围
        mPaint.getTextBounds(mLevel, 0, mLevel.length(), mTextBound);


        switch (mcurrentLevel) {
            case HIGH:
                if (i <= 100) {
                    //画出等级文字
                    canvas.drawText(i + "", mWidth / 2 - mTextBound.width() / 2 - dip2px(getContext(), 2), mHeight / 2 + mTextBound.height() / 2 - dip2px(getContext(), 3), mPaint);
                } else {
                    canvas.drawText(100 + "", mWidth / 2 - mTextBound.width() / 2 - dip2px(getContext(), 2), mHeight / 2 + mTextBound.height() / 2 - dip2px(getContext(), 3), mPaint);
                }
                break;

            case MIDDLE:
                if (i <= 80) {
                    //画出等级文字
                    canvas.drawText(i + "", mWidth / 2 - mTextBound.width() / 2 - dip2px(getContext(), 2), mHeight / 2 + mTextBound.height() / 2 - dip2px(getContext(), 3), mPaint);
                } else {
                    canvas.drawText(80 + "", mWidth / 2 - mTextBound.width() / 2 - dip2px(getContext(), 2), mHeight / 2 + mTextBound.height() / 2 - dip2px(getContext(), 3), mPaint);
                }
                break;

            case LOW:

                if (i <= 40) {
                    //画出等级文字
                    canvas.drawText(i + "", mWidth / 2 - mTextBound.width() / 2 - dip2px(getContext(), 2), mHeight / 2 + mTextBound.height() / 2 - dip2px(getContext(), 3), mPaint);
                } else {
                    canvas.drawText(40 + "", mWidth / 2 - mTextBound.width() / 2 - dip2px(getContext(), 2), mHeight / 2 + mTextBound.height() / 2 - dip2px(getContext(), 3), mPaint);
                }
                break;
        }


        //画出描述文字
        mPaintDescribeText.setColor(Color.BLACK);
        mPaintDescribeText.setTextSize(mDescribeTextSize);
        canvas.drawText(levelDescribeText, mWidth / 2 - mDescribeTextBound.width() / 2, mHeight / 2 + mTextBound.height() / 2 + dip2px(getContext(), 27), mPaintDescribeText);


        //计算居中的矩形范围
        rect.left = mWidth / 2 - mImage.getWidth() / 2;
        rect.right = mWidth / 2 + mImage.getWidth() / 2;
        rect.top = mHeight / 2 - mImage.getHeight() / 2;
        rect.bottom = mHeight / 2 + mImage.getHeight() / 2;

        //画出图片
        canvas.drawBitmap(mImage, null, rect, mPaint);


        mPaint.setColor(getResources().getColor(R.color.hint_textcolor));
        mPaint.setStrokeWidth(7);

        mPaint.setStyle(Paint.Style.STROKE); //设置空心
//            paint.setStrokeWidth(roundWidth); //设置圆环的宽度
        mPaint.setAntiAlias(true);  //消除锯齿

//            paint.setStrokeCap(Paint.Cap.ROUND);


        //画圆
        circleBean.setCircleX(mWidth / 2);
        circleBean.setCircleY(mHeight / 2 + mHeight / 9 + dip2px(getContext(), 4));
        circleBean.setRadius(mImage.getWidth() / 2 + dip2px(getContext(), 10));


        //计算出view中心点和 圆弧中心点的具体,
        distance = distanceBetweenTwoPoint(
                mWidth / 2,
                mHeight / 2,
                circleBean.getCircleX(),
                circleBean.getCircleY()
        );

        //定弧框(弧框的定位要根据,圆心来定位的, 所以,两者都是互调动的, 才能保持同步)

        rectF.top = circleBean.getCircleY() - circleBean.getRadius();
        rectF.left = circleBean.getCircleX() - circleBean.getRadius();
        rectF.right = circleBean.getCircleX() + circleBean.getRadius();
        rectF.bottom = circleBean.getCircleY() + circleBean.getRadius();

        //灰色圆弧
        canvas.drawArc(rectF, 160, 218.5f, false, mPaint);
//        canvas.drawArc(rectF, 0, 360, false, mPaint);

        //画出箭头图片
        mPaint.setColor(Color.BLACK);

        //带颜色的弧度, 这个框的坐标， 根据圆来定
        mPaint.setColor(currentLevelColor);
        canvas.drawArc(rectF, 160, currentDegree, false, mPaint);

        //转起来的图片
        canvas.rotate(currentDegree + 250, circleBean.getCircleX(), circleBean.getCircleY());

        //箭头的矩形框
        rect1 = new Rect(
                mWidth / 2 - mImage_arror.getWidth() / 2,
                mHeight / 2 - (int) circleBean.getRadius() - mImage_arror.getHeight() + (int) distance + mImage_arror.getHeight() / 2,
                mWidth / 2 + mImage_arror.getWidth() / 2,
                mHeight / 2 - (int) circleBean.getRadius() + (int) distance + mImage_arror.getHeight() / 2);

        //将箭头画出来
        canvas.drawBitmap(mImage_arror, null, rect1, mPaint);
    }

    public void setLevelColor(int currentLevel) {

        mcurrentLevel = currentLevel;

        if (currentLevel == HIGH) {
            this.currentLevelColor = getResources().getColor(R.color.high);
            this.mLevel = "高";
            this.mImage = BitmapFactory.decodeResource(getResources(), R.mipmap.chat_high);
            this.mImage_arror = BitmapFactory.decodeResource(getResources(), R.mipmap.chat_high_arrow);
//            mLevel = "100";

        } else if (currentLevel == MIDDLE) {
            this.currentLevelColor = getResources().getColor(R.color.middle);
            this.mLevel = "中";
            this.mImage = BitmapFactory.decodeResource(getResources(), R.mipmap.chat_mid);
            this.mImage_arror = BitmapFactory.decodeResource(getResources(), R.mipmap.chat_mid_arrow);
            mLevel = "80";

        } else if (currentLevel == LOW) {
            this.currentLevelColor = getResources().getColor(R.color.low);
            this.mLevel = "低";
            this.mImage = BitmapFactory.decodeResource(getResources(), R.mipmap.chat_low);
            this.mImage_arror = BitmapFactory.decodeResource(getResources(), R.mipmap.chat_low_arrow);
            mLevel = "40";


        }


    }



    public void setCurrentDegree(int testCount) {

        this.currentDegree = testCount;
        postInvalidate();

    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
    public static double distanceBetweenTwoPoint(double x1,double y1,double x2,double y2){
        double d = (x2-x1)*(x2-x1) + (y2-y1)*(y2-y1);
        return Math.sqrt(d);
    }

}
*/
