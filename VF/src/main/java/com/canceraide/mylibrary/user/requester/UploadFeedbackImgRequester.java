package com.canceraide.mylibrary.user.requester;

import com.canceraide.mylibrary.net.http.BaseRequest;
import com.canceraide.mylibrary.net.http.OnResponseListener;
import com.canceraide.mylibrary.net.http.Requester;
import com.canceraide.mylibrary.user.bean.MyInfo;
import com.canceraide.mylibrary.user.bean.UploadImgInfo;
import com.canceraide.mylibrary.utils.json.JsonHelper;

import java.util.HashMap;
import java.util.List;

/**
 * @Description描述:上传意见反馈图片
 * @Author作者: xsy
 * @Date日期: 2018/12/25
 */
public class UploadFeedbackImgRequester extends Requester<List<UploadImgInfo>> {
    private List<String> imgUrl;

    public UploadFeedbackImgRequester(OnResponseListener<List<UploadImgInfo>> onResponseListener, List<String> imgUrl) {
        super(onResponseListener);
        this.imgUrl = imgUrl;
    }

    @Override
    protected HashMap<String, String> getBody(HashMap<String, String> body) {
        for (int i = 0; i < imgUrl.size(); i++) {
            body.put(i + "", imgUrl.get(i));
        }

        return body;
    }

    @Override
    protected String getURLSuffix() {
        return "/app/Patient/v2/UploadImg/MySuggestion";
    }

    @Override
    protected String getMethod() {
        return BaseRequest.FILE;
    }

}
