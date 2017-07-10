package cn.net.ntech.lntu.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cn.net.ntech.lntu.R;
import cn.net.ntech.lntu.bean.PingJiaoInfo;

/**
 * Created by Android-J on 2016/6/10.
 */
public class PingJiaoAdapter extends RecyclerView.Adapter<PingJiaoAdapter.ViewHolder> {
    private Context context;
    private List<PingJiaoInfo> pingJiaoInfoList;
    public PingJiaoAdapter(Context context,List<PingJiaoInfo> pingJiaoInfoList){
        this.context = context;
        this.pingJiaoInfoList = pingJiaoInfoList;
    }

    @Override
    public PingJiaoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pingjiaoinfo, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(PingJiaoAdapter.ViewHolder holder, int position) {
        PingJiaoInfo info = pingJiaoInfoList.get(position);
        holder.textView1.setText(info.getTecherName());
        holder.textView2.setText(info.getClassName());
        if (info.getState().equals("已评估")){
            holder.imageView.setImageResource(R.mipmap.ic_yue);
        }
    }

    @Override
    public int getItemCount() {
        return pingJiaoInfoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView1;
        public TextView textView2;
        public ImageView imageView;
        public ViewHolder(View itemView) {
            super(itemView);
            textView1 = (TextView) itemView.findViewById(R.id.tv_textView1);
            textView2 = (TextView) itemView.findViewById(R.id.tv_textView2);
            imageView = (ImageView) itemView.findViewById(R.id.iv_image);
        }
    }
}
