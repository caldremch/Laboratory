package com.caldremch.dialog;

/**
 * @author Caldremch
 * @date 2020-07-14 18:45
 * @email caldremch@163.com
 * @describe
 **/
class TempJava {
    int gravity;
    float widthScale;
    boolean cancelOutSide;
    boolean isAllowingStateLoss;

    public int getGravity() {
        return gravity;
    }

    public void setGravity(int gravity) {
        this.gravity = gravity;
    }

    public float getWidthScale() {
        return widthScale;
    }

    public void setWidthScale(float widthScale) {
        this.widthScale = widthScale;
    }

    public boolean isCancelOutSide() {
        return cancelOutSide;
    }

    public void setCancelOutSide(boolean cancelOutSide) {
        this.cancelOutSide = cancelOutSide;
    }

    public boolean isAllowingStateLoss() {
        return isAllowingStateLoss;
    }

    public void setAllowingStateLoss(boolean allowingStateLoss) {
        isAllowingStateLoss = allowingStateLoss;
    }
}
