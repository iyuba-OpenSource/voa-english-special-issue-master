package com.iyuba.voaEnglish.model.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ClockRecordBean {

    @SerializedName("result")
    private int result;
    @SerializedName("data")
    private List<DataDTO> data;
    @SerializedName("canExchange")
    private String canExchange;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public List<DataDTO> getData() {
        return data;
    }

    public void setData(List<DataDTO> data) {
        this.data = data;
    }

    public String getCanExchange() {
        return canExchange;
    }

    public void setCanExchange(String canExchange) {
        this.canExchange = canExchange;
    }

    public static class DataDTO {
        @SerializedName("date")
        private String date;
        @SerializedName("num")
        private int num;
        @SerializedName("flg")
        private int flg;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public int getFlg() {
            return flg;
        }

        public void setFlg(int flg) {
            this.flg = flg;
        }
    }
}
