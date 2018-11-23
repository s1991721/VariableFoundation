package com.canceraide.mylibrary.db.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by mr.lin on 2018/11/16
 */
public class UserOpenHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;

    public UserOpenHelper(Context context, String name) {
        super(context, "db_user_" + name, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
