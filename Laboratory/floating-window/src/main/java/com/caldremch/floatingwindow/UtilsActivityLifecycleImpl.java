package com.caldremch.floatingwindow;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.Nullable;

import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * <pre>
 *     author: blankj
 *     blog  : http://blankj.comafterNodeAccess
 *     time  : 2020/03/19
 *     desc  :
 * </pre>
 */
final class UtilsActivityLifecycleImpl implements Application.ActivityLifecycleCallbacks {

    static final UtilsActivityLifecycleImpl INSTANCE = new UtilsActivityLifecycleImpl();
    private final LinkedList<Activity> mActivityList = new LinkedList<>();

    private final List<OnAppStatusChangedListener> mStatusListeners = new CopyOnWriteArrayList<>();
    private int mForegroundCount = 0;
    private int mConfigCount = 0;
    private boolean mIsBackground = false;

    void init(Application app) {
        app.registerActivityLifecycleCallbacks(this);
    }

    void addOnAppStatusChangedListener(final OnAppStatusChangedListener listener) {
        mStatusListeners.add(listener);
    }

    void removeOnAppStatusChangedListener(final OnAppStatusChangedListener listener) {
        mStatusListeners.remove(listener);
    }


    boolean isAppForeground() {
        return !mIsBackground;
    }


    ///////////////////////////////////////////////////////////////////////////
    // lifecycle start
    ///////////////////////////////////////////////////////////////////////////
    @Override
    public void onActivityPreCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {/**/}

    @Override
    public void onActivityCreated(@NonNull Activity activity, Bundle savedInstanceState) {

        if (mActivityList.size() == 0) {
            postStatus(activity, true);
        }
        setTopActivity(activity);
    }

    private void setTopActivity(final Activity activity) {
        if (mActivityList.contains(activity)) {
            if (!mActivityList.getFirst().equals(activity)) {
                mActivityList.remove(activity);
                mActivityList.addFirst(activity);
            }
        } else {
            mActivityList.addFirst(activity);
        }
    }

    @Override
    public void onActivityPostCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {/**/}

    @Override
    public void onActivityPreStarted(@NonNull Activity activity) {/**/}

    @Override
    public void onActivityStarted(@NonNull Activity activity) {
        if (!mIsBackground) {
            setTopActivity(activity);
        }
        if (mConfigCount < 0) {
            ++mConfigCount;
        } else {
            ++mForegroundCount;
        }
    }

    @Override
    public void onActivityPostStarted(@NonNull Activity activity) {/**/}

    @Override
    public void onActivityPreResumed(@NonNull Activity activity) {/**/}

    @Override
    public void onActivityResumed(@NonNull final Activity activity) {
        setTopActivity(activity);
        if (mIsBackground) {
            mIsBackground = false;
            postStatus(activity, true);
        }
    }

    @Override
    public void onActivityPostResumed(@NonNull Activity activity) {/**/}

    @Override
    public void onActivityPrePaused(@NonNull Activity activity) {/**/}

    @Override
    public void onActivityPaused(@NonNull Activity activity) {
    }

    @Override
    public void onActivityPostPaused(@NonNull Activity activity) {/**/}

    @Override
    public void onActivityPreStopped(@NonNull Activity activity) {/**/}

    @Override
    public void onActivityStopped(Activity activity) {
        if (activity.isChangingConfigurations()) {
            --mConfigCount;
        } else {
            --mForegroundCount;
            if (mForegroundCount <= 0) {
                mIsBackground = true;
                postStatus(activity, false);
            }
        }
    }

    @Override
    public void onActivityPostStopped(@NonNull Activity activity) {/**/}

    @Override
    public void onActivityPreSaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {/**/}

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {/**/}

    @Override
    public void onActivityPostSaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {/**/}

    @Override
    public void onActivityPreDestroyed(@NonNull Activity activity) {/**/}

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {
        mActivityList.remove(activity);
    }

    @Override
    public void onActivityPostDestroyed(@NonNull Activity activity) {/**/}


    private void postStatus(final Activity activity, final boolean isForeground) {
        if (mStatusListeners.isEmpty()) return;
        for (OnAppStatusChangedListener statusListener : mStatusListeners) {
            if (isForeground) {
                statusListener.onForeground();
            } else {
                statusListener.onBackground();
            }
        }
    }

}
