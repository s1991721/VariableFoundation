package com.jef.variablefoundation.db.cache;

import android.content.Context;
import android.content.SharedPreferences;

import com.jef.variablefoundation.base.BaseManager;
import com.jef.variablefoundation.user.UserManager;

/**
 * Created by mr.lin on 2018/11/15
 * 设置管理
 */
public class SettingManager extends BaseManager {

    private SharedPreferences globalSP;

    public static final String GLOBAL_KEY_CONFIG = "global_key_config";
    public static final String GLOBAL_KEY_MOBILE_REGULAR = "global_key_mobile_regular";
    public static final String GLOBAL_KEY_PASSWORD_REGULAR = "global_key_password_regular";
    public static final String GLOBAL_KEY_USER_INFO = "global_key_user_info";

    public static final String USER_KEY_CACHE_DISEASE="user_key_cache_disease";
    public static final String USER_KEY_CACHE_DRUG="user_key_cache_drug";
    public static final String USER_KEY_CACHE_EXAM="user_key_cache_exam";
    public static final String USER_KEY_CACHE_SYMPTOM="user_key_cache_symptom";


    @Override
    protected void onManagerCreate() {
        globalSP = getApplication().getSharedPreferences("setting_global", Context.MODE_PRIVATE);
    }

    private SharedPreferences getUserSP() {
        return getApplication().getSharedPreferences("setting_" + getManager(UserManager.class).getUID(), Context.MODE_PRIVATE);
    }

    public void setUserSetting(String key, String value) {
        getUserSP().edit().putString(key, value).apply();
    }

    public void setGlobalSetting(String key, String value) {
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
