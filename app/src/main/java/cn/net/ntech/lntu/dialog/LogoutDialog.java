package cn.net.ntech.lntu.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import java.io.File;

import cn.net.ntech.lntu.R;
import cn.net.ntech.lntu.activity.LoginActivity;

/**
 * Created by NTECHER on 2016/3/15.
 */
public class LogoutDialog extends Dialog {
    private Context context;
    public LogoutDialog(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_logout);
        TextView queding = (TextView) findViewById(R.id.tv_logout_queding);
        TextView quxiao = (TextView)findViewById(R.id.tv_logout_quxiao);
        queding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //删除数据库
                File dbFile = new File("/data/data/cn.net.ntech.lntu/databases/classinfo.db");
                dbFile.delete();
                //删除数据库
                File db1File = new File("/data/data/cn.net.ntech.lntu/databases/configinfo.db");
                db1File.delete();
                //删除日期设置
                SharedPreferences sp = context.getSharedPreferences("setDate", context.MODE_PRIVATE);
                SharedPreferences.Editor ed = sp.edit();
                ed.clear();
                ed.commit();
                //删除个人信息
                SharedPreferences sp1 = context.getSharedPreferences("info",context.MODE_PRIVATE);
                SharedPreferences.Editor ed1 = sp1.edit();
                ed1.clear();
                ed1.commit();
                //删除头像
                File srcfile = new File(context.getFilesDir()+"/img.jpg");
                srcfile.delete();
                Intent intent = new Intent(getContext(), LoginActivity.class);
                getContext().startActivity(intent);

            }
        });
        quxiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}
