package cn.net.ntech.lntu.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import cn.net.ntech.lntu.R;
import cn.net.ntech.lntu.dao.ConfigInfoOpenHelper;
import cn.net.ntech.lntu.bean.ExitApplication;
import cn.net.ntech.lntu.utils.FileUtils;

public class SplashActivity extends AppCompatActivity {

    private String[] version = new String[2];
    private String login_url = "http://60.18.131.131:11080/newacademic/j_acegi_security_check";

    Handler handler = new Handler(){
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 2:
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    break;
            }
        }
    };

    /*activity创建时调用*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        //任务栈
        ExitApplication.addActivity(this);
//        //获取版本号
//        versionCode = getVersionCode();

        //创建文件夹
        File file = new File(FileUtils.FILEDIR);
        if (!file.exists()){
            file.mkdirs();
        }
        //判断账号是否存在，存在则自动登录
        ConfigInfoOpenHelper openHelper = new ConfigInfoOpenHelper(SplashActivity.this, "configinfo.db", null, 1);
        SQLiteDatabase db = openHelper.getWritableDatabase();
        Cursor cs = db.rawQuery("select * from configinfo", null);
        if (cs.getCount() > 0) {
            //自动登录并获取cookie
            login();
        }else {
            new Thread(){
                @Override
                public void run() {
                    super.run();
                    try {
                        sleep(3*1000);
                        handler.sendEmptyMessage(2);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        }
    }

//    /*获取当前版本号*/
//    private int getVersionCode(){
//        int mVersionCode = 0;
//        PackageManager packageManager = getPackageManager();
//        try {
//            PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(), 0);
//            mVersionCode = packageInfo.versionCode;
//        } catch (PackageManager.NameNotFoundException e) {
//            //找到、不到包名时会报错
//            e.printStackTrace();
//        }
//        return mVersionCode;
//    }

    /*
    从服务器获取数据进行版本校验

    */
//    private void checkVersion(){
//        final long StartTime = System.currentTimeMillis();
//        final Message msg = new Message();
//        new Thread(){
//            @Override
//            public void run() {
//                try {
//                    /*与服务器通讯*/
//                    URL url = new URL("http://www.ntech.net.cn/app/lntu/update.json");
//                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//                    conn.setRequestMethod("GET");
//                    conn.setConnectTimeout(3000);
//                    conn.setReadTimeout(3000);
//                    conn.connect();
//                    if(conn.getResponseCode() == 200){
//                        InputStream inputStream = conn.getInputStream();
//                        String reasult = StreamUtils.streamToString(inputStream);
//                        JSONObject jo = new JSONObject(reasult);
//                        String NVersionCode = jo.get("versionCode").toString();
//                        String nVersionName = jo.get("versionName").toString();
//                        String updateContent = jo.get("updateContent").toString();
//                        String downloadUrl = jo.get("downloadUrl").toString();
//                        //获取版本号并进行比较
//                        int nVersionCode = 1;
//                        try {
//                            nVersionCode = Integer.parseInt(NVersionCode);
//                        } catch (NumberFormatException e) {
//                            e.printStackTrace();
//                        }
//                        //判断本地版本与服务器版本
//                        if(versionCode<nVersionCode){
//                            String[] updateMessage = new String[3];
//                            updateMessage[0] = nVersionName;
//                            updateMessage[1] = updateContent;
//                            updateMessage[2] = downloadUrl;
//                            msg.obj = updateMessage;
//                            msg.what = 2;
//                            System.out.println(reasult);
//                        }else {
//                            msg.what = 3;
//                        }
//                    }
//                }catch (Exception e) {
//                    msg.what = 4;
//                    e.printStackTrace();
//                }finally {
//                    /*让闪屏页面始终保持3秒钟*/
//                    long EndTime = System.currentTimeMillis();
//                    if((EndTime-StartTime) < 2500){
//                        try {
//                            Thread.sleep(2500 - (EndTime - StartTime));
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                    handler.sendMessage(msg);
//                }
//            }
//        }.start();
//
//    }
    @Override
    public void onBackPressed() {
    }
    public void login(){
        //获取数据库中的帐号和密码
        String password = null;
        String username = null;
        ConfigInfoOpenHelper openHelper = new ConfigInfoOpenHelper(this, "configinfo.db", null, 1);
        SQLiteDatabase db = openHelper.getWritableDatabase();
        Cursor cs = db.rawQuery("select * from configinfo", null);
        while (cs.moveToNext()){
            password = cs.getString(cs.getColumnIndex("password"));
            username = cs.getString(cs.getColumnIndex("username"));
        }
        db.close();
        final HttpUtils http = new HttpUtils(5*1000);
        RequestParams params = new RequestParams();
        List<NameValuePair> list = new ArrayList<NameValuePair>();
        //添加pose信息
        list.add(new BasicNameValuePair("j_username", username));
        list.add(new BasicNameValuePair("j_password", password));
        params.setHeader("Host", "60.18.131.131:11080");
        params.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1; Win64; x64; Trident/4.0; .NET CLR 2.0.50727; SLCC2; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; .NET4.0C; Tablet PC 2.0; .NET4.0E)");
        params.addBodyParameter(list);
        http.configResponseTextCharset("GBK");
        http.send(HttpRequest.HttpMethod.POST, login_url, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                Document html = Jsoup.parse(responseInfo.result);
                //若包含字段则登录失败
                Elements error_result = html.select("div.error");
                String errorinfo = null;
                for (Element error : error_result) {
                    errorinfo = error.text();
                }
                if (errorinfo == null) {
                    DefaultHttpClient dh = (DefaultHttpClient) http.getHttpClient();
                    CookieStore cs = dh.getCookieStore();
                    List<Cookie> cookies = cs.getCookies();
                    String aa = null;
                    for (int i = 0; i < cookies.size(); i++) {
                        if ("JSESSIONID".equals(cookies.get(i).getName())) {
                            aa = cookies.get(i).getValue();
                            break;
                        }
                    }
                    System.out.println(aa+"***");
                    String cookie = "JSESSIONID=" + aa;
                    //储存cookie信息
                    SharedPreferences sp = getSharedPreferences("cookie", MODE_PRIVATE);
                    SharedPreferences.Editor ed = sp.edit();
                    ed.putString("cookie", cookie);
                    ed.commit();
                    startActivity(new Intent(SplashActivity.this,MainActivity.class));
                }else {
                    Toast.makeText(SplashActivity.this, "当前网络不可用，请检查网络设置", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SplashActivity.this,MainActivity.class));
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                Toast.makeText(SplashActivity.this, "当前网络不可用，请检查网络设置", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(SplashActivity.this,MainActivity.class));
            }
        });
    }
}
