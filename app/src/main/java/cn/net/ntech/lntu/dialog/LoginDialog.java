package cn.net.ntech.lntu.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;

import cn.net.ntech.lntu.R;

/**
 * Created by NTECHER on 2016/3/8.
 */
public class LoginDialog extends Dialog {
    private Context context;
    private ProgressBar progressBar;
    private TextView textView;
    public LoginDialog(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_login);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        textView = (TextView) findViewById(R.id.tv_login_dialog);
    }


    public void downloadClass(){
        textView.setText("正在为你导入课程");
    }

    @Override
    public void onBackPressed() {

    }
}
