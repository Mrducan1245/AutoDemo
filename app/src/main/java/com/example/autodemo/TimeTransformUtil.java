package com.example.autodemo;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeTransformUtil {
    public static long getCurrentMills(){
        return System.currentTimeMillis();
    }

    /**
     * 日期转时间戳
     * @param date
     * @return
     */
    public static long date2Mills(Date date){
        return date.getTime();
    }

    /**
     * 时间戳转日期
     * @param mills
     * @return
     */
    public static Date mills2Date(long mills){
        return new Date(mills);
    }

    /**
     * 日期字符串转时间戳
     * @param dateStr
     * @param formatStr
     * @return
     */
    public static long string2Mills(String dateStr,String formatStr){
        try {
            @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatStr);
            return simpleDateFormat.parse(dateStr).getTime();

        }catch (Exception e){
            return 0L;
        }
    }

    /**
     * calendar转时间戳
     * @param calendar
     * @return
     */
    public static long calendar2Mills(Calendar calendar){
        return calendar.getTime().getTime();
    }
}
