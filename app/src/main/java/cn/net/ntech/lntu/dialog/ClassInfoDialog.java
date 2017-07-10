package cn.net.ntech.lntu.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Window;
import java.util.List;
import cn.net.ntech.lntu.R;
import cn.net.ntech.lntu.adapter.ClassInfoAdapter;

/**
 * Created by NTECHER on 2016/3/24.
 */
public class ClassInfoDialog extends Dialog {
    private Context context;
    private List list;
    private RecyclerView.LayoutManager mLayoutManager;
    private String weekday;
    private int weeknum;

    public ClassInfoDialog(Context context, int themeResId,List list,String weekday,int weeknum) {
        super(context, themeResId);
        this.list = list;
        this.weekday = weekday;
        this.weeknum = weeknum;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_class_info);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_classinfo);
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(new ClassInfoAdapter(getContext(),list,weekday,weeknum));
        setCanceledOnTouchOutside(true);
    }

}
