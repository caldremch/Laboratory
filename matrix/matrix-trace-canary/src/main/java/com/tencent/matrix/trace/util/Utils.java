package com.tencent.matrix.trace.util;

import com.tencent.matrix.util.DeviceUtil;

public class Utils {

    public static String getStack() {
        StackTraceElement[] trace = new Throwable().getStackTrace();
        return getStack(trace);
    }

    public static String getStack(StackTraceElement[] trace) {
        return getStack(trace, "", -1);
    }

    public static String getStack(StackTraceElement[] trace, String preFixStr, int limit) {
        if ((trace == null) || (trace.length < 3)) {
            return "";
        }
        if (limit < 0) {
            limit = Integer.MAX_VALUE;
        }
        StringBuilder t = new StringBuilder(" \n");
        for (int i = 3; i < trace.length - 3 && i < limit; i++) {
            t.append(preFixStr);
            t.append("at ");
            t.append(trace[i].getClassName());
            t.append(":");
            t.append(trace[i].getMethodName());
            t.append("(" + trace[i].getLineNumber() + ")");
            t.append("\n");

        }
        return t.toString();
    }

    public static String calculateCpuUsage(long threadMs, long ms) {
        if (threadMs <= 0) {
            return ms > 1000 ? "0%" : "100%";
        }

        if (threadMs >= ms) {
            return "100%";
        }

        return String.format("%.2f", 1.f * threadMs / ms * 100) + "%";
    }

    public static boolean isEmpty(String str) {
        return null == str || str.equals("");
    }

    /**
     *      *
     * adb shell root获取后, 可以查看文件
     * /proc/12140/stat, 内容如下
     * 12140 (sh) S 12105 642 0 0 -1 4210688 737 6506 0 2 0 1 5 4 10 -10 1 0 26648747 2182029312 812 18446744073709551615 1 1 0 0 0 0 0 1 1208083710 0 0 0 17 5 0 0 0 0 0 0 0 0 0 0 0 0 0
     * 进程id
     *
     *
     */
    public static int[] getProcessPriority(int pid) {
        String name = String.format("/proc/%s/stat", pid);
        int priority = Integer.MIN_VALUE;
        int nice = Integer.MAX_VALUE;
        try {
            String content = DeviceUtil.getStringFromFile(name).trim();
            String[] args = content.split(" ");
            if (args.length >= 19) {
                //18:priority: 进程优先级, 此次等于10.
                //Nice值是类UNIX操作系统中表示静态优先级的数值。每个进程都有自己的静态优先级，优先级高的进程得以优先运行。
                //19:nice: 进程优先级, 此次等于10. “Nice值”这个名称来自英文单词nice，意思为友好。Nice值越高，这个进程越“友好”，就会让给其他进程越多的时间。
                priority = Integer.parseInt(args[17].trim());
                nice = Integer.parseInt(args[18].trim());
            }
        } catch (Exception e) {
            return new int[]{priority, nice};
        }
        return new int[]{priority, nice};
    }

    public static String formatTime(final long timestamp) {
        return new java.text.SimpleDateFormat("[yy-MM-dd HH:mm:ss]").format(new java.util.Date(timestamp));
    }
}
