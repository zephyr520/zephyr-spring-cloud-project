package com.zephyr.common.util;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * Java8 LocalDateTime工具类(线程安全)
 * Java8的日期类其实已经很强大的，所以这工具类的意义不大。
 * 
 * 日期比较:
 * LocalDateTime.now().isAfter(other);
 * LocalDateTime.now().isBefore(other);
 * LocalDateTime.now().isEqual(other);
 * 
 * 日期加减，ChronoUnit为时间单位类
 * LocalDateTime.now().plus(amountToAdd, unit);
 * LocalDateTime.now().minus(amountToSubtract, unit);
 * LocalDateTime.now().plusDays(days);
 * LocalDateTime.now().minusDays(days);
 * 
 * @author jiwen2c@qq.com
 * @date 2017年8月6日 上午7:08:34
 */

public class LDTUtil {


    /**
     * 默认日期时间格式
     */
	public static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
	public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);

    /**
     * 默认日期格式
     */
	public static final String DATE_PATTERN = "yyyy-MM-dd";
	public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_PATTERN);

    /**
     * 默认时间格式
     */
	public static final String TIME_PATTERN = "HH:mm:ss";
	public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern(TIME_PATTERN);

    /**
     * 默认时间格式
     */
    public static final String MONTH_PATTERN = "yyyy-MM";
    public static final DateTimeFormatter MONTH__FORMATTER = DateTimeFormatter.ofPattern(TIME_PATTERN);

    /**
     * 默认日期格式
     */
    public static final String DATE_DOT_PATTERN = "yyyy.MM.dd";
    public static final DateTimeFormatter DATE_DOT_FORMATTER = DateTimeFormatter.ofPattern(DATE_DOT_PATTERN);

    /**
     * 默认日期格式
     */
    public static final String DATE_CN_PATTERN = "yyyy年MM月dd日";
    public static final DateTimeFormatter DATE_CN_FORMATTER = DateTimeFormatter.ofPattern(DATE_CN_PATTERN);
    
    /**
	 * 将Date转换为LocalDate
	 * @param date
	 * @return
	 */
    public static LocalDate toLocalDate(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()).toLocalDate();
    }
    
    /**
	 * 将Date转换为LocalDateTime
	 * @param date
	 * @return
	 */
    public static LocalDateTime toLocalDateTime(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }
    
    /**
     * 将LocalDateTime转换为Date
     * @param localDateTime
     * @return
     */
    public static Date toDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }
    
    /**
     * 将LocalDate转换为Date
     * @param localDate
     * @return
     */
    public static Date toDate(LocalDate localDate) {
    	return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }
    
    /**
     * 获取当前日期时间
     * @return
     */
    public static LocalDateTime getDateTime() {
    	return LocalDateTime.now();
    }
    
    /**
     * 获取指定日期时间
     * @param dateTimeStr
     * @return
     */
    public static LocalDateTime getDateTime(String dateTimeStr) {    	
    	return LocalDateTime.parse(dateTimeStr, DATE_TIME_FORMATTER);
    }
    
    /**
     * 根据指定格式，获取指定日期时间
     * @param dateTimeStr, dateTimePattern
     * @return
     */
    public static LocalDateTime getDateTime(String dateTimeStr, String dateTimePattern) {
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateTimePattern);
    	return LocalDateTime.parse(dateTimeStr, formatter);
    }
    
    /**
     * 获取当前日期
     * @return
     */
    public static LocalDate getDate() {
    	return LocalDate.now();
    }
    
    /**
     * 获取指定日期
     * @param dateStr
     * @return
     */
    public static LocalDate getDate(String dateStr) {
    	return LocalDate.parse(dateStr, DATE_FORMATTER);
    }

