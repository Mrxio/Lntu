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

import com.readystatesoftware.viewbadger.BadgeView;

import java.util.ArrayList;
import java.util.List;

import cn.net.ntech.lntu.R;
import cn.net.ntech.lntu.activity.ClassInfoActivity;
import cn.net.ntech.lntu.dao.ClassInfoOpenHelper;
import cn.net.ntech.lntu.dialog.ClassInfoDialog;
import cn.net.ntech.lntu.utils.StringUtils;

/**
 * Created by NTECHER on 2016/6/11.
 */
public class ClassAdapter extends RecyclerView.Adapter<ClassAdapter.ViewHolder>{
    private Context context;
    private List<String> list;
    private ClassInfoOpenHelper openHelper;
    private SQLiteDatabase db;
    private int weeknameConfig;
    public ClassAdapter(Context context,List<String> list,int weeknameConfig){
        this.context = context;
        this.list = list;
        this.openHelper = new ClassInfoOpenHelper(context, 1);
        this.db = openHelper.getWritableDatabase();
        this.weeknameConfig = weeknameConfig;
    }
    @Override
    public ClassAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_class, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ClassAdapter.ViewHolder holder, int position) {
        int i = position%7+1;
        int j = position/7+1;
        String textInfo = null;
        String weekday = "";
        String classtime = "";
        int xinqi = 0;
        weekday = StringUtils.toWeekDay(i,1);
        xinqi = StringUtils.toResourcePath(i);
        classtime = StringUtils.toClassName(j);
        //读取数据库中的课程信息
        Cursor cs = db.query("classinfo", new String[]{"classname", "classteacher", "classweeknum", "classweek", "classtime", "classplace"}, "classweek = ? and classtime = ?", new String[]{weekday, classtime}, null, null, null);
        String name = null;
        String teacher = null;
        String weeknum = null;
        String place = null;
        List timelist = new ArrayList();
        List classlist = new ArrayList();
        while(cs.moveToNext()) {
            name = cs.getString(cs.getColumnIndex("classname"));
            teacher = cs.getString(cs.getColumnIndex("classteacher"));
            weeknum = cs.getString(cs.getColumnIndex("classweeknum"));
            place = cs.getString(cs.getColumnIndex("classplace"));
            timelist.add(weeknum);
            classlist.add(name);
        }
        textInfo = name+"@"+place+"·"+teacher;
        //如果当前时间段有课
        if(name!=null) {
            int color = 0;
            int reasoure = 0;
            int reasoure1 ;
            //匹配课程，如果课程少于10门，每种课程一种颜色，超过10门的部分随机取色
            for (int x = 0; x < list.size(); x++) {
                if (name.equals(list.get(x))) {
                    if (x < 10) {
                        color = x;
                    } else {
                        color = (int) (Math.random() * 10);
                    }
                }
            }
            switch (color) {
                case 0:
                    reasoure = R.drawable.button_gz_background1;
                    break;
                case 1:
                    reasoure = R.drawable.button_gz_background1;
                    break;
                case 2:
                    reasoure = R.drawable.button_gz_background2;
                    break;
                case 3:
                    reasoure = R.drawable.button_gz_background3;
                    break;
                case 4:
                    reasoure = R.drawable.button_gz_background4;
                    break;
                case 5:
                    reasoure = R.drawable.button_gz_background5;
                    break;
                case 6:
                    reasoure = R.drawable.button_gz_background6;
                    break;
                case 7:
                    reasoure = R.drawable.button_gz_background7;
                    break;
                case 8:
                    reasoure = R.drawable.button_gz_background8;
                    break;
                case 9:
                    reasoure = R.drawable.button_gz_background9;
                    break;
            }
            reasoure1 = reasoure;
            String mixweekNum;
            String maxweekNum;
            String[] weekNum;
            int danshuang = 0;
            //判断当前课程是否仅一周
            if (!weeknum.contains("-")) {
                mixweekNum = weeknum;
                maxweekNum = weeknum;
            } else {
                weekNum = weeknum.split("-");
                //判断单双周
                mixweekNum = weekNum[0];
                maxweekNum = weekNum[1];
                if (weeknum.contains("单")) {
                    maxweekNum = weekNum[1].replace("单", "");
                    danshuang = 1;
                } else if (weeknum.contains("双")) {
                    maxweekNum = weekNum[1].replace("双", "");
                    danshuang = 2;
                }
            }
            int x = weeknameConfig % 2;
            int mixWeekNum = Integer.parseInt(mixweekNum.trim(), 10);
            int maxWeekNum = Integer.parseInt(maxweekNum.trim(), 10);

            //判断当前课程是否为本周
            if (maxWeekNum < weeknameConfig || mixWeekNum > weeknameConfig) {
                textInfo = "[非本周]" + name + "@" + place + "·" + teacher;
                reasoure1 = R.drawable.button_gz_background;
            }
            if ((danshuang == 1 && x == 0) || (danshuang == 2 && x != 0)) {
                textInfo = "[非本周]" + name + "@" + place + "·" + teacher;
                reasoure1 = R.drawable.button_gz_background;
            }

            //判断是否有多节课
            if (timelist.size() > 1) {
                //判断时间是否都为本周
                for (Object tlist : timelist) {
                    String weeknumlist = tlist.toString();
                    String mixweekNum1;
                    String maxweekNum1;
                    String[] weekNum1;
                    int danshuang1 = 0;
                    //判断当前课程是否仅一周
                    if (!weeknumlist.contains("-")) {
                        mixweekNum1 = weeknumlist;
                        maxweekNum1 = weeknumlist;
                    } else {
                        weekNum1 = weeknumlist.split("-");
                        //判断单双周
                        mixweekNum1 = weekNum1[0];
                        maxweekNum1 = weekNum1[1];
                        if (weeknumlist.contains("单")) {
                            maxweekNum1 = weekNum1[1].replace("单", "");
                            danshuang1 = 1;
                        } else if (weeknumlist.contains("双")) {
                            maxweekNum1 = weekNum1[1].replace("双", "");
                            danshuang = 2;
                        }
                    }
                    int x1 = weeknameConfig % 2;
                    int mixWeekNum1 = Integer.parseInt(mixweekNum1.trim(), 10);
                    int maxWeekNum1 = Integer.parseInt(maxweekNum1.trim(), 10);
                    //判断当前课程是否为本周
                    if (maxWeekNum1 < weeknameConfig || mixWeekNum1 > weeknameConfig) {

                    } else if ((danshuang1 == 1 && x1 == 0) || (danshuang1 == 2 && x1 != 0)) {
                    } else {
                        //此课为当前周，显示此课
                        Cursor cs2 = db.query("classinfo", new String[]{"classname", "classteacher", "classweeknum", "classweek", "classtime", "classplace"}, "classweek = ? and classtime = ? and classweeknum = ?", new String[]{weekday, classtime, weeknumlist}, null, null, null);
                        while (cs2.moveToNext()) {
                            name = cs2.getString(cs2.getColumnIndex("classname"));
                            teacher = cs2.getString(cs2.getColumnIndex("classteacher"));
                            place = cs2.getString(cs2.getColumnIndex("classplace"));
                        }
                        textInfo = name + "@" + place + "·" + teacher;
                        reasoure1 = reasoure;

                    }
                }
            }
            holder.textView.setText(textInfo);
            holder.textView.setBackgroundResource(reasoure1);
            final String finalName = name;
            final List listinfo = classlist;
            final String weekday1 = weekday;
            final int numofweek = weeknameConfig;
            if (timelist.size()>1){
                holder.textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //
                        ClassInfoDialog dialog = new ClassInfoDialog(context,R.style.ClassInfoDialog,listinfo,weekday1,numofweek);
                        dialog.show();
                    }
                });
            } else {
                holder.textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.setClass(context, ClassInfoActivity.class);
                        intent.putExtra("class", finalName);
                        context.startActivity(intent);
                    }
                });
            }
            if (timelist.size()>1){
                int mun = timelist.size();
                BadgeView badgeView = new BadgeView(context, holder.textView);
                badgeView.setWidth(10);
                badgeView.setHeight(10);
                badgeView.setBackgroundResource(R.mipmap.geziyingyin);
                badgeView.setBadgeMargin(0);
                badgeView.show();
            }
        }
    }

    @Override
    public int getItemCount() {
        return 35;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.tv_textView);
        }
    }
}
