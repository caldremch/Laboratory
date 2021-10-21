package com.date.picker.panel;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.date.picker.panel.entity.DayData;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Caldremch
 * @email finishmo@qq.com
 * @date 2021/9/8 10:49
 * @description
 */
public class DatePickerPanelView extends ConstraintLayout {

    private int combineType = 0;
    private MagicIndicator magicIndicator;
    private TextView tvConfirm;
    private ViewPager2 vp;
    private List<IVpPage> pages = new ArrayList<>();
    private DatePickerPanelVpAdapter vpAdapter;
    private final int row = 7;//日期面板显示6行 + 星期几 1
    private int calendarItemHeight = 0;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日");

    public DatePickerPanelView(Context context) {
        this(context, null);
    }

    public DatePickerPanelView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }


    public DatePickerPanelView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.DatePickerPanelView);
        combineType = typedArray.getInt(R.styleable.DatePickerPanelView_combine, 0);
        typedArray.recycle();
        inflateView();
    }


    private void inflateView() {
        //初始话先重置
        DatePickerPanelVpAdapter.InnerData.selectedDate = -1;
        DatePickerPanelVpAdapter.InnerData.limitType = 0;

        LayoutInflater.from(getContext()).inflate(R.layout.date_picker_panel_view, this, true);
        magicIndicator = findViewById(R.id.indicator);
        tvConfirm = findViewById(R.id.tv_confirm);
        vp = findViewById(R.id.vp);
        vp.setUserInputEnabled(false);
        ViewGroup.LayoutParams params = vp.getLayoutParams();
        params.height = calculateVp2Height();
        vp.setLayoutParams(params);
        initView();

        tvConfirm.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback != null) {
                    callback.onConfirm(currentSelectedTimeStamp);
                }
            }
        });
    }

    private Callback callback;

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    public interface Callback {
        void onConfirm(long timeStamp);
    }


    private int calculateVp2Height() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.day_item, null);
        TextView textView = view.findViewById(R.id.tv_title);
        int widthMeasureSpec = MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int heightMeasureSpec = MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        textView.measure(widthMeasureSpec, heightMeasureSpec);
        calendarItemHeight = textView.getMeasuredHeight();
        return calendarItemHeight * row;
    }

    private void initView() {
        initPage();
        initIndicator();
    }

    /**
     * 日历初始化后执行
     *
     * @param type
     */
    public void setLimitType(int type) {
        DatePickerPanelVpAdapter.InnerData.limitType = type;
        if (vpAdapter != null) {
            vpAdapter.notifyDataSetChanged();
        }
    }

    private int calIndex = -1;

    private int findCalendarIndex() {
        if (calIndex != -1) {
            return calIndex;
        }
        for (int i = 0; i < pages.size(); i++) {
            if (pages.get(i) instanceof IVpPage.CalendarPage) {
                calIndex = i;
                break;
            }
        }
        return calIndex;
    }

    private IVpPage.CalendarPage calendarPage;

    private void initPage() {
        vpAdapter = new DatePickerPanelVpAdapter(calendarItemHeight);
        calendarPage = (IVpPage.CalendarPage) IVpPage.CALENDAR_PAGE;
        pages.add(calendarPage);
        vpAdapter.setPages(pages);
        vp.setAdapter(vpAdapter);
        vpAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull @NotNull RecyclerView.Adapter adapter, DayData dayData) {
                currentSelectedTimeStamp = dayData.mills;
                updateTitle(currentSelectedTimeStamp);
            }
        });


    }

    private void updateTitle(long timeStamp) {
        String title = dateFormat.format(timeStamp);
        calendarPage.title = title;
        commonNavigator.notifyDataSetChanged();
    }

    private long currentSelectedTimeStamp = new Date().getTime();

    private CommonNavigator commonNavigator;

    public void setSelectedDate(long timeStamp) {
        DatePickerPanelVpAdapter.InnerData.selectedDate = DatePickerPanelUtils.clearHMS(timeStamp);
        updateTitle(timeStamp);
        if (vpAdapter != null) {
            vpAdapter.notifyDataSetChanged();
        }
    }

    private void initIndicator() {

        commonNavigator = new CommonNavigator(getContext());
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return combineType == 0 ? 1 : 2;
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                Log.d("getTitleView", "getTitleView: ");
                ColorTransitionPagerTitleView colorTransitionPagerTitleView = new ColorTransitionPagerTitleView(context);
                colorTransitionPagerTitleView.setNormalColor(Color.GRAY);
                colorTransitionPagerTitleView.setTextSize(16);
                colorTransitionPagerTitleView.setSelectedColor(Color.parseColor("#21272A"));
                colorTransitionPagerTitleView.setText(pages.get(index).getTitle());
                colorTransitionPagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        vp.setCurrentItem(index);
                    }
                });
                return colorTransitionPagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                if (combineType == 0) return null;
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setMode(LinePagerIndicator.MODE_WRAP_CONTENT);
                return indicator;
            }
        });
        magicIndicator.setNavigator(commonNavigator);

        vp.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                magicIndicator.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                magicIndicator.onPageSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                magicIndicator.onPageScrollStateChanged(state);
            }
        });
    }

}
