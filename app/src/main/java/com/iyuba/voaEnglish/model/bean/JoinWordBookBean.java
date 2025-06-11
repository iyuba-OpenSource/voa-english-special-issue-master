package com.iyuba.voaEnglish.model.bean;

import com.google.gson.annotations.SerializedName;

public class JoinWordBookBean {

    @Override
    public String toString() {
        return "JoinWordBookBean{" +
                "result=" + result +
                ", strWord='" + strWord + '\'' +
                '}';
    }

    @SerializedName("result")
    private int result;
    @SerializedName("strWord")
    private String strWord;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getStrWord() {
        return strWord;
    }

    public void setStrWord(String strWord) {
        this.strWord = strWord;
    }
}
