package cn.net.ntech.lntu.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

import java.util.List;
import cn.net.ntech.lntu.R;
import cn.net.ntech.lntu.bean.ExitApplication;
import cn.net.ntech.lntu.bean.TestInfo;
import cn.net.ntech.lntu.adapter.TestInfoAdapter;
import cn.net.ntech.lntu.utils.MyHttpUtils;
import cn.net.ntech.lntu.utils.TestHtmlUtils;
import cn.net.ntech.lntu.view.ClickCallback;
import cn.net.ntech.lntu.view.ProgressView;

public class TestActivity extends BaseActivity {
    private ProgressView pv_progress;
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        //任务栈
        ExitApplication.addActivity(this);
        getTest();
        pv_progress.start();
        pv_progress.setOnClickedCallBack(new ClickCallback() {
            @Override
            public void Clicked() {
                getTest();
            }
        });
    }

    private void initView() {
        setContentView(R.layout.activity_test);
        pv_progress = (ProgressView) findViewById(R.id.pv_test_progress);
        recyclerView = (RecyclerView) findViewById(R.id.lv_testinfo);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(TestActivity.this));
        setTitle("考试查询");
    }
    private void getTest(){
        SharedPreferences sp = getSharedPreferences("cookie", MODE_PRIVATE);
        //从SharedPreference里取数据
        String cookie = sp.getString("cookie", "");
        MyHttpUtils.getTest(cookie, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String info = responseInfo.result;
                List<TestInfo> testinfo = TestHtmlUtils.getTestInfo(info);
                recyclerView.setAdapter(new TestInfoAdapter(TestActivity.this,testinfo));
                pv_progress.finish();
            }

            @Override
            public void onFailure(HttpException e, String s) {
                pv_progress.failed();
                Toast.makeText(TestActivity.this, "当前网络较慢，请检查网络设置", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
