package com.iyuba.voaEnglish.model.bean;

import com.google.gson.annotations.SerializedName;

public class ComposeBean {

    @Override
    public String toString() {
        return "ComposeBean{" +
                "result='" + result + '\'' +
                ", message='" + message + '\'' +
                ", url='" + url + '\'' +
                '}';
    }

    @SerializedName("result")
    private String result;
    @SerializedName("message")
    private String message;
    @SerializedName("URL")
    private String url;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
