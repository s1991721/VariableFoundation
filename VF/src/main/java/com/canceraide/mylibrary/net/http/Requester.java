package com.canceraide.mylibrary.net.http;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import com.canceraide.mylibrary.base.BaseApplication;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.$Gson$Types;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by mr.lin on 2018/11/7
 * <p>
 * 网络请求应用层的解析处理
 * 所有的请求都必须继承自此
 */
public abstract class Requester<R> extends HandleRequest {

    private OnResponseListener<R> onResponseListener;
    private Handler handler = new Handler(Looper.getMainLooper());

    public Requester(OnResponseListener<R> onResponseListener) {
        this.onResponseListener = onResponseListener;
    }

    @Override
    void onRawResponse(String json) {
        int status;
        String msg;
        R object = null;

        try {
            JSONObject jsonObject = new JSONObject(json);
            status = jsonObject.getInt("Status");
            if (status == HttpCode.OK) {
                object = generics(jsonObject.optString("Data"));
            }
            msg = jsonObject.getString("Msg");
        } catch (JSONException e) {
            e.printStackTrace();
            status = HttpCode.Serialize_Error;
            msg = "Serialize Error";
        }

        final int finalStatus = status;
        final String finalMsg = msg;
        final R finalObject = object;
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (finalStatus == HttpCode.OK) {
                    onResponseListener.onResponse(finalObject);
                } else {
                    onResponseListener.onError(finalStatus, finalMsg);
                }
                if (finalStatus == HttpCode.Un_Login) {
                    BaseApplication.getInstance().triggerLogin();
                }
            }
        });
    }

    @Override
    void onError(final String msg) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                onResponseListener.onError(HttpCode.Error, msg);
            }
        });
    }

    private R generics(String data) {
        if (TextUtils.isEmpty(data)) {
            return null;
        }
        Type type = getSuperclassTypeParameter();
        if (type == null) {
            return null;
        }
        return new GsonBuilder().create().fromJson(data, type);
    }

    private Type getSuperclassTypeParameter() {
        Type superclass = getClass().getGenericSuperclass();
        if (superclass instanceof Class) {
            return null;
            //throw new RuntimeException("Missing type parameter.");
        }
        ParameterizedType parameterized = (ParameterizedType) superclass;
        return $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[0]);
    }

}
