package cn.net.ntech.lntu.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

import cn.net.ntech.lntu.R;
import cn.net.ntech.lntu.utils.GongGaoInfoUtils;
import cn.net.ntech.lntu.utils.MyHttpUtils;

public class GongGaoInfoActivity extends AppCompatActivity {
    private TextView info;
    private ImageView iv_browser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gong_gao_info);
        info = (TextView) findViewById(R.id.tv_gonggao_info);
        TextView newstitle = (TextView) findViewById(R.id.tv_gonggao_title);
        TextView newstime = (TextView) findViewById(R.id.tv_gonggao_time);
        iv_browser = (ImageView) findViewById(R.id.iv_browser);
        Intent intent = getIntent();
        Toolbar toolbar = (Toolbar) findViewById(R.id.tb_gonggaoinfo_toolbar);
        toolbar.setTitle("正文");
        toolbar.setNavigationIcon(R.mipmap.ic_back);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        final String url = intent.getStringExtra("url");
        String title = intent.getStringExtra("title");
        String time = intent.getStringExtra("time");
        newstitle.setText(title);
        newstime.setText(time + "发布");
        MyHttpUtils.normalGet(url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String info1 = responseInfo.result;
                String gonggao = GongGaoInfoUtils.getInfo(info1);
                info.setText(gonggao);
            }

            @Override
            public void onFailure(HttpException e, String s) {

            }
        });
        iv_browser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(GongGaoInfoActivity.this,BrowserActivity.class);
                intent1.putExtra("url",url);
                startActivity(intent1);
            }
        });
    }
}
