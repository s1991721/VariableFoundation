package com.jef.variablefoundation.user.bean;

/**
 * Created by mr.lin on 2018/12/7
 * 用户信息
 */
public class UserInfo {

    private String UserToken;
    private String AccountId;
    private String AccountName;
    private String UserName;
    private String PhoneNumber;
    //0：男 1：女
    private String Sex;
    private String PhotoPath;
    private boolean HasPrimaryCondition;


    public String getUserToken() {
        return UserToken;
    }

    public void setUserToken(String userToken) {
        UserToken = userToken;
    }

    public String getAccountId() {
        return AccountId;
    }

    public void setAccountId(String accountId) {
        AccountId = accountId;
    }

    public String getAccountName() {
        return AccountName;
    }

    public void setAccountName(String accountName) {
        AccountName = accountName;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getSex() {
        return Sex;
    }

    public void setSex(String sex) {
        Sex = sex;
    }

    public String getPhotoPath() {
        return PhotoPath;
    }

    public void setPhotoPath(String photoPath) {
        PhotoPath = photoPath;
    }

    public boolean isHasPrimaryCondition() {
        return HasPrimaryCondition;
    }

    public void setHasPrimaryCondition(boolean hasPrimaryCondition) {
        HasPrimaryCondition = hasPrimaryCondition;
    }
}
