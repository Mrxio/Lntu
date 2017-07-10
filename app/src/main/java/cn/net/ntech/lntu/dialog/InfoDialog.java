package cn.net.ntech.lntu.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import cn.net.ntech.lntu.R;
import cn.net.ntech.lntu.bean.ExitApplication;

/**
 * Created by NTECHER on 2016/6/12.
 */
public class InfoDialog extends Dialog{
    private TextView tv_title;
    private TextView tv_content;
    private TextView tv_queding;
    private TextView tv_pingshi;
    private TextView tv_qizhong;
    private TextView tv_qimo;
    private TextView tv_zong;
    private String jisuan ;
    private String pingshi;
    private String qizhong;
    private String qimo;
    private String zong;
    public TextView tv_xiangqing;

    public InfoDialog(Context context,String jisuan ,String pingshi,String qizhong,String qimo,String zong) {
        super(context);
        this.jisuan = jisuan;
        this.pingshi =pingshi;
        this.qizhong = qizhong;
        this.qimo = qimo;
        this.zong = zong;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_info);
        tv_queding = (TextView) findViewById(R.id.tv_info_queding);
        tv_title = (TextView) findViewById(R.id.tv_info_title);
        tv_content = (TextView) findViewById(R.id.tv_info_content);
        tv_pingshi = (TextView) findViewById(R.id.tv_info_pingshi);
        tv_qizhong = (TextView) findViewById(R.id.tv_info_qizhong);
        tv_qimo = (TextView) findViewById(R.id.tv_info_qimo);
        tv_zong = (TextView) findViewById(R.id.tv_info_zong);
        tv_content.setText("("+jisuan+")");
        tv_pingshi.setText(pingshi);
        tv_qizhong.setText(qizhong);
        tv_qimo.setText(qimo);
        tv_zong.setText(zong);
        tv_queding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        tv_xiangqing = (TextView) findViewById(R.id.tv_info_xiangqing);
    }
}
