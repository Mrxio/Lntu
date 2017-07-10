package cn.net.ntech.lntu.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by NTECHER on 2016/3/10.
 */
public class TimeUtils {
    public static int getWeekNum(String setDate) throws ParseException {
        //数据库中储存的配置信息为2015-02-03/星期几/n
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //获取配置信息中的时间信息
        String[] setDateInfo = setDate.split("/");
        //获取设置时间相对应的周一时间
        int i = 0;
        int n = Integer.parseInt(setDateInfo[2]);
        switch (setDateInfo[1]){
            case "周一":
                i = 0;
                break;
            case "周二":
                i = 1;
                break;
            case "周三":
                i = 2;
                break;
            case "周四":
                i = 3;
                break;
            case "周五":
                i = 4;
                break;
            case "周六":
                i = 5;
                break;
            case "周日":
                i = 6;
                break;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(sdf.parse(setDateInfo[0]));
        c.add(Calendar.DAY_OF_MONTH, -i);
        Date date_start = c.getTime();
        //获取系统当前时间
        Date curDate = new Date(System.currentTimeMillis());
        String nowTime = sdf.format(curDate);
        //进行计算
        Date date_end = null;
        date_end = sdf.parse(nowTime);
        Calendar fromCalendar = Calendar.getInstance();
        fromCalendar.setTime(date_start);
        fromCalendar.set(Calendar.HOUR_OF_DAY, 0);
        fromCalendar.set(Calendar.MINUTE, 0);
        fromCalendar.set(Calendar.SECOND, 0);
        fromCalendar.set(Calendar.MILLISECOND, 0);
        Calendar toCalendar = Calendar.getInstance();
        toCalendar.setTime(date_end);
        toCalendar.set(Calendar.HOUR_OF_DAY, 0);
        toCalendar.set(Calendar.MINUTE, 0);
        toCalendar.set(Calendar.SECOND, 0);
        toCalendar.set(Calendar.MILLISECOND, 0);
        int days = (int) ((toCalendar.getTime().getTime() - fromCalendar.getTime().getTime()) / (1000 * 60 * 60 * 24));
        int weeknum = days/7;
        return weeknum+n;
    }
    public static String getNowTime(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        Date curDate = new Date(System.currentTimeMillis());
        String nowTime = sdf.format(curDate);
        return nowTime;
    }
    public static String getTime(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日HHmmss");
        Date curDate = new Date(System.currentTimeMillis());
        String nowTime = sdf.format(curDate);
        return nowTime;
    }
    public static String getTermNum(){
        SimpleDateFormat year = new SimpleDateFormat("yyyy");
        SimpleDateFormat mouth = new SimpleDateFormat("M");
        SimpleDateFormat day = new SimpleDateFormat("D");
        Date curDate = new Date(System.currentTimeMillis());
        String nowyear = year.format(curDate);
        int y = Integer.parseInt(nowyear);
        String nowmouth = mouth.format(curDate);
        int m = Integer.parseInt(nowmouth);
        String nowday = day.format(curDate);
        int d = Integer.parseInt(nowday);
        int yearnum = 36;
        int termnum = 1;
        if ( m < 2 || ( m == 2 && d <= 15)){//前一年秋季
            yearnum = y - 2016-1 + yearnum;
            termnum = 2;
        }else if ((m > 2 && m < 8) || m == 2 && d > 15 || m == 8 && d <= 15){//本年春季
            yearnum = y - 2016 + yearnum;
            termnum = 1;
        }else if (m > 8 || m == 8 && d > 15){//本年秋季
            yearnum = y - 2016 + yearnum;
            termnum = 2;
        }
        return yearnum+"-"+termnum;
    }
}
