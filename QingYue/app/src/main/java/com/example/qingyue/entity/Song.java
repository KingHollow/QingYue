package com.example.qingyue.entity;

import android.app.Application;
import android.graphics.Bitmap;

import com.example.qingyue.utils.ImgIOJsonOutputUtils;

public class Song extends Application {


    private String name;
    private String singer;
    private String album;
    private String lyric;
    private String picString;
    private String like;


    public String getName() { return this.name; }

    public void setName(String name) { this.name = name; }

    public String getAlbum() { return this.album; }

    public void setAlbum(String album) { this.album = album; }

    public String getSinger() { return this.singer; }

    public void setSinger(String singer) { this.singer = singer; }

    public String getLyric() { return this.lyric; }

    public void setLyric(String lyric) { this.lyric = lyric; }

    public String getPicString() { return this.picString; }

    public void setPicString(String picString) { this.picString = picString; }

    //public void setPic(String picString) { this.pic = ImgIOJsonOutputUtils.base64ToBitmap(this.picString); }

    public String getLike() { return this.like; }

    public void setLike(String like) { this.like = like; }

    @Override
    public void onCreate(){

        name = "";
        singer = "";
        album = "";
        lyric = "";
        picString = "";
        like = "";

        super.onCreate();
    }

}
