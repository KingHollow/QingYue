package com.javaweb.qingyue.entity;

public class Post {
    private int id = 0;
    private int type = 0;
    private User poster;
    private String time = "";
    private String content = "";
    private int likes = 0;
    private int comments = 0;
    private int reposts = 0;
    private int repostedId = 0;
    private String repostedName = "";
    private String repostedContent = "";
    private String cardImgUrl = "";
    private String cardTitle = "";
    private String cardContent = "";

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public int getType() { return type; }

    public void setType(int type) { this.type = type; }

    public User getPoster() { return poster; }

    public void setPoster(User poster) { this.poster = poster; }

    public String getTime() { return time; }

    public void setTime(String time) { this.time = time; }

    public String getContent() { return content; }

    public void  setContent(String content) { this.content = content; }

    public int getLikes() { return likes; }

    public void setLikes(int likes) { this.likes = likes; }

    public int getComments() { return  comments; }

    public void setComments(int comments) { this.comments = comments; }

    public int getReposts() { return reposts; }

    public void setReposts(int reposts) { this.reposts = reposts; }

    public int getRepostedId() { return repostedId; }

    public void setRepostedId(int repostedId) { this.repostedId = repostedId; }

    public String getRepostedName() { return repostedName; }

    public void setRepostedName(String repostedName) { this.repostedName = repostedName; }

    public String getRepostedContent() { return repostedContent; }

    public void setRepostedContent(String repostedContent) { this.repostedContent = repostedContent; }

    public String getCardTitle() { return cardTitle; }

    public void setCardTitle(String cardTitle) { this.cardTitle = cardTitle; }

    public String getCardContent() { return cardContent; }

    public void setCardContent(String cardContent) { this.cardContent = cardContent; }

    public String getCardImgUrl() { return cardImgUrl; }

    public void setCardImgUrl(String cardImgUrl) { this.cardImgUrl = cardImgUrl; }
}
