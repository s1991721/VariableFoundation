package com.canceraide.mylibrary.net.http;

/**
 * Created by mr.lin on 2018/11/7
 * 网络配置
 */
public class HttpConfig {
    public static final String H5_URL = "http://test-mobile.kmhealthstation.com:9003/";//h5页面
//    public static final String URL = "http://dev-www.canceraide.com:9139/";//请求基地址
//    public static final String URL = "http://test-www.canceraide.com:9003/";//测试环境
    public static final String URL = "http://prwww.canceraide.com/";//预发布
    public static final String AppSecret = "c6d5b5a1a63546369c02c70efe9bbe71";
    public static final int ConnectTimeOut = 10;//网络超时时间
    public static final int ReadTimeOut = 30;
    public static final int WriteTimeOut = 60;
}
