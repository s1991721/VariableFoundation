package com.jef.variablefoundation.net.http;

import java.util.HashMap;

/**
 * Created by mr.lin on 2018/11/7
 * <p>
 * 不同网络框架的约束
 */
public interface IRequest {

    //GET、POST

    void setBody(HashMap<String, String> body);

    void setHeader(HashMap<String, String> header);

    void setURL(String url);

    void setMethod(String method);

    void request();

    //File

    long getFileLength(String url);

}
