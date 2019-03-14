package com.jef.variablefoundation.user.requester;

import com.jef.variablefoundation.net.http.OnResponseListener;
import com.jef.variablefoundation.net.http.Requester;

import java.util.HashMap;

/**
 * Created by mr.lin on 2018/12/4
 * 注册接口
 */
public class RegisterRequester extends Requester {

    private String accountName;
    private String phoneNumber;
    private String password;
    private String verificationCode;

    public RegisterRequester(OnResponseListener onResponseListener, String accountName, String phoneNumber, String password, String verificationCode) {
        super(onResponseListener);
        this.accountName = accountName;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.verificationCode = verificationCode;
    }

    @Override
    protected HashMap<String, String> getBody(HashMap<String, String> body) {
        body.put("AccountName", accountName);
        body.put("PhoneNumber", phoneNumber);
        body.put("Password", password);
        body.put("VerificationCode", verificationCode);
        return body;
    }

    @Override
    protected String getURLSuffix() {
        return "/app/Account/Register";
    }

}
