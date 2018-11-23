package com.canceraide.mylibrary.http;

import android.text.TextUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by mr.lin on 2018/11/7
 * <p>
 * 实际的OkHttp请求封装
 */
public class OkHttpRequest implements IRequest {

    private OkHttpClient okHttpClient;

    private HashMap<String, String> body;
    private HashMap<String, String> header;
    private String url;
    private String method;

    private IResponseListener iResponseListener;

    public OkHttpRequest(IResponseListener iResponseListener) {
        okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(HttpConfig.ConnectTimeOut, TimeUnit.SECONDS)
                .readTimeout(HttpConfig.ReadTimeOut, TimeUnit.SECONDS)
                .writeTimeout(HttpConfig.WriteTimeOut, TimeUnit.SECONDS)
                .retryOnConnectionFailure(false)
                .build();

        this.iResponseListener = iResponseListener;
    }

    @Override
    public void setBody(HashMap<String, String> body) {
        this.body = body;
    }

    @Override
    public void setHeader(HashMap<String, String> header) {
        this.header = header;
    }

    @Override
    public void setURL(String url) {
        this.url = url;
    }

    @Override
    public void setMethod(String method) {
        this.method = method;
    }

    @Override
    public void request() {
        okhttp3.Request.Builder requestBuilder = new Request.Builder().url(url);
        if (method.equals("POST")) {
            postRequest(requestBuilder);
        } else {
            getRequest(requestBuilder);
        }
        addHeader(requestBuilder);

        Call call = okHttpClient.newCall(requestBuilder.build());
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (iResponseListener != null) {
                    iResponseListener.onResponse(HttpCode.Error, e.getMessage());
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (iResponseListener != null) {
                    iResponseListener.onResponse(HttpCode.OK, response.body().string());
                }
            }
        });
    }


    private Request.Builder postRequest(Request.Builder requestBuilder) {
        FormBody.Builder requestBodyBuilder = new FormBody.Builder();
        for (String key : body.keySet()) {
            String value = body.get(key);
            if (!TextUtils.isEmpty(value)) {
                requestBodyBuilder.add(key, value);
            }
        }

        requestBuilder.method(method, requestBodyBuilder.build());

        return requestBuilder;
    }

    private Request.Builder getRequest(Request.Builder requestBuilder) {
        requestBuilder.get();
        return requestBuilder;
    }

    private Request.Builder addHeader(Request.Builder requestBuilder) {
        for (String key : header.keySet()) {
            String value = header.get(key);
            if (!TextUtils.isEmpty(value)) {
                requestBuilder.addHeader(key, value);
            }
        }

        return requestBuilder;
    }

}
