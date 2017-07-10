package cn.net.ntech.lntu.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

import org.w3c.dom.Text;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;

import cn.net.ntech.lntu.R;
import cn.net.ntech.lntu.bean.ExitApplication;
import cn.net.ntech.lntu.dialog.NormalDialog;
import cn.net.ntech.lntu.utils.FileUtils;
import cn.net.ntech.lntu.utils.TimeUtils;

public class BrowserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser);
        Toolbar toolbar = (Toolbar) findViewById(R.id.tb_browser_toolbar);
        toolbar.setTitle("详情");
        toolbar.setNavigationIcon(R.mipmap.ic_back);
        setSupportActionBar(toolbar);
        ExitApplication.addActivity(this);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        final String html = intent.getStringExtra("html");
        SharedPreferences sp = getSharedPreferences("cookie", MODE_PRIVATE);
        WebView wv_web = (WebView) findViewById(R.id.wv_web);
        wv_web.getSettings().setJavaScriptEnabled(true);
        wv_web.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        wv_web.setWebChromeClient(new WebChromeClient());
        ImageView iv_download = (ImageView) findViewById(R.id.iv_browser_download);

        if (!TextUtils.isEmpty(url)) {
            wv_web.loadUrl(url);
        } else if (!TextUtils.isEmpty(html)) {
            wv_web.getSettings().setDefaultTextEncodingName("GBK");//设置默认为utf-8
            wv_web.loadDataWithBaseURL(null, html, "text/html", "GBK", null);
        }
        if (!TextUtils.isEmpty(html)) {
            iv_download.setVisibility(View.VISIBLE);
            final String filename = FileUtils.FILEDIR+"/"+"试卷复查"+TimeUtils.getTime()+".doc";
            iv_download.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    saveFile(html,filename);
                }
            });
        }
    }

    private void saveFile(String str, String filename) {
        NormalDialog dialog = new NormalDialog(BrowserActivity.this,"正在为你解析保存…");
        dialog.show();
        try {
            OutputStreamWriter write = null;
            BufferedWriter out = null;
            if (filename != null) {
                try {   // new FileOutputStream(fileName, true) 第二个参数表示追加写入
                    write = new OutputStreamWriter(new FileOutputStream(filename), Charset.forName("gbk"));//一定要使用gbk格式
                    out = new BufferedWriter(write, 512);
                } catch (Exception e) {
                }
            }
            out.write(str);
            out.flush();
            out.close();
            dialog.dismiss();
            Toast.makeText(BrowserActivity.this, "下载完成,文件保存在"+filename+"\n若发现格式不正确，请在电脑上下载", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            dialog.dismiss();
            Toast.makeText(BrowserActivity.this, "解析失败", Toast.LENGTH_SHORT).show();
        }
    }
}
