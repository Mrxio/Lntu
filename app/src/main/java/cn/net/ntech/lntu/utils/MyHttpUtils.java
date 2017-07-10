package cn.net.ntech.lntu.utils;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android-J on 2016/6/11.
 */
public class MyHttpUtils {

    private static String puturl = "http://60.18.131.131:11080/newacademic/eva/index/putresult.jsdo";
    private static String pingjiao_url = "http://60.18.131.131:11080/newacademic/eva/index/resultlist.jsdo?groupId=&moduleId=506";
    private static String baidukey = "b6f1c4f7b41d72d03bf03c1eba47865e";
    private static String info_url = "http://60.18.131.131:11080/newacademic/student/studentinfo/studentinfo.jsdo?groupId=&moduleId=2060";
    /**
     * 提交评教
     *
     * @param list
     * @param cookie
     * @param callBack
     */
    public static final void putPingJiao(List<NameValuePair> list,String cookie ,String refreshUrl,RequestCallBack<String> callBack) {
        HttpUtils http = init();
        RequestParams params = new RequestParams();
        params.addBodyParameter(list);
        params.setHeader("Host", "60.18.131.131:11080");
        params.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1; Win64; x64; Trident/4.0; .NET CLR 2.0.50727; SLCC2; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; .NET4.0C; Tablet PC 2.0; .NET4.0E)");
        params.setHeader("Referer", refreshUrl);
        params.addHeader("Cookie", cookie);
        http.configResponseTextCharset("GBK");
        http.send(HttpRequest.HttpMethod.POST,puturl , params, callBack);
    }

    /**
     *登录
     *
     * @param username
     * @param password
     * @param callBack
     */
    public static final void putLogin(String username,String password ,RequestCallBack<String> callBack) {
        HttpUtils http = init();
        RequestParams params = new RequestParams();
        List<NameValuePair> list = new ArrayList<NameValuePair>();
        //添加pose信息
        list.add(new BasicNameValuePair("j_username", username));
        list.add(new BasicNameValuePair("j_password", password));
        params.setHeader("Host", "60.18.131.131:11080");
        params.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1; Win64; x64; Trident/4.0; .NET CLR 2.0.50727; SLCC2; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; .NET4.0C; Tablet PC 2.0; .NET4.0E)");
        params.addBodyParameter(list);
        http.configResponseTextCharset("GBK");
        http.send(HttpRequest.HttpMethod.POST,puturl , params, callBack);
    }

    /**
     * 获取某个科目评教操作页面
     *
     * @param cookie
     * @param url
     * @param callBack
     */
    public static final void getEachPingJiaoInfo(String url, String cookie ,RequestCallBack<String> callBack) {
        HttpUtils http = init();
        RequestParams params = new RequestParams();
        params.setHeader("Host", "60.18.131.131:11080");
        params.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1; Win64; x64; Trident/4.0; .NET CLR 2.0.50727; SLCC2; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; .NET4.0C; Tablet PC 2.0; .NET4.0E)");
        params.setHeader("Referer", pingjiao_url);
        params.addHeader("Cookie", cookie);
        http.configResponseTextCharset("GBK");
        http.send(HttpRequest.HttpMethod.GET,url , params, callBack);
    }

    /**
     * 获取评教的列表
     *
     * @param cookie
     * @param callBack
     */
    public static final void getPingJiaoList(String cookie ,RequestCallBack<String> callBack) {
        HttpUtils http = init();
        RequestParams params = new RequestParams();
        params.setHeader("Host", "60.18.131.131:11080");
        params.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1; Win64; x64; Trident/4.0; .NET CLR 2.0.50727; SLCC2; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; .NET4.0C; Tablet PC 2.0; .NET4.0E)");
        params.setHeader("Referer", pingjiao_url);
        params.addHeader("Cookie", cookie);
        http.configResponseTextCharset("GBK");
        http.send(HttpRequest.HttpMethod.GET,pingjiao_url , params, callBack);
    }

    /**
     * 获取当前年份和学期的课表
     *
     * @param cookie
     * @param callBack
     */
    public static final void getClass(String cookie ,RequestCallBack<String> callBack) {
        String termnum = TimeUtils.getTermNum();
        String[] arr =  termnum.split("-");
        String year = arr[0];
        String term = arr[1];
        String classUrl = UrlUtils.baseclass_url+"year="+year+"&term="+term;
        HttpUtils http = init();
        RequestParams params = new RequestParams();
        params.setHeader("Host", "60.18.131.131:11080");
        params.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1; Win64; x64; Trident/4.0; .NET CLR 2.0.50727; SLCC2; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; .NET4.0C; Tablet PC 2.0; .NET4.0E)");
        params.setHeader("Referer", classUrl);
        params.addHeader("Cookie", cookie);
        http.configResponseTextCharset("GBK");
        http.send(HttpRequest.HttpMethod.GET,classUrl , params, callBack);
    }

    /**
     * 查询成绩
     *
     * @param cookie
     * @param callBack
     */
    public static final void getGrade(String cookie ,RequestCallBack<String> callBack) {
        String url = UrlUtils.chengji_url;
        HttpUtils http = init();
        RequestParams params = new RequestParams();
        params.setHeader("Host", "60.18.131.131:11080");
        params.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1; Win64; x64; Trident/4.0; .NET CLR 2.0.50727; SLCC2; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; .NET4.0C; Tablet PC 2.0; .NET4.0E)");
        params.setHeader("Referer", url);
        params.addHeader("Cookie", cookie);
        http.configResponseTextCharset("GBK");
        http.send(HttpRequest.HttpMethod.GET,url , params, callBack);
    }

    /**
     * 查询挂科
     *
     * @param cookie
     * @param callBack
     */
    public static final void getGuake(String cookie ,RequestCallBack<String> callBack) {
        String url = UrlUtils.guake_url;
        HttpUtils http = init();
        RequestParams params = new RequestParams();
        params.setHeader("Host", "60.18.131.131:11080");
        params.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1; Win64; x64; Trident/4.0; .NET CLR 2.0.50727; SLCC2; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; .NET4.0C; Tablet PC 2.0; .NET4.0E)");
        params.setHeader("Referer", url);
        params.addHeader("Cookie", cookie);
        http.configResponseTextCharset("GBK");
        http.send(HttpRequest.HttpMethod.GET,url , params, callBack);
    }

    /**
     * 查询考试
     *
     * @param cookie
     * @param callBack
     */
    public static final void getTest(String cookie ,RequestCallBack<String> callBack) {
        String url = UrlUtils.kaoshi_url;
        HttpUtils http = init();
        RequestParams params = new RequestParams();
        params.setHeader("Host", "60.18.131.131:11080");
        params.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1; Win64; x64; Trident/4.0; .NET CLR 2.0.50727; SLCC2; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; .NET4.0C; Tablet PC 2.0; .NET4.0E)");
        params.setHeader("Referer", url);
        params.addHeader("Cookie", cookie);
        http.configResponseTextCharset("GBK");
        http.send(HttpRequest.HttpMethod.GET,url , params, callBack);
    }

    /**
     * 查询公告
     * @param callBack
     */
    public static final void getGongGao(RequestCallBack<String> callBack) {
        String url = UrlUtils.gonggao_url;
        HttpUtils http = init();
        RequestParams params = new RequestParams();
        params.setHeader("Host", "60.18.131.131:11080");
        params.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1; Win64; x64; Trident/4.0; .NET CLR 2.0.50727; SLCC2; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; .NET4.0C; Tablet PC 2.0; .NET4.0E)");
        http.configResponseTextCharset("GBK");
        http.send(HttpRequest.HttpMethod.GET,url , params, callBack);
    }

    /**
     * 获取个人信息
     * @param cookie
     * @param callBack
     */
    public static final void getInfo(String cookie,RequestCallBack<String> callBack) {
        HttpUtils http = init();
        RequestParams params = new RequestParams();
        params.setHeader("Host", "60.18.131.131:11080");
        params.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1; Win64; x64; Trident/4.0; .NET CLR 2.0.50727; SLCC2; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; .NET4.0C; Tablet PC 2.0; .NET4.0E)");
        params.setHeader("Referer", info_url);
        params.addHeader("Cookie", cookie);
        http.configResponseTextCharset("GBK");
        http.send(HttpRequest.HttpMethod.GET,info_url , params, callBack);
    }
    /**
     * 通用GET访问
     * @param url
     * @param callBack
     */
    public static final void normalGet(String url,RequestCallBack<String> callBack) {
        HttpUtils http = init();
        RequestParams params = new RequestParams();
        params.setHeader("Host", "60.18.131.131:11080");
        params.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1; Win64; x64; Trident/4.0; .NET CLR 2.0.50727; SLCC2; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; .NET4.0C; Tablet PC 2.0; .NET4.0E)");
        http.configResponseTextCharset("GBK");
        http.send(HttpRequest.HttpMethod.GET,url , params, callBack);
    }

    /**
     * 带cookie的一般访问
     * @param url
     * @param cookie
     * @param callBack
     */
    public static final void normalGetWithCookie(String url,String cookie,RequestCallBack<String> callBack) {
        HttpUtils http = init();
        RequestParams params = new RequestParams();
        params.setHeader("Host", "60.18.131.131:11080");
        params.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1; Win64; x64; Trident/4.0; .NET CLR 2.0.50727; SLCC2; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; .NET4.0C; Tablet PC 2.0; .NET4.0E)");
        params.setHeader("Referer", url);
        params.addHeader("Cookie", cookie);
        http.configResponseTextCharset("GBK");
        http.send(HttpRequest.HttpMethod.GET,url , params, callBack);
    }
    //获取天气信息
    public static final void getWether(String cityName,RequestCallBack<String> callBack) {
        HttpUtils http = init();
        RequestParams params = new RequestParams();
        params.setHeader("apikey", baidukey);
        http.configResponseTextCharset("UTF-8");
        http.send(HttpRequest.HttpMethod.GET,"http://apis.baidu.com/thinkpage/weather_api/suggestion?location="+cityName+"&language=zh-Hans&unit=c&start=0&days=1" , params, callBack);
    }

    private static final HttpUtils init() {
        // 构造方法设置超时时间
        HttpUtils http = new HttpUtils(20 * 1000);
        return http;
    }

    private MyHttpUtils() {
        throw new AssertionError();
    }
}
