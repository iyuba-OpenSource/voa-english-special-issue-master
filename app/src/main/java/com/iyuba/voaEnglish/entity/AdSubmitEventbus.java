package com.iyuba.voaEnglish.entity;

import java.util.List;

public class AdSubmitEventbus {

    private String date_time;

    private String appid;
    private String device;
    private String deviceid;

    private String uid;
    private String packageStr;
    private String os;

    private List<Ads> ads;


    public String getDate_time() {
        return date_time;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(String deviceid) {
        this.deviceid = deviceid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPackageStr() {
        return packageStr;
    }

    public void setPackageStr(String packageStr) {
        this.packageStr = packageStr;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public List<Ads> getAds() {
        return ads;
    }

    public void setAds(List<Ads> ads) {
        this.ads = ads;
    }


    @Override
    public String toString() {
        return "AdSubmitEventbus{" +
                "date_time='" + date_time + '\'' +
                ", appid='" + appid + '\'' +
                ", device='" + device + '\'' +
                ", deviceid='" + deviceid + '\'' +
                ", uid='" + uid + '\'' +
                ", packageStr='" + packageStr + '\'' +
                ", os='" + os + '\'' +
                ", ads=" + ads +
                '}';
    }

    public static class Ads {

        private String platform;

        /**
         * 广告位,
         * banner 1,
         * 信息流 2,
         * 开屏 3,
         * 激励 4,
         * 插屏 5,
         * draw 6, 顺序1-6
         */
        private String ad_space;

        private String type;

        private String date_time;


        public String getDate_time() {
            return date_time;
        }

        public void setDate_time(String date_time) {
            this.date_time = date_time;
        }

        public String getPlatform() {
            return platform;
        }

        public void setPlatform(String platform) {
            this.platform = platform;
        }

        public String getAd_space() {
            return ad_space;
        }

        public void setAd_space(String ad_space) {
            this.ad_space = ad_space;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        @Override
        public String toString() {
            return "Ads{" +
                    "platform='" + platform + '\'' +
                    ", ad_space='" + ad_space + '\'' +
                    ", type='" + type + '\'' +
                    ", date_time='" + date_time + '\'' +
                    '}';
        }
    }
}
