package cn.net.ntech.lntu.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by NTECHER on 2016/3/16.
 */
public class GongGaoInfoUtils {
    public static String getInfo(String html){
        String gonggaoinfo = "";
        Document info = Jsoup.parse(html);
        Elements newsinfo = info.select("p");
        for (Element info1:newsinfo){
            gonggaoinfo = gonggaoinfo +"    "+ info1.text()+"\n";
        }
        return gonggaoinfo;
    }
}
