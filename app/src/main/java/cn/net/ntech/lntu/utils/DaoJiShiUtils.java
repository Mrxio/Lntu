package cn.net.ntech.lntu.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by NTECHER on 2016/3/25.
 */
public class DaoJiShiUtils {
    public static int getDaoJiShi(String time) throws ParseException {
        String[] timeArry = time.split(" ");
        time = timeArry[0];
        //获取当前时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date curDate = new Date(System.currentTimeMillis());
        String nowTime = sdf.format(curDate);
        //进行计算
        Date date_end = null;
        date_end = sdf.parse(nowTime);
        Calendar fromCalendar = Calendar.getInstance();
        Date date_start;
        date_start = sdf.parse(time);
        fromCalendar.setTime(date_end);
        fromCalendar.set(Calendar.HOUR_OF_DAY, 0);
        fromCalendar.set(Calendar.MINUTE, 0);
        fromCalendar.set(Calendar.SECOND, 0);
        fromCalendar.set(Calendar.MILLISECOND, 0);
        Calendar toCalendar = Calendar.getInstance();
        toCalendar.setTime(date_start);
        toCalendar.set(Calendar.HOUR_OF_DAY, 0);
        toCalendar.set(Calendar.MINUTE, 0);
        toCalendar.set(Calendar.SECOND, 0);
        toCalendar.set(Calendar.MILLISECOND, 0);
        int days = (int) ((toCalendar.getTime().getTime() - fromCalendar.getTime().getTime()) / (1000 * 60 * 60 * 24));
        return days;
    }
}
