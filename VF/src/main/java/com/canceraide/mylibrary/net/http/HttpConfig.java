package com.canceraide.mylibrary.net.http;

/**
 * Created by mr.lin on 2018/11/7
 * 网络配置
 */
public class HttpConfig {

    public static final int DEV = 1;
    public static final int TEST = 2;
    public static final int PRE_PRODUCE = 3;
    public static final int PRODUCE = 4;

    public static final int environment = PRE_PRODUCE;

    public static String H5_URL;
    public static String URL;

    static {
        switch (environment) {
            case DEV:
                H5_URL = "http://dev-mobile.kmhealthstation.com:9139";
                URL = "http://dev-www.canceraide.com:9139/";//请求基地址
                break;
            case TEST:
                H5_URL = "http://test-mobile.kmhealthstation.com:9003/";//h5页面
                URL = "http://test-www.canceraide.com:9003/";//测试环境
                break;
            case PRE_PRODUCE:
                H5_URL = "http://prmobile.kmhealthstation.com";
                URL = "http://prwww.canceraide.com/";//预发布
                break;
            case PRODUCE:
                H5_URL = "http://mobile.kmhealthstation.com";
                break;
        }
    }

    public static final String AppSecret = "c6d5b5a1a63546369c02c70efe9bbe71";
    public static final int ConnectTimeOut = 10;//网络超时时间
    public static final int ReadTimeOut = 30;
    public static final int WriteTimeOut = 60;
}
