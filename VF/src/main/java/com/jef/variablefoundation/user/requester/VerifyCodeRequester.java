package com.jef.variablefoundation.user.requester;

import com.jef.variablefoundation.net.http.OnResponseListener;
import com.jef.variablefoundation.net.http.Requester;

import java.util.HashMap;

/**
 * Created by mr.lin on 2018/12/4
 * 验证码
 */
public class VerifyCodeRequester extends Requester {

    public static final String TYPE_REGISTER = "0";
    public static final String TYPE_RESET = "1";
    public static final String TYPE_MODIFY_PHONE = "2";

    private String mobile;
    private String smsType;

    public VerifyCodeRequester(OnResponseListener onResponseListener, String mobile, String smsType) {
        super(onResponseListener);
        this.mobile = mobile;
        this.smsType = smsType;
    }

    @Override
    protected HashMap<String, String> getBody(HashMap<String, String> body) {
        body.put("Mobile", mobile);
        body.put("SmsType", smsType);
        return body;
    }

    @Override
    protected String getURLSuffix() {
        return "/app/Account/SendVerifyCode";
    }


}
