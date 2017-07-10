package cn.net.ntech.lntu.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import java.io.File;
import cn.net.ntech.lntu.R;
import cn.net.ntech.lntu.activity.LoginActivity;
import cn.net.ntech.lntu.activity.MainActivity;
import cn.net.ntech.lntu.bean.AntoLogin;
import cn.net.ntech.lntu.dao.ConfigInfoOpenHelper;
import cn.net.ntech.lntu.bean.ExitApplication;

/**
 * Created by NTECHER on 2016/3/9.
 */
public class UpdateDialog extends Dialog{
    private Context context;
    private String VersionName;
    private String updateContent;
    private String downloadUrl;
    public UpdateDialog(Context context,String VersionName,String updateContent,String downloadUrl) {
        super(context);
        this.context = context;
        this.VersionName = VersionName;
        this.updateContent = updateContent;
        this.downloadUrl = downloadUrl;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialoglayout);
        final TextView title = (TextView)findViewById(R.id.tv_dialog_login_title);
        final TextView content = (TextView)findViewById(R.id.tv_content);
        final TextView quediing = (TextView)findViewById(R.id.tv_queding);
        final TextView quxiao = (TextView)findViewById(R.id.tv_quxiao);
        final ProgressBar progressbar = (ProgressBar) findViewById(R.id.pb_download);
        title.setText(VersionName + "版本发布啦！");
        content.setText(updateContent);
        quediing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*开启线程下载，并在状态栏中显示下载进度*/
                HttpUtils http = new HttpUtils();
                String target = Environment.getExternalStorageDirectory()+"/lntu.apk";
                title.setVisibility(View.GONE);
                quediing.setVisibility(View.GONE);
                quxiao.setVisibility(View.GONE);
                content.setText("正在下载");
                progressbar.setVisibility(View.VISIBLE);
                HttpHandler httpHandler = http.download(downloadUrl, target, new RequestCallBack<File>() {
                    @Override
                    public void onLoading(long total, long current, boolean isUploading) {
                        super.onLoading(total, current, isUploading);
                        System.out.println(total + "/" + current);
                        progressbar.setMax((int) total);
                        progressbar.setProgress((int)current);
                    }

                    @Override
                    public void onSuccess(ResponseInfo<File> responseInfo) {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.addCategory(Intent.CATEGORY_DEFAULT);
                        intent.setDataAndType(Uri.fromFile(responseInfo.result), "application/vnd.android.package-archive");
                        context.startActivity(intent);
                        ExitApplication.exit();
                    }
                    @Override
                    public void onFailure(HttpException e, String s) {
                        Toast.makeText(context, "下载失败"+s, Toast.LENGTH_SHORT).show();
                        //判断数据库是否存在,如果存在，则直接跳转到
                        ConfigInfoOpenHelper openHelper = new ConfigInfoOpenHelper(context, "configinfo.db", null, 1);
                        SQLiteDatabase db = openHelper.getWritableDatabase();
                        Cursor cs = db.rawQuery("select * from configinfo", null);
                        Intent intent = new Intent();
                        if (cs.getCount() > 0){
                            AntoLogin.AntoLogin(context);
                            intent.setClass(context,MainActivity.class);
                        }else {
                            intent.setClass(context, LoginActivity.class);
                        }
                        context.startActivity(intent);
                    }
                });
            }
        });

        quxiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //判断数据库是否存在,如果存在，则直接跳转到
                ConfigInfoOpenHelper openHelper = new ConfigInfoOpenHelper(context, "configinfo.db", null, 1);
                SQLiteDatabase db = openHelper.getWritableDatabase();
                Cursor cs = db.rawQuery("select * from configinfo", null);
                Intent intent = new Intent();
                if (cs.getCount() > 0){
                    AntoLogin.AntoLogin(context);
                    intent.setClass(context,MainActivity.class);
                }else {
                    intent.setClass(context, LoginActivity.class);
                }
                context.startActivity(intent);
                dismiss();
            }
        });
    }

    @Override
    public void onBackPressed() {

    }
}
