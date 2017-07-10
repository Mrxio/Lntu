package cn.net.ntech.lntu.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

import java.util.ArrayList;
import java.util.List;
import cn.net.ntech.lntu.R;
import cn.net.ntech.lntu.adapter.GuaKeAdapter;
import cn.net.ntech.lntu.bean.GuaKeInfo;
import cn.net.ntech.lntu.utils.GuaKeUtils;
import cn.net.ntech.lntu.utils.MyHttpUtils;
import cn.net.ntech.lntu.view.ClickCallback;
import cn.net.ntech.lntu.view.ProgressView;

public class GuaKeActivity extends BaseActivity {
    private ProgressView pv_progress;
    private ExpandableListView expandableListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        pv_progress.start();
        getGuaKe();
        pv_progress.setOnClickedCallBack(new ClickCallback() {
            @Override
            public void Clicked() {
                getGuaKe();
            }
        });

    }
    private void initView(){
        setContentView(R.layout.activity_gua_ke);
        setTitle("挂科");
        pv_progress = (ProgressView) findViewById(R.id.pv_gua_progress);
        expandableListView = (ExpandableListView) findViewById(R.id.elv_guakeinfo);
    }
    private void getGuaKe(){
        SharedPreferences sp = getSharedPreferences("cookie", MODE_PRIVATE);
        //从SharedPreference里取数据
        String cookie = sp.getString("cookie", "");
        MyHttpUtils.getGuake(cookie, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                //获取到的信息
                String info = responseInfo.result;
                //得到挂科信息，并保存到集合中
                List<GuaKeInfo> guaKeInfos = GuaKeUtils.getInfo(info);
                ImageView imageView = (ImageView) findViewById(R.id.iv_tishi);
                TextView textView = (TextView) findViewById(R.id.tv_tishi);
                imageView.setVisibility(View.GONE);
                textView.setVisibility(View.GONE);
                if (guaKeInfos.size() == 0){
                    imageView.setVisibility(View.VISIBLE);
                    textView.setVisibility(View.VISIBLE);
                }
                List<String> name_list = new ArrayList();
                for (GuaKeInfo info_guake:guaKeInfos){
                    String name_guake = info_guake.getClassName();
                    if (name_list.size() == 0){
                        name_list.add(name_guake);
                    }else {
                        System.out.println();
                        if (!name_list.contains(name_guake)) {
                            name_list.add(name_guake);
                            System.out.println(name_guake);
                        }
                    }
                }
                expandableListView.setAdapter(new GuaKeAdapter(GuaKeActivity.this,guaKeInfos,name_list));
                pv_progress.finish();
            }

            @Override
            public void onFailure(HttpException e, String s) {
                pv_progress.failed();
                Toast.makeText(GuaKeActivity.this, "当前网络较慢，请检查网络设置", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
