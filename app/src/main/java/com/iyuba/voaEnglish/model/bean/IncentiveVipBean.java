package com.iyuba.voaEnglish.model.bean;

import com.google.gson.annotations.SerializedName;

public class IncentiveVipBean {


    @SerializedName("result")
    private int result;
    @SerializedName("message")
    private String message;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
