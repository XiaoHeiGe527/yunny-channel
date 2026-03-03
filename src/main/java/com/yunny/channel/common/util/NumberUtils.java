package com.yunny.channel.common.util;



import cn.hutool.core.date.DatePattern;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author Mr. Du
 * @explain
 * @createTime 2019/12/27 14:34
 * @motto The more learn, the more found his ignorance.
 */

public class NumberUtils {

    private final static SimpleDateFormat shortSdf = new SimpleDateFormat("yyyyMMdd");
    private final static SimpleDateFormat shortMon= new SimpleDateFormat("yyyyMM");

    /**
     * 获取编号
     * @param prefix 前缀 根据需求
     * @param centre 创建时间格式“20191128”
     * @param number 序列号 redis获取
     * @param  length 长度
     * @return
     */
    public static String createNumber(String prefix,String centre,long number,int length){

        String s=Long.toHexString(number);
        StringBuilder stringBuilder=new StringBuilder(prefix).append(centre);
        for(int i=s.length();i< length ;i++){
            stringBuilder.append("0");
        }
        stringBuilder.append(s);
        return stringBuilder.toString();
    }

//    public static void main(String[] args) {
//
//        System.out.println(  NumberUtils.createNumber("测试","20240428",110,3));
//    }


    /**
     * 获取当前时间格式化 精准到天
     * @param date
     * @return
     */
    public static String formatLocalDateTimeString(LocalDateTime date) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DatePattern.PURE_DATE_PATTERN);
        return date.format(dtf);
    }

    //

    /**
     * 获取当前时间格式化 精确到月
     * @param date
     * @return
     */
    public static String formatLocalDateTimeStringMonth(LocalDateTime date) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DatePattern.NORM_MONTH_PATTERN);
        return date.format(dtf);
    }


    /**
     * 获取当前时间格式化 精确到秒
     * @param date
     * @return
     */
    public static String formatLocalDateTimeSStringMonth(LocalDateTime date) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DatePattern.PURE_DATETIME_PATTERN);
        return date.format(dtf);
    }




    /**
     * 获取当前时间格式化 精准到毫秒
     * @param date
     * @return
     */
    public static String formatLocalDateTimeMsString(LocalDateTime date) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DatePattern.PURE_DATETIME_MS_PATTERN);
        return date.format(dtf);
    }

}

