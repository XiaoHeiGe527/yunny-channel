package com.yunny.channel.common.util;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataFormatUtil {

    /**
     * 去除字符串中的空格、回车、换行符、制表符
     *
     * @param str
     * @return
     */
    public static String strBlank(String str) {
        if (str != null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            return m.replaceAll("");
        }
        return "";
    }

    public static String getStr(Object obj) {
        return obj == null ? null : strBlank(String.valueOf(obj));
    }

    public static String getStrTrim(Object obj) {
        return obj == null ? null : String.valueOf(obj).trim();
    }


    public static Date getDate(Object obj) {
        if (obj != null) {
            String dateStr = String.valueOf(obj);
            if (StrUtil.isNotBlank(dateStr)) {
                return DateUtil.parse(dateStr);
            }
        }
        return null;
    }

    public static Map<String, Object> getMap(Object obj) {
        return obj == null ? null : (Map<String, Object>)obj;
    }

    public static BigDecimal getBigDecimal(Object obj) {
        String dateStr = getStr(obj);
        if (StrUtil.isNotBlank(dateStr)) {
            return new BigDecimal(dateStr);
        }
        return null;
    }

    public static String getIp(Object obj) {
        String dateStr = getStr(obj);
        if (StrUtil.isNotBlank(dateStr)) {
            dateStr = dateStr.replaceAll("unknown", "");
            return dateStr;
        }
        return null;
    }

    public static String getMac(Object obj) {
        String dateStr = getStr(obj);
        if (StrUtil.isNotBlank(dateStr)) {
            dateStr = dateStr.replaceAll("unkown;", "");
            int index = dateStr.indexOf(";");
            return index == -1 ? dateStr : dateStr.substring(0, index);
        }
        return null;
    }

    public static Integer getInteger(Object obj) {
        String intStr = getStr(obj);
        if (StrUtil.isNotBlank(intStr)) {
            return Integer.valueOf(intStr);
        }
        return null;
    }

    public static Long getLong(Object obj) {
        String longStr = getStr(obj);
        if (StrUtil.isNotBlank(longStr)) {
            return Long.valueOf(longStr);
        }
        return null;
    }

    public static Float getFloat(Object obj) {
        String intStr = getStr(obj);
        if (StrUtil.isNotBlank(intStr)) {
            return Float.valueOf(intStr);
        }
        return null;
    }

    public static String getProvinceCode(String taxNumber) {
        if (StrUtil.isNotBlank(taxNumber)) {
            return StrUtil.startWith(taxNumber, "9")
                    ? StrUtil.subWithLength(taxNumber, 2, 2)
                    : StrUtil.subWithLength(taxNumber, 0, 2);
        }
        return null;
    }

}
