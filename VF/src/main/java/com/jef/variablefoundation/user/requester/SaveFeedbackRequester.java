package com.jef.variablefoundation.user.requester;

import com.jef.variablefoundation.net.http.OnResponseListener;
import com.jef.variablefoundation.net.http.Requester;
import com.jef.variablefoundation.user.bean.Feedback;

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
