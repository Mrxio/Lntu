package cn.net.ntech.lntu.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import cn.net.ntech.lntu.bean.GuaKeInfo;

/**
 * Created by NTECHER on 2016/3/16.
 */
public class GuaKeUtils {
    public static List<GuaKeInfo> getInfo(String html){
        List<GuaKeInfo> guaKeInfos = new ArrayList<>();
        Document info = Jsoup.parse(html);
        Elements testinfo = info.select("tr.infolist_common");
        for (Element test:testinfo){
            String classNum = test.select("td").get(7).text();
            String className = test.select("td").get(1).text();
            String classGrade = test.select("td").get(3).text();
            String classGPA = test.select("td").get(4).text();
            String classType = test.select("td").get(8).text();
            String classTime = test.select("td").get(9).text();
            GuaKeInfo guaKeInfo = new GuaKeInfo(classNum,className,classGrade,classGPA,classType,classTime);
            guaKeInfos.add(guaKeInfo);
        }
        return guaKeInfos;
    }
}
