package cn.net.ntech.lntu.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.view.Window;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import cn.net.ntech.lntu.R;

/**
 * Created by NTECHER on 2016/3/14.
 */
public class WeekSelectDialog extends Dialog {
    private NumberPicker numberPicker;
    private Context context;
    public WeekSelectDialog(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_weekselect);
        numberPicker = (NumberPicker)findViewById(R.id.np_weeknum);
        TextView queding = (TextView)findViewById(R.id.tv_weekselect_queding);
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(25);
        queding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int weeknum = numberPicker.getValue();
                //获取系统当前时间
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date curDate = new Date(System.currentTimeMillis());
                String nowTime = sdf.format(curDate);
                //获取当前星期
                long time=System.currentTimeMillis();
                Date date=new Date(time);
                SimpleDateFormat format=new SimpleDateFormat("E");
                String week = format.format(date);
                //保存数据
                SharedPreferences sp = context.getSharedPreferences("setDate", context.MODE_PRIVATE);
                SharedPreferences.Editor ed = sp.edit();
                //写数据
                ed.putString("setDate", nowTime+"/"+week+"/"+weeknum);
                ed.commit();
                Toast.makeText(context,"设置成功,当前为第"+ weeknum+"周",Toast.LENGTH_SHORT).show();
                dismiss();
            }
        });
    }
    @Override
    public void onBackPressed() {

    }
}
