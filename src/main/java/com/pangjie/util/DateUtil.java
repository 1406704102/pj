package com.pangjie.util;

import java.util.Calendar;
import java.util.Date;

public class DateUtil {

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
}