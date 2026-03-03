package com.yunny.channel.common.constant;

/**
* @Description 日期格式化常量
* @CreateDate 2020/3/20 10:59
*/
public class DatePatternConstants {
	/** 标准日期格式：yyyy-MM-dd */
	public final static String NORM_DATE_PATTERN = "yyyy-MM-dd";

    /** 标准日期格式 精确到月份：yyyy-MM */
    public final static String NORM_MONTH_PATTERN = "yyyy-MM";


	/** 标准时间格式：HH:mm:ss */
	public final static String NORM_TIME_PATTERN = "HH:mm:ss";



    /** 标准日期时间格式，精确到小时：yyyy-MM-dd HH */
    public final static String NORM_DATETIME_HOUR_PATTERN ="yyyy-MM-dd HH";


	/** 标准日期时间格式，精确到分：yyyy-MM-dd HH:mm */
	public final static String NORM_DATETIME_MINUTE_PATTERN = "yyyy-MM-dd HH:mm";

	/** 标准日期时间格式，精确到秒：yyyy-MM-dd HH:mm:ss */
	public final static String NORM_DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

	/** 标准日期时间格式，精确到毫秒：yyyy-MM-dd HH:mm:ss.SSS */
	public final static String NORM_DATETIME_MS_PATTERN = "yyyy-MM-dd HH:mm:ss.SSS";

	/** 标准日期格式：yyyy年MM月dd日 */
	public final static String CHINESE_DATE_PATTERN = "yyyy年MM月dd日";

	//-------------------------------------------------------------------------------------------------------------------------------- Pure
	/** 标准日期格式：yyyyMMdd */
	public final static String PURE_DATE_PATTERN = "yyyyMMdd";

	/** 标准日期格式：HHmmss */
	public final static String PURE_TIME_PATTERN = "HHmmss";

	/** 标准日期格式：yyyyMMddHHmmss */
	public final static String PURE_DATETIME_PATTERN = "yyyyMMddHHmmss";

	/** 标准日期格式：yyyyMMddHHmmssSSS */
	public final static String PURE_DATETIME_MS_PATTERN = "yyyyMMddHHmmssSSS";

	//-------------------------------------------------------------------------------------------------------------------------------- Others
	/** HTTP头中日期时间格式：EEE, dd MMM yyyy HH:mm:ss z */
	public final static String HTTP_DATETIME_PATTERN = "EEE, dd MMM yyyy HH:mm:ss z";

	/** JDK中日期时间格式：EEE MMM dd HH:mm:ss zzz yyyy */
	public final static String JDK_DATETIME_PATTERN = "EEE MMM dd HH:mm:ss zzz yyyy";

	/** UTC时间：yyyy-MM-dd'T'HH:mm:ss'Z' */
	public final static String UTC_PATTERN = "yyyy-MM-dd'T'HH:mm:ss'Z'";

	/** UTC时间：yyyy-MM-dd'T'HH:mm:ssZ */
	public final static String UTC_WITH_ZONE_OFFSET_PATTERN = "yyyy-MM-dd'T'HH:mm:ssZ";

	/** UTC时间：yyyy-MM-dd'T'HH:mm:ss.SSS'Z' */
	public final static String UTC_MS_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

	/** UTC时间：yyyy-MM-dd'T'HH:mm:ssZ */
	public final static String UTC_MS_WITH_ZONE_OFFSET_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";

	/**
	 *   yyyy-MM-dd 00:00:00
	 */
	public final static String UTC_LOCAL_DATE = "yyyy-MM-dd 00:00:00";


}
