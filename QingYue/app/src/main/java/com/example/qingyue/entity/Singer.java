package com.example.qingyue.entity;

import android.app.Application;
import android.graphics.Bitmap;

import com.example.qingyue.utils.ImgIOJsonOutputUtils;

public class Singer extends Application {

    private String name;
    private String intro;
    private String picString;
    private String like;


    public String getName() { return this.name; }

    public void setName(String name) { this.name = name; }

    public String getIntro() { return this.intro; }

    public void setIntro(String intro) { this.intro = intro; }

    public String getPicString() { return this.picString; }

    public void setPicString(String picString) { this.picString = picString; }

    //public void setPic(String picString) { this.pic = ImgIOJsonOutputUtils.base64ToBitmap(this.picString); }

    public String getLike() { return this.like; }

    public void setLike(String like) { this.like = like; }

    @Override
    public void onCreate(){

        name = "";
        intro = "";
        picString = "";
        like = "";

        super.onCreate();
    }
}
