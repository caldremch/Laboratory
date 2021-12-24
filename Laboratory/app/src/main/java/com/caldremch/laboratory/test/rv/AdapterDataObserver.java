package com.caldremch.laboratory.test.rv;

import androidx.annotation.Nullable;

/**
 * @author Caldremch
 * @date 2020-07-23 09:36
 * @email caldremch@163.com
 * @describe
 **/
class AdapterDataObserver {
    public void onChanged() {
        // Do nothing
    }

    public void onItemRangeChanged(int positionStart, int itemCount) {
        // do nothing
    }

    public void onItemRangeChanged(int positionStart, int itemCount, @Nullable Object payload) {
        // fallback to onItemRangeChanged(positionStart, itemCount) if app
        // does not override this method.
        onItemRangeChanged(positionStart, itemCount);
    }

    public void onItemRangeInserted(int positionStart, int itemCount) {
        // do nothing
    }

    public void onItemRangeRemoved(int positionStart, int itemCount) {
        // do nothing
    }

    public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
        // do nothing
    }
}
