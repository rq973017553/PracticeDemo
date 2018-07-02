package com.rq.practice.database.base;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;

/**
 * DBHelper
 * @author rock you
 * @version 1.0.0
 */
public abstract class DBHelper extends SQLiteOpenHelper{

    // 数据库名称
    private static final String DB_NAME = "practice.db";

    private static final int DB_VERSION = 1;

    public DBHelper(Context context){
        this(context, null);
    }

    public DBHelper(Context context, SQLiteDatabase.CursorFactory factory){
        super(context, DB_NAME, factory, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
       String sql = onCreate();
       if (!TextUtils.isEmpty(sql)){
           db.execSQL(sql);
       }else {
           throw new IllegalArgumentException("sql sentence is null or empty!");
       }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion){
            // 老版本升级为新版本
            onUpgrade();
        }else {
            // 低版本覆盖安装高版本
            onDropTable(); // 先摧毁老表
            onCreate(db); // 再创建新表
        }
    }

    /**
     * 创建表结构
     * @return 创建表结构语句
     */
    protected abstract String onCreate();

    /**
     * 更新表结构
     */
    protected abstract void onUpgrade();

    /**
     * 摧毁表结构
     */
    protected abstract void onDropTable();

    /**
     * 摧毁表结构
     * @param tableName 表名
     */
    protected void dropDataTable(String tableName){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+tableName);
    }
}
