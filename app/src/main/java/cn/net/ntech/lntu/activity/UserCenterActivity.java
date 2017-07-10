package cn.net.ntech.lntu.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import java.io.File;
import cn.net.ntech.lntu.R;
import cn.net.ntech.lntu.dialog.LogoutDialog;
import cn.net.ntech.lntu.view.CircleImageView;

public class UserCenterActivity extends BaseActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_center);
        initView();
    }

    private void initView() {
        setTitle("个人中心");
        TextView tv_name = (TextView) findViewById(R.id.tv_user_text1);
        findViewById(R.id.bt_user_logout).setOnClickListener(this);
        findViewById(R.id.tv_user_text2).setOnClickListener(this);
        findViewById(R.id.tv_user_text3).setOnClickListener(this);
        findViewById(R.id.tv_user_text4).setOnClickListener(this);
        findViewById(R.id.tv_user_text5).setOnClickListener(this);
        CircleImageView imageView = (CircleImageView)findViewById(R.id.iv_user_head);
        SharedPreferences sp3 = getSharedPreferences("info", MODE_PRIVATE);
        String name_user = sp3.getString("name", "辽工大教务通");
        tv_name.setText(name_user);
//        String name_class = sp3.getString("clas", "www.ntech.net.cn");
//        String sex = sp3.getString("sex","女");
//        if (sex.equals("女")){
//            iv_sex.setImageResource(R.mipmap.girl);
//        }
//        tv_name.setText(name_user);
//        tv_class.setText(name_class);
        File file = new File(getFilesDir()+"/img.jpg");
        if (file.exists()){
            Bitmap srcBm = BitmapFactory.decodeFile(getFilesDir()+"/img.jpg");
            imageView.setImageBitmap(srcBm);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_user_logout:
                LogoutDialog dialog = new LogoutDialog(this);
                dialog.show();
                break;
            case R.id.tv_user_text2:
                //分享
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "我正在使用辽工大教务通软件，查课表、成绩、公告，一键评教特别方便，推荐你也用一下，下载地址http://www.ntech.net.cn/app/lntu.apk");
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
                break;
            case R.id.tv_user_text3:
                //评价
                Uri uri = Uri.parse("market://details?id=" + getPackageName());
                Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
            case R.id.tv_user_text4:
                // 反馈
                startActivity(new Intent(this,FeedBackActivity.class));
                break;
            case R.id.tv_user_text5:
                //关于
                startActivity(new Intent(this,AboutActivity.class));
                break;
        }
    }
}
