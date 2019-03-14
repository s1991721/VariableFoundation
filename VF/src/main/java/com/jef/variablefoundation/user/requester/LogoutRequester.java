package com.jef.variablefoundation.user.requester;

import com.jef.variablefoundation.net.http.BaseRequest;
import com.jef.variablefoundation.net.http.OnResponseListener;
import com.jef.variablefoundation.net.http.Requester;
import com.jef.variablefoundation.user.bean.UserInfo;

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
