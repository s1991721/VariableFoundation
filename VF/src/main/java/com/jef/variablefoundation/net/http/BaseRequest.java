package com.jef.variablefoundation.net.http;

import com.jef.variablefoundation.NetWorkManager;
import com.jef.variablefoundation.R;
import com.jef.variablefoundation.base.BaseApplication;
import com.jef.variablefoundation.net.RequestProxy;
import com.jef.variablefoundation.utils.ToastUtils;

import java.util.HashMap;

import okhttp3.MediaType;

/**
 * Created by mr.lin on 2018/11/7
 * <p>
 * 网络请求的应用层最底层的类
 */
public abstract class BaseRequest {

    public static final String POST = "POST";
    public static final String GET = "GET";
    public static final String DELETE = "DELETE";
    public static final String FILE = "FILE";
    public static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");

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
        if (!BaseApplication.getInstance().getManager(NetWorkManager.class).isConnected()) {
            ToastUtils.showToast(R.string.net_error);
            iResponseListener.onResponse(HttpCode.NO_NETWORK,"");
            return;
        }
        IRequest requester = new RequestProxy(iResponseListener);
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

    protected abstract String getURL();

    protected abstract String getURLSuffix();

}
