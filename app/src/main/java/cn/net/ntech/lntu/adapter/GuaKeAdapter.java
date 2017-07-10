package cn.net.ntech.lntu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.net.ntech.lntu.R;
import cn.net.ntech.lntu.bean.GuaKeInfo;

/**
 * Created by NTECHER on 2016/3/16.
 */
public class GuaKeAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<GuaKeInfo> info;
    private List<String> namelist;
    private LayoutInflater mInflater;

    public GuaKeAdapter(Context context,List<GuaKeInfo> info,List<String> namelist) {
        this.context = context;
        this.info = info;
        this.namelist = namelist;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getGroupCount() {
        return namelist.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        String name = namelist.get(groupPosition);
        int i = 0;
        for (GuaKeInfo in:info){
            if(name.equals(in.getClassName())){
                i = i + 1;
            }
        }
        return i;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return null;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        View view = mInflater.inflate(R.layout.item_guake_name,null);
        TextView name = (TextView) view.findViewById(R.id.tv_item_guake_name);
        name.setText(namelist.get(groupPosition));
        return view;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null){
            convertView = mInflater.inflate(R.layout.item_guake,null);
            holder = new ViewHolder();
            holder.classNum = (TextView)convertView.findViewById(R.id.tv_guake_classnum);
            holder.className = (TextView)convertView.findViewById(R.id.tv_guake_classname);
            holder.classGrade = (TextView)convertView.findViewById(R.id.tv_guake_classgrade);
            holder.classGPA = (TextView)convertView.findViewById(R.id.tv_guake_classgpa);
            holder.classTime = (TextView)convertView.findViewById(R.id.tv_guake_classtime);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }
        String name = namelist.get(groupPosition);
        List<GuaKeInfo> guake_info = new ArrayList<GuaKeInfo>();
        for (GuaKeInfo in:info){
            if(name.equals(in.getClassName())){
                   guake_info.add(in);
            }
        }
        GuaKeInfo guaKeInfo = guake_info.get(childPosition);
        holder.classNum.setText("考试类型："+guaKeInfo.getClassNum());
        holder.className.setText(guaKeInfo.getClassName()+"（"+guaKeInfo.getClassType()+")");
        holder.classGrade.setText(guaKeInfo.getClassGrade());
        holder.classGPA.setText("课程学分："+guaKeInfo.getClassGPA());
        holder.classTime.setText("课程时间："+guaKeInfo.getClassTime());
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
    public static class ViewHolder{
        public TextView classNum;
        public TextView className;
        public TextView classGrade;
        public TextView classGPA;
        public TextView classTime;
    }
}