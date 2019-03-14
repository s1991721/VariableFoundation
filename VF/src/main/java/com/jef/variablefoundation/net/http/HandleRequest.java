package com.jef.variablefoundation.net.http;


import com.jef.variablefoundation.base.BaseApplication;
import com.jef.variablefoundation.user.UserManager;
import com.jef.variablefoundation.user.bean.UserInfo;
import com.jef.variablefoundation.utils.Logger;
import com.jef.variablefoundation.utils.StringUtils;
import com.jef.variablefoundation.utils.TimeUtils;

import java.util.HashMap;

/**
 * Created by mr.lin on 2018/11/7
 * 应用层阶段的网络处理
 */
public abstract class HandleRequest extends BaseRequest {

    private Logger logger = new Logger("HTTP");

    protected HandleRequest() {
        IResponseListener iResponseListener = new IResponseListener() {
            @Override
            public void onResponse(int code, String data) {
                String log = String.format("Response:%s\n%s", getURLSuffix(), data);
                logger.i(log);
                if (code == HttpCode.OK) {
                    onRawResponse(data);
                } else if (code == HttpCode.Error){
                    onError("服务器开小差了!");
                }else if (code == HttpCode.NO_NETWORK){
                    onError("请检查网络连接!");
                }
            }
        };
        setIResponseListener(iResponseListener);
    }

    @Override
    protected void preRequest() {
        super.preRequest();
        String log = String.format("Request:%s \nURL:%s\nHeader:%s\nBody:%s\nMethod:%s"
                , getURLSuffix()
                , url
                , header.toString()
                , body.toString()
                , method);
        logger.i(log);
    }

    @Override
    HashMap<String, String> getHeader(HashMap<String, String> header) {
        String timespan = TimeUtils.timeStamp();
        String noncestr = StringUtils.randomString(32);
        UserInfo userInfo = BaseApplication.getInstance().getManager(UserManager.class).getUser();
        String userToken = userInfo == null ? "" : userInfo.getUserToken();
        header.put("timestamp", timespan);
        header.put("noncestr", noncestr);
        header.put("usertoken", userToken);
        header.put("sign", StringUtils.sign(timespan, noncestr, userToken, HttpConfig.AppSecret));
        return header;
    }

    @Override
    protected String getURL() {
        return HttpConfig.URL;
    }

    abstract void onRawResponse(String json);

    abstract void onError(String msg);


}
