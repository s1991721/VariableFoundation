package com.canceraide.mylibrary.http;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.google.gson.GsonBuilder;
import com.google.gson.internal.$Gson$Types;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by mr.lin on 2018/11/7
 *
 * 网络请求应用层的解析处理
 * 所有的请求都必须继承自此
 */
public abstract class Requester<R> extends HandleRequest {

    private OnResponseListener onResponseListener;
    private Handler handler = new Handler(Looper.getMainLooper(), new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            onResponseListener.onResponse(msg.arg1, msg.obj);
            return true;
        }
    });

    public Requester(OnResponseListener<R> onResponseListener) {
        this.onResponseListener = onResponseListener;
    }

    @Override
    void onRawResponse(int code, String data) {
        R d = null;
        Message message = new Message();
        try {
            d = generics(data);
        } catch (Exception e) {
            message.arg1 = HttpCode.Gson_Error;
        }

        message.arg1 = code;
        message.obj = d;
        handler.sendMessage(message);
    }

    private R generics(String data) {
        Type type = getSuperclassTypeParameter();
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
