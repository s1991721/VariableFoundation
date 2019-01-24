package com.canceraide.mylibrary.user.requester;

import com.canceraide.mylibrary.net.http.BaseRequest;
import com.canceraide.mylibrary.net.http.OnResponseListener;
import com.canceraide.mylibrary.net.http.Requester;
import com.canceraide.mylibrary.user.bean.MyInfo;

import java.util.HashMap;

/**
 * @Description描述:
 * @Author作者: xsy
 * @Date日期: 2018/12/25
 */
public class MyInfoRequester extends Requester<MyInfo> {


    public MyInfoRequester(OnResponseListener<MyInfo> onResponseListener) {
        super(onResponseListener);
    }

    @Override
    protected HashMap<String, String> getBody(HashMap<String, String> body) {
        return body;
    }

    @Override
    protected String getURLSuffix() {
        return "/app/Patient/v2/MyInfo";
    }

    @Override
    protected String getMethod() {
        return BaseRequest.GET;
    }
}
