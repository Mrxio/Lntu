package cn.net.ntech.lntu.activity;

import android.content.Intent;
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
import cn.net.ntech.lntu.adapter.GongGaoAdapter;
import cn.net.ntech.lntu.bean.GongGaoInfo;
import cn.net.ntech.lntu.utils.GongGaoUtils;
import cn.net.ntech.lntu.utils.MyHttpUtils;
import cn.net.ntech.lntu.view.ClickCallback;
import cn.net.ntech.lntu.view.ProgressView;

public class GongGaoActivity extends BaseActivity {
    private ProgressView pv_progress;
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        pv_progress.start();
        getGongGao();
        pv_progress.setOnClickedCallBack(new ClickCallback() {
            @Override
            public void Clicked() {
                getGongGao();
            }
        });
    }

    private void initView() {
        setContentView(R.layout.activity_gong_gao);
        setTitle("公告");
        pv_progress = (ProgressView) findViewById(R.id.pv_gong_progress);
        recyclerView = (RecyclerView) findViewById(R.id.rv_gonggaoinfo);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void getGongGao(){
        MyHttpUtils.getGongGao(new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                //获取课程表并储存在数据库中
                String gonggaoinfo = responseInfo.result;
                List<GongGaoInfo> info = GongGaoUtils.getGongGaoItem(gonggaoinfo);
                GongGaoAdapter adapter = new GongGaoAdapter(GongGaoActivity.this,info);
                recyclerView.setAdapter(adapter);
                pv_progress.finish();
            }

            @Override
            public void onFailure(HttpException e, String s) {
                pv_progress.failed();
                Toast.makeText(GongGaoActivity.this, "当前网络较慢，请检查网络设置", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
