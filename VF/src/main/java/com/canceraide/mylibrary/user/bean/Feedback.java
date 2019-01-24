package com.canceraide.mylibrary.user.bean;

/**
 * @Description描述:意见反馈
 * @Author作者: xsy
 * @Date日期: 2018/12/25
 */
public class Feedback {

    /**
     * ID : 0
     * SuggestionContent : string
     * Img1 : string
     * Img2 : string
     * ContactWay : string
     */

    private int ID;
    private String SuggestionContent;
    private String Img1;
    private String Img2;
    private String ContactWay;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getSuggestionContent() {
        return SuggestionContent;
    }

    public void setSuggestionContent(String SuggestionContent) {
        this.SuggestionContent = SuggestionContent;
    }

    public String getImg1() {
        return Img1;
    }

    public void setImg1(String Img1) {
        this.Img1 = Img1;
    }

    public String getImg2() {
        return Img2;
    }

    public void setImg2(String Img2) {
        this.Img2 = Img2;
    }

    public String getContactWay() {
        return ContactWay;
    }

    public void setContactWay(String ContactWay) {
        this.ContactWay = ContactWay;
    }
}
