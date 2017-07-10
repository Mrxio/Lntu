package cn.net.ntech.lntu.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2017/1/8.
 */

public class SPUtils {
    private Context context;
    private SharedPreferences sp;

    public void SPUtils(Context context){
        this.context = context;
        this.sp = context.getSharedPreferences("lntu", context.MODE_PRIVATE);
    }

}
