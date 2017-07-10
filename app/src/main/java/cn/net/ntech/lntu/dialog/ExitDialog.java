package cn.net.ntech.lntu.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import cn.net.ntech.lntu.R;
import cn.net.ntech.lntu.bean.ExitApplication;

/**
 * Created by NTECHER on 2016/3/15.
 */
public class ExitDialog extends Dialog {
    public ExitDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_exit_layout);
        TextView queding = (TextView) findViewById(R.id.tv_exit_queding);
        TextView quxiao = (TextView) findViewById(R.id.tv_exit_quxiao);
        queding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExitApplication.exit();
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
