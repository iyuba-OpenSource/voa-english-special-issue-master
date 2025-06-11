package com.iyuba.voaEnglish.util.ad;

import android.view.View;

import com.yd.saas.common.pojo.YdNativePojo;

public class BannerAd {

    private String type;

    private String key;

    private View view;

    private YdNativePojo ydNativePojo;

    private int requestCount = -1;

    private int returnCount = -1;

    private int clickCount = -1;

    /**
     * 平台, 百度1, 流量汇2, 穿山甲3, 快手4, 顺序1-4
     */
    private int platform;


    public int getPlatform() {
        return platform;
    }

    public void setPlatform(int platform) {
        this.platform = platform;
    }

    public int getRequestCount() {
        return requestCount;
    }

    public void setRequestCount(int requestCount) {
        this.requestCount = requestCount;
    }

    public int getReturnCount() {
        return returnCount;
    }

    public void setReturnCount(int returnCount) {
        this.returnCount = returnCount;
    }

    public int getClickCount() {
        return clickCount;
    }

    public void setClickCount(int clickCount) {
        this.clickCount = clickCount;
    }

    public YdNativePojo getYdNativePojo() {
        return ydNativePojo;
    }

    public void setYdNativePojo(YdNativePojo ydNativePojo) {
        this.ydNativePojo = ydNativePojo;
    }

    public BannerAd(String type, String key) {
        this.type = type;
        this.key = key;
    }


    public BannerAd(String type, String key, int platform) {
        this.type = type;
        this.key = key;
        this.platform = platform;
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
