package com.canceraide.mylibrary.http;

import java.util.HashMap;

/**
 * Created by mr.lin on 2018/11/7
 *
 * 不同网络框架的约束
 */
public interface IRequest {

    void setBody(HashMap<String, String> body);

    void setHeader(HashMap<String, String> header);

    void setURL(String url);

    void setMethod(String method);

    void request();

}
