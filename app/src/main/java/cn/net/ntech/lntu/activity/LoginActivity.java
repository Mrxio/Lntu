package cn.net.ntech.lntu.activity;


import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
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

import java.util.ArrayList;
import java.util.List;
import cn.net.ntech.lntu.R;
import cn.net.ntech.lntu.dao.ClassInfoOpenHelper;
import cn.net.ntech.lntu.dao.ConfigInfoOpenHelper;
import cn.net.ntech.lntu.dialog.LoginDialog;
import cn.net.ntech.lntu.bean.ExitApplication;
import cn.net.ntech.lntu.utils.HtmlUtils;
import cn.net.ntech.lntu.utils.InfoUtils;
import cn.net.ntech.lntu.utils.MyHttpUtils;

public class LoginActivity extends AppCompatActivity {
    private String login_url = "http://60.18.131.131:11080/newacademic/j_acegi_security_check";
    private String cookie = null;
    private Button bt_login;
    private String username;
    private String password;
    private LoginDialog dialog;
    private int MSG_DOWNLOADING = 4;
    private int MSG_DOWNLOAD_SUCCEED = 5;
    private int MSG_ALL_RIGHT = 6;
    private String classinfo;
    private TextInputLayout til_username;
    private TextInputLayout til_password;

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            Intent intent = new Intent();
            switch (msg.what){
                case 4:
                    dialog.downloadClass();
                    break;
                case 5:
                case 6:
                    intent.setClass(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    dialog.dismiss();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //任务栈
        ExitApplication.addActivity(this);
        bt_login = (Button) findViewById(R.id.bt_login);
        til_username = (TextInputLayout) findViewById(R.id.til_username);
        til_password = (TextInputLayout) findViewById(R.id.til_password);
        til_username.setHint("教务在线账户");
        til_password.setHint("教务在线密码");
        //登陆按键的单击事件
        til_username.getEditText().addTextChangedListener(new TextWatcher() {//对edittext添加监听
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (til_username.getEditText().getText().toString().length() > 10) {
                    til_username.setErrorEnabled(true);//是否设置错误提示信息
                    til_username.setError("账号为10位");//设置错误提示信息
                } else {
                    til_username.setErrorEnabled(false);//不设置错误提示信息
                }
            }
        });

        til_password.getEditText().addTextChangedListener(new TextWatcher() {//对edittext添加监听
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (til_password.getEditText().getText().toString().length() > 20) {
                    til_password.setErrorEnabled(true);//是否设置错误提示信息
                    til_password.setError("密码位数超限");//设置错误提示信息
                } else {
                    til_password.setErrorEnabled(false);//不设置错误提示信息
                }
            }
        });

        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = til_username.getEditText().getText().toString().trim();
                password = til_password.getEditText().getText().toString().trim();
                System.out.println(username+password);
                if (username.equals("")) {
                    til_username.setError("帐号不能为空");
                } else if (password.equals("")) {
                    til_password.setError("密码不能为空");
                } else if (username.equals("") && password.equals("")) {
                    til_username.setError("帐号不能为空");
                    til_password.setError("密码不能为空");
                } else {
                    dialog = new LoginDialog(LoginActivity.this);
                    dialog.setCanceledOnTouchOutside(false);
                    dialog.show();
                    login();
                }
            }
        });
    }
    @Override
    public void onBackPressed() {
        ExitApplication.exit();
    }
    //获取课表信息
    public void getClassInfo() {
        //检查课程信息是否已经存在数据库中
        ClassInfoOpenHelper openHelper = new ClassInfoOpenHelper(LoginActivity.this, 1);
        SQLiteDatabase db = openHelper.getWritableDatabase();
        Cursor cs = db.rawQuery("select * from classinfo", null);
        if (cs.getCount() < 2){
            Message MSG = new Message();
            MSG.what = MSG_DOWNLOADING;
            handler.sendMessage(MSG);
            MyHttpUtils.getClass(cookie, new RequestCallBack<String>() {
                @Override
                public void onSuccess(ResponseInfo<String> responseInfo) {
                    //获取课程表并储存在数据库中
                    classinfo = responseInfo.result;
                    HtmlUtils.HtmlAnalysisi(classinfo, LoginActivity.this);
                    Message msg = new Message();
                    msg.what = MSG_DOWNLOAD_SUCCEED;
                    msg.obj = classinfo;
                    handler.sendMessage(msg);
                }

                @Override
                public void onFailure(HttpException e, String s) {
                    dialog.dismiss();
                    netError();
                }
            });
        }else {
            Message msg6 = new Message();
            msg6.what = MSG_ALL_RIGHT;
            handler.sendMessage(msg6);
        }
    }
    //获取个人信息
    public void getInfo() {
        MyHttpUtils.getInfo(cookie, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                InfoUtils.getInfo(responseInfo.result, LoginActivity.this);
            }

            @Override
            public void onFailure(HttpException e, String s) {
                netError();
            }
        });
    }
    //登录
    public void login(){
        final HttpUtils http = new HttpUtils(10*1000);
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
                    Toast.makeText(LoginActivity.this, aa+"**", Toast.LENGTH_SHORT).show();
                    System.out.println(aa+"***");
                    cookie = "JSESSIONID=" + aa;
                    //登录成功，将帐号密码和cookie写入数据库中
                    ConfigInfoOpenHelper openHelper = new ConfigInfoOpenHelper(LoginActivity.this, "configinfo.db", null, 1);
                    SQLiteDatabase db = openHelper.getWritableDatabase();
                    ContentValues cv = new ContentValues();
                    cv.put("username", username);
                    cv.put("password", password);
                    db.insert("configinfo", null, cv);
                    db.close();
                    //储存cookie信息
                    SharedPreferences sp = getSharedPreferences("cookie", MODE_PRIVATE);
                    SharedPreferences.Editor ed = sp.edit();
                    ed.putString("cookie", cookie);
                    ed.commit();
                    getInfo();
                    getClassInfo();
                }else {
                    dialog.dismiss();
                    til_username.setError("账户或密码错误");
                    til_password.setError("账户或密码错误");
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                dialog.dismiss();
                netError();
            }
        });
    }
    public void netError(){
        Toast.makeText(LoginActivity.this, "当前网络不可用，请检查网络设置", Toast.LENGTH_SHORT).show();
    }
}