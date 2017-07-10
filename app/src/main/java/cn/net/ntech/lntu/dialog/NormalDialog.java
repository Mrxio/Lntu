package cn.net.ntech.lntu.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;
import cn.net.ntech.lntu.R;

/**
 * Created by NTECHER on 2016/3/21.
 */
public class NormalDialog extends Dialog {
    private String info;
    private Context context;
    private TextView textView;
    public NormalDialog(Context context,String info) {
        super(context);
        this.context = context;
        this.info = info;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_normal);
        textView = (TextView)findViewById(R.id.tv_dialog_info);
        textView.setText(info);
    }
    public void setTextView(String string){
        textView.setText(string);
    }
}
