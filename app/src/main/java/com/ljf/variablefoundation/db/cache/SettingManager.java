package com.ljf.variablefoundation.db.cache;

import android.content.Context;
import android.content.SharedPreferences;

import com.ljf.variablefoundation.UserManager;
import com.ljf.variablefoundation.base.BaseApplication;
import com.ljf.variablefoundation.base.BaseManager;

/**
 * Created by mr.lin on 2018/11/15
 * 设置管理
 */
public class SettingManager extends BaseManager {

    private SharedPreferences globalSP;

    @Override
    protected void onManagerCreate() {
        globalSP = BaseApplication.getInstance().getSharedPreferences("setting_global", Context.MODE_PRIVATE);
    }

    private SharedPreferences getUserSP() {
        return BaseApplication.getInstance().getSharedPreferences("setting_" + getManager(UserManager.class).getUID(), Context.MODE_PRIVATE);
    }

    public void setUserSetting(String key, String value) {
        getUserSP().edit().putString(key, value).apply();
    }

    public void setGlobalSettng(String key, String value) {
        globalSP.edit().putString(key, value).apply();
    }

    public String getUserSetting(String key, String def) {
        return getUserSP().getString(key, def);
    }

    public String getGlobalSetting(String key, String def) {
        return globalSP.getString(key, def);
    }

    public void removeUserSetting(String key) {
        getUserSP().edit().remove(key).apply();
    }

    public void removeGlobalSetting(String key) {
        globalSP.edit().remove(key).apply();
    }

    public void removeAllUserSetting() {
        getUserSP().edit().clear().apply();
    }

    public void removeAllGlobalSetting() {
        globalSP.edit().clear().apply();
    }
}
