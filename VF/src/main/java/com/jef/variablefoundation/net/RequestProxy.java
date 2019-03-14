package com.jef.variablefoundation.net;

import android.support.annotation.Nullable;

import com.jef.variablefoundation.net.http.IRequest;
import com.jef.variablefoundation.net.http.IResponseListener;
import com.jef.variablefoundation.net.requests.OkHttpRequest;

import java.util.HashMap;

/**
 * Created by mr.lin on 2018/12/8
 * 请求代理
 */
public class RequestProxy implements IRequest {

    private IRequest realRequest;

    public RequestProxy(@Nullable IResponseListener responseListener) {
        realRequest = new OkHttpRequest(responseListener);
    }

    @Override
    public void setBody(HashMap<String, String> body) {
        realRequest.setBody(body);
    }

    @Override
    public void setHeader(HashMap<String, String> header) {
        realRequest.setHeader(header);
    }

    @Override
    public void setURL(String url) {
        realRequest.setURL(url);
    }

    @Override
    public void setMethod(String method) {
        realRequest.setMethod(method);
    }

    @Override
    public void request() {
        realRequest.request();
    }

    @Override
    public long getFileLength(String url) {
        return realRequest.getFileLength(url);
    }
}
