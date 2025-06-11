package com.iyuba.voaEnglish.model.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HotWordBean {

    @Override
    public String toString() {
        return "HotWordBean{" +
                "result='" + result + '\'' +
                ", data=" + data +
                ", message='" + message + '\'' +
                '}';
    }

    @SerializedName("result")
    private String result;
    @SerializedName("data")
    private List<String> data;
    @SerializedName("message")
    private String message;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
