package com.javaweb.qingyue.entity;

public class Singer {
    private int id = 0;
    private String name = "";
    private String intro = "";
    private String picUrl = "\\usr\\source\\singerpic\\null.jpg";

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getIntro() { return intro; }

    public void setIntro(String intro) { this.intro = intro; }

    public String getPicUrl() { return picUrl; }

    public void setPicUrl(String picUrl) { this.picUrl = picUrl; }
}
