package com.yunny.channel.common.util;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.yunny.channel.common.exception.MyException;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 时间显示格式化
 *
 * @author lj
 */
public class DateFormatUtil {

    /**
     * 时间格式化
     *
     * @param date
     * @return
     */
    public static String format(Date date) {
        long minutes = DateUtil.between(date, new Date(), DateUnit.MINUTE);
        if (minutes < 60L) {
            return (minutes <= 0 ? 1 : minutes) + "分钟前";
        }
        if (minutes < 24L * 60L) {
            long hours = minutes / 60;
            return hours + "小时前";
        }
        if (minutes < 48L * 60L) {
            return "1天前";
        }
        if (minutes < 72L * 60L) {
            return "2天前";
        }
        if (minutes < 96L * 60L) {
            return "3天前";
        }
        return DateUtil.format(date, DatePattern.NORM_DATE_PATTERN);
    }

    public static void main(String[] args) {
        List<String> monthBetween = getMonthBetween("2019-01", "2020-03");
        for (String month : monthBetween) {
            System.out.println(DateUtil.format(DateUtil.parseDate(month + "-01"), "yy年M月"));
        }

    }

    /**
     * 获取两个日期之间的所有月份
     *
     * @param minMonth 格式：yyyy-MM
     * @param maxMonth 格式：yyyy-MM
     * @return
     */
    public static List<String> getMonthBetween(String minMonth, String maxMonth) {
        List<String> result = CollUtil.toList();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");// 格式化为年月

            Calendar min = Calendar.getInstance();
            Calendar max = Calendar.getInstance();

            min.setTime(sdf.parse(minMonth));
            min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), 1);

            max.setTime(sdf.parse(maxMonth));
            max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), 2);

            while (min.before(max)) {
                result.add(sdf.format(min.getTime()));
                min.add(Calendar.MONTH, 1);
            }
        } catch (Exception e) {
            throw new MyException("获取月份区间异常");
        }

        return result;
    }

    // 获取指定日期的开始时间
    public static Date getStartOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    // 获取指定日期的结束时间
    public static Date getEndOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }


}
