package cn.net.ntech.lntu.bean;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import cn.net.ntech.lntu.dao.ConfigInfoOpenHelper;


/**
 * Created by NTECHER on 2016/3/14.
 */
public class AntoLogin {
    public static void AntoLogin(final Context context) {
//        MyHttpUtils.putLogin();
        final String login_url = "http://60.18.131.131:11080/newacademic/j_acegi_security_check";
        new Thread() {
            @Override
            public void run() {
                //获取数据库中的帐号和密码
                String password = null;
                String username = null;
                ConfigInfoOpenHelper openHelper = new ConfigInfoOpenHelper(context, "configinfo.db", null, 1);
                SQLiteDatabase db = openHelper.getWritableDatabase();
                Cursor cs = db.rawQuery("select * from configinfo", null);
                String setDate = null;
                while (cs.moveToNext()){
                    password = cs.getString(cs.getColumnIndex("password"));
                    username = cs.getString(cs.getColumnIndex("username"));
                }
                db.close();
                HttpClient httpClient = new DefaultHttpClient();
                        /*尝试登陆并获取cookie*/
                HttpResponse httpResponse;
                //建立HTTP Post连线
                HttpPost httpRequest = new HttpPost(login_url);
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                //添加pose信息
                params.add(new BasicNameValuePair("j_username", username));
                params.add(new BasicNameValuePair("j_password", password));
                //添加请求头
                httpRequest.setHeader("Host", "60.18.131.131:11080");
                httpRequest.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1; Win64; x64; Trident/4.0; .NET CLR 2.0.50727; SLCC2; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; .NET4.0C; Tablet PC 2.0; .NET4.0E)");
                try {
                    // 发出HTTP request
                    httpRequest.setEntity(new UrlEncodedFormEntity(params, "GBK"));
                    // 取得HTTP response
                    httpResponse = httpClient.execute(httpRequest); // 执行
                    // 获取状态码
                    int Status = httpResponse.getStatusLine().getStatusCode();
                    System.out.println("状态吗：--------" + Status);
                    if (Status == 200) {
                        //解析服务器返回数据，判断当前登陆是否成功
                        HttpEntity entity = httpResponse.getEntity();
                        String result = EntityUtils.toString(entity);
                        Document html = Jsoup.parse(result);
                        //若包含字段则登录失败
                        Elements error_result = html.select("div.error");
                        String errorinfo = null;
                        for (Element error : error_result) {
                            errorinfo = error.text();
                        }
                        String jsessionid;
                        if (errorinfo == null) {
                            jsessionid = ((AbstractHttpClient) httpClient).getCookieStore().getCookies().get(0).getValue();
                            String cookie = "JSESSIONID=" + jsessionid;
                            //登陆成功，保存cookie信息
                            SharedPreferences sp = context.getSharedPreferences("cookie", context.MODE_PRIVATE);
                            SharedPreferences.Editor ed = sp.edit();
                            ed.putString("cookie", cookie);
                            ed.commit();
                        }
                    }
                } catch (ClientProtocolException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
