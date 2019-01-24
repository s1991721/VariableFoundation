package com.canceraide.mylibrary.user.requester;

import com.canceraide.mylibrary.net.http.BaseRequest;
import com.canceraide.mylibrary.net.http.OnResponseListener;
import com.canceraide.mylibrary.net.http.Requester;

import java.util.HashMap;

/**
 * Created by mr.lin on 2018/12/7
 * 检查账号可用性
 */
public class CheckAccountRequester extends Requester {

    private String account;

    public CheckAccountRequester(OnResponseListener onResponseListener, String account) {
        super(onResponseListener);
        this.account = account;
    }

    @Override
    protected HashMap<String, String> getBody(HashMap<String, String> body) {
        body.put("name", account);
        return body;
    }

    @Override
    protected String getURLSuffix() {
        return "/app/Account/CheckAccountName";
    }

    @Override
    protected String getMethod() {
        return BaseRequest.GET;
    }
}
