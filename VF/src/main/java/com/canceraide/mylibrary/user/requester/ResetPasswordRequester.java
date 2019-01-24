package com.canceraide.mylibrary.user.requester;

import com.canceraide.mylibrary.net.http.OnResponseListener;
import com.canceraide.mylibrary.net.http.Requester;

import java.util.HashMap;

/**
 * Created by mr.lin on 2018/12/6
 * 重置密码
 */
public class ResetPasswordRequester extends Requester {

    private String password;
    private String mobile;
    private String verifyCode;

    public ResetPasswordRequester(OnResponseListener onResponseListener, String password, String mobile, String verifyCode) {
        super(onResponseListener);
        this.password = password;
        this.mobile = mobile;
        this.verifyCode = verifyCode;
    }

    @Override
    protected HashMap<String, String> getBody(HashMap<String, String> body) {
        body.put("Password", password);
        body.put("Mobile", mobile);
        body.put("VerifyCode", verifyCode);
        return body;
    }

    @Override
    protected String getURLSuffix() {
        return "/app/Account/ForgetPwd";
    }
}
