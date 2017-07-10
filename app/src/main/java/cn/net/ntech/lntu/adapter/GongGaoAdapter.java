package cn.net.ntech.lntu.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.List;

import cn.net.ntech.lntu.R;
import cn.net.ntech.lntu.activity.GongGaoInfoActivity;
import cn.net.ntech.lntu.bean.GongGaoInfo;

public class GongGaoAdapter extends RecyclerView.Adapter<GongGaoAdapter.ViewHolder> {
    private Context context;
    private List<GongGaoInfo> info;

    public GongGaoAdapter(Context context,List<GongGaoInfo> info) {
        this.context = context;
        this.info = info;
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_title;
        public TextView tv_time;
        public FrameLayout cardView;
        public ViewHolder(View view) {
            super(view);
            this.tv_title = (TextView) view.findViewById(R.id.tv_gonggaoinfo_title);
            this.tv_time = (TextView) view.findViewById(R.id.tv_gonggaoinfo_time);
            this.cardView = (FrameLayout)view.findViewById(R.id.card_gonggao);
        }
    }

    @Override
    public GongGaoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gonggao, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final GongGaoInfo gongGaoInfo = info.get(position);
        holder.tv_title.setText(gongGaoInfo.getTitle());
        holder.tv_time.setText(gongGaoInfo.getTime());
        int drawable = R.drawable.gonggao;
        switch (position%5){
            case 0:
                drawable = R.drawable.gonggaoinfo1;
                break;
            case 1:
                drawable = R.drawable.gonggaoinfo2;
                break;
            case 2:
                drawable = R.drawable.gonggaoinfo3;
                break;
            case 3:
                drawable = R.drawable.gonggaoinfo4;
                break;
            case 4:
                drawable = R.drawable.gonggao;
                break;
        }
        holder.cardView.setBackgroundResource(drawable);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent();
                String url = gongGaoInfo.getUrl();
                String title = gongGaoInfo.getTitle();
                String time = gongGaoInfo.getTime();
                intent1.putExtra("url",url);
                intent1.putExtra("title",title);
                intent1.putExtra("time",time);
                intent1.setClass(context, GongGaoInfoActivity.class);
                context.startActivity(intent1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return info.size();
    }
}