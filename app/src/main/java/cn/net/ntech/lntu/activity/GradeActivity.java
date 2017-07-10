package cn.net.ntech.lntu.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

import java.util.List;
import cn.net.ntech.lntu.R;
import cn.net.ntech.lntu.bean.ExitApplication;
import cn.net.ntech.lntu.bean.GradeInfo;
import cn.net.ntech.lntu.adapter.GradeInfoAdapter;
import cn.net.ntech.lntu.utils.GradeHtmlUtils;
import cn.net.ntech.lntu.utils.MyHttpUtils;
import cn.net.ntech.lntu.utils.TimeUtils;
import cn.net.ntech.lntu.view.ClickCallback;
import cn.net.ntech.lntu.view.ProgressView;

public class GradeActivity extends BaseActivity {
    private String year =  "全部";
    private String term =  "全部";
    private RecyclerView recyclerView;
    private ProgressView pv_progress;
    private Spinner sn_term;
    private Spinner sn_year;
    private String[] arr_year;
    private String[] arr_term = {"全部","春","秋"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        setTitle("成绩查询");
        getGrade();
        pv_progress.start();
        pv_progress.setOnClickedCallBack(new ClickCallback() {
            @Override
            public void Clicked() {
                getGrade();
            }
        });

    }

    private void initView() {
        setContentView(R.layout.activity_grade);
        //任务栈
        ExitApplication.addActivity(this);
        pv_progress = (ProgressView) findViewById(R.id.pv_grade_progress);
        //设置RecyclerView
        recyclerView = (RecyclerView) findViewById(R.id.lv_gradeinfo);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        SharedPreferences sp3 = getSharedPreferences("info", MODE_PRIVATE);
        String nowtime = TimeUtils.getNowTime();
        String starttime = sp3.getString("time", nowtime);
        int now = Integer.parseInt(nowtime);
        int start = Integer.parseInt(starttime);
        arr_year = new String[now-start+2];
        arr_year[0] = "全部";
        int j = 1;
        for (int i=start;i<=now;i++){
            arr_year[j] = i+"";
            j=j+1;
        }
        //设置spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,arr_term);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,arr_year);
        sn_term = (Spinner) findViewById(R.id.sn_term);
        sn_year = (Spinner) findViewById(R.id.sn_year);
        sn_term.setAdapter(adapter);
        sn_year.setAdapter(adapter1);
    }

    private void getGrade(){
        SharedPreferences sp = getSharedPreferences("cookie", MODE_PRIVATE);
        //从SharedPreference里取数据
        String cookie = sp.getString("cookie", "");
        MyHttpUtils.getGrade(cookie, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String info = responseInfo.result;
                pullGrade(info);
            }

            @Override
            public void onFailure(HttpException e, String s) {
                pv_progress.failed();
                Toast.makeText(GradeActivity.this, "当前网络较慢，请检查网络设置", Toast.LENGTH_SHORT).show();
            }
        });
    }
    //解析成绩
    private void pullGrade(final String gradeinfo){
        List<GradeInfo> gradeinfos = GradeHtmlUtils.getGradeForm(GradeActivity.this, gradeinfo, year, term);
        String GPA = GradeHtmlUtils.getGPA(gradeinfo);
        setTitle("成绩查询("+GPA+")");
        recyclerView.setAdapter(new GradeInfoAdapter(GradeActivity.this, gradeinfos));
        pv_progress.finish();
        //处理查询事件
        sn_term.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String year = (String) sn_year.getSelectedItem();
                String term = arr_term[position];
                List<GradeInfo> gradeinfos1 = GradeHtmlUtils.getGradeForm(GradeActivity.this, gradeinfo,year,term);
                recyclerView.setAdapter(new GradeInfoAdapter(GradeActivity.this, gradeinfos1));
                Toast.makeText(GradeActivity.this,year+"-"+term,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sn_year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String year = arr_year[position];
                String term = (String) sn_term.getSelectedItem();
                List<GradeInfo> gradeinfos1 = GradeHtmlUtils.getGradeForm(GradeActivity.this, gradeinfo, year, term);
                recyclerView.setAdapter(new GradeInfoAdapter(GradeActivity.this, gradeinfos1));
                Toast.makeText(GradeActivity.this,year+"-"+term,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
