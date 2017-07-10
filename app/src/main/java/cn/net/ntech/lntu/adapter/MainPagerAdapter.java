package cn.net.ntech.lntu.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.util.List;

import cn.net.ntech.lntu.R;

/**
 * Created by Administrator on 2016/11/3.
 */

public class MainPagerAdapter extends PagerAdapter {
    private List<View> mViewList;

    public MainPagerAdapter(List<View> mViewList) {
        this.mViewList = mViewList;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mViewList.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(mViewList.get(position));
        return (mViewList.get(position));
    }

    @Override
    public int getCount() {
        if (mViewList == null){
            return 0;
        }
        return mViewList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
