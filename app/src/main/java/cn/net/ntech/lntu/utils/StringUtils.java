package cn.net.ntech.lntu.utils;

import cn.net.ntech.lntu.R;

/**
 * Created by NTECHER on 2016/3/9.
 */
public class StringUtils {
    public static String toWeekDay(int week,int type){
        String weekday = null;
        String weeknum = null;
        String[] weekNum = new String[]{"第一周","第二周","第三周","第四周","第五周","第六周","第七周","第八周","第九周","第十周",
                "第十一周","第十二周","第十三周","第十四周","第十五周","第十六周","第十七周","第十八周","第十九周","第二十周",
                "第二十一周","第二十二周","第二十三周","第二十四周","第二十五周"};
        weeknum = weekNum[week-1];
        String result = null;
        switch (week){
            case 1:
                weekday = "周一";
                break;
            case 2:
                weekday = "周二";
                break;
            case 3:
                weekday = "周三";
                break;
            case 4:
                weekday = "周四";
                break;
            case 5:
                weekday = "周五";
                break;
            case 6:
                weekday = "周六";
                break;
            case 7:
                weekday = "周日";
                break;
        }
        switch (type){
            case 1:
                result = weekday;
                break;
            case 2:
                result = weeknum;
                break;
        }
       return result;
    }
    public static int toResourcePath(int i){
        int xinqi = 0;
        switch (i){
            case 1:
                xinqi = R.id.tv_textview1;
                break;
            case 2:
                xinqi = R.id.tv_textview2;
                break;
            case 3:
                xinqi = R.id.tv_textview3;
                break;
            case 4:
                xinqi = R.id.tv_textview4;
                break;
            case 5:
                xinqi = R.id.tv_textview5;
                break;
            case 6:
                xinqi = R.id.tv_textview6;
                break;
            case 7:
                xinqi = R.id.tv_textview7;
                break;
        }
        return xinqi;
    }
    public static String toClassName(int i){
        String classtime = null;
        switch (i){
            case 1:
                classtime = "第一大节";
                break;
            case 2:
                classtime = "第二大节";
                break;
            case 3:
                classtime = "第三大节";
                break;
            case 4:
                classtime = "第四大节";
                break;
            case 5:
                classtime = "第五大节";
                break;
        }
        return classtime;
    }
}
