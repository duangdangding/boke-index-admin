package com.pearadmin.boke.utils;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class DateUtil {

    /**
     * 今天还剩多少秒
     * @return
     */
    public static int getSeconds(){
        Calendar curDate = Calendar.getInstance();
        Calendar tommorowDate = new GregorianCalendar(curDate
                .get(Calendar.YEAR), curDate.get(Calendar.MONTH), curDate
                .get(Calendar.DATE) + 1, 0, 0, 0);
        return (int)(tommorowDate.getTimeInMillis() - curDate .getTimeInMillis()) / 1000;
    }
}
