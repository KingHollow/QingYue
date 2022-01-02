package com.example.qingyue.entity;

import android.app.Application;
import android.graphics.Bitmap;

import com.example.qingyue.utils.ImgIOJsonOutputUtils;

public class Post extends Application {


    private int id;
    private int o_id;
    private int type = 0;
    private String poster;
    private String headpic;
    private Bitmap pic_h;
    private String time = "";
    private String content = "";
    private int likes = 0; // repost = null
    private int comments = 0;
    private int reposts = 0;
    private String repostedName = ""; //post = null
    private String repostedContent = "";
    private String cardImg = ""; //post may have
    private Bitmap pic_c;
    private String cardTitle = "";
    private String cardContent = "";

    public int getID() { return this.id; }

    public void setID(int id) { this.id = id; }

    public int get_o_ID() { return this.o_id; }

    public void set_o_ID(int id) { this.o_id = id; }

    public int getType() { return this.type; }

    public void setType(int type) { this.type = type; }

    public String getPoster() { return this.poster; }

    public void setPoster(String poster) { this.poster = poster; }

    public String getHeadpic() { return this.headpic; }

    public void setHeadpic(String headpic) { this.headpic = headpic; }

    public void setPic_h(String headpic) { this.pic_h = ImgIOJsonOutputUtils.base64ToBitmap(this.headpic); }

    public String getTime() { return this.time; }

    public void setTime(String time) { this.time = time; }

    public String getContent() { return this.content; }

    public void setContent(String content) { this.content = content; }

    public int getLikes() { return this.likes; }

    public void setLikes(int likes) { this.likes = likes; }

    public int getComments() { return this.comments; }

    public void setComments(int comments) { this.comments = comments; }

    public int getReposts() { return this.reposts; }

    public void setReposts(int reposts) { this.reposts = reposts; }

    public String getRepostedName() { return this.repostedName; }

    public void setRepostedName(String repostedName) { this.repostedName = repostedName; }

    public String getRepostedContent() { return this.repostedContent; }

    public void setRepostedContent(String repostedContent) { this.repostedContent = repostedContent; }

    public String getCardImg() { return this.cardImg; }

    public void setCardImg(String cardImg) { this.cardImg = cardImg; }

    public void setPic_c(String cardImg) { this.pic_c = ImgIOJsonOutputUtils.base64ToBitmap(this.cardImg); }

    public String getCardTitle() { return this.cardTitle; }

    public void setCardTitle(String cardTitle) { this.cardTitle = cardTitle; }

    public String getCardContent() { return this.cardContent; }

    public void setCardContent(String cardContent) { this.cardContent = cardContent; }

    @Override
    public void onCreate(){

        id = 0;
        type = 0;
        poster = "";
        headpic = "";
        time = "";
        content = "";
        likes = 0; // repost = null
        comments = 0;
        reposts = 0;
        repostedName = ""; //post = null
        repostedContent = "";
        cardImg = ""; //post may have
        cardTitle = "";
        cardContent = "";

        super.onCreate();
    }
}
