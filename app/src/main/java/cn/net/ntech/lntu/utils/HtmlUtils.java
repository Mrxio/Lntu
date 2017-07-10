package cn.net.ntech.lntu.utils;


import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import cn.net.ntech.lntu.bean.ClassInfo;
import cn.net.ntech.lntu.dao.ClassInfoDao;
import cn.net.ntech.lntu.dao.ClassInfoOpenHelper;

/**
 * Created by NTECHER on 2016/3/6.
 */
/*
解析课表信息，并储存到数据库
 */
public class HtmlUtils {
    public static void HtmlAnalysisi(String HtmlText,Context context){
        ClassInfoDao dao = new ClassInfoDao(context);
        Document html = Jsoup.parse(HtmlText);
        //第一个table，其中包含课表信息
        Element subjece_time = html.select("table.infolist_tab").first();
        //获取课表信息
        Elements time_place = subjece_time.select("tr.infolist_common");

        for (Element timeplace:time_place){
            String class_name = timeplace.select("td").get(2).text();
            String class_teacher = timeplace.select("td").get(3).text();
            String class_grade = timeplace.select("td").get(4).text();
            String class_place = timeplace.select("tbody").text();
            String[] info = class_place.split(" ");
            if(info.length>2){
                int i = info.length / 4;
                for(int k = 0;k < i;k++){
                    ClassInfo classInfo = new ClassInfo( class_name,  class_teacher,  class_grade,  info[0+4*k],  info[1+4*k],  info[2+4*k],  info[3+4*k]);
                    dao.insertClass(classInfo,"0");
                }
            }
        }
        dao.closeDB();
    }
}
