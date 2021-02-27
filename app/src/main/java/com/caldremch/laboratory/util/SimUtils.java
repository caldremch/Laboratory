package com.caldremch.laboratory.util;

import android.Manifest;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.lang.reflect.Method;

import pub.devrel.easypermissions.EasyPermissions;

/**
 *https://github.com/xiangcman/DoubleSimCard-master
 *
 * 在 androidP 上 getNetworkOperatorName 等反射方法已经不行 被标记为不可用
 */
@Deprecated
public class SimUtils {
    private static final String TAG = SimUtils.class.getSimpleName();
    private static final String SIM_STATE = "getSimState";
    private static final String SIM_OPERATOR_NAME = "getNetworkOperatorName";
    private static final String SIM_NETWORK_TYPE = "getNetworkType";
    private static final String SIM_IMEI = "getImei";
    private static final String SIM_LINE_NUMBER = "getLine1Number";

    public static String getSimPhonenumber(Context context, int slotIdx) {
        if (hasPermission(context)) {
            Log.d(TAG, "READ_PHONE_STATE permission has BEEN granted to getSimPhonenumber().");
            if (getSimStateBySlotIdx(context, slotIdx)) {
                return (String) getSimByMethod(context, SIM_LINE_NUMBER, getSubidBySlotId(context, slotIdx));
            }
            return null;
        } else {
            Log.d(TAG, "READ_PHONE_STATE permission has NOT been granted to getSimPhonenumber().");
            return null;
        }
    }


    private static boolean hasPermission(Context context){
        return EasyPermissions.hasPermissions(context, Manifest.permission.READ_PHONE_STATE);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public static int getSimNetworkType(Context context, int slotIdx) {
        if (hasPermission(context)) {
            Log.d(TAG, "READ_PHONE_STATE permission has BEEN granted to getSimNetworkType().");
            if (getSimStateBySlotIdx(context, slotIdx)) {
                return (int) getSimByMethod(context, SIM_NETWORK_TYPE, getSubidBySlotId(context, slotIdx));
            }
        } else {
            Log.d(TAG, "READ_PHONE_STATE permission has NOT been granted to getSimNetworkType().");
        }
        return TelephonyManager.NETWORK_TYPE_UNKNOWN;
    }

    public static String getSimNetworkName(Context context, int slotIdx) {
        return getNetworkName(getSimNetworkType(context, slotIdx));
    }

    public static String getSimOperatorName(Context context, int slotIdx) {
        if (getSimStateBySlotIdx(context, slotIdx)) {
            return (String) getSimByMethod(context, SIM_OPERATOR_NAME, getSubidBySlotId(context, slotIdx));
        }
        return null;
    }

    /**
     * @param context
     * @param slotIdx:0(sim1),1(sim2)
     * @return
     */
    public static boolean getSimStateBySlotIdx(Context context, int slotIdx) {
        boolean isReady = false;
        Object getSimState = getSimByMethod(context, SIM_STATE, slotIdx);
        if (getSimState != null) {
            int simState = Integer.parseInt(getSimState.toString());
            if ((simState != TelephonyManager.SIM_STATE_ABSENT) && (simState != TelephonyManager.SIM_STATE_UNKNOWN)) {
                isReady = true;
            }
        }
        return isReady;
    }

    public static Object getSimByMethod(Context context, String method, int param) {
        TelephonyManager telephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        try {
            Class<?> telephonyClass = Class.forName(telephony.getClass().getName());
            Class<?>[] parameter = new Class[1];
            parameter[0] = int.class;
            Method getSimState = telephonyClass.getMethod(method, parameter);
            Object[] obParameter = new Object[1];
            obParameter[0] = param;
            Object ob_phone = getSimState.invoke(telephony, obParameter);

            if (ob_phone != null) {
                return ob_phone;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static class CurrentNetwork {
        public String whichSim;//那张卡
        public String netWorkName;//几G网络
        public String operateName;//卡生厂商
    }

    public static CurrentNetwork getCurrentNetwork(Context context) {
        CurrentNetwork currentNetwork = new CurrentNetwork();
        ConnectivityManager connectionManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectionManager.getActiveNetworkInfo();
        TelephonyManager tm = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        Log.d(TAG, "state:" + tm.getSimState());
        if (networkInfo != null) {
            if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                boolean status = networkInfo.isConnected();
                int sim1NetWorkType = getSimNetworkType(context, 0);
                int sim2NetWorkType = getSimNetworkType(context, 1);
                if (networkInfo.getSubtype() == sim1NetWorkType) {
                    if (getSimStateBySlotIdx(context, 0)) {
                        currentNetwork.netWorkName = getNetworkName(sim1NetWorkType);
                        currentNetwork.operateName = getSimOperatorName(context, 0);
                        currentNetwork.whichSim = "卡1";
                    }
                } else if (networkInfo.getSubtype() == sim2NetWorkType) {
                    if (getSimStateBySlotIdx(context, 1)) {
                        currentNetwork.netWorkName = getNetworkName(sim2NetWorkType);
                        currentNetwork.operateName = getSimOperatorName(context, 1);
                        currentNetwork.whichSim = "卡2";
                    }
                }
            }
        } else {
            // Logger.d(TAG, "network info is null: ");
        }
        return currentNetwork;
    }

    public static String getNetworkName(int networkType) {
        switch (networkType) {
            case TelephonyManager.NETWORK_TYPE_GPRS:
            case TelephonyManager.NETWORK_TYPE_EDGE:
            case TelephonyManager.NETWORK_TYPE_CDMA:
            case TelephonyManager.NETWORK_TYPE_1xRTT:
            case TelephonyManager.NETWORK_TYPE_IDEN:
                return "2G";
            case TelephonyManager.NETWORK_TYPE_UMTS:
            case TelephonyManager.NETWORK_TYPE_EVDO_0:
            case TelephonyManager.NETWORK_TYPE_EVDO_A:
            case TelephonyManager.NETWORK_TYPE_HSDPA:
            case TelephonyManager.NETWORK_TYPE_HSUPA:
            case TelephonyManager.NETWORK_TYPE_HSPA:
            case TelephonyManager.NETWORK_TYPE_EVDO_B:
            case TelephonyManager.NETWORK_TYPE_EHRPD:
            case TelephonyManager.NETWORK_TYPE_HSPAP:
                return "3G";
            case TelephonyManager.NETWORK_TYPE_LTE:
                return "4G";
            case TelephonyManager.NETWORK_TYPE_NR:
                return "5G";
            default:
                return "UNKNOWN";
        }
    }

    /**
     * to
     *
     * @param context
     * @param slotId
     * @return
     */
    public static int getSubidBySlotId(Context context, int slotId) {
        SubscriptionManager subscriptionManager = (SubscriptionManager) context.getSystemService(
                Context.TELEPHONY_SUBSCRIPTION_SERVICE);
        try {
            Class<?> telephonyClass = Class.forName(subscriptionManager.getClass().getName());
            Class<?>[] parameter = new Class[1];
            parameter[0] = int.class;
            Method getSimState = telephonyClass.getMethod("getSubId", parameter);
            Object[] obParameter = new Object[1];
            obParameter[0] = slotId;
            Object ob_phone = getSimState.invoke(subscriptionManager, obParameter);

            if (ob_phone != null) {
                Log.d(TAG, "slotId:" + slotId + ";" + ((int[]) ob_phone)[0]);
                return ((int[]) ob_phone)[0];
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;

    }

}
