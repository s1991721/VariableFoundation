package com.canceraide.mylibrary.utils.async;

import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by mr.lin on 2018/11/16
 */
public interface DBTask<Result> {

    Result runOnDBThread(SQLiteOpenHelper sqLiteOpenHelper);

    void runOnUIThread(Result result);

}
