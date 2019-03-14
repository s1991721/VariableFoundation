package com.jef.variablefoundation.user.requester;

import com.jef.variablefoundation.net.http.OnResponseListener;
import com.jef.variablefoundation.net.http.Requester;
import com.jef.variablefoundation.user.bean.UserInfo;

import java.util.HashMap;

/**
 * Created by mr.lin on 2018/11/7
 * 登录接口
 */
public class LoginRequester extends Requester<UserInfo> {

    private String account;
    private String password;

    public LoginRequester(OnResponseListener<UserInfo> onResponseListener, String account, String password) {
        super(onResponseListener);
        this.account = account;
        this.password = password;
    }

    @Override
    protected HashMap<String, String> getBody(HashMap<String, String> body) {
        body.put("Account", account);
        body.put("Password", password);
        return body;
    }

    @Override
    protected String getMethod() {
        return POST;
    }

    @Override
    protected String getURLSuffix() {
        return "/app/Account/Login";
    }
}
