package com.canceraide.mylibrary.user.requester;

import com.canceraide.mylibrary.net.http.OnResponseListener;
import com.canceraide.mylibrary.net.http.Requester;

import java.util.HashMap;

/**
 * @Description描述:修改密码
 * @Author作者: xsy
 * @Date日期: 2018/12/26
 */
public class ModifyPasswordRequester extends Requester {
    private String oldPwd;
    private String newPwd;

    public ModifyPasswordRequester(OnResponseListener onResponseListener, String oldPwd, String newPwd) {
        super(onResponseListener);
        this.oldPwd = oldPwd;
        this.newPwd = newPwd;
    }

    @Override
    protected HashMap<String, String> getBody(HashMap<String, String> body) {
        body.put("OldPwd", oldPwd);
        body.put("NewPwd", newPwd);
        return body;
    }

    @Override
    protected String getURLSuffix() {
        return "/app/Account/UpdatePwd";
    }
}
