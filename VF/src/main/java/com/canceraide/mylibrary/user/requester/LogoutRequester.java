package com.canceraide.mylibrary.user.requester;

import com.canceraide.mylibrary.net.http.BaseRequest;
import com.canceraide.mylibrary.net.http.OnResponseListener;
import com.canceraide.mylibrary.net.http.Requester;
import com.canceraide.mylibrary.user.bean.UserInfo;

import java.util.HashMap;

/**
 * Created by mr.lin on 2018/12/10
 * 登出
 */
public class LogoutRequester extends Requester<UserInfo> {

    public LogoutRequester(OnResponseListener<UserInfo> onResponseListener) {
        super(onResponseListener);
    }

    @Override
    protected HashMap<String, String> getBody(HashMap<String, String> body) {
        return body;
    }

    @Override
    protected String getURLSuffix() {
        return "/app/Account/LogOut";
    }

    @Override
    protected String getMethod() {
        return BaseRequest.GET;
    }
}
