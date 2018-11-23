package com.canceraide.mylibrary.http;

import java.util.HashMap;

/**
 * Created by mr.lin on 2018/11/7
 *
 * 网络请求的应用层最底层的类
 */
public abstract class BaseRequest {

    public static final String POST = "POST";
    public static final String GET = "GET";

    private IResponseListener iResponseListener;

    protected HashMap<String, String> body;
    protected HashMap<String, String> header;
    protected String method;
    protected String url;

    public void setIResponseListener(IResponseListener iResponseListener) {
        this.iResponseListener = iResponseListener;
    }

    //实际请求前参数组装
    protected void preRequest() {
        body = getBody();
        header = getHeader();
        method = getMethod();
        url = getURL() + getURLSuffix();
    }

    //可更换不同的网络请求框架
    public void request() {
        preRequest();
        IRequest requester = new OkHttpRequest(iResponseListener);
        requester.setURL(url);
        requester.setBody(body);
        requester.setHeader(header);
        requester.setMethod(method);
        requester.request();
    }

    private HashMap<String, String> getBody() {
        HashMap<String, String> body = new HashMap<>();
        return getBody(body);
    }

    private HashMap<String, String> getHeader() {
        HashMap<String, String> header = new HashMap<>();
        return getHeader(header);
    }

    //默认POST请求
    protected String getMethod() {
        return POST;
    }

    protected abstract HashMap<String, String> getBody(HashMap<String, String> body);

    abstract HashMap<String, String> getHeader(HashMap<String, String> header);

    abstract String getURL();

    abstract String getURLSuffix();

}
