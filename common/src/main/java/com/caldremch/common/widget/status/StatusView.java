package com.caldremch.common.widget.status;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.IntDef;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;

import com.caldremch.common.R;
import com.caldremch.common.helper.UiHelper;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static android.view.Gravity.CENTER;

/**
 * @author Caldremch
 * @date 2019-08-23 09:43
 * @email caldremch@163.com
 * @describe 用于普通页面加载
 **/
// TODO: 2019-08-23 添加NetWatchDog , 网络恢复,自动请求
public class StatusView extends FrameLayout implements IStatusView, LifecycleObserver {

    public static final int VIEW_STATE_CONTENT = 0;
    public static final int VIEW_STATE_ERROR = 1;
    public static final int VIEW_STATE_LOADING = 2;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({VIEW_STATE_CONTENT, VIEW_STATE_ERROR, VIEW_STATE_LOADING})
    public @interface ViewState {
    }

    //一版界面状态,不含empty
    private View mContentView;
    private View mErrorView;
    private View mLoadingView;
    private LoadingCircleRing mLoadWidget;
    private IStatusView overrideStatusView;

    private boolean handleContentView = true;

    public View getContentView() {
        return mContentView;
    }

    public StatusView(@NonNull Fragment context, View fragmentRootView) {
        super(context.getContext());
        //重写回调
        if (context instanceof IStatusView) {
            this.overrideStatusView = (IStatusView) context;
        }

        //生命周期
        if (context instanceof LifecycleOwner) {
            ((LifecycleOwner) context).getLifecycle().addObserver(this);
        }

        mContentView = fragmentRootView;
        addSubView(mContentView);
        initStatusView();
    }

    /**
     * 将 ContentView 加到了当前容器
     *
     * @param context
     * @param layoutResID
     */
    public StatusView(@NonNull Context context, @LayoutRes int layoutResID) {
        super(context);

        //重写回调
        if (context instanceof IStatusView) {
            this.overrideStatusView = (IStatusView) context;
        }

        //生命周期
        if (context instanceof LifecycleOwner) {
            ((LifecycleOwner) context).getLifecycle().addObserver(this);
        }

        mContentView = inflate(getContext(), layoutResID, null);
        addSubView(mContentView);

        initStatusView();
    }

    /**
     * 如果直接传递了 contentView 进来, 那么不会对 contentView 进行隐藏或者显示, 而是知识单纯的出现 loading, error状态,
     * 这两种状态任意一个Gone, 都显示出 contentView 内容
     *
     * @param context
     */
    public StatusView(@NonNull Context context) {
        super(context);

        //重写回调
        if (context instanceof IStatusView) {
            this.overrideStatusView = (IStatusView) context;
        }

        //生命周期
        if (context instanceof LifecycleOwner) {
            ((LifecycleOwner) context).getLifecycle().addObserver(this);
        }

        handleContentView = false; //不处理
        initStatusView();
    }

    private void initStatusView() {
        //提供可重写的errorView
        if (overrideStatusView != null) {
            mErrorView = overrideStatusView.getErrorView() == null ? initErrorView() : overrideStatusView.getErrorView();
        } else {
            mErrorView = initErrorView();
        }

        if (mErrorView != null) {
            addSubView(mErrorView);
            mErrorView.setVisibility(GONE);
        }

        mLoadingView = initLoadingView();

        if (mLoadingView != null) {
            addSubView(mLoadingView);
            mLoadingView.setVisibility(GONE);
        }

        //默认状态
    }

    public void addSubView(View view) {
        addView(view, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    public StatusView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public StatusView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public View initErrorView() {
        mErrorView = LayoutInflater.from(getContext()).inflate(R.layout.common_loading_error, null);

        TextView textView = mErrorView.findViewById(R.id.tv_reload);
        if (textView != null) {
            textView.setOnClickListener(v -> {
                if (overrideStatusView != null) {
                    setViewStatus(VIEW_STATE_LOADING);
                    overrideStatusView.reTry();
                }
            });
        }

        return mErrorView;
    }

    @Override
    public View initLoadingView() {
        mLoadWidget = new LoadingCircleRing(getContext());
        FrameLayout frameLayout = new FrameLayout(getContext());
        frameLayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        LayoutParams params = new LayoutParams(UiHelper.dp2px(getContext(), 30), UiHelper.dp2px(getContext(), 30));
        params.gravity = CENTER;
        frameLayout.addView(mLoadWidget, params);
        return frameLayout;
    }

    @Override
    public void startLoading() {
        mErrorView.setVisibility(GONE);
        mContentView.setVisibility(GONE);
        mLoadingView.setVisibility(VISIBLE);
        if (mLoadWidget != null && !mLoadWidget.isRunning()) {
            mLoadWidget.startAnim();
        }
    }

    @Override
    public void stopLoading() {
        if (mLoadWidget != null && mLoadWidget.isRunning()) {
            mLoadWidget.stopAnim();
        }
    }

    @Override
    public View getErrorView() {
        return mErrorView;
    }

    @Override
    public void setViewStatus(@StatusView.ViewState int status) {
        switch (status) {
            case VIEW_STATE_LOADING:
                startLoading();
                break;
            case VIEW_STATE_CONTENT:
                stopLoading();
                if (mErrorView.getVisibility() == VISIBLE) {
                    mErrorView.setVisibility(GONE);
                }

                if (mContentView.getVisibility() == GONE) {
                    mContentView.setVisibility(VISIBLE);
                }

                if (mLoadingView.getVisibility() == VISIBLE) {
                    mLoadingView.setVisibility(GONE);
                }

                break;
            case VIEW_STATE_ERROR:
                stopLoading();
                mErrorView.setVisibility(VISIBLE);
                mContentView.setVisibility(GONE);
                mLoadingView.setVisibility(GONE);
                break;

        }
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy() {
        stopLoading();
    }

}
