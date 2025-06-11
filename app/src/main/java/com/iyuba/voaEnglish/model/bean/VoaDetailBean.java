package com.iyuba.voaEnglish.model.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VoaDetailBean {

    @Override
    public String toString() {
        return "VoaDetailBean{" +
                "total='" + total + '\'' +
                ", voaexam=" + voaexam +
                ", voawords=" + voawords +
                ", images='" + images + '\'' +
                ", voatext=" + voatext +
                '}';
    }

    @SerializedName("total")
    private String total;
    @SerializedName("voaexam")
    private List<VoaexamDTO> voaexam;
    @SerializedName("voawords")
    private List<VoawordsDTO> voawords;
    @SerializedName("Images")
    private String images;
    @SerializedName("voatext")
    private List<VoatextDTO> voatext;

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<VoaexamDTO> getVoaexam() {
        return voaexam;
    }

    public void setVoaexam(List<VoaexamDTO> voaexam) {
        this.voaexam = voaexam;
    }

    public List<VoawordsDTO> getVoawords() {
        return voawords;
    }

    public void setVoawords(List<VoawordsDTO> voawords) {
        this.voawords = voawords;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public List<VoatextDTO> getVoatext() {
        return voatext;
    }

    public void setVoatext(List<VoatextDTO> voatext) {
        this.voatext = voatext;
    }

    public static class VoaexamDTO {

        @Override
        public String toString() {
            return "VoaexamDTO{" +
                    "answer4='" + answer4 + '\'' +
                    ", indexId='" + indexId + '\'' +
                    ", answer='" + answer + '\'' +
                    ", answer2='" + answer2 + '\'' +
                    ", answer3='" + answer3 + '\'' +
                    ", answer1='" + answer1 + '\'' +
                    ", question='" + question + '\'' +
                    ", testType='" + testType + '\'' +
                    '}';
        }

        @SerializedName("Answer4")
        private String answer4;
        @SerializedName("IndexId")
        private String indexId;
        @SerializedName("Answer")
        private String answer;
        @SerializedName("Answer2")
        private String answer2;
        @SerializedName("Answer3")
        private String answer3;
        @SerializedName("Answer1")
        private String answer1;
        @SerializedName("Question")
        private String question;
        @SerializedName("TestType")
        private String testType;

        public String getAnswer4() {
            return answer4;
        }

        public void setAnswer4(String answer4) {
            this.answer4 = answer4;
        }

        public String getIndexId() {
            return indexId;
        }

        public void setIndexId(String indexId) {
            this.indexId = indexId;
        }

        public String getAnswer() {
            return answer;
        }

        public void setAnswer(String answer) {
            this.answer = answer;
        }

        public String getAnswer2() {
            return answer2;
        }

        public void setAnswer2(String answer2) {
            this.answer2 = answer2;
        }

        public String getAnswer3() {
            return answer3;
        }

        public void setAnswer3(String answer3) {
            this.answer3 = answer3;
        }

        public String getAnswer1() {
            return answer1;
        }

        public void setAnswer1(String answer1) {
            this.answer1 = answer1;
        }

        public String getQuestion() {
            return question;
        }

        public void setQuestion(String question) {
            this.question = question;
        }

        public String getTestType() {
            return testType;
        }

        public void setTestType(String testType) {
            this.testType = testType;
        }
    }

    public static class VoawordsDTO {

        @Override
        public String toString() {
            return "VoawordsDTO{" +
                    "answer4='" + answer4 + '\'' +
                    ", indexId='" + indexId + '\'' +
                    ", answer='" + answer + '\'' +
                    ", answer2='" + answer2 + '\'' +
                    ", answer3='" + answer3 + '\'' +
                    ", def='" + def + '\'' +
                    ", answer1='" + answer1 + '\'' +
                    ", words='" + words + '\'' +
                    ", question='" + question + '\'' +
                    ", testType='" + testType + '\'' +
                    '}';
        }

        @SerializedName("Answer4")
        private String answer4;
        @SerializedName("IndexId")
        private String indexId;
        @SerializedName("Answer")
        private String answer;
        @SerializedName("Answer2")
        private String answer2;
        @SerializedName("Answer3")
        private String answer3;
        @SerializedName("def")
        private String def;
        @SerializedName("Answer1")
        private String answer1;
        @SerializedName("words")
        private String words;
        @SerializedName("Question")
        private String question;
        @SerializedName("TestType")
        private String testType;

        public String getAnswer4() {
            return answer4;
        }

        public void setAnswer4(String answer4) {
            this.answer4 = answer4;
        }

        public String getIndexId() {
            return indexId;
        }

        public void setIndexId(String indexId) {
            this.indexId = indexId;
        }

        public String getAnswer() {
            return answer;
        }

        public void setAnswer(String answer) {
            this.answer = answer;
        }

        public String getAnswer2() {
            return answer2;
        }

        public void setAnswer2(String answer2) {
            this.answer2 = answer2;
        }

        public String getAnswer3() {
            return answer3;
        }

        public void setAnswer3(String answer3) {
            this.answer3 = answer3;
        }

        public String getDef() {
            return def;
        }

        public void setDef(String def) {
            this.def = def;
        }

        public String getAnswer1() {
            return answer1;
        }

        public void setAnswer1(String answer1) {
            this.answer1 = answer1;
        }

        public String getWords() {
            return words;
        }

        public void setWords(String words) {
            this.words = words;
        }

        public String getQuestion() {
            return question;
        }

        public void setQuestion(String question) {
            this.question = question;
        }

        public String getTestType() {
            return testType;
        }

        public void setTestType(String testType) {
            this.testType = testType;
        }
    }

    public static class VoatextDTO {

        @Override
        public String toString() {
            return "VoatextDTO{" +
                    "answer4='" + answer4 + '\'' +
                    ", indexId='" + indexId + '\'' +
                    ", answer2='" + answer2 + '\'' +
                    ", answer3='" + answer3 + '\'' +
                    ", def='" + def + '\'' +
                    ", answer1='" + answer1 + '\'' +
                    ", words='" + words + '\'' +
                    ", endY='" + endY + '\'' +
                    ", endX='" + endX + '\'' +
                    ", testType='" + testType + '\'' +
                    ", imgPath='" + imgPath + '\'' +
                    ", answer='" + answer + '\'' +
                    ", endTiming=" + endTiming +
                    ", paraId='" + paraId + '\'' +
                    ", idIndex='" + idIndex + '\'' +
                    ", sentenceCn='" + sentenceCn + '\'' +
                    ", imgWords='" + imgWords + '\'' +
                    ", startX='" + startX + '\'' +
                    ", question='" + question + '\'' +
                    ", timing=" + timing +
                    ", sentence='" + sentence + '\'' +
                    ", startY='" + startY + '\'' +
                    '}';
        }

        @SerializedName("Answer4")
        private String answer4;
        @SerializedName("IndexId")
        private String indexId;
        @SerializedName("Answer2")
        private String answer2;
        @SerializedName("Answer3")
        private String answer3;
        @SerializedName("def")
        private String def;
        @SerializedName("Answer1")
        private String answer1;
        @SerializedName("words")
        private String words;
        @SerializedName("End_y")
        private String endY;
        @SerializedName("End_x")
        private String endX;
        @SerializedName("TestType")
        private String testType;
        @SerializedName("ImgPath")
        private String imgPath;
        @SerializedName("Answer")
        private String answer;
        @SerializedName("EndTiming")
        private double endTiming;
        @SerializedName("ParaId")
        private String paraId;
        @SerializedName("IdIndex")
        private String idIndex;
        @SerializedName("sentence_cn")
        private String sentenceCn;
        @SerializedName("ImgWords")
        private String imgWords;
        @SerializedName("Start_x")
        private String startX;
        @SerializedName("Question")
        private String question;
        @SerializedName("Timing")
        private double timing;
        @SerializedName("Sentence")
        private String sentence;
        @SerializedName("Start_y")
        private String startY;

        public String getAnswer4() {
            return answer4;
        }

        public void setAnswer4(String answer4) {
            this.answer4 = answer4;
        }

        public String getIndexId() {
            return indexId;
        }

        public void setIndexId(String indexId) {
            this.indexId = indexId;
        }

        public String getAnswer2() {
            return answer2;
        }

        public void setAnswer2(String answer2) {
            this.answer2 = answer2;
        }

        public String getAnswer3() {
            return answer3;
        }

        public void setAnswer3(String answer3) {
            this.answer3 = answer3;
        }

        public String getDef() {
            return def;
        }

        public void setDef(String def) {
            this.def = def;
        }

        public String getAnswer1() {
            return answer1;
        }

        public void setAnswer1(String answer1) {
            this.answer1 = answer1;
        }

        public String getWords() {
            return words;
        }

        public void setWords(String words) {
            this.words = words;
        }

        public String getEndY() {
            return endY;
        }

        public void setEndY(String endY) {
            this.endY = endY;
        }

        public String getEndX() {
            return endX;
        }

        public void setEndX(String endX) {
            this.endX = endX;
        }

        public String getTestType() {
            return testType;
        }

        public void setTestType(String testType) {
            this.testType = testType;
        }

        public String getImgPath() {
            return imgPath;
        }

        public void setImgPath(String imgPath) {
            this.imgPath = imgPath;
        }

        public String getAnswer() {
            return answer;
        }

        public void setAnswer(String answer) {
            this.answer = answer;
        }

        public double getEndTiming() {
            return endTiming;
        }

        public void setEndTiming(double endTiming) {
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

        public String getSentenceCn() {
            return sentenceCn;
        }

        public void setSentenceCn(String sentenceCn) {
            this.sentenceCn = sentenceCn;
        }

        public String getImgWords() {
            return imgWords;
        }

        public void setImgWords(String imgWords) {
            this.imgWords = imgWords;
        }

        public String getStartX() {
            return startX;
        }

        public void setStartX(String startX) {
            this.startX = startX;
        }

        public String getQuestion() {
            return question;
        }

        public void setQuestion(String question) {
            this.question = question;
        }

        public double getTiming() {
            return timing;
        }

        public void setTiming(double timing) {
            this.timing = timing;
        }

        public String getSentence() {
            return sentence;
        }

        public void setSentence(String sentence) {
            this.sentence = sentence;
        }

        public String getStartY() {
            return startY;
        }

        public void setStartY(String startY) {
            this.startY = startY;
        }


    }


}
