package com.yunny.channel.util;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author Administrator
 * 
 */
public class TimeUtil {


	public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

	public static final String DATE_FORMAT_MINUTE = "yyyy-MM-dd HH:mm";

	/**
	 * 将date 增加N分钟 负数就是减去
	 * @param date 时间
	 * @param minute 分钟
	 * @return
	 */
	public static Date DateAddMinuteByLocalDateTime(Date date,Long minute){
		//将Date 转换Local
		Instant instant = date.toInstant();
		ZoneId zoneId = ZoneId.systemDefault();
		LocalDateTime localDateTime = instant.atZone(zoneId).toLocalDateTime();

		LocalDateTime plusMinutesResult = localDateTime.plusMinutes(minute);

		ZoneId zoneId2 = ZoneId.systemDefault();
		ZonedDateTime zdt = plusMinutesResult.atZone(zoneId2);
	   return  Date.from(zdt.toInstant());
	}



	/**
	 * 将date 增加N分钟 负数就是减去
	 * @param date 时间
	 * @param hours 分钟
	 * @return
	 */
	public static Date DateAddHoursByLocalDateTime(Date date,Long hours){
		//将Date 转换Local
		Instant instant = date.toInstant();
		ZoneId zoneId = ZoneId.systemDefault();
		LocalDateTime localDateTime = instant.atZone(zoneId).toLocalDateTime();
		LocalDateTime plusHoursResult = localDateTime.plusHours(hours);
		ZoneId zoneId2 = ZoneId.systemDefault();
		ZonedDateTime zdt = plusHoursResult.atZone(zoneId2);
		return  Date.from(zdt.toInstant());
	}






	/**
	 * 将date 增加N天 负数就是减去
	 * @param date 时间
	 * @param day 分钟
	 * @return
	 */
	public static Date DateAddDayByLocalDateTime(Date date,Long day){
		//将Date 转换Local
		Instant instant = date.toInstant();
		ZoneId zoneId = ZoneId.systemDefault();
		LocalDateTime localDateTime = instant.atZone(zoneId).toLocalDateTime();

		LocalDateTime plusDayResult = localDateTime.plusDays(day);

		ZoneId zoneId2 = ZoneId.systemDefault();
		ZonedDateTime zdt = plusDayResult.atZone(zoneId2);
		return  Date.from(zdt.toInstant());
	}

	/**
	 * 将date 增加N月 负数就是减去
	 * @param date 时间
	 * @param months 分钟
	 * @return
	 */
	public static Date DateAddMonthsByLocalDateTime(Date date,Long months){
		//将Date 转换Local
		Instant instant = date.toInstant();
		ZoneId zoneId = ZoneId.systemDefault();
		LocalDateTime localDateTime = instant.atZone(zoneId).toLocalDateTime();

		LocalDateTime plusMonthsResult = localDateTime.plusMonths(months);

		ZoneId zoneId2 = ZoneId.systemDefault();
		ZonedDateTime zdt = plusMonthsResult.atZone(zoneId2);
		return  Date.from(zdt.toInstant());
	}


	/**
	 * 将date 增加N年 负数就是减去
	 * @param date 时间
	 * @param years 分钟
	 * @return
	 */
	public static Date DateAddYearsByLocalDateTime(Date date,Long years){
		//将Date 转换LocalDateTime
		Instant instant = date.toInstant();
		ZoneId zoneId = ZoneId.systemDefault();
		LocalDateTime localDateTime = instant.atZone(zoneId).toLocalDateTime();

		//localDateTime + N年
		LocalDateTime plusYearsResult = localDateTime.plusYears(years);

		//LocalDateTime转Date
		ZoneId zoneId2 = ZoneId.systemDefault();
		ZonedDateTime zdt = plusYearsResult.atZone(zoneId2);
		return  Date.from(zdt.toInstant());
	}

	/**
	 * 计算LocalDateTime时间差（单位分钟）
	 * @param begin
	 * @param end
	 * @return
	 */
	public static  long differenceMinutes(LocalDateTime begin,LocalDateTime end){
		Duration duration = Duration.between(begin,end);
		return  duration.toMinutes();
	}


	/**
	 * 计算LocalDateTime时间差（单位小时）
	 * @param begin
	 * @param end
	 * @return
	 */
	public static  long differenceHours(LocalDateTime begin,LocalDateTime end){
		Duration duration = Duration.between(begin,end);
		return  duration.toHours();
	}




	/**
	 * 计算LocalDateTime时间差（单位天）
	 * @param begin
	 * @param end
	 * @return
	 */
	public static  long differenceDays(LocalDateTime begin,LocalDateTime end){
		Duration duration = Duration.between(begin,end);
		return duration.toDays(); //相差的天数

	}

