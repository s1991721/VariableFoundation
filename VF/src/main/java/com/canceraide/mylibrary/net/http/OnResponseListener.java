package com.canceraide.mylibrary.net.http;

/**
 * Created by mr.lin on 2018/11/7
 * <p>
 * UI层结果回调
 */
public interface OnResponseListener<R> {

    void onResponse(R data);


    void onError(int code, String msg);
}
