package com.pangjie.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    public static final DateTimeFormatter DFY_MD_HMS = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static final DateTimeFormatter DFY_MD = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * LocalDateTime 转时间戳
     *
     * @param localDateTime /
     * @return /
     */
    public static Long getTimeStamp(LocalDateTime localDateTime) {
        return localDateTime.atZone(ZoneId.systemDefault()).toEpochSecond();
    }

    /**
     * 时间戳转LocalDateTime
     *
     * @param timeStamp /
     * @return /
     */
    public static LocalDateTime fromTimeStamp(Long timeStamp) {
        return LocalDateTime.ofEpochSecond(timeStamp, 0, OffsetDateTime.now().getOffset());
    }

    /**
     * LocalDateTime 转 Date
     * Jdk8 后 不推荐使用 {@link Date} Date
     *
     * @param localDateTime /
     * @return /
     */
    public static Date toDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * LocalDate 转 Date
     * Jdk8 后 不推荐使用 {@link Date} Date
     *
     * @param localDate /
     * @return /
     */
    public static Date toDate(LocalDate localDate) {
        return toDate(localDate.atTime(LocalTime.now(ZoneId.systemDefault())));
    }


    /**
     * Date转 LocalDateTime
     * Jdk8 后 不推荐使用 {@link Date} Date
     *
     * @param date /
     * @return /
     */

    public static LocalDateTime toLocalDateTime(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    /**
     * 日期 格式化
     *
     * @param localDateTime /
     * @param patten        /
     * @return /
     */
    public static String localDateTimeFormat(LocalDateTime localDateTime, String patten) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern(patten);
        return df.format(localDateTime);
    }

    /**
     * 日期 格式化
     *
     * @param localDateTime /
     * @param df            /
     * @return /
     */
    public static String localDateTimeFormat(LocalDateTime localDateTime, DateTimeFormatter df) {
        return df.format(localDateTime);
    }

    /**
     * 日期格式化 yyyy-MM-dd HH:mm:ss
     *
     * @param localDateTime /
     * @return /
     */
    public static String localDateTimeFormatyMdHms(LocalDateTime localDateTime) {
        return DFY_MD_HMS.format(localDateTime);
    }

    /**
     * 日期格式化 yyyy-MM-dd
     *
     * @param localDateTime /
     * @return /
     */
    public String localDateTimeFormatyMd(LocalDateTime localDateTime) {
        return DFY_MD.format(localDateTime);
    }

    /**
     * 字符串转 LocalDateTime ，字符串格式 yyyy-MM-dd
     *
     * @param localDateTime /
     * @return /
     */
    public static LocalDateTime parseLocalDateTimeFormat(String localDateTime, String pattern) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
        return LocalDateTime.from(dateTimeFormatter.parse(localDateTime));
    }

    /**
     * 字符串转 LocalDateTime ，字符串格式 yyyy-MM-dd
     *
     * @param localDateTime /
     * @return /
     */
    public static LocalDateTime parseLocalDateTimeFormat(String localDateTime, DateTimeFormatter dateTimeFormatter) {
        return LocalDateTime.from(dateTimeFormatter.parse(localDateTime));
    }

    /**
     * 字符串转 LocalDateTime ，字符串格式 yyyy-MM-dd HH:mm:ss
     *
     * @param localDateTime /
     * @return /
     */
    public static LocalDateTime parseLocalDateTimeFormatyMdHms(String localDateTime) {
        return LocalDateTime.from(DFY_MD_HMS.parse(localDateTime));
    }
    public static Date getNowTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY));
        return calendar.getTime();
    }

    /*
     * @Author pangjie
     * @Description //TODO 获取当前时间 - reduce 小时的时间
     * @Date 10:37 2020/10/20 0020
     * @Param
     * @return
     */
    public static Date getNowTimeReduce(Integer reduce) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) - reduce);
        return calendar.getTime();
    }

    /*
     * @Author pangjie
     * @Description //TODO 获取当前时间 + plus 小时的时间
     * @Date 10:37 2020/10/20 0020
     * @Param
     * @return
     */
    public static Date getNowTimePlus(Integer plus) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) + plus);
        return calendar.getTime();
    }

    /*
     * @Author pangjie
     * @Description //TODO 获取-reduce天
     * @Date 16:59 21.8.9
     * @Param
     * @return
     */
    public static Date getNowDayReduce(Integer reduce) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - reduce);
        return calendar.getTime();
    }

    /*
     * @Author pangjie
     * @Description //TODO 获取+plus天
     * @Date 16:59 21.8.9
     * @Param
     * @return
     */
    public static Date getNowDayPlus(Integer plus) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + plus);
        return calendar.getTime();
    }


    //获取当天起始时间
    public static Date getStartTime(Date date) {
        Calendar dateStart = Calendar.getInstance();
        dateStart.setTime(date);
        dateStart.set(Calendar.HOUR_OF_DAY, 0);
        dateStart.set(Calendar.MINUTE, 0);
        dateStart.set(Calendar.SECOND, 0);
        return dateStart.getTime();
    }

    //获取当天结束时间
    public static Date getEndTime(Date date) {
        Calendar dateEnd = Calendar.getInstance();
        dateEnd.setTime(date);
        dateEnd.set(Calendar.HOUR_OF_DAY, 23);
        dateEnd.set(Calendar.MINUTE, 59);
        dateEnd.set(Calendar.SECOND, 59);
        return dateEnd.getTime();
    }
    /*
     * @Author pangjie
     * @Description //TODO "yyyy-MM-dd hh:mm:ss"
     * @Date 10:34 21.8.16
     * @Param
     * @return
     */
    public static String getDayString(Date date, String Format) {
        SimpleDateFormat format = new SimpleDateFormat(Format);
        return format.format(date);
    }

    public static Integer getTimeBetweenDays(Timestamp timestamp1, Timestamp timestamp2) {
        LocalDate startDate = Instant.ofEpochMilli(timestamp1.getTime()).atZone(ZoneOffset.ofHours(8)).toLocalDate();
//        System.out.println("开始时间：" + startDate);

        LocalDate endDate = Instant.ofEpochMilli(timestamp2.getTime()).atZone(ZoneOffset.ofHours(8)).toLocalDate();
//        System.out.println("结束时间：" + endDate);

        long daysDiff = ChronoUnit.DAYS.between(startDate, endDate);
//        System.out.println("两个时间之间的天数差为：" + daysDiff);
        return Math.toIntExact(daysDiff);
    }

    /*
     * @Author PangJie___
     * @Description  获取两个时间间隔
     * @Date 15:17 2023/3/23
     * param
     * return
     */
    public static Integer getTimeBetweenSeconds(Timestamp timestamp1, Timestamp timestamp2) {
        long time = timestamp1.getTime();
        LocalDateTime startDate = Instant.ofEpochMilli(time).atZone(ZoneOffset.ofHours(8)).toLocalDateTime();
//        System.out.println("开始时间：" + startDate);

        LocalDateTime endDate = Instant.ofEpochMilli(timestamp2.getTime()).atZone(ZoneOffset.ofHours(8)).toLocalDateTime();
//        System.out.println("结束时间：" + endDate);
        long secondsDiff = ChronoUnit.SECONDS.between(startDate, endDate);
//        System.out.println("两个时间之间的秒数差为：" + secondsDiff);
        return Math.toIntExact(secondsDiff);
    }
}