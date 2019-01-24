package com.canceraide.mylibrary.user.bean;

/**
 * @Description描述:上传图片
 * @Author作者: xsy
 * @Date日期: 2018/12/25
 */
public class UploadImgInfo {

    /**
     * UploadFileName : string
     * SavePath : string
     * FileUrl : string
     */

    private String UploadFileName;
    private String SavePath;
    private String FileUrl;

    public String getUploadFileName() {
        return UploadFileName;
    }

    public void setUploadFileName(String UploadFileName) {
        this.UploadFileName = UploadFileName;
    }

    public String getSavePath() {
        return SavePath;
    }

    public void setSavePath(String SavePath) {
        this.SavePath = SavePath;
    }

    public String getFileUrl() {
        return FileUrl;
    }

    public void setFileUrl(String FileUrl) {
        this.FileUrl = FileUrl;
    }
}
