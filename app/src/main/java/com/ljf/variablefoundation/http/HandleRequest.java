package com.ljf.variablefoundation.http;


import com.ljf.variablefoundation.utils.Logger;

import java.util.HashMap;

/**
 * Created by mr.lin on 2018/11/7
 * 应用层阶段的网络处理
 */
public abstract class HandleRequest<R> extends BaseRequest {

    private Logger logger = new Logger("HTTP");

    public HandleRequest() {
        IResponseListener iResponseListener = new IResponseListener() {
            @Override
            public void onResponse(int code, String data) {
                String log = String.format("Response:\n%s", data);
                logger.i(log);
                //todo 拦截指定异常
                onRawResponse(code, data);
            }
        };
        setIResponseListener(iResponseListener);
    }

    @Override
    protected void preRequest() {
        super.preRequest();
        String log = String.format("Request: \nURL:%s\nHeader:%s\nBody:%s\nMethod:%s"
                , url
                , header.toString()
                , body.toString()
                , method);
        logger.i(log);
    }

    //默认无header
    @Override
    HashMap<String, String> getHeader(HashMap<String, String> header) {
        return header;
    }

    @Override
    String getURL() {
        return HttpConfig.URL;
    }

    //默认无路由
    @Override
    String getURLSuffix() {
        return "";
    }

    abstract void onRawResponse(int code, String data);

}
