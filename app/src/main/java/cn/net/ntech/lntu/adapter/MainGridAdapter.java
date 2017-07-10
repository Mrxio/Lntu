package cn.net.ntech.lntu.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import cn.net.ntech.lntu.R;

/**
 * Created by NTECHER on 2016/3/16.
 */
public class MainGridAdapter extends BaseAdapter {
    private String[] textitem = new String[]{"我的课表","考试安排","成绩查询","挂科查询","一键评教","教务公告","CET4/6"};
    private int[] imgpath = new int[]{R.mipmap.ic_kebiaochaxun,R.mipmap.ic_kaoshianpai,R.mipmap.ic_chengjichaxun,R.mipmap.ic_guakechaxun,R.mipmap.ic_yijianpingjiao,R.mipmap.ic_jiaowugonggao,R.mipmap.ic_jiaowugonggao};
    private Context context;
    public MainGridAdapter(Context context){
        this.context = context;
    }
    @Override
    public int getCount() {
        return textitem.length;
    }

    @Override
    public Object getItem(int position) {
        return textitem[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = View.inflate(context, R.layout.item_main_griditem,null);
        ImageView imageView = (ImageView) view.findViewById(R.id.iv_item);
        TextView textView = (TextView) view.findViewById(R.id.tv_item);
        textView.setText(textitem[position]);
        imageView.setImageResource(imgpath[position]);
        return view;
    }
}
