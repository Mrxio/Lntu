package cn.net.ntech.lntu.activity;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;
import com.github.clans.fab.FloatingActionButton;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.util.ArrayList;
import java.util.List;
import cn.net.ntech.lntu.R;
import cn.net.ntech.lntu.dialog.NormalDialog;
import cn.net.ntech.lntu.adapter.PingJiaoAdapter;
import cn.net.ntech.lntu.bean.PingJiaoInfo;
import cn.net.ntech.lntu.utils.MyHttpUtils;
import cn.net.ntech.lntu.view.ClickCallback;
import cn.net.ntech.lntu.view.ProgressView;

public class PingJiaoActivity extends BaseActivity{
    private String pingjiaoinfo;
    private String baseurl = "http://60.18.131.131:11080/newacademic/eva/index/";
    private String puturl = "http://60.18.131.131:11080/newacademic/eva/index/putresult.jsdo";
    private String pingjiao_url = "http://60.18.131.131:11080/newacademic/eva/index/resultlist.jsdo?groupId=&moduleId=506";
    private String refreshurl = "http://60.18.131.131:11080/newacademic/eva/index/evaindexinfo.jsdo?sub=1&result=50751848&id=47731709";
    private RecyclerView rv_list;
    private List<PingJiaoInfo> pingJiaoInfoList = new ArrayList<>();
    private List<PingJiaoInfo> unPingJiaoInfoList = new ArrayList<>();
    private HttpClient httpClient = new DefaultHttpClient();
    private static String HAOPING = "100.0#19187175#19187176";
    private static String ZHONGPING = "50.0#19187175#19187178";
    private static String CHAPING = "1.0#19187175#19187180";
    private String pingjia = HAOPING;
    private String cookie;
    private NormalDialog dialog;
    private PingJiaoAdapter adapter;
    private int index;
    private ProgressView pv_progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ping_jiao);
        initView();
        SharedPreferences sp = getSharedPreferences("cookie", MODE_PRIVATE);
        cookie = sp.getString("cookie", null);
        dialog = new NormalDialog(this,"正在为你评教");
        pv_progress.start();
        getPingJiao();
        pv_progress.setOnClickedCallBack(new ClickCallback() {
            @Override
            public void Clicked() {
                getPingJiao();
            }
        });
    }
    //初始化组件
    public void initView(){
        pv_progress = (ProgressView) findViewById(R.id.pv_ping_progress);
        rv_list = (RecyclerView) findViewById(R.id.rv_list);

        setTitle("评教");
        rv_list.setLayoutManager(new LinearLayoutManager(this));
        FloatingActionButton fab_haoping = (FloatingActionButton) findViewById(R.id.fab_haoping);
        FloatingActionButton fab_zhongping = (FloatingActionButton) findViewById(R.id.fab_zhongping);
        FloatingActionButton fab_chaping = (FloatingActionButton) findViewById(R.id.fab_chaping);
        fab_haoping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (unPingJiaoInfoList.size() > 0){
                    pingjia = HAOPING;
                    dialog.show();
                    index = 0;
                    startPingJiao();
                }else {
                    Toast.makeText(PingJiaoActivity.this, "你评价完了哦，赶紧去查成绩吧~", Toast.LENGTH_SHORT).show();
                }
            }
        });
        fab_zhongping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (unPingJiaoInfoList.size() > 0){
                    pingjia = ZHONGPING;
                    dialog.show();
                    index = 0;
                    startPingJiao();
                }else {
                    Toast.makeText(PingJiaoActivity.this, "你评价完了哦，赶紧去查成绩吧~", Toast.LENGTH_SHORT).show();
                }
            }
        });
        fab_chaping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (unPingJiaoInfoList.size() > 0){
                    pingjia = CHAPING;
                    dialog.show();
                    index = 0;
                    startPingJiao();
                }else {
                    Toast.makeText(PingJiaoActivity.this, "你评价完了哦，赶紧去查成绩吧~", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    /**
     * 解析评教列表
     *
     * @param html
     * @param type 0为所有，1为未评教列表
     * @return
     */
    public List<PingJiaoInfo> getPingJiaoList(String html,int type){
        List<PingJiaoInfo> list = new ArrayList<>();
        list.clear();
        Document document = Jsoup.parse(html);
        Elements testinfo = document.select("tr.infolist_common");
        for (Element info : testinfo) {
            String teacher = info.select("td").get(0).text();
            String classinfo = info.select("td").get(1).text();
            String state = info.select("span").get(0).text();
            String url = "";
            if (type == 0){
                if (state.equals("未评估")) {
                    url = baseurl + info.select("a").get(0).attr("href");
                }
                list.add(new PingJiaoInfo(classinfo,state, teacher, url));
            }else if (type == 1){
                if (state.equals("未评估")) {
                    url = baseurl + info.select("a").get(0).attr("href");
                    list.add(new PingJiaoInfo(classinfo,state, teacher, url));
                }
            }
        }
        return list;
    }
    public void startPingJiao(){
        int size = unPingJiaoInfoList.size();
        if (size > 0 && index < size){
            dialog.setTextView("正在为你评教"+(index+1)+"/"+size);
            final String url = unPingJiaoInfoList.get(index).getUrl();//获取某个科目评教操作页面的Url
            //进入评教操作页面
            MyHttpUtils.getEachPingJiaoInfo(url, cookie, new RequestCallBack<String>() {
                @Override
                public void onSuccess(ResponseInfo<String> responseInfo) {
                    //获取到的信息
                    String info1 = responseInfo.result;
                    Document document = Jsoup.parse(info1);
                    List<NameValuePair> params = new ArrayList<NameValuePair>();
                    Elements elements1 = document.select("input[type=hidden]");//获取所有隐藏提交项
                    for (Element element : elements1) {
                        String name = element.attr("name");
                        String value = element.attr("value");
                        params.add(new BasicNameValuePair(name, value));
                        System.out.println(name + "---" + value);
                    }
                    Elements elements2 = document.select("input[type=radio]");//获取评论提交按钮的名称
                    List<String> nameList = new ArrayList<String>();
                    for (Element element : elements2) {
                        String name = element.attr("name");
                        String value = element.attr("value");
                        if (nameList.isEmpty() || !nameList.contains(name)) {
                            nameList.add(name);
                            params.add(new BasicNameValuePair(name, pingjia));
                        }
                        System.out.println(name + "---" + value);
                    }
                    //提交评教
                    MyHttpUtils.putPingJiao(params, cookie,url, new RequestCallBack<String>() {
                        @Override
                        public void onSuccess(ResponseInfo<String> responseInfo) {
                            index = index + 1;
                            startPingJiao();
                        }

                        @Override
                        public void onFailure(HttpException e, String s) {
                            Toast.makeText(PingJiaoActivity.this, "出错鸟", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    });
                }

                @Override
                public void onFailure(HttpException e, String s) {
                    Toast.makeText(PingJiaoActivity.this, "出错鸟", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            });
            System.out.println("完事啦");
        }else {
            MyHttpUtils.getPingJiaoList(cookie, new RequestCallBack<String>() {
                @Override
                public void onSuccess(ResponseInfo<String> responseInfo) {
                    pingJiaoInfoList = getPingJiaoList(responseInfo.result,0);
                    unPingJiaoInfoList = getPingJiaoList(responseInfo.result,0);
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onFailure(HttpException e, String s) {

                }
            });
            dialog.dismiss();
        }
    }
    private void getPingJiao(){
        MyHttpUtils.getPingJiaoList(cookie, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                pingjiaoinfo = responseInfo.result;
                pingJiaoInfoList = getPingJiaoList(pingjiaoinfo, 0);
                unPingJiaoInfoList = getPingJiaoList(pingjiaoinfo,1);
                adapter = new PingJiaoAdapter(PingJiaoActivity.this, pingJiaoInfoList);
                rv_list.setAdapter(adapter);
                pv_progress.finish();
            }

            @Override
            public void onFailure(HttpException e, String s) {
                pv_progress.failed();
                Toast.makeText(PingJiaoActivity.this, "当前网络较慢，请检查网络设置", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
