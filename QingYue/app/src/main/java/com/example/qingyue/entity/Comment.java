package com.example.qingyue.entity;

import android.app.Application;

public class Comment  extends Application {

    private int id;
    private String name;
    private String comment;
    private String picString;
    private String time;


    public int getID() { return this.id; }

    public void setID(int id) { this.id = id; }

    public String getName() { return this.name; }

    public void setName(String name) { this.name = name; }

    public String getComment() { return this.comment; }

    public void setComment(String comment) { this.comment = comment; }

    public String getPicString() { return this.picString; }

    public void setPicString(String picString) { this.picString = picString; }

    //public void setPic(String picString) { this.pic = ImgIOJsonOutputUtils.base64ToBitmap(this.picString); }

    public String getTime() { return this.time; }

    public void setTime(String time) { this.time = time; }

    @Override
    public void onCreate(){

        id = 0;
        name = "";
        comment = "";
        picString = "";
        time = "";

        super.onCreate();
    }
}
