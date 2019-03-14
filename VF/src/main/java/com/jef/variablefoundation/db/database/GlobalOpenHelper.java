package com.jef.variablefoundation.db.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by mr.lin on 2018/11/16
 */
public class GlobalOpenHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;

    public static final String TABLE_SYMPTOMS = "table_symptoms";
    public static final String TABLE_DISEASE = "table_disease";
    public static final String TABLE_EXAM = "table_exam";
    public static final String TABLE_PROVINCE = "table_province";
    public static final String TABLE_CITY = "table_city";

    public GlobalOpenHelper(Context context) {
        super(context, "db_global", null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_SYMPTOMS + "(id integer primary key,name text)");
        db.execSQL("create table " + TABLE_DISEASE + "(id integer primary key,name text)");
        db.execSQL("create table " + TABLE_EXAM +
                "(id integer primary key,parent_id integer,name text,en_name text,pin_yin_code text" +
                ",data_type integer,regular_range text,show_on_chart text,options text,input_units text" +
                ", input_hint text,unified_unit text,is_required text)");

        db.execSQL("create table " + TABLE_PROVINCE + "(id integer primary key,name text)");
        db.execSQL("create table " + TABLE_CITY + "(id integer ,name text,province_id int,primary key(id,province_id))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
