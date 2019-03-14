package com.jef.variablefoundation.db.cache;

import com.jef.variablefoundation.user.UserManager;
import com.jef.variablefoundation.base.BaseManager;
import com.jef.variablefoundation.utils.json.JsonHelper;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.Serializable;
import java.util.List;

/**
 * Created by mr.lin on 2018/11/15
 * 缓存管理
 */
public class CacheManager extends BaseManager {

    @Override
    protected void onManagerCreate() {

    }

    private ACache getCache() {
        String uid = getManager(UserManager.class).getUID();
        return ACache.get(getApplication(), "cache_" + uid);
    }

    public void putString(String key, String value) {
        getCache().put(key, value);
    }

    public void putObject(String key, Serializable value) {
        getCache().put(key, value);
    }

    public void putList(String key, List value) {
        JSONArray jsonArray = JsonHelper.toJSONArray(value);
        getCache().put(key, jsonArray.toString());
    }

    public String getString(String key) {
        return getCache().getAsString(key);
    }

    public <O> O getObject(String key) {
        return (O) getCache().getAsObject(key);
    }

    public <O> List<O> getList(String key, Class<O> cls) {
        String value = getCache().getAsString(key);
        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (jsonArray != null) {
            return JsonHelper.toList(jsonArray, cls);
        } else {
            return null;
        }
    }

    //移除项
    public void remove(String key) {
        getCache().remove(key);
    }

    //清除当前用户数据
    public void clear() {
        getCache().clear();
    }

}