	/**
	 * 将分钟 转换格式为 N天N小时N 分钟
	 * @param cost
	 * @return
	 */
	public static  String  formatMinutesStr(Long cost){
		StringBuffer sb = new StringBuffer();
		if(cost>0){
			Long day = cost / (60L * 24L);
			Long hour = (cost - day *(60 * 24))/(60);
			Long min = cost- day * (60 *24) - hour * (60);
			if(day>0){
				sb.append(day+"天");
			}
			if(hour>0){
				sb.append(hour+"小时");
			}
			if(min>0){
				sb.append(min+"分钟");
			}

		}
		 return sb.toString();
	}

	/**
	 * 将LocalDateTime 转换String
	 * @param time
	 * @param format 转换的格式
	 * @return
	 */
	public static String timeFormatString(LocalDateTime time,String format){
		DateTimeFormatter df = DateTimeFormatter.ofPattern(format);
		return time.format(df);
	}

	/**
	 * 将字符串转换 将LocalDateTime
	 * @param time
	 * @param format
	 * @return
	 */
	public static LocalDateTime stringFormatToTime(String time,String format){
		DateTimeFormatter df = DateTimeFormatter.ofPattern(format);
		return LocalDateTime.parse(time,df);

	}


	/**
	 * 获取指定日期的零点
	 * @param time
	 * @return
	 */
	public static LocalDateTime getTodayStart(LocalDateTime time){

		 return LocalDateTime.of(LocalDate.now(), LocalTime.MIN);//当天零点
	}


	/**
	 * 获取指定日期的结束
	 * @param time
	 * @return
	 */
	public static LocalDateTime getTodayEnd(LocalDateTime time){

		return LocalDateTime.of(LocalDate.now(), LocalTime.MAX);//当天结束
	}


	public static void main(String[] args) {

//		//DATE_FORMAT_MINUTE = "yyyy-MM-dd HH:mm";
//		DateTimeFormatter df = DateTimeFormatter.ofPattern(TimeUtil.DATE_FORMAT_MINUTE);
//
//
//		LocalDateTime time = LocalDateTime.now();
//		LocalDateTime ldt = LocalDateTime.parse("2020-01-19 08:51",df);
//
//
//		//比较   现在的时间 比 设定的时间 之后  返回的类型是Boolean类型
//		System.out.println(ldt.isAfter(time));
//		System.out.println((133/30)+"个月");
//		Long date =5562L;
//		long yearCount = 365L;
//		long monthCount = 30L;
//
//
//		long year =date/yearCount;
//
//		long month =(date -yearCount*year)/monthCount;
//
//		long days =(date -yearCount*year) -(month*monthCount);
//
//
//
//	System.out.println(year+"年");
//		System.out.println(month+"月");
//		System.out.println(days+"天");
//		System.out.println(29%30);
		//		DateTimeFormatter df = DateTimeFormatter.ofPattern(TimeUtil.DATE_FORMAT_MINUTE);
//
//
//		DateTimeFormatter df = DateTimeFormatter.ofPattern(TimeUtil.DATE_FORMAT_MINUTE);
//		LocalDateTime time = LocalDateTime.now();
//		LocalDateTime ldt = LocalDateTime.parse("2020-01-19 08:51",df);
//
//
//		LocalDateTime now =LocalDateTime.now();
//		for(int i =0;i<99999;i++){
//		int x =i/2;
//		}
//		LocalDateTime js =LocalDateTime.now();
//		// 当前时间 在X 时间之后  true
//		System.out.println(now.isAfter(ldt));
//		// 当前时间 在X 时间之前  true
//		System.out.println(now.isBefore(ldt));\

		//DATE_FORMAT_MINUTE = "yyyy-MM-dd HH:mm";
		DateTimeFormatter df = DateTimeFormatter.ofPattern(TimeUtil.DATE_TIME_FORMAT);

		LocalDateTime time = LocalDateTime.now();
		//LocalDateTime ldt = LocalDateTime.parse("2020-01-19 08:51",df);



		System.out.println(TimeUtil.getTodayStart(time).format(df));

		System.out.println(TimeUtil.getTodayEnd(time).format(df));


	}

	/**
	 * localDateTime转String
	 * @param localDateTime
	 * @param pattern
	 * @return
	 */
	public static String localDateTimeParseString(LocalDateTime localDateTime,String pattern){
		DateTimeFormatter df = DateTimeFormatter.ofPattern(pattern);
		return df.format(localDateTime);
	}
}
