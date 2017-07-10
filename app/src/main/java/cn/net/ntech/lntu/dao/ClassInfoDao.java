package cn.net.ntech.lntu.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

import cn.net.ntech.lntu.bean.ClassInfo;

/**
 * Created by Administrator on 2016/11/8.
 */

public class ClassInfoDao {
    private ClassInfoOpenHelper openHelper;
    private SQLiteDatabase db;
        public ClassInfoDao(Context context) {
        this.openHelper = ClassInfoOpenHelper.getDBHelper(context,1);
        this.db = openHelper.getWritableDatabase();
    }

    /**
     * 插入课表
     * 
     * @param info
     * @param type  课表类型  0为系统课表 1为用户添加课表
     * @return
     */
    synchronized public boolean insertClass(ClassInfo info,String type) {
        if (info == null|| TextUtils.isEmpty(type)){
            return false;
        }
//        if (isHistoryExist(str)){
//            deleteHistoryByStr(str);
//        }
        ContentValues value = new ContentValues();
        value.putNull("_id");
        value.put("classname", info.getClassname());
        value.put("classteacher", info.getClassteacher());
        value.put("classgrade", info.getClassgrade());
        value.put("classweeknum", info.getClassweeknum());
        value.put("classweek", info.getClassweek());
        value.put("classtime", info.getClasstime());
        value.put("classplace", info.getClassplace());
        value.put("classtype", type);
        long id = db.insert("classinfo", null, value);
        if (id == -1) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 根据类型删除课表
     *
     * @param type 0为系统，1为用户
     * @return
     */
    synchronized public boolean deleteClassByType(String type) {
        int deleteIs = db.delete("classinfo", "classtype=?", new String[]{type + ""});
        if (deleteIs <= 0) {
            return false;
        } else {
            return false;

        }
    }

    /**
     * 查询课表
     *
     * @return
     */
    synchronized public List<ClassInfo> queryClass() {
        List<ClassInfo> list = new ArrayList<ClassInfo>();
        Cursor cursor = db.rawQuery("select * from classinfo", null);
        while (cursor.moveToNext()) {
            String classname = cursor.getString(cursor.getColumnIndex("classname"));
            String classteacher = cursor.getString(cursor.getColumnIndex("classteacher"));
            String classgrade = cursor.getString(cursor.getColumnIndex("classgrade"));
            String classweeknum = cursor.getString(cursor.getColumnIndex("classweeknum"));
            String classweek = cursor.getString(cursor.getColumnIndex("classweek"));
            String classtime = cursor.getString(cursor.getColumnIndex("classtime"));
            String classplace = cursor.getString(cursor.getColumnIndex("classplace"));
            ClassInfo info = new ClassInfo( classname,  classteacher,  classgrade,  classweeknum,  classweek,  classtime,  classplace);
            list.add(info);
        }
        cursor.close();
        return list;
    }

    /**
     * 通过周和时间获取课程
     *
     * @param weekday
     * @param time
     * @return
     */
    synchronized public List<ClassInfo> queryClassByTime(String weekday, String time) {
        List<ClassInfo> list = new ArrayList<ClassInfo>();
        Cursor cursor = db.rawQuery("select * from classinfo where classweek = ? and classtime = ?", new String[]{weekday, time});
        while (cursor.moveToNext()) {
            String classname = cursor.getString(cursor.getColumnIndex("classname"));
            String classteacher = cursor.getString(cursor.getColumnIndex("classteacher"));
            String classgrade = cursor.getString(cursor.getColumnIndex("classgrade"));
            String classweeknum = cursor.getString(cursor.getColumnIndex("classweeknum"));
            String classweek = cursor.getString(cursor.getColumnIndex("classweek"));
            String classtime = cursor.getString(cursor.getColumnIndex("classtime"));
            String classplace = cursor.getString(cursor.getColumnIndex("classplace"));
            ClassInfo info = new ClassInfo( classname,  classteacher,  classgrade,  classweeknum,  classweek,  classtime,  classplace);
            list.add(info);
        }
        cursor.close();
        return list;
    }

    /**
     * 查询去重课程的名称的集合
     *
     * @return
     */
    synchronized public List<String> qurrClassName(){
        Cursor cs1 = db.rawQuery("select distinct classname from classinfo", null);
        List list = new ArrayList();
        while (cs1.moveToNext()){
            String eachclass = cs1.getString(cs1.getColumnIndex("classname"));
            list.add(eachclass);
        }
        cs1.close();
        return list;
    }
    public void clearClass(){
        String sql = "DELETE FROM classinfo;";
        db.execSQL(sql);
    }

    public void closeDB(){
        db.close();
    }
}