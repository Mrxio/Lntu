package cn.net.ntech.lntu.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by NTECHER on 2016/3/10.
 */
public class ConfigInfoOpenHelper extends SQLiteOpenHelper {
    public ConfigInfoOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table configinfo (_id integer primary key autoincrement, username char(10), password char(20))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
