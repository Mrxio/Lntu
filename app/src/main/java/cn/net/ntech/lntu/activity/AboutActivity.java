package cn.net.ntech.lntu.activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import cn.net.ntech.lntu.R;

/**
 * Created by NTECHER on 2016/3/16.
 */
public class AboutActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        setTitle("关于");
        TextView textView = (TextView) findViewById(R.id.tv_about);
        TextView tv_banbenhao = (TextView)findViewById(R.id.tv_about_banbenhao);
        String banbenhao = getVersionName();
        tv_banbenhao.setText("当前版本:"+banbenhao);
        String about = "一、关于软件\n\n辽工大教务通意在为工大学子提供更加便利的教务查询服务，让工大学子不用繁于登录教务在线系统，手机上轻松点击就能获取各项功能。" +
                "\n\n课表查询："+"\n课表查询会在首次登录和刷新课表时耗费少量流量获取服务器数据，之后没有网也可以查询，课表查询提供课程的详细信息，方便快捷。" +
                "\n\n考试查询：" +
                "\n考试查询将考试数据按时间排列，考过的会自动排在首位并进行提醒，方便醒目。" +
                "\n\n成绩查询：" +
                "\n成绩查询可以清楚的查看到相关科目的具体信息，成绩会用不同颜色标注，清晰可见。" +
                "\n\n挂科查询：" +
                "\n挂科查询，将同一课程折叠起来，方便查询当前所挂科目的总数和具体情况。" +
                "\n\n一键评教：" +
                "\n再也不用登录教务在线系统一个一个科目的评价啦，手机上只要一键就可以全部搞定，默认好评，轻松快捷。" +
                "\n\n教务公告：" +
                "\n教务公告只显示教务系统最新的13条信息，并对内容进行整理，更加方便阅读，再也不会错过重要的公告了。" +
                "\n\n二、免责声明" +
                "\n①本软件非辽宁工程技术大学教务在线官方APP，但数据来自教务系统，您在教务在线系统所进行的任何操作，本人不承担任何责任；" +
                "\n②本软件非盈利目的，如果发现有侵犯版权的地方请及时与我们联系，我们将立即删除相关内容；" +
                "\n③本软件在获取更新和教务数据时会消耗一些流量，相关费用由使用者自行承担；" +
                "\n④本软件的相关教务信息来自教务在线官方系统，所有数据都来自辽宁工程技术大学的服务器，本人不会获取相关信息，对于用户信息可能出现的泄露，请及时登录教务在线系统修改密码，本人对此不负任何责任；" +
                "\n⑤登录即表示您同意以上条款。" +
                "\n\n三、联系我们";
        textView.setText(about);
        TextView mail = (TextView) findViewById(R.id.tv_email);
        TextView net = (TextView) findViewById(R.id.tv_net);
        mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data=new Intent(Intent.ACTION_SENDTO);
                data.setData(Uri.parse("mailto:mail@ntech.net.cn"));
                startActivity(data);
            }
        });
        net.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse("http://www.ntech.net.cn");
                intent.setData(content_url);
                startActivity(intent);
            }
        });
    }

    /*获取当前版本名*/
    private String getVersionName(){
        PackageManager packageManager = getPackageManager();
        String mVersionName = "";
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(), 0);
            mVersionName = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            //找到、不到包名时会报错
            e.printStackTrace();
        }
        return mVersionName;
    }
}
