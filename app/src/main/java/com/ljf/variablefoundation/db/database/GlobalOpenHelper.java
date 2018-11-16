package com.ljf.variablefoundation.db.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by mr.lin on 2018/11/16
 */
public class GlobalOpenHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;

    public GlobalOpenHelper(Context context) {
        super(context, "db_global", null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
