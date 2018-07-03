package com.rq.practice.database;

import android.content.Context;

import com.rq.practice.database.base.DBHelper;

/**
 * STUDENT_TABLE
 * @author rock you
 * @version 1.0.0
 */
public class StudentDBHelper extends DBHelper {

    private static final String STUDENT_TABLE_NAME = "student_table";

    public StudentDBHelper(Context context){
        super(context);
    }

    @Override
    protected String onCreate() {
//        CREATE TABLE IF NOT EXISTS
        return "CREATE TABLE IF NOT EXISTS "+STUDENT_TABLE_NAME+"("+
                ");";
    }

    @Override
    protected void onUpgrade(int version) {
        if (version < 3){
            // 数据库升级代码
            version = 3;
        }
        if (version < 6){
            // 数据库升级代码
            version = 6;
        }
    }

    @Override
    protected void onDropTable() {
        dropDataTable();
    }

    @Override
    protected String getTableName() {
        return STUDENT_TABLE_NAME;
    }
}
