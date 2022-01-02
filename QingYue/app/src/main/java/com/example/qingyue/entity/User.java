package com.example.qingyue.entity;

import android.app.Application;

public class User extends Application {


    private String username;


    public String getUserName() {
        return this.username;
    }

    public void setUserName(String username) {
        this.username = username;
    }

    @Override
    public void onCreate(){

        username = "";

        super.onCreate();
    }

//    public User(int userid, String username, String nickname, String password, String sex, String region, String signature) {
//        this.userid = userid;
//        this.username = username;
//        this.nickname = nickname;
//        this.password = password;
//        this.sex = sex;
//        this.region = region;
//        this.signature = signature;
//    }

//    public int getUserId() {
//        return userid;
//    }
//
//
//
//
//
//    public String getNickName() {
//        return nickname;
//    }
//
//    public void setNickName(String nickname) {
//        this.nickname = nickname;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    public String getSex() {
//        return sex;
//    }
//
//    public void setSex(String sex) {
//        this.sex = sex;
//    }
//
//    public String getRegion() {
//        return region;
//    }
//
//    public void setRegion(String region) {
//        this.region = region;
//    }
//
//    public String getSignature() {
//        return signature;
//    }
//
//    public void setSignature(String signature) {
//        this.signature = signature;
//    }
}

