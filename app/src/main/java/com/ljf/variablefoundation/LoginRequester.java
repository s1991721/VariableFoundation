package com.ljf.variablefoundation;

import com.ljf.variablefoundation.http.OnResponseListener;
import com.ljf.variablefoundation.http.Requester;

import java.util.HashMap;

/**
 * Created by mr.lin on 2018/11/7
 */
public class LoginRequester extends Requester<LoginInfo> {

    public LoginRequester(OnResponseListener<LoginInfo> onResponseListener) {
        super(onResponseListener);
    }

    @Override
    protected HashMap<String, String> getBody(HashMap<String, String> body) {
        return body;
    }

    @Override
    protected String getMethod() {
        return GET;
    }

}
