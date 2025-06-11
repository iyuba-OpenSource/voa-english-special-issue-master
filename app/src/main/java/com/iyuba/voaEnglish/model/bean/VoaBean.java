package com.iyuba.voaEnglish.model.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VoaBean {

    @Override
    public String toString() {
        return "VoaBean{" +
                "total='" + total + '\'' +
                ", data=" + data +
                '}';
    }

    @SerializedName("total")
    private String total;
    @SerializedName("data")
    private List<DataDTO> data;

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<DataDTO> getData() {
        return data;
    }

    public void setData(List<DataDTO> data) {
        this.data = data;
    }

    public static class DataDTO {

        @Override
        public String toString() {
            return "DataDTO{" +
                    "creatTime='" + creatTime + '\'' +
                    ", category='" + category + '\'' +
                    ", title='" + title + '\'' +
                    ", texts=" + texts +
                    ", sound='" + sound + '\'' +
                    ", pic='" + pic + '\'' +
                    ", voaId='" + voaId + '\'' +
                    ", pagetitle='" + pagetitle + '\'' +
                    ", url='" + url + '\'' +
                    ", descCn='" + descCn + '\'' +
                    ", titleCn='" + titleCn + '\'' +
                    ", video='" + video + '\'' +
                    ", publishTime='" + publishTime + '\'' +
                    ", hotFlg='" + hotFlg + '\'' +
                    ", introDesc='" + introDesc + '\'' +
                    ", readCount='" + readCount + '\'' +
                    '}';
        }

        @SerializedName("CreatTime")
        private String creatTime;
        @SerializedName("Category")
        private String category;
        @SerializedName("Title")
        private String title;
        @SerializedName("Texts")
        private int texts;
        @SerializedName("Sound")
        private String sound;
        @SerializedName("Pic")
        private String pic;
        @SerializedName("VoaId")
        private String voaId;
        @SerializedName("Pagetitle")
        private String pagetitle;
        @SerializedName("Url")
        private String url;
        @SerializedName("DescCn")
        private String descCn;
        @SerializedName("Title_cn")
        private String titleCn;
        @SerializedName("Video")
        private String video;
        @SerializedName("PublishTime")
        private String publishTime;
        @SerializedName("HotFlg")
        private String hotFlg;
        @SerializedName("IntroDesc ")
        private String introDesc;
        @SerializedName("ReadCount")
        private String readCount;

        public String getCreatTime() {
            return creatTime;
        }

        public void setCreatTime(String creatTime) {
            this.creatTime = creatTime;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getTexts() {
            return texts;
        }

        public void setTexts(int texts) {
            this.texts = texts;
        }

        public String getSound() {
            return sound;
        }

        public void setSound(String sound) {
            this.sound = sound;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public String getVoaId() {
            return voaId;
        }

        public void setVoaId(String voaId) {
            this.voaId = voaId;
        }

        public String getPagetitle() {
            return pagetitle;
        }

        public void setPagetitle(String pagetitle) {
            this.pagetitle = pagetitle;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getDescCn() {
            return descCn;
        }

        public void setDescCn(String descCn) {
            this.descCn = descCn;
        }

        public String getTitleCn() {
            return titleCn;
        }

        public void setTitleCn(String titleCn) {
            this.titleCn = titleCn;
        }

        public String getVideo() {
            return video;
        }

        public void setVideo(String video) {
            this.video = video;
        }

        public String getPublishTime() {
            return publishTime;
        }

        public void setPublishTime(String publishTime) {
            this.publishTime = publishTime;
        }

        public String getHotFlg() {
            return hotFlg;
        }

        public void setHotFlg(String hotFlg) {
            this.hotFlg = hotFlg;
        }

        public String getIntroDesc() {
            return introDesc;
        }

        public void setIntroDesc(String introDesc) {
            this.introDesc = introDesc;
        }

        public String getReadCount() {
            return readCount;
        }

        public void setReadCount(String readCount) {
            this.readCount = readCount;
        }
    }
}
