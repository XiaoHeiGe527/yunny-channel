package com.yunny.channel.util;

import com.yunny.channel.common.constant.DatePatternConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

/**
 * 日、星期（周）、旬、月、季度、年等时间工具类
 */
public class DateUtil {



    private static Logger logger= LoggerFactory.getLogger(DateUtil.class);

    private final static SimpleDateFormat shortSdf = new SimpleDateFormat(DatePatternConstants.NORM_DATE_PATTERN);
    private final static SimpleDateFormat longHourSdf = new SimpleDateFormat(DatePatternConstants.NORM_DATETIME_HOUR_PATTERN);
    private final static SimpleDateFormat sdf = new SimpleDateFormat(DatePatternConstants.NORM_DATETIME_PATTERN);
    private final static SimpleDateFormat longSdf = new SimpleDateFormat(DatePatternConstants.NORM_DATETIME_MS_PATTERN);
    private final static SimpleDateFormat shortMon= new SimpleDateFormat(DatePatternConstants.NORM_MONTH_PATTERN);
    /**
     * 根据要求的格式，格式化时间，返回String * * @param format 默认：yyyy-MM-dd HH:mm:ss * @param time 要格式化的时间 * @return 时间字符串
     */
    public static String toStr(String format, Date time) {
        SimpleDateFormat df = null;
        if (null == format) {
            df = new SimpleDateFormat(DatePatternConstants.NORM_DATETIME_MS_PATTERN);
        } else {
            df = new SimpleDateFormat(format);
        }
        try {
            return df.format(time);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取指定时间之前x日的00:00:00.
     *
     * @param day 应为负数
     * @return the date
     */
    public static Date minusStart(int day, Date date) {
        // if (day == 1) {
        // day = 0;
        // }
        day = -day;
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, day);
        cal.set(Calendar.HOUR_OF_DAY, 00);
        cal.set(Calendar.MINUTE, 00);
        cal.set(Calendar.SECOND, 00);
        cal.set(Calendar.MILLISECOND, 00);
        return cal.getTime();
    }


    /**
     * 获取指定时间之前x日的23:59:59.
     *
     * @param day 应为负数
     * @return the date
     */
    public static Date minusEnd(int day, Date date) {
        // if (day == 1) {
        // day = 0;
        // }
        day = -day;
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, day);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 59);
        return cal.getTime();
    }


    /**
     * 获取指定时间之前x日的xx:00:00.
     * @param day
     * @param date
     * @return
     */
    public static Date minusStartHour(int day, Date date) {
        // if (day == 1) {
        // day = 0;
        // }
        day = -day;
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, day);
        cal.set(Calendar.MINUTE, 00);
        cal.set(Calendar.SECOND, 00);
        cal.set(Calendar.MILLISECOND, 00);
        return cal.getTime();
    }


    /**
     * 方法1
     * @param date
     * @return
     */
    public static LocalDateTime date2LocalDateTime1(Date date) {
        if(null == date) {
            return null;
        }
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

}



