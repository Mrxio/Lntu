package cn.net.ntech.lntu.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.ParseException;
import java.util.List;
import cn.net.ntech.lntu.R;
import cn.net.ntech.lntu.bean.TestInfo;
import cn.net.ntech.lntu.utils.DaoJiShiUtils;


public class TestInfoAdapter extends RecyclerView.Adapter<TestInfoAdapter.ViewHolder> {
    private List<TestInfo> testinfo;
    private Context context;

    public TestInfoAdapter(Context context,List<TestInfo> testinfo) {
        this.context = context;
        this.testinfo = testinfo;
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_name;
        public TextView tv_place;
        public TextView tv_time;
        private TextView tv_daojishi;
        private ImageView iv_daojishi;
        public ViewHolder(View view) {
            super(view);
            this.tv_name = (TextView) view.findViewById(R.id.tv_list_testname);
            this.tv_place = (TextView) view.findViewById(R.id.tv_list_testplace);
            this.tv_time = (TextView)view.findViewById(R.id.tv_list_testtime);
            this.tv_daojishi = (TextView) view.findViewById(R.id.tv_daojishi);
            this.iv_daojishi = (ImageView) view.findViewById(R.id.iv_daojishi);
        }
    }

    @Override
    public TestInfoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_test_layout, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TestInfo info = testinfo.get(position);
        holder.tv_name.setText(info.getTestSubject());
        holder.tv_time.setText("时间:"+info.getTestTime());
        holder.tv_place.setText("地点:"+info.getTestPlace());
        //计算倒计时
        int daojishi = 0;
        try {
            daojishi = DaoJiShiUtils.getDaoJiShi(info.getTestTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.iv_daojishi.setVisibility(View.GONE);
        holder.tv_daojishi.setVisibility(View.GONE);
        if (daojishi<0){
            holder.iv_daojishi.setVisibility(View.VISIBLE);
        }else{
            if (daojishi ==0){
                holder.tv_daojishi.setText("今天");
                holder.tv_daojishi.setTextColor(Color.argb(255, 255,0, 0));
            }else if (daojishi<=7&&daojishi>0){
                holder.tv_daojishi.setText(daojishi+"天");
                holder.tv_daojishi.setTextColor(Color.argb(255,255, 0,0));
            }else {
                holder.tv_daojishi.setText(daojishi+"天");
                holder.tv_daojishi.setTextColor(Color.argb(255,0,255,0));
            }
            holder.tv_daojishi.setVisibility(View.VISIBLE);
        }
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
        holder.tv_name.setBackgroundResource(color);
    }

    @Override
    public int getItemCount() {
        return testinfo.size();
    }
}