//    public static void main(String[] args) {
//        System.out.println(toDate(LocalDateTime.now()));
//        Date date1 = new Date();
//        Date date2 = new Date();
//        LocalDate d1 = toLocalDate(date1);
//        LocalDate d2 = toLocalDate(date2);
//        System.out.println(d1);
//        System.out.println(d2);
//        System.out.println(d1.isEqual(d2));
//    }
    
    /**
     * 根据指定格式，获取指定日期
     * @param dateStr
     * @param datePattern
     * @return
     */
    public static LocalDate getDate(String dateStr, String datePattern) {
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern(datePattern);
    	return LocalDate.parse(dateStr, formatter);
    }
    
    /**
     * 获取增加指定天数的时间
     * @param date
     * @param days
     * @return
     */
    public static LocalDate getDateAdd(Date date, int days) {
    	LocalDate localDate = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()).toLocalDate();
    	return localDate.plusDays((long) days);
    }
    
    /**
     * 获取当前时间
     * @return
     */
    public static LocalTime getTime() {
    	return LocalTime.now();
    }

    /**
     * 获取c常规日期
     * @return
     */
    public static Date getNormalDate(LocalDate localDate) {
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zdt = localDate.atStartOfDay(zoneId);
        Date date = Date.from(zdt.toInstant());
        return  date;
    }

    /**
     * 获取指定时间
     * @param timeStr
     * @return
     */
    public static LocalTime getTime(String timeStr) {
    	return LocalTime.parse(timeStr, TIME_FORMATTER);
    }
    
    /**
     * 根据指定格式，获取指定时间
     * @param timeStr
     * @param timePattern
     * @return
     */
    public static LocalTime getTime(String timeStr, String timePattern) {
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern(timePattern);
    	return LocalTime.parse(timeStr, formatter);
    }
    
    /**
     * 获取当前日期时间的字符串
     * @return
     */
    public static String getDateTimeStr() {
    	return LocalDateTime.now().format(DATE_TIME_FORMATTER);
    }
    
    /**
     * 获取指定日期时间的字符串
     * @param localDateTime
     * @return
     */
    public static String getDateTimeStr(LocalDateTime localDateTime) {
    	return localDateTime.format(DATE_TIME_FORMATTER);
    }
    
    /**
     * 根据指定格式，获取当前日期时间字符串
     * @param dateTimePattern
     * @return
     */
    public static String getDateTimeStr(String dateTimePattern) {
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateTimePattern);
    	return LocalDateTime.now().format(formatter);
    }
    
    /**
     * 根据指定格式，获取指定日期时间字符串
     * @param localDateTime
     * @param dateTimePattern
     * @return
     */
    public static String getDateTimeStr(LocalDateTime localDateTime, String dateTimePattern) {
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateTimePattern);
    	return localDateTime.format(formatter);
    }
    
    /**
     * 获取当前日期的字符串
     * @return
     */
    public static String getDateStr() {
    	return LocalDate.now().format(DATE_FORMATTER);
    }
    
    /**
     * 获取指定日期的字符串
     * @param localDate
     * @return
     */
    public static String getDateStr(LocalDate localDate) {
    	return localDate.format(DATE_FORMATTER);
    }
    
    /**
     * 根据指定格式，获取当前日期的字符串
     * @param datePattern
     * @return
     */
    public static String getDateStr(String datePattern) {
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern(datePattern);
    	return LocalDate.now().format(formatter);
    }
    
    /**
     * 根据指定格式，获取指定日期的字符串
     * @param localDate
     * @param datePattern
     * @return
     */
    public static String getDateStr(LocalDate localDate, String datePattern) {
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern(datePattern);
    	return localDate.format(formatter);
    }
    
    /**
     * 获取当前时间的字符串
     * @return
     */
    public static String getTimeStr() {
    	return LocalTime.now().format(TIME_FORMATTER);
    }
    
    /**
     * 获取指定时间的字符串
     * @param localTime
     * @return
     */
    public static String getTimeStr(LocalTime localTime) {
    	return localTime.format(TIME_FORMATTER);
    }
    
    /**
     * 根据指定格式，获取当前时间字符串
     * @param timePattern
     * @return
     */
    public static String getTimeStr(String timePattern) {
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern(timePattern);
    	return LocalTime.now().format(formatter);
    }
    
    /**
     * 根据指定格式，获取指定时间字符串
     * @param localTime
     * @param timePattern
     * @return
     */
    public static String getTimeStr(LocalTime localTime, String timePattern) {
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern(timePattern);
    	return localTime.format(formatter);
    }
    
	/**
	 * 获取一天的开始时间
	 * 
	 * @param localDate
	 * @return
	 */
	public static LocalDateTime getDayStart(LocalDate localDate) {
		return localDate.atTime(LocalTime.MIDNIGHT);
	}

	/**
	 * 获取一天的结束时间
	 * 
	 * @param localDate
	 * @return
	 */
	public static LocalDateTime getDayEnd(LocalDate localDate) {
		return localDate.atTime(LocalTime.MAX);
	}
    
    /**
     * 获取两个日期的年数差
     * @param start
     * @param end
     * @return
     */
    public static long getYears(LocalDate start, LocalDate end) {
    	return start.until(end, ChronoUnit.YEARS);
    }
    
    /**
     * 获取两个日期的月数差
     * @param start
     * @param end
     * @return
     */
    public static long getMonths(LocalDate start, LocalDate end) {
    	return start.until(end, ChronoUnit.MONTHS);
    }
    
    /**
     * 获取两个日期的天数差
     * @param start
     * @param end
     * @return
     */
    public static long getDays(LocalDate start, LocalDate end) {
    	return start.until(end, ChronoUnit.DAYS);
    }

    /**
     * 计算两个时间相差的秒数
     * @param start
     * @param end
     * @return
     */
    public static long diffSeconds(LocalDateTime start, LocalDateTime end) {
        return ChronoUnit.SECONDS.between(start, end);
    }
    
	public static long getDiffNum(Date startDate, Date endDate, int diffType){
		LocalDateTime start = LDTUtil.toLocalDateTime(startDate);
		LocalDateTime end = LDTUtil.toLocalDateTime(endDate);

		Duration duration = Duration.between(start, end);
		long num = 0;
		switch (diffType) {
		// 天数
		case 1:
			num = diffDays(startDate, endDate);
			break;
		// 小时
		case 2:
			num = duration.toHours();
			break;
		// 分钟
		case 3:
			num = duration.toMinutes();
			break;
		// 秒
       case 4:
           num = diffSeconds(start, end);
           break;
		// 默认天
		default:
			num = diffDays(startDate, endDate);
			break;
		}

		return num;
	}
    
    /**
     * 获取两个日期的天数差
     * @param startdtae
     * @param endate
     * @return
     */
    public static long diffDays(Date startdtae, Date endate){
        LocalDate start= LDTUtil.toLocalDate(startdtae);
        LocalDate end= LDTUtil.toLocalDate(endate);
        return start.until(end, ChronoUnit.DAYS);
    }

    /**
     * 拼接日期
     * @param start
     * @param end
     * @return
     */
    public static String contactDate(LocalDate start, LocalDate end) {
        StringBuilder sb = new StringBuilder();
        if (start != null && end != null) {
            return sb.append(start.format(DATE_FORMATTER)).append("--->").append(end.format(DATE_FORMATTER)).toString();
        }
        return null;
    }

    /**
     * 获取指定日期时间的字符串(点号为分隔符)
     * @param localDateTime
     * @return
     */
    public static String getDateTimeDotStr(LocalDateTime localDateTime) {
        return localDateTime.format(DATE_DOT_FORMATTER);
    }
    
    public static String getDateToCNStr(Date date){
		return toLocalDate(date).format(DATE_CN_FORMATTER);			
    }
    
    public static boolean isEuqalDate(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            return false;
        }
        LocalDate d1 = toLocalDate(date1);
        LocalDate d2 = toLocalDate(date2);
        return d1.isEqual(d2);
    }
}
