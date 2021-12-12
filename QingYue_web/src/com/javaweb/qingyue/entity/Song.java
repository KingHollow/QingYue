package com.javaweb.qingyue.entity;

public class Song {
    private int id = 0;
    private String name = "";
    private String singer = "";
    private String album = "";
    private String lyric = "";
    private String picUrl = "\\usr\\source\\songpic\\null.jpg";

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getAlbum() { return album; }

    public void setAlbum(String album) { this.album = album; }

    public String getSinger() { return singer; }

    public void setSinger(String singer) { this.singer = singer; }

    public String getLyric() { return lyric; }

    public void setLyric(String lyric) { this.lyric = lyric; }

    public String getPicUrl() { return picUrl; }

    public void setPicUrl(String picUrl) { this.picUrl = picUrl; }
}
