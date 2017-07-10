package cn.net.ntech.lntu.utils;


import android.content.Context;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.util.ArrayList;
import java.util.List;
import cn.net.ntech.lntu.bean.GradeInfo;


/**
 * Created by NTECHER on 2016/3/12.
 */

public class GradeHtmlUtils {
    public static List<GradeInfo> getGradeForm(Context context,String html,String year,String term){
        String yearterm = year + term;
        List<GradeInfo> gradeinfos = new ArrayList<>();
        //解析数据得到成绩信息
        Document gradeHtml = Jsoup.parse(html);
        Elements gradeInfo = gradeHtml.select("tr[style=display:'']");
        for (Element info:gradeInfo){
            String kechengbianhao = info.select("td").get(0).text();
            String kechengmingcheng = info.select("td").get(1).text();
            String fenshu = info.select("td").get(3).text();
            String xuefen = info.select("td").get(4).text();
            String leixing = info.select("td").get(6).text();
            String kaoshileixing = info.select("td").get(8).text();
            String shijian = info.select("td").get(9).text();
            Elements url = info.select("td").get(11).select("a");
            String docurl = "";
            if (url.size()>0){
                docurl = "http://60.18.131.131:11080/newacademic/student/queryscore/"+url.get(0).attr("href");
            }
            if (year.equals("全部")&&(!term.equals("全部"))){
                if (shijian.contains(term)){
                    GradeInfo gradeinfo = new GradeInfo(kechengbianhao,kechengmingcheng,fenshu,xuefen,leixing,kaoshileixing,shijian,docurl);
                    gradeinfos.add(gradeinfo);
                }
            }else if (!year.equals("全部")&&(term.equals("全部"))){
                if (shijian.contains(year)){
                    GradeInfo gradeinfo = new GradeInfo(kechengbianhao,kechengmingcheng,fenshu,xuefen,leixing,kaoshileixing,shijian,docurl);
                    gradeinfos.add(gradeinfo);
                }
            }else if (yearterm.equals("全部全部")||shijian.equals(yearterm)){
                GradeInfo gradeinfo = new GradeInfo(kechengbianhao,kechengmingcheng,fenshu,xuefen,leixing,kaoshileixing,shijian,docurl);
                gradeinfos.add(gradeinfo);
            }
        }
        return gradeinfos;
    }
    public static String getGPA(String html){
        Document gradeHtml = Jsoup.parse(html);
        Elements jidian = gradeHtml.select("td:contains(平均学分绩)");
        String xuefenjidian = null;
        for (Element jd:jidian){
            xuefenjidian = jd.text();
        }
        String[] jidianinfo = xuefenjidian.split("，");
        xuefenjidian = jidianinfo[0].replace("你获得的平均学分绩是","绩点:");
        return xuefenjidian;
    }
}
