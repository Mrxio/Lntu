package cn.net.ntech.lntu.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import cn.net.ntech.lntu.R;


/**
 * Created by Administrator on 2016/11/26.
 */

public class ProgressView extends RelativeLayout {

    private LinearLayout ll_isprogress;
    private LinearLayout ll_failed;
    private Button bt_restart;
    private ClickCallback clickCallback;
    public ProgressView(Context context) {
        super(context);
    }

    public ProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        View view = LayoutInflater.from(context).inflate(R.layout.view_progress, this, true);
        ll_isprogress = (LinearLayout) view.findViewById(R.id.ll_isprogress);
        ll_failed = (LinearLayout) view.findViewById(R.id.ll_failed);
        bt_restart = (Button) findViewById(R.id.bt_restart);
        ll_isprogress.setVisibility(VISIBLE);
        ll_failed.setVisibility(GONE);
        bt_restart.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                ll_isprogress.setVisibility(VISIBLE);
                ll_failed.setVisibility(GONE);
                clickCallback.Clicked();
            }
        });
    }
    public void start(){
        ll_isprogress.setVisibility(VISIBLE);
        ll_failed.setVisibility(GONE);
    }
    public void finish(){
        this.setVisibility(GONE);
    }
    public void failed(){
        ll_isprogress.setVisibility(GONE);
        ll_failed.setVisibility(VISIBLE);
    }


    public void setOnClickedCallBack(ClickCallback clickCallback){
        this.clickCallback = clickCallback;
    }
}
