package com.javaweb.qingyue.entity;

public class Comment {
    private int id = 0;
    private int postId = 0;
    private int commenterId = 0;
    private String content = "";
    private String time = "";

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public int getPostId() { return  postId; }

    public void setPostId(int postId) { this.postId = postId; }

    public int getCommenterId() { return commenterId; }

    public void setCommenterId(int commenterId) { this.commenterId = commenterId; }

    public String getContent() { return content; }

    public void setContent(String content) { this.content = content; }
}
