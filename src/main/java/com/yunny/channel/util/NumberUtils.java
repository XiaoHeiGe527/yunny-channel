package com.yunny.channel.util;



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

    /**
     * 获取当前时间格式化
     * @param date
     * @return
     */
    public static String formatLocalDateTimeString(LocalDateTime date) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");
        return date.format(dtf);
    }

    /**
     * 获取当前时间格式化 精确到月
     * @param date
     * @return
     */
    public static String formatLocalDateTimeStringMonth(LocalDateTime date) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMM");
        return date.format(dtf);
    }

}

