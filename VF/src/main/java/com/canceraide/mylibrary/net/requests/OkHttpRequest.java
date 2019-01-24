package com.canceraide.mylibrary.net.requests;

import android.text.TextUtils;

import com.canceraide.mylibrary.net.http.*;

import okhttp3.*;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * Created by mr.lin on 2018/11/7
 * <p>
 * 实际的OkHttp请求封装
 */
public class OkHttpRequest implements IRequest {

    private static final OkHttpClient okHttpClient =
            new OkHttpClient.Builder()
                    .connectTimeout(HttpConfig.ConnectTimeOut, TimeUnit.SECONDS)
                    .readTimeout(HttpConfig.ReadTimeOut, TimeUnit.SECONDS)
                    .writeTimeout(HttpConfig.WriteTimeOut, TimeUnit.SECONDS)
                    .retryOnConnectionFailure(true)
                    .build();

    private HashMap<String, String> body;
    private HashMap<String, String> header;
    private String url;
    private String method;

    private IResponseListener iResponseListener;

    public OkHttpRequest(IResponseListener iResponseListener) {
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
        if (method.equals(BaseRequest.POST)) {
            postRequest(requestBuilder);
        } else if (method.equals(BaseRequest.DELETE)) {
            deleteRequest(requestBuilder);
        } else if (method.equals(BaseRequest.FILE)) {
            postFileRequest(requestBuilder);
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

        requestBuilder.post(requestBodyBuilder.build());

        return requestBuilder;
    }

    private Request.Builder deleteRequest(Request.Builder requestBuilder) {
        StringBuffer stringBuffer = new StringBuffer(url).append("?");
        for (String key : body.keySet()) {
            String value = body.get(key);
            stringBuffer.append(key + "=" + value + "&");
        }
        requestBuilder.url(stringBuffer.toString());
        requestBuilder.delete();
        return requestBuilder;
    }

    private Request.Builder getRequest(Request.Builder requestBuilder) {
        StringBuffer stringBuffer = new StringBuffer(url).append("?");
        for (String key : body.keySet()) {
            String value = body.get(key);
            if (TextUtils.isEmpty(value)) {
                continue;
            }
            stringBuffer.append(key + "=" + value + "&");
        }
        stringBuffer.deleteCharAt(stringBuffer.length() - 1);
        requestBuilder.url(stringBuffer.toString());
        requestBuilder.get();
        return requestBuilder;
    }

    private Request.Builder postFileRequest(Request.Builder requestBuilder) {
        MultipartBody.Builder requestBodyBuilder = new MultipartBody.Builder();
        requestBodyBuilder.setType(MultipartBody.FORM);
        for (String key : body.keySet()) {
            String value = body.get(key);
            if (!TextUtils.isEmpty(value)) {
                File file = new File(value);
                requestBodyBuilder.addFormDataPart("img", file.getName(), RequestBody.create(BaseRequest.MEDIA_TYPE_PNG, file));
            }
        }

        requestBuilder.post(requestBodyBuilder.build());

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

    //GET
    @Override
    public long getFileLength(String url) {
        Request request = new Request.Builder().url(url).build();

        try {
            Response response = okHttpClient.newCall(request).execute();
            if (response != null && response.isSuccessful()) {
                long length = response.body().contentLength();
                response.body().close();
                return length;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

}
