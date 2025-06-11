package com.iyuba.voaEnglish.model.bean;

import com.google.gson.annotations.SerializedName;

public class LogLoginBean {
    @Override
    public String toString() {
        return "LogLoginBean{" +
                "amount='" + amount + '\'' +
                ", mobile='" + mobile + '\'' +
                ", message='" + message + '\'' +
                ", result='" + result + '\'' +
                ", uid=" + uid +
                ", isteacher='" + isteacher + '\'' +
                ", expireTime=" + expireTime +
                ", money='" + money + '\'' +
                ", credits=" + credits +
                ", jiFen=" + jiFen +
                ", vipStatus='" + vipStatus + '\'' +
                ", imgSrc='" + imgSrc + '\'' +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                '}';
    }

    @SerializedName("Amount")
    private String amount;
    @SerializedName("mobile")
    private String mobile;
    @SerializedName("message")
    private String message;
    @SerializedName("result")
    private String result;
    @SerializedName("uid")
    private int uid;
    @SerializedName("isteacher")
    private String isteacher;
    @SerializedName("expireTime")
    private int expireTime;
    @SerializedName("money")
    private String money;
    @SerializedName("credits")
    private int credits;
    @SerializedName("jiFen")
    private int jiFen;
    @SerializedName("vipStatus")
    private String vipStatus;
    @SerializedName("imgSrc")
    private String imgSrc;
    @SerializedName("email")
    private String email;
    @SerializedName("username")
    private String username;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getIsteacher() {
        return isteacher;
    }

    public void setIsteacher(String isteacher) {
        this.isteacher = isteacher;
    }

    public int getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(int expireTime) {
        this.expireTime = expireTime;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public int getJiFen() {
        return jiFen;
    }

    public void setJiFen(int jiFen) {
        this.jiFen = jiFen;
    }

    public String getVipStatus() {
        return vipStatus;
    }

    public void setVipStatus(String vipStatus) {
        this.vipStatus = vipStatus;
    }

    public String getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
