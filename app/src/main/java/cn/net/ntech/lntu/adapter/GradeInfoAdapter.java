package cn.net.ntech.lntu.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.UnsupportedEncodingException;
import java.util.List;
import cn.net.ntech.lntu.R;
import cn.net.ntech.lntu.activity.BrowserActivity;
import cn.net.ntech.lntu.bean.GradeInfo;
import cn.net.ntech.lntu.dialog.InfoDialog;
import cn.net.ntech.lntu.dialog.NormalDialog;
import cn.net.ntech.lntu.utils.MyHttpUtils;


public class GradeInfoAdapter extends RecyclerView.Adapter<GradeInfoAdapter.ViewHolder> {
    private List<GradeInfo> gradeinfos;
    private Context context;
    private String cookie;
    private NormalDialog dialog;
    public GradeInfoAdapter(Context context,List<GradeInfo> gradeinfos) {
        this.context = context;
        this.gradeinfos = gradeinfos;
        SharedPreferences sp = context.getSharedPreferences("cookie",context.MODE_PRIVATE);
        this.cookie = sp.getString("cookie","");
        this.dialog = new NormalDialog(context,"正在为你计算…");
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView classname;
        public TextView classnum;
        public TextView classcredit;
        public TextView terminfo;
        public TextView classgrade;
        public TextView testtype;
        public TextView pingshi;

        public ViewHolder(View view) {
            super(view);
            this.classname = (TextView)view.findViewById(R.id.tv_list_classname);
            this.classnum = (TextView) view.findViewById(R.id.tv_list_classnum);
            this.classcredit = (TextView) view.findViewById(R.id.tv_list_classcredit);
            this.terminfo = (TextView) view.findViewById(R.id.tv_list_terminfo);
            this.classgrade = (TextView) view.findViewById(R.id.tv_list_classgrade);
            this.testtype = (TextView) view.findViewById(R.id.tv_list_testtype);
            this.pingshi = (TextView) view.findViewById(R.id.tv_list_pingshi);
        }
    }

    @Override
    public GradeInfoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.gradelistlayout, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final GradeInfo info = gradeinfos.get(position);
        holder.classname.setText(info.getKechengmingcheng() + "(" + info.getLeixing() + ")");
        holder.classnum.setText("编号：" + info.getKechengbianhao());
        holder.classcredit.setText("学分：" + info.getXuefen());
        holder.terminfo.setText("学年：" + info.getShijian());
        holder.classgrade.setText(info.getFenshu());
        holder.testtype.setText("类型：" + info.getKaoshileixing());
        //设置颜色
        int color = R.color.color1;
        switch (position%5){
            case 0:
                color = R.color.color1;
                break;
            case 1:
                color = R.color.color2;
                break;
            case 2:
                color = R.color.color3;
                break;
            case 3:
                color = R.color.color4;
                break;
            case 4:
                color = R.color.color5;
                break;
        }
        holder.classname.setBackgroundResource(color);
        final String url = info.getUrl();
        if (!TextUtils.isEmpty(url)){
            holder.pingshi.setText("详情>>>");
            holder.pingshi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.show();
                    MyHttpUtils.normalGetWithCookie(info.getUrl(), cookie, new RequestCallBack<String>() {
                        @Override
                        public void onSuccess(final ResponseInfo<String> responseInfo) {
                            dialog.dismiss();
                            Document document = Jsoup.parse(responseInfo.result);
                            Elements trs = document.select("tr");
                            String jisuan = trs.get(4).text();
                            Elements info = trs.get(6).select("td");
                            String pingshi = info.get(0).text();
                            String qizhong = info.get(1).text();
                            String qimo = info.get(2).text();
                            String zong = info.get(3).text();
                            if (jisuan.contains("成绩信息")){
                                jisuan = jisuan.replace("成绩信息：","");
                            }
                            InfoDialog infoDialog = new InfoDialog(context,jisuan,pingshi,qizhong,qimo,zong);
                            infoDialog.show();
                            infoDialog.tv_xiangqing.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(context, BrowserActivity.class);
                                    intent.putExtra("html",responseInfo.result);
                                    context.startActivity(intent);
                                }
                            });
                        }

                        @Override
                        public void onFailure(HttpException e, String s) {
                            dialog.dismiss();
                        }
                    });
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return gradeinfos.size();
    }
}