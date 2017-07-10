package cn.net.ntech.lntu.dao;

        import android.content.Context;
        import android.database.sqlite.SQLiteDatabase;
        import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by NTECHER on 2016/3/7.
 */
public class ClassInfoOpenHelper extends SQLiteOpenHelper {

    private static ClassInfoOpenHelper fdb;

    public ClassInfoOpenHelper(Context context,int version) {
        super(context,"classinfo.db", null,version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table classinfo (_id integer primary key autoincrement, classname char(10), classteacher char(20), classgrade char(20), classweeknum char(20), classweek char(20), classtime char(20), classplace char(20), classtype char(20))");
    }

    public static ClassInfoOpenHelper getDBHelper(Context context, int version) {
        if (fdb == null) {
            fdb = new ClassInfoOpenHelper(context, version);
        }
        return fdb;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
