package com.canceraide.mylibrary.user.requester;

import com.canceraide.mylibrary.net.http.BaseRequest;
import com.canceraide.mylibrary.net.http.OnResponseListener;
import com.canceraide.mylibrary.net.http.Requester;
import com.canceraide.mylibrary.user.bean.UploadImgInfo;

import java.util.HashMap;

/**
 * @Description描述:更新个人图片
 * @Author作者: xsy
 * @Date日期: 2018/12/25
 */
public class UploadHeadImgRequester extends Requester<UploadImgInfo> {
    private String headImgUrl;

    public UploadHeadImgRequester(OnResponseListener<UploadImgInfo> onResponseListener, String headImgUrl) {
        super(onResponseListener);
        this.headImgUrl = headImgUrl;
    }

    @Override
    protected HashMap<String, String> getBody(HashMap<String, String> body) {
        body.put("headImgUrl", headImgUrl);

        return body;
    }

    @Override
    protected String getURLSuffix() {
        return "/app/Patient/v2/UploadHeadPhoto";
    }

    @Override
    protected String getMethod() {
        return BaseRequest.FILE;
    }
}
