package com.caldremch.laboratory.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import java.lang.reflect.Method;

/**
 * @auther Caldremch
 * @email finishmo@qq.com
 * @date 2/26/21 14:24
 * @description 来源网络
 */
public class SimUtilsEx {
    /**
     * 获取网络运营商名称
     * <p>中国移动、如中国联通、中国电信</p>
     *
     * @return 运营商名称
     */
    public static String getNetworkOperatorName(Context context) {
        String  opeType = "unknown";
        // No sim
        if (!hasSim(context.getApplicationContext())) {
            return opeType;
        }

        TelephonyManager tm = (TelephonyManager) context.getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
        String operator = tm.getSimOperator();
        if ("46001".equals(operator) || "46006".equals(operator) || "46009".equals(operator)) {
            opeType = "中国联通";
        } else if ("46000".equals(operator) || "46002".equals(operator) || "46004".equals(operator) || "46007".equals(operator)) {
            opeType = "中国移动";

        } else if ("46003".equals(operator) || "46005".equals(operator) || "46011".equals(operator)) {
            opeType = "中国电信";
        } else {
            opeType = "unknown";
        }
        return opeType;
    }

    /**
     * 检查手机是否有sim卡
     */
    private static boolean hasSim(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String operator = tm.getSimOperator();
        if (TextUtils.isEmpty(operator)) {
            return false;
        }
        return true;
    }

    /**
     * 判断数据流量开关是否打开
     */
    public static boolean isMobileDataEnabled(Context context) {
        try {
            Method method = ConnectivityManager.class.getDeclaredMethod("getMobileDataEnabled");
            method.setAccessible(true);
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            return (Boolean) method.invoke(connectivityManager);
        } catch (Throwable t) {
            return false;
        }
    }
}
