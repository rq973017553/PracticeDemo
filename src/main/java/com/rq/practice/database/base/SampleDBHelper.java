package com.rq.practice.database.base;

import android.content.Context;

/**
 * SampleDBHelper
 * @author rock you
 * @version 1.0.0
 */
public class SampleDBHelper extends DBHelper {

    public SampleDBHelper(Context context){
        super(context);
    }

    @Override
    protected String onCreate() {
        return null;
    }

    @Override
    protected void onUpgrade(int version) {
        //Empty
    }

    @Override
    protected void onDropTable() {
        //Empty
    }

    @Override
    protected String getTableName() {
        return null;
    }
}
