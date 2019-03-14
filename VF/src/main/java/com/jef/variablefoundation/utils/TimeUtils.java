package com.jef.variablefoundation.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by mr.lin on 2018/12/4
 * 时间工具
 */
public class TimeUtils {

    //时间戳
    public static String timeStamp() {
        return String.valueOf(System.currentTimeMillis());
    }

    //当前所在时段 上下晚
    public static int getCurrentSlot() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        if (hour > 5 && hour < 7) {
            return 0;
        } else if (hour > 7 && hour < 9) {
            return 1;
        } else if (hour >= 9 && hour <= 12) {
            return 2;
        } else if (hour > 12 && hour < 14) {
            return 3;
        } else if (hour >= 14 && hour < 18) {
            return 4;
        } else if (hour == 18) {
            return 5;
        } else if (hour >= 19 && hour < 24) {
            return 6;
        } else {
            return 7;
        }
    }

    //获取当前时间
    public static String getCurrentTime(String format) {
        Date date = new Date();
        return getFormat(format).format(date);
    }

    //获取格式
    public static SimpleDateFormat getFormat(String format) {
        return new SimpleDateFormat(format);
    }

    //获取日期
    public static String getTime(Date date) {
        return getFormat("yyyy-MM-dd").format(date);
    }

}
