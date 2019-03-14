package com.jef.variablefoundation.user.requester;

import com.jef.variablefoundation.net.http.OnResponseListener;
import com.jef.variablefoundation.net.http.Requester;
import com.jef.variablefoundation.user.bean.MyInfo;

import java.util.HashMap;

/**
 * @Description描述:更新我的个人信息
 * @Author作者: xsy
 * @Date日期: 2018/12/25
 */
public class EditMyInfoRequester extends Requester<MyInfo> {
    private MyInfo myInfo;

    public EditMyInfoRequester(OnResponseListener onResponseListener, MyInfo myInfo) {
        super(onResponseListener);
        this.myInfo = myInfo;
    }

    @Override
    protected HashMap<String, String> getBody(HashMap<String, String> body) {
        body.put("EditPatientInfoType", myInfo.getEditPatientInfoType());
        body.put("UserName", myInfo.getUserName());
        body.put("PhoneNumber", myInfo.getPhoneNumber());
        body.put("Sex", myInfo.getSex() + "");
        body.put("PhotoPath", myInfo.getPhotoPath());
        body.put("Email", myInfo.getEmail());
        body.put("Birthday", myInfo.getBirthday());
        body.put("Province", myInfo.getProvince());
        body.put("City", myInfo.getCity());
        body.put("NativeProvince", myInfo.getNativeProvince());
        body.put("NativeCity", myInfo.getNativeCity());
        body.put("VerificationCode", myInfo.getVerificationCode());
        return body;
    }

    @Override
    protected String getURLSuffix() {
        return "/app/Patient/v2/EditInfo";
    }

}
