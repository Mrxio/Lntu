package cn.net.ntech.lntu.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.List;
import cn.net.ntech.lntu.R;
import cn.net.ntech.lntu.activity.ClassActivity;
import cn.net.ntech.lntu.bean.GridViewItemInfo;

/**
 * Created by Administrator on 2016/11/7.
 */

public class MainMenuAdapter extends BaseAdapter {
    private List<GridViewItemInfo> mDatas;
    private LayoutInflater mLayoutInflater;
    private Context context;
    private int mIndex;
    private int mPageSize;//每页可以显示的最多条目
    public MainMenuAdapter(Context context, List<GridViewItemInfo> mDatas , int mIndex ) {
        this.context = context;
        this.mDatas = mDatas;
        mLayoutInflater = LayoutInflater.from(context);
        this.mIndex = mIndex;
        mPageSize = 6;
    }

    @Override
    public int getCount() {
        return mDatas.size() > (mIndex + 1) * mPageSize ? mPageSize : (mDatas.size() - mIndex * mPageSize);
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position + mIndex * mPageSize);
    }

    @Override
    public long getItemId(int position) {
        return position + mIndex * mPageSize;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder vh = null;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.item_main_griditem, parent, false);
            vh = new ViewHolder();
            vh.tv = (TextView) convertView.findViewById(R.id.tv_item);
            vh.iv = (ImageView) convertView.findViewById(R.id.iv_item);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        int pos = position + mIndex * mPageSize;//在给View绑定显示的数据时，计算正确的position = position + mIndex * mPageSize
        vh.tv.setText(mDatas.get(pos).getText());
        vh.iv.setImageResource(mDatas.get(pos).getImgRes());

        int columnWidth = parent.getWidth()/3;
        convertView.setLayoutParams(new GridView.LayoutParams(columnWidth-2,columnWidth));//设置item的长和宽
        return convertView;
    }

    class ViewHolder {
        public TextView tv;
        public ImageView iv;
    }
}
