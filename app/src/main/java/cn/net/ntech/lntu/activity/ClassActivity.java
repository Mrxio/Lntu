package cn.net.ntech.lntu.activity;


import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.text.TextPaint;
import android.view.GestureDetector;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import cn.net.ntech.lntu.R;
import cn.net.ntech.lntu.adapter.ClassAdapter;
import cn.net.ntech.lntu.dao.ClassInfoDao;
import cn.net.ntech.lntu.dao.ClassInfoOpenHelper;
import cn.net.ntech.lntu.bean.ExitApplication;
import cn.net.ntech.lntu.utils.HtmlUtils;
import cn.net.ntech.lntu.utils.MyHttpUtils;
import cn.net.ntech.lntu.utils.StringUtils;
import cn.net.ntech.lntu.utils.TimeUtils;
import cn.net.ntech.lntu.view.MyGridLayoutManager;


public class ClassActivity extends AppCompatActivity{

    private int weeknameConfig = 1;
    private int weeknameConfig2;
    private GestureDetector gestureDetector;
    private RecyclerView recyclerView;
    private ClassAdapter adapter;
    private TextView textView1;
    private TextView textView2;
    private TextView textView3;
    private TextView textView4;
    private TextView textView5;
    private TextView textView6;
    private TextView textView7;
    private TextView tv_title;
    private ImageView iv_emnu;
    private ClassInfoDao dao;
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class);
        gestureDetector = new GestureDetector(this,onGestureListener);
        //任务栈
        ExitApplication.addActivity(this);

        initView();
        initTitle();
        initData();
    }
    //初始化组件
    private void initView() {
        tv_title = (TextView) findViewById(R.id.tv_class_text);
        findViewById(R.id.iv_class_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        recyclerView = (RecyclerView) findViewById(R.id.rv_class);
        recyclerView.setLayoutManager(new MyGridLayoutManager(this, 7));
        recyclerView.setHasFixedSize(true);

        textView1 = (TextView) findViewById(R.id.tv_textview1);
        textView2 = (TextView) findViewById(R.id.tv_textview2);
        textView3 = (TextView) findViewById(R.id.tv_textview3);
        textView4 = (TextView) findViewById(R.id.tv_textview4);
        textView5 = (TextView) findViewById(R.id.tv_textview5);
        textView6 = (TextView) findViewById(R.id.tv_textview6);
        textView7 = (TextView) findViewById(R.id.tv_textview7);

        iv_emnu = (ImageView) findViewById(R.id.iv_class_emun);
        iv_emnu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                PopupMenu popup = new PopupMenu(ClassActivity.this, iv_emnu);
                //Inflating the Popup using xml file
                popup.getMenuInflater().inflate(R.menu.poupup_menu_class, popup.getMenu());
                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.m_class_refresh:
                                UpdateClass up = new UpdateClass(ClassActivity.this);
                                up.show();
                                break;
                            case R.id.m_class_change:
                                WeekSelectDialogNew dialog = new WeekSelectDialogNew(ClassActivity.this);
                                dialog.setCanceledOnTouchOutside(false);
                                dialog.show();
                                initData();
                                break;
                            case R.id.m_class_add:
                                startActivity(new Intent(ClassActivity.this,AddClassActivity.class));
                                break;
                        }
                        return true;
                    }
                });
                popup.show(); //showing popup menu
            }
        });
    }

    // 获取当前系统时间,并让制定的组件变色
    public void getTime(){
        long time=System.currentTimeMillis();
        Date date=new Date(time);
        SimpleDateFormat format=new SimpleDateFormat("E");
        String week = format.format(date);
        TextPaint tp;
        switch (week){
            case "周一":
                textView1.setTextColor(0xffff0000);
                tp = textView1.getPaint();
                tp.setFakeBoldText(true);
                break;
            case "周二":
                textView2.setTextColor(0xffff0000);
                tp = textView2.getPaint();
                tp.setFakeBoldText(true);
                break;
            case "周三":
                textView3.setTextColor(0xffff0000);
                tp = textView3.getPaint();
                tp.setFakeBoldText(true);
                break;
            case "周四":
                textView4.setTextColor(0xffff0000);
                tp = textView4.getPaint();
                tp.setFakeBoldText(true);
                break;
            case "周五":
                textView5.setTextColor(0xffff0000);
                tp = textView5.getPaint();
                tp.setFakeBoldText(true);
                break;
            case "周六":
                textView6.setTextColor(0xffff0000);
                tp = textView6.getPaint();
                tp.setFakeBoldText(true);
                break;
            case "周日":
                textView7.setTextColor(0xffff0000);
                tp = textView7.getPaint();
                tp.setFakeBoldText(true);
                break;
        }
    }

    public void initTitle(){
        //获取当前周
        SharedPreferences sp = getSharedPreferences("setDate", MODE_PRIVATE);
        String timeinfo = sp.getString("setDate",null);
        if (timeinfo == null){
            Toast.makeText(this,"未知错误，错误编码xt0001",Toast.LENGTH_LONG).show();
        }else {
            try {
                weeknameConfig = TimeUtils.getWeekNum(timeinfo);
                weeknameConfig2 = weeknameConfig;
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        tv_title.setText(StringUtils.toWeekDay(weeknameConfig, 2));
    }

    //初始化数据
    private void initData(){
        //读取数据库中除重的课程
        dao = new ClassInfoDao(this);
        List<String> list = dao.qurrClassName();
        adapter = new ClassAdapter(ClassActivity.this,list,weeknameConfig2);
        recyclerView.setAdapter(adapter);
        getTime();
    }

    private GestureDetector.OnGestureListener onGestureListener = new GestureDetector.SimpleOnGestureListener() {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            float x = e2.getX() - e1.getX();
            float y = e2.getY() - e1.getY();
            if (e2.getY() - e1.getY()<0){
                y = e1.getY() - e2.getY();
            }
            if (e1.getX() - e2.getX() > 180 && Math.abs(velocityX) > 10 && y < 100) {
                if (weeknameConfig2>1&&weeknameConfig2<=26){
                    weeknameConfig2 = weeknameConfig2-1;
                    String weeknum = StringUtils.toWeekDay(weeknameConfig2, 2);
                    if (weeknameConfig2!=weeknameConfig){
                        weeknum = weeknum + "*";
                    }

                    tv_title.setText(weeknum);
                    initData();
                }
            } else if (e2.getX() - e1.getX() > 180 && Math.abs(velocityX) > 10 && y < 100) {
                if (weeknameConfig2>0&&weeknameConfig2<=24){
                    weeknameConfig2 = weeknameConfig2+1;

                    String weeknum = StringUtils.toWeekDay(weeknameConfig2, 2);
                    if (weeknameConfig2!=weeknameConfig){
                        weeknum = weeknum + "*";
                    }
                    tv_title.setText(weeknum);
                    initData();
                }
            }
            return false;
        }
    };

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        this.gestureDetector.onTouchEvent(ev);
        return super.dispatchTouchEvent(ev);
    }

    //对话框
    public class WeekSelectDialogNew extends Dialog {
        private NumberPicker numberPicker;
        private Context context;
        public WeekSelectDialogNew(Context context) {
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
                    //设置标题和数据
                    initTitle();
                    initData();
                    dismiss();
                }
            });
        }
        @Override
        public void onBackPressed() {

        }
    }
    public class UpdateClass extends Dialog {
        private ProgressBar pb;
        private TextView textView;
        private Context context;
        Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what){
                    case 2:
                        textView.setText("正在连接服务器…");
                        break;
                    case 3:
                        textView.setText("正在请求数据…");
                        break;
                    case 4:
                        Toast.makeText(ClassActivity.this, "更新课表成功", Toast.LENGTH_SHORT).show();
                        initData();
                        dismiss();
                        break;
                    case 5:
                        dismiss();
                        Toast.makeText(context, "更新失败", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };

        //刷新课表对话框
        public UpdateClass(Context context) {
            super(context);
            this.context = context;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.dialog_updateclass);
            pb = (ProgressBar) findViewById(R.id.pb_updateclass_pb);
            textView = (TextView) findViewById(R.id.tv_updateclass_textview);
            textView.setText("正在删除课表…");
            //删除数据库
            dao.clearClass();
            Message msg = new Message();
            msg.what = 2;
            handler.sendMessage(msg);
            //获取cookie
            SharedPreferences sp = getContext().getSharedPreferences("cookie", getContext().MODE_PRIVATE);
            //从SharedPreference里取数据
            String cookie = sp.getString("cookie", "");
            MyHttpUtils.getClass(cookie, new RequestCallBack<String>() {
                @Override
                public void onSuccess(ResponseInfo<String> responseInfo) {
                    Message MSG = new Message();
                    MSG.what = 3;
                    handler.sendMessage(MSG);
                    //获取课程表并储存在数据库中
                    String classinfo = responseInfo.result;
                    HtmlUtils.HtmlAnalysisi(classinfo, getContext());
                    Message msg1 = new Message();
                    msg1.what = 4;
                    handler.sendMessage(msg1);
                }

                @Override
                public void onFailure(HttpException e, String s) {
                    Message msg1 = new Message();
                    msg1.what = 5;
                    handler.sendMessage(msg1);
                }
            });
        }
        @Override
        public void onBackPressed() {

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dao.closeDB();
    }
}