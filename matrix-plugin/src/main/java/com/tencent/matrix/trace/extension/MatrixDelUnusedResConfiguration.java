package com.tencent.matrix.trace.extension;

import java.util.HashSet;
import java.util.Set;

/**
 * @auther Caldremch
 * @email finishmo@qq.com
 * @date 3/2/21 14:42
 * @description
 */
public class MatrixDelUnusedResConfiguration {

    boolean enable;
    String variant;
    boolean needSign;
    boolean shrinkArsc;
    String apksignerPath;
    Set<String> unusedResources;
    Set<String> ignoreResources;

   public MatrixDelUnusedResConfiguration() {
        enable = false;
        variant = "";
        needSign = false;
        shrinkArsc = false;
        apksignerPath = "";
        unusedResources = new HashSet<>();
        ignoreResources = new HashSet<>();
    }

    public boolean isEnable() {
        return enable;
    }

    public String getVariant() {
        return variant;
    }

    public boolean isNeedSign() {
        return needSign;
    }

    public boolean isShrinkArsc() {
        return shrinkArsc;
    }

    public String getApksignerPath() {
        return apksignerPath;
    }

    public Set<String> getUnusedResources() {
        return unusedResources;
    }

    public Set<String> getIgnoreResources() {
        return ignoreResources;
    }
}
