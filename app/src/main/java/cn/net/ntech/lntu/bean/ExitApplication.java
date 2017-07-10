package cn.net.ntech.lntu.bean;

import android.app.Activity;
import android.app.Application;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by NTECHER on 2016/3/15.
 */
public class ExitApplication extends Application {
    private static List<Activity> activityList = new LinkedList();
    private static ExitApplication instance;
    public ExitApplication()
    {
    }
    //单例模式中获取唯一的ExitApplication实例
    public static ExitApplication getInstance()
    {
        if(null == instance)
        {
            instance = new ExitApplication();
        }
        return instance;
    }
    //添加Activity到容器中
    public static void addActivity(Activity activity)
    {
        activityList.add(activity);
    }
    //遍历所有Activity并finish
    public static void exit()
    {
        for(Activity activity:activityList)
        {
            activity.finish();
        }
        System.exit(0);
    }
}