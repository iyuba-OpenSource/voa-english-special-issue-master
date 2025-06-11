package com.iyuba.voaEnglish.model.bean;

import com.google.gson.annotations.SerializedName;

public class CollectBean {

    @Override
    public String toString() {
        return "CollectBean{" +
                "result=" + result +
                ", type=" + type +
                ", topic=" + topic +
                ", msg=" + msg +
                '}';
    }

    @SerializedName("result")
    private int result;


    @SerializedName("type")
    private String type;


    @SerializedName("topic")
    private String topic;

    @SerializedName("msg")
    private String msg;


    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
