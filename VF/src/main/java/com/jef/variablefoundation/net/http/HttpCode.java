package com.jef.variablefoundation.net.http;

/**
 * Created by mr.lin on 2018/11/7
 * 错误码
 */
public class HttpCode {

    public static final int OK = 0;//成功
    public static final int Error = -1;//请求失败
    public static final int Serialize_Error = -2;//序列化失败
    public static final int NO_NETWORK = -3;//断网

    public static final int Un_Login = 401;//未登录
}
