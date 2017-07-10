package cn.net.ntech.lntu.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import cn.net.ntech.lntu.bean.GongGaoInfo;

/**
 * Created by NTECHER on 2016/3/16.
 */
public class GongGaoUtils {
    public static List<GongGaoInfo> getGongGaoItem(String html){
        List<GongGaoInfo> list = new ArrayList<>();
        Document htmlinfo = Jsoup.parse(html);
        Elements gonggaoTable = htmlinfo.select("a:not(.page_a)");
        Elements timeinfo = htmlinfo.select("td[width=70]");
        String[] newtime = new String[timeinfo.size()];
        int i = 0;
        for (Element time:timeinfo){
            newtime[i] = time.text();
            i = i + 1;
        }
        int j = 0;
        for (Element url:gonggaoTable){
            String title = url.text();
            String item_url = url.attr("href");
            String newsurl = item_url.replace("..", "http://60.18.131.133:8090/lntu");
            String time = newtime[j];
            j = j + 1;
            time = time.substring(5);
            GongGaoInfo info = new GongGaoInfo(title,newsurl,time);
            list.add(info);
        }
        return list;
    }
}
