package com.example.qingyue.entity;

public class RcmdUser {
    String headPicUrl;
    String name;
    String similarity;
    String labels;
    String singerLike;

    public String getSingerLike() {
        return singerLike;
    }

    public void setSingerLike(String singerLike) {
        this.singerLike = singerLike;
    }

    public String getHeadPicUrl() {
        return headPicUrl;
    }

    public void setHeadPicUrl(String headPicUrl) {
        this.headPicUrl = headPicUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSimilarity() {
        return similarity;
    }

    public void setSimilarity(String similarity) {
        this.similarity = similarity;
    }

    public String getLabels() {
        return labels;
    }

    public void setLabels(String labels) {
        this.labels = labels;
    }
}
