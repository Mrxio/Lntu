package cn.net.ntech.lntu.activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import cn.net.ntech.lntu.R;
import cn.net.ntech.lntu.dao.ClassInfoOpenHelper;
import cn.net.ntech.lntu.bean.ExitApplication;


public class ClassInfoActivity extends AppCompatActivity {
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_info);
        //任务栈
        ExitApplication.addActivity(this);
        //找到组件
        toolbar = (Toolbar)findViewById(R.id.tb_classinfotb);
        toolbar.setNavigationIcon(R.mipmap.ic_back);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        TextView tv_class_teacher = (TextView)findViewById(R.id.tv_class_teacher);
        TextView tv_class_grade = (TextView)findViewById(R.id.tv_class_grade);
        TextView tv_class_place = (TextView)findViewById(R.id.tv_class_place);
        TextView tv_class_time = (TextView)findViewById(R.id.tv_class_time);
        TextView tv_class_weeknum = (TextView)findViewById(R.id.tv_class_weeknum);

        //获得传过来的数据
        Intent intent = getIntent();
        String name = intent.getStringExtra("class");
        CollapsingToolbarLayout collapsingToolbar =(CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(name);
        Toolbar toolbar = (Toolbar) findViewById(R.id.tb_classinfotb);
        toolbar.setTitle(name);
        //从数据库中获取相关信息
        ClassInfoOpenHelper openHelper = new ClassInfoOpenHelper(ClassInfoActivity.this, 1);
        SQLiteDatabase db = openHelper.getWritableDatabase();
        Cursor cs = db.query("classinfo", new String[]{"classname", "classteacher", "classgrade", "classweeknum", "classweek", "classtime", "classplace"}, "classname = ?", new String[]{name}, null, null, null);
        String teacher = "";
        String grade = "";
        String weeknum = "";
        String week = "";
        String time = "";
        String place = "";
        String classtime = "";
        String classname = "";
        while(cs.moveToNext()) {
            classname = cs.getString(cs.getColumnIndex("classname"));
            teacher = cs.getString(cs.getColumnIndex("classteacher"));
            grade = cs.getString(cs.getColumnIndex("classgrade"));
            weeknum = cs.getString(cs.getColumnIndex("classweeknum"));
            week = cs.getString(cs.getColumnIndex("classweek"));
            time = cs.getString(cs.getColumnIndex("classtime"));
            place = cs.getString(cs.getColumnIndex("classplace"));
            if ((name).equals(classname)){
                classtime = classtime +week +"   "+ time+"\n";
            }
        }
        tv_class_teacher.setText("任课教师："+teacher);
        tv_class_grade.setText("课程学分："+grade);
        tv_class_place.setText("上课地点："+place);
        tv_class_time.setText("上课时间："+classtime.trim());
        tv_class_weeknum.setText("上课周期：第"+weeknum+"周");

    }

}
