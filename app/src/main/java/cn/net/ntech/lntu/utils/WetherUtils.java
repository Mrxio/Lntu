package cn.net.ntech.lntu.utils;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.net.ntech.lntu.bean.WetherInfo;

/**
 * Created by Administrator on 2016/11/3.
 */

public class WetherUtils {
    public static WetherInfo getWether(String json){
        WetherInfo wetherInfo = null;
        try {
            JSONObject jsonobject = new JSONObject(json);
            JSONObject daily = jsonobject.getJSONArray("results").getJSONObject(0).getJSONArray("daily").getJSONObject(0);
            String wether = daily.getString("text_day");
            String minT = daily.getString("low");
            String maxT = daily.getString("high");
            wetherInfo = new WetherInfo(wether,maxT,minT);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return wetherInfo;
    }
}
