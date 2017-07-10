package cn.net.ntech.lntu.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import java.util.ArrayList;
import java.util.List;
import cn.net.ntech.lntu.R;
import cn.net.ntech.lntu.adapter.MainMenuAdapter;
import cn.net.ntech.lntu.adapter.MainPagerAdapter;
import cn.net.ntech.lntu.bean.GridViewItemInfo;
import cn.net.ntech.lntu.dialog.NormalDialog;
import cn.net.ntech.lntu.bean.ExitApplication;
import cn.net.ntech.lntu.dialog.ExitDialog;
import cn.net.ntech.lntu.dialog.WeekSelectDialog;
import cn.net.ntech.lntu.utils.MyHttpUtils;

public class MainActivity extends AppCompatActivity{

    private String cookie = null;
    private NormalDialog normalDialog;
    private ViewPager viewPager;
    private int pageIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //任务栈
        ExitApplication.addActivity(this);
        //初始化组件
        initView();
        //找到控件对象

//        MyHttpUtils.getWether("fuxin", new RequestCallBack<String>() {
//            @Override
//            public void onSuccess(ResponseInfo<String> responseInfo) {
//                WetherInfo info = WetherUtils.getWether(responseInfo.result);
//                if (info!=null){
//                    tv_tF.setText(info.getMinT()+"℃ ~ "+info.getMaxT()+"℃");
//                    tv_wetherF.setText(info.getWether());
//                }
//            }
//
//            @Override
//            public void onFailure(HttpException e, String s) {
//
//            }
//        });

        //获取cookie
        SharedPreferences sp = getSharedPreferences("cookie", MODE_PRIVATE);
        //从SharedPreference里取数据
        cookie = sp.getString("cookie", "");
        //判断是否设置了周，如果没设置，弹出对话框提醒设置
        SharedPreferences sp1 = getSharedPreferences("setDate", MODE_PRIVATE);
        String setDate = sp1.getString("setDate", null);
        if (setDate == null){
            WeekSelectDialog dialog = new WeekSelectDialog(MainActivity.this);
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
        }
        //加载dialog
        normalDialog = new NormalDialog(MainActivity.this,"正在加载请稍等哒~");
        normalDialog.setCanceledOnTouchOutside(false);
    }
    //初始化组件
    private void initView() {
        int screenWidth  = getResources().getDisplayMetrics().widthPixels;
        viewPager = (ViewPager) findViewById(R.id.vp_main_pager);
        LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) viewPager.getLayoutParams();
        linearParams.width = screenWidth;
        linearParams.height = screenWidth/3*2;
        viewPager.setLayoutParams(linearParams);//动态设置viewpager的大小
        final int pageCount = 2;
        List<View> list = new ArrayList<>();
        String[] arr = {"我的课表","成绩查询","考试安排","一键评教","教务公告","CET4/6","挂科查询","个人中心"};
        int[] imgRes = {R.mipmap.ic_kebiaochaxun,R.mipmap.ic_chengjichaxun,R.mipmap.ic_kaoshianpai,R.mipmap.ic_yijianpingjiao,R.mipmap.ic_jiaowugonggao,R.mipmap.lntu,R.mipmap.ic_guakechaxun,R.mipmap.lntu};
        List<GridViewItemInfo> dates = new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {
            GridViewItemInfo info = new GridViewItemInfo(arr[i],imgRes[i]);
            dates.add(info);
        }
        for(int index = 0 ; index < pageCount ; index++){
            View view = (View) LayoutInflater.from(this).inflate(R.layout.item_main_viewpager,viewPager,false);
            GridView grid = (GridView) view.findViewById(R.id.gv_main);
            grid.setAdapter(new MainMenuAdapter(this,dates,index));
            list.add(grid);
            grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    gridClickEvent(pageIndex,position);
                }
            });
        }
        viewPager.setAdapter(new MainPagerAdapter(list));
        final RadioGroup radioGroup = (RadioGroup) findViewById(R.id.rg_main_dot);
        radioGroup.check(R.id.rb_main_1);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                pageIndex = position;
                if (position == 0){
                    radioGroup.check(R.id.rb_main_1);
                }else {
                    radioGroup.check(R.id.rb_main_2);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    //GridView点击事件处理
    private void gridClickEvent(int mIndex, int position) {
        Intent intent = new Intent();
        if (mIndex == 0){
            switch (position){
                case 0:
                    intent.setClass(MainActivity.this, ClassActivity.class);
                    break;
                case 1:
                    intent.setClass(MainActivity.this, GradeActivity.class);
                    break;
                case 2:
                    intent.setClass(MainActivity.this, TestActivity.class);
                    break;
                case 3:
                    intent.setClass(MainActivity.this, PingJiaoActivity.class);
                    break;
                case 4:
                    intent.setClass(MainActivity.this, GongGaoActivity.class);
                    break;
                case 5:
                    intent.setClass(MainActivity.this,BrowserActivity.class);
                    intent.putExtra("url","http://www.yunchafen.com.cn/score/alipay/cet-login");
                    break;
            }
        }else if (mIndex == 1){
            switch (position){
                case 0:
                    intent.setClass(MainActivity.this, GuaKeActivity.class);
                    break;
                case 1:
                    intent.setClass(this,UserCenterActivity.class);
                    break;
            }
        }
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
            //退出程序，弹出对话框
            ExitDialog dialog = new ExitDialog(MainActivity.this);
            dialog.show();
    }
}
