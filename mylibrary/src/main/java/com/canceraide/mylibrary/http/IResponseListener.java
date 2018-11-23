package com.canceraide.mylibrary.http;

/**
 * Created by mr.lin on 2018/11/7
 *
 * 不同网络框架结果返回的约束
 */
public interface IResponseListener {

    void onResponse(int code, String data);
}
