package cn.net.ntech.lntu.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import cn.net.ntech.lntu.bean.TestInfo;

/**
 * Created by NTECHER on 2016/3/13.
 */
public class TestHtmlUtils {
    public static List<TestInfo> getTestInfo(String html){
        List<TestInfo> testInfos = new ArrayList<TestInfo>();
        Document test = Jsoup.parse(html);
        Elements testinfo = test.select("tr.infolist_common");
        for(Element info:testinfo){
            String testName = info.select("td").get(0).text();
            String testTime = info.select("td").get(1).text();
            String testPlace = info.select("td").get(2).text();
            TestInfo testInfo = new TestInfo(testName,testTime,testPlace);
            testInfos.add(testInfo);
        }
        return testInfos;
    }
}
