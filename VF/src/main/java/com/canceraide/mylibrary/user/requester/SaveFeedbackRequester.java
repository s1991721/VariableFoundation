package com.canceraide.mylibrary.user.requester;

import com.canceraide.mylibrary.net.http.OnResponseListener;
import com.canceraide.mylibrary.net.http.Requester;
import com.canceraide.mylibrary.user.bean.Feedback;
import com.canceraide.mylibrary.user.bean.MyInfo;

import java.util.HashMap;

/**
 * @Description描述:保存意见反馈
 * @Author作者: xsy
 * @Date日期: 2018/12/25
 */
public class SaveFeedbackRequester extends Requester<Feedback> {
    private Feedback feedback;

    public SaveFeedbackRequester(OnResponseListener onResponseListener, Feedback feedback) {
        super(onResponseListener);
        this.feedback = feedback;
    }

    @Override
    protected HashMap<String, String> getBody(HashMap<String, String> body) {
        body.put("ID", feedback.getID() + "");
        body.put("SuggestionContent", feedback.getSuggestionContent());
        body.put("Img1", feedback.getImg1());
        body.put("Img2", feedback.getImg2());

        return body;
    }

    @Override
    protected String getURLSuffix() {
        return "/app/Patient/v2/SaveMySuggestion";
    }

}
