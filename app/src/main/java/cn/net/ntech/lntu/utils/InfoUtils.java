package cn.net.ntech.lntu.utils;

import android.content.Context;
import android.content.SharedPreferences;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by NTECHER on 2016/3/30.
 */
public class InfoUtils {
    public static void getInfo(String html, final Context context){
        Document htmlinfo = Jsoup.parse(html);
        Elements info = htmlinfo.select("td");
        String name = info.get(4).text().trim();
        String sex = info.get(12).text().trim();
        String clas = info.get(14).text().trim();
        String time = info.get(24).text().trim();
        final String image_url = info.get(3).select("img").attr("src");
        if (!time.isEmpty()){
            String [] timeinfo = time.split("-");
            time = timeinfo[0];
            SharedPreferences sp = context.getSharedPreferences("info", context.MODE_PRIVATE);
            SharedPreferences.Editor ed = sp.edit();
            ed.putString("name", name);
            ed.putString("sex", sex);
            ed.putString("clas", clas);
            ed.putString("time", time);
            ed.commit();
        }
        new Thread(){
            @Override
            public void run() {
                try {
                        URL url = new URL(image_url);
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                        conn.setConnectTimeout(5000);
                        conn.setReadTimeout(5000);
                        conn.setRequestMethod("GET");
                        //conn.connect();
                        if (conn.getResponseCode() == 200){
                            InputStream is = conn.getInputStream();
                            //2.把流里的数据读取出来，并构造成图片
                            File file = new File(context.getFilesDir()+"/img.jpg");
                            FileOutputStream fos = new FileOutputStream(file);
                            byte[] b = new byte[1024];
                            int len = 0;
                            while((len = is.read(b)) != -1){
                                fos.write(b, 0, len);
                            }
                        }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
