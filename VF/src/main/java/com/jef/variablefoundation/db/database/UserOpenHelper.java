package com.jef.variablefoundation.db.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by mr.lin on 2018/11/16
 */
public class UserOpenHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;

    public static final String TABLE_MY_SYMPTOMS = "table_my_symptoms";
    public static final String TABLE_MY_DISEASE = "table_my_disease";
    public static final String TABLE_MY_EXAM = "table_my_exam";
    public static final String TABLE_MY_MEDICINE = "table_my_medicine";

    public UserOpenHelper(Context context, String name) {
        super(context, "db_user_" + name, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_MY_SYMPTOMS + "(my_symptom_id integer primary key,symptom_id integer,my_symptom_name text,severity_level integer,severity_name text,log_date text)");
        db.execSQL("create table " + TABLE_MY_DISEASE + "(my_disease_id integer primary key,disease_id integer,my_disease_name text,is_primary text,first_diagnose_time text,symptoms_time text)");
        db.execSQL("create table " + TABLE_MY_EXAM + "(id integer primary key, json text)");
        db.execSQL("create table " + TABLE_MY_MEDICINE + "(medicine_id integer primary key, name text,is_stop text,stop_comment text,stop_reason text,product_id integer,begin_date text,end_date text,dosage double,unit text,dosage_id integer)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
