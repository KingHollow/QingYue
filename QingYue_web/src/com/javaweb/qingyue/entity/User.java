package com.javaweb.qingyue.entity;

public class User {
    private int id = 0;
    private String name = "";
    private String password = "";
    private String nickname = "";
    private String sex = "";
    private String region = "";
    private String headshoturl = "";
    private String signature;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getHeadshoturl() {
        return headshoturl;
    }

    public void setHeadshoturl(String headshoturl) {
        this.headshoturl = headshoturl;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
}
