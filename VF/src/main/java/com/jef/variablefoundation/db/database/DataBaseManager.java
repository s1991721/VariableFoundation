package com.jef.variablefoundation.db.database;

import android.database.sqlite.SQLiteOpenHelper;

import com.jef.variablefoundation.base.BaseManager;
import com.jef.variablefoundation.base.Manager;
import com.jef.variablefoundation.user.UserManager;
import com.jef.variablefoundation.user.bean.UserInfo;
import com.jef.variablefoundation.utils.async.AsyncTaskVF;
import com.jef.variablefoundation.utils.async.DBTask;

/**
 * Created by mr.lin on 2018/11/15
 * 数据库管理
 */
public class DataBaseManager extends BaseManager {

    @Manager
    private UserManager userManager;

    private UserManager.OnUserStateChangeListener onUserStateChangeListener = new UserManager.OnUserStateChangeListener() {
        @Override
        public void onLogin(UserInfo userInfo) {
            initUserOpenHelper();
        }

        @Override
        public void onLogout() {
            initUserOpenHelper();
        }

    };

    private SQLiteOpenHelper userSQLiteOpenHelper;
    private SQLiteOpenHelper globalSQLiteOpenHelper;

    @Override
    protected void onManagerCreate() {
        userManager.addOnUserStateChangeListener(onUserStateChangeListener);
        initUserOpenHelper();
        initGlobalOpenHelper();
    }

    private void initUserOpenHelper() {
        if (userSQLiteOpenHelper != null) {
            userSQLiteOpenHelper.close();
        }
        AsyncTaskVF<SQLiteOpenHelper> asyncTaskVF = new AsyncTaskVF<SQLiteOpenHelper>() {
            @Override
            public SQLiteOpenHelper runOnBackGround() {
                return new UserOpenHelper(getApplication(), userManager.getUID());
            }

            @Override
            public void runOnUIThread(SQLiteOpenHelper sqLiteOpenHelper) {
                userSQLiteOpenHelper = sqLiteOpenHelper;
            }
        };
        asyncTaskVF.execute();
    }

    private void initGlobalOpenHelper() {
        AsyncTaskVF<SQLiteOpenHelper> asyncTaskVF = new AsyncTaskVF<SQLiteOpenHelper>() {
            @Override
            public SQLiteOpenHelper runOnBackGround() {
                return new GlobalOpenHelper(getApplication());
            }

            @Override
            public void runOnUIThread(SQLiteOpenHelper sqLiteOpenHelper) {
                globalSQLiteOpenHelper = sqLiteOpenHelper;
            }
        };
        asyncTaskVF.execute();
    }

    public <Result> void submitUserDBTask(final DBTask<Result> dbTask) {
        AsyncTaskVF<Result> asyncTaskVF = new AsyncTaskVF<Result>() {
            @Override
            public Result runOnBackGround() {
                return dbTask.runOnDBThread(userSQLiteOpenHelper);
            }

            @Override
            public void runOnUIThread(Result result) {
                dbTask.runOnUIThread(result);
            }
        };
        asyncTaskVF.execute();
    }

    public <Result> void submitGlobalDBTask(final DBTask<Result> dbTask) {
        AsyncTaskVF<Result> asyncTaskVF = new AsyncTaskVF<Result>() {
            @Override
            public Result runOnBackGround() {
                return dbTask.runOnDBThread(globalSQLiteOpenHelper);
            }

            @Override
            public void runOnUIThread(Result result) {
                dbTask.runOnUIThread(result);
            }
        };
        asyncTaskVF.execute();
    }
}
