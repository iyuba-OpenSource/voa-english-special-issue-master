package com.iyuba.voaEnglish.model.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WordSearchBean {

    @Override
    public String toString() {
        return "WordSearchBean{" +
                "wordId='" + wordId + '\'' +
                ", word='" + word + '\'' +
                ", def='" + def + '\'' +
                ", phAm='" + phAm + '\'' +
                ", phAmMp3='" + phAmMp3 + '\'' +
                ", titleData=" + titleData +
                ", phEn='" + phEn + '\'' +
                ", titleToal=" + titleToal +
                ", phEnMp3='" + phEnMp3 + '\'' +
                ", textData=" + textData +
                ", english=" + english +
                ", wordCn='" + wordCn + '\'' +
                ", textToal=" + textToal +
                '}';
    }

    @SerializedName("WordId")
    private String wordId;
    @SerializedName("Word")
    private String word;
    @SerializedName("def")
    private String def;
    @SerializedName("ph_am")
    private String phAm;
    @SerializedName("ph_am_mp3")
    private String phAmMp3;
    @SerializedName("titleData")
    private List<TitleDataDTO> titleData;
    @SerializedName("ph_en")
    private String phEn;
    @SerializedName("titleToal")
    private int titleToal;
    @SerializedName("ph_en_mp3")
    private String phEnMp3;
    @SerializedName("textData")
    private List<TextDataDTO> textData;
    @SerializedName("English")
    private boolean english;
    @SerializedName("WordCn")
    private String wordCn;
    @SerializedName("textToal")
    private int textToal;

    public String getWordId() {
        return wordId;
    }

    public void setWordId(String wordId) {
        this.wordId = wordId;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getDef() {
        return def;
    }

    public void setDef(String def) {
        this.def = def;
    }

    public String getPhAm() {
        return phAm;
    }

    public void setPhAm(String phAm) {
        this.phAm = phAm;
    }

    public String getPhAmMp3() {
        return phAmMp3;
    }

    public void setPhAmMp3(String phAmMp3) {
        this.phAmMp3 = phAmMp3;
    }

    public List<TitleDataDTO> getTitleData() {
        return titleData;
    }

    public void setTitleData(List<TitleDataDTO> titleData) {
        this.titleData = titleData;
    }

    public String getPhEn() {
        return phEn;
    }

    public void setPhEn(String phEn) {
        this.phEn = phEn;
    }

    public int getTitleToal() {
        return titleToal;
    }

    public void setTitleToal(int titleToal) {
        this.titleToal = titleToal;
    }

    public String getPhEnMp3() {
        return phEnMp3;
    }

    public void setPhEnMp3(String phEnMp3) {
        this.phEnMp3 = phEnMp3;
    }

    public List<TextDataDTO> getTextData() {
        return textData;
    }

    public void setTextData(List<TextDataDTO> textData) {
        this.textData = textData;
    }

    public boolean isEnglish() {
        return english;
    }

    public void setEnglish(boolean english) {
        this.english = english;
    }

    public String getWordCn() {
        return wordCn;
    }

    public void setWordCn(String wordCn) {
        this.wordCn = wordCn;
    }

    public int getTextToal() {
        return textToal;
    }

    public void setTextToal(int textToal) {
        this.textToal = textToal;
    }

    public static class TitleDataDTO {
        @SerializedName("Category")
        private String category;
        @SerializedName("Title_Cn")
        private String titleCn;
        @SerializedName("CreateTime")
        private String createTime;
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
        @SerializedName("ReadCount")
        private String readCount;

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getTitleCn() {
            return titleCn;
        }

        public void setTitleCn(String titleCn) {
            this.titleCn = titleCn;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
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

        public String getReadCount() {
            return readCount;
        }

        public void setReadCount(String readCount) {
            this.readCount = readCount;
        }
    }

    public static class TextDataDTO {
        @SerializedName("EndTiming")
        private String endTiming;
        @SerializedName("ParaId")
        private String paraId;
        @SerializedName("IdIndex")
        private String idIndex;
        @SerializedName("SoundText")
        private String soundText;
        @SerializedName("Sentence_cn")
        private String sentenceCn;
        @SerializedName("Timing")
        private String timing;
        @SerializedName("VoaId")
        private String voaId;
        @SerializedName("Sentence")
        private String sentence;

        public String getEndTiming() {
            return endTiming;
        }

        public void setEndTiming(String endTiming) {
            this.endTiming = endTiming;
        }

        public String getParaId() {
            return paraId;
        }

        public void setParaId(String paraId) {
            this.paraId = paraId;
        }

        public String getIdIndex() {
            return idIndex;
        }

        public void setIdIndex(String idIndex) {
            this.idIndex = idIndex;
        }

        public String getSoundText() {
            return soundText;
        }

        public void setSoundText(String soundText) {
            this.soundText = soundText;
        }

        public String getSentenceCn() {
            return sentenceCn;
        }

        public void setSentenceCn(String sentenceCn) {
            this.sentenceCn = sentenceCn;
        }

        public String getTiming() {
            return timing;
        }

        public void setTiming(String timing) {
            this.timing = timing;
        }

        public String getVoaId() {
            return voaId;
        }

        public void setVoaId(String voaId) {
            this.voaId = voaId;
        }

        public String getSentence() {
            return sentence;
        }

        public void setSentence(String sentence) {
            this.sentence = sentence;
        }
    }
}
