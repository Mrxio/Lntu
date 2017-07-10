package cn.net.ntech.lntu.activity;

import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.net.ntech.lntu.R;
import cn.net.ntech.lntu.dialog.NormalDialog;

public class FeedBackActivity extends BaseActivity {
    private EditText email;
    private EditText content;
    private HttpClient httpClient = new DefaultHttpClient();
    private String feedback_url = "http://www.ntech.net.cn/app/lntu/send.php";
    private NormalDialog dialog;
    private Button tijiao;
    private TextView textView1;
    private TextView textView2;
    private TextView tv_tishi;
    private ImageView im_tishi;
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 2:
                    dialog.show();
                    break;
                case 3:
                    dialog.dismiss();
                    email.setVisibility(View.GONE);
                    content.setVisibility(View.GONE);
                    tijiao.setVisibility(View.GONE);
                    textView1.setVisibility(View.GONE);
                    textView2.setVisibility(View.GONE);
                    tv_tishi.setVisibility(View.VISIBLE);
                    im_tishi.setVisibility(View.VISIBLE);
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back);
        setTitle("反馈");
        dialog = new NormalDialog(FeedBackActivity.this,"正在提交…");
        email = (EditText) findViewById(R.id.et_feedback_email);
        content = (EditText) findViewById(R.id.et_feedback_content);
        tijiao = (Button) findViewById(R.id.bt_feedback);
        textView1 = (TextView) findViewById(R.id.tv_feedback2);
        textView2 = (TextView) findViewById(R.id.tv_feedback1);
        tv_tishi = (TextView) findViewById(R.id.tv_feedback_tishi);
        im_tishi = (ImageView) findViewById(R.id.im_feedback);
        tijiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String e_mail = email.getText().toString().trim();
                final String fdcontent = content.getText().toString().trim();
                if (e_mail.equals("")||fdcontent.equals("")){
                    Snackbar.make(content,"请将内容填写完整",Snackbar.LENGTH_SHORT).show();
                }else{
                    String strPhoneModule = android.os.Build.MODEL;
                    String strSystemType = android.os.Build.VERSION.RELEASE;
                    final String device = strPhoneModule+"-"+strSystemType;
                    //获取系统时间
                    Date date=new Date(System.currentTimeMillis());
                    SimpleDateFormat format=new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
                    final String seedtime = format.format(date);
                    //连接服务器进行反馈
                    new Thread() {
                        @Override
                        public void run() {
                            Message msg1 = new Message();
                            msg1.what=2;
                            handler.sendMessage(msg1);
                    /*尝试登陆并获取cookie*/
                            HttpResponse httpResponse;
                            //建立HTTP Post连线
                            HttpPost httpRequest = new HttpPost(feedback_url);
                            List<NameValuePair> params = new ArrayList<NameValuePair>();
                            //添加pose信息
                            params.add(new BasicNameValuePair("device", device));
                            params.add(new BasicNameValuePair("send_time", seedtime));
                            params.add(new BasicNameValuePair("message", fdcontent));
                            params.add(new BasicNameValuePair("email", e_mail));
                            try {
                                // 发出HTTP request
                                httpRequest.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
                                // 取得HTTP response
                                httpResponse = httpClient.execute(httpRequest); // 执行
                                // 获取状态码
                                int Status = httpResponse.getStatusLine().getStatusCode();
                                if (Status == 200) {
                                    Message msg = new Message();
                                    msg.what=3;
                                    handler.sendMessage(msg);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }.start();

                }
            }
        });
    }
}
