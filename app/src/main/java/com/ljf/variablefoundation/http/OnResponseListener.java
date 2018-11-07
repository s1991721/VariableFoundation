package com.ljf.variablefoundation.http;

/**
 * Created by mr.lin on 2018/11/7
 *
 * UI层结果回调
 */
public interface OnResponseListener<R> {

    void onResponse(int code, R data);
}
