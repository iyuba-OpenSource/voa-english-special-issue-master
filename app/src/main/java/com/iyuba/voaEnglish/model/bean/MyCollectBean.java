package com.iyuba.voaEnglish.model.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MyCollectBean {

    @Override
    public String toString() {
        return "MyCollectBean{" +
                "result=" + result +
                ", total=" + total +
                ", data=" + data +
                ", counts=" + counts +
                ", totalPage=" + totalPage +
                ", message='" + message + '\'' +
                '}';
    }

    @SerializedName("result")
    private int result;
    @SerializedName("total")
    private int total;
    @SerializedName("data")
    private List<DataDTO> data;
    @SerializedName("counts")
    private int counts;
    @SerializedName("totalPage")
    private int totalPage;
    @SerializedName("message")
    private String message;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<DataDTO> getData() {
        return data;
    }

    public void setData(List<DataDTO> data) {
        this.data = data;
    }

    public int getCounts() {
        return counts;
    }

    public void setCounts(int counts) {
        this.counts = counts;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static class DataDTO {
        @SerializedName("CollectDate")
        private String collectDate;
        @SerializedName("Category")
        private String category;
        @SerializedName("SentenceId")
        private String sentenceId;
        @SerializedName("pic")
        private String pic;
        @SerializedName("title")
        private String title;
        @SerializedName("voaid")
        private String voaid;
        @SerializedName("titlecn")
        private String titlecn;
        @SerializedName("Sentence")
        private String sentence;
        @SerializedName("SentenceFlg")
        private int sentenceFlg;
        @SerializedName("CreateTime")
        private String createTime;
        @SerializedName("Title")
        private String Title;
        @SerializedName("Sound")
        private String sound;
        @SerializedName("Pic")
        private String Pic;
        @SerializedName("Flag")
        private String flag;
        @SerializedName("Series")
        private String Series;
        @SerializedName("Type")
        private String type;
        @SerializedName("DescCn")
        private String descCn;
        @SerializedName("Title_cn")
        private String titleCn;
        @SerializedName("series")
        private String series;
        @SerializedName("Video")
        private String video;
        @SerializedName("CategoryName")
        private String categoryName;
        @SerializedName("topic")
        private String topic;
        @SerializedName("Id")
        private String id;
        @SerializedName("ReadCount")
        private String readCount;
        @SerializedName("hotflg")
        private String hotflg;

        public String getCollectDate() {
            return collectDate;
        }

        public void setCollectDate(String collectDate) {
            this.collectDate = collectDate;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getSentenceId() {
            return sentenceId;
        }

        public void setSentenceId(String sentenceId) {
            this.sentenceId = sentenceId;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getVoaid() {
            return voaid;
        }

        public void setVoaid(String voaid) {
            this.voaid = voaid;
        }

        public String getTitlecn() {
            return titlecn;
        }

        public void setTitlecn(String titlecn) {
            this.titlecn = titlecn;
        }

        public String getSentence() {
            return sentence;
        }

        public void setSentence(String sentence) {
            this.sentence = sentence;
        }

        public int getSentenceFlg() {
            return sentenceFlg;
        }

        public void setSentenceFlg(int sentenceFlg) {
            this.sentenceFlg = sentenceFlg;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getTitlez() {
            return Title;
        }

        public void setTitlez(String title) {
            this.Title = title;
        }

        public String getSound() {
            return sound;
        }

        public void setSound(String sound) {
            this.sound = sound;
        }

        public String getPicz() {
            return Pic;
        }

        public void setPicz(String pic) {
            this.Pic = pic;
        }

        public String getFlag() {
            return flag;
        }

        public void setFlag(String flag) {
            this.flag = flag;
        }

        public String getSeriesz() {
            return Series;
        }
        public void setSeriesz(String series) {
            this.Series = series;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
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

        public String getSeries() {
            return series;
        }

        public void setSeries(String series) {
            this.series = series;
        }

        public String getVideo() {
            return video;
        }

        public void setVideo(String video) {
            this.video = video;
        }

        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }

        public String getTopic() {
            return topic;
        }

        public void setTopic(String topic) {
            this.topic = topic;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getReadCount() {
            return readCount;
        }

        public void setReadCount(String readCount) {
            this.readCount = readCount;
        }

        public String getHotflg() {
            return hotflg;
        }

        public void setHotflg(String hotflg) {
            this.hotflg = hotflg;
        }
    }
}
