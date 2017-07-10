package cn.net.ntech.lntu.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import cn.net.ntech.lntu.R;
import cn.net.ntech.lntu.activity.ClassInfoActivity;
import cn.net.ntech.lntu.dao.ClassInfoOpenHelper;

/**
 * Created by NTECHER on 2016/3/23.
 */
public class ClassInfoAdapter extends RecyclerView.Adapter<ClassInfoAdapter.ViewHolder> {
    private Context context;
    private List list;
    private String weekday;
    private int weeknum1;
    public ClassInfoAdapter(Context context, List list, String weekday, int weeknum1) {
        this.context = context;
        this.list = list;
        this.weekday = weekday;
        this.weeknum1 = weeknum1;
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;
        public ViewHolder(View view) {
            super(view);
            this.mTextView = (TextView) view.findViewById(R.id.tv_item_recycler);
        }
    }

    @Override
    public ClassInfoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final String classname = list.get(position).toString();
        ClassInfoOpenHelper openHelper = new ClassInfoOpenHelper(context,  1);
        SQLiteDatabase db = openHelper.getWritableDatabase();
        //读取数据库中的课程信息

        Cursor cs = db.query("classinfo", new String[]{"classname", "classteacher", "classweeknum", "classweek", "classtime", "classplace"}, "classname = ? and classweek = ? ", new String[]{classname,weekday}, null, null, null);
        String teacher = null;
        String weeknum = null;
        String place = null;
        while(cs.moveToNext()) {
            teacher = cs.getString(cs.getColumnIndex("classteacher"));
            weeknum = cs.getString(cs.getColumnIndex("classweeknum"));
            place = cs.getString(cs.getColumnIndex("classplace"));
        }
        //判断是否为当前周
        String mixweekNum;
        String maxweekNum;
        String[] weekNum;
        int danshuang = 0;
        //判断当前课程是否仅一周
        if (!weeknum.contains("-")) {
            mixweekNum = weeknum;
            maxweekNum = weeknum;
        }else {
            weekNum = weeknum.split("-");
            //判断单双周
            mixweekNum = weekNum[0];
            maxweekNum = weekNum[1];
            if(weeknum.contains("单")){
                maxweekNum =weekNum[1].replace("单","");
                danshuang=1;
            }else if (weeknum.contains("双")){
                maxweekNum = weekNum[1].replace("双","");
                danshuang=2;
            }
        }
        String classinfo = classname+"@"+place+"·"+teacher;
        int x = weeknum1%2;
        int mixWeekNum = Integer.parseInt(mixweekNum.trim(), 10);
        int maxWeekNum = Integer.parseInt(maxweekNum.trim(), 10);
        int reasoure1 = R.drawable.button_gz_background1;
        //判断当前课程是否为本周
        if (maxWeekNum<weeknum1||mixWeekNum>weeknum1){
            classinfo = "[非本周]"+classname+"@"+place+"·"+teacher;
            reasoure1 = R.drawable.button_gz_background;
        }
        if ((danshuang==1&&x==0)||(danshuang==2&&x!=0)){
            classinfo = "[非本周]"+classname+"@"+place+"·"+teacher;
            reasoure1 = R.drawable.button_gz_background;
        }

        holder.mTextView.setBackgroundResource(reasoure1);
        holder.mTextView.setText(classinfo);
        holder.mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(context, ClassInfoActivity.class);
                intent.putExtra("class", classname);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
