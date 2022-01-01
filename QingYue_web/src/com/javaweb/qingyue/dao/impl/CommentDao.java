package com.javaweb.qingyue.dao.impl;

import com.javaweb.qingyue.entity.Comment;
import com.javaweb.qingyue.util.DBconn;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CommentDao {
    public int getCommentsCountByPostId(int postId) {
        DBconn.init();
        String sql = "select count(*) as num from comment where postid = " + postId;
        ResultSet rs = DBconn.selectSql(sql);
        try {
            if(rs.next()){
                return rs.getInt("num");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public boolean addCommentToPost(int postId, int commenterId, String content){
        DBconn.init();
        String sql = "insert into comment(PostID, CommenterID, Content, Time) values(" + postId + ", " + commenterId + ", '" + content + "', STR_TO_DATE('"+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) +"','%Y-%m-%d %H:%i:%s'))";
        int i = DBconn.addUpdDel(sql);
        if(i > 0) return true;
        return false;
    }

    public Comment getCommentById(int commentId){
        DBconn.init();
        String sql = "select * from comment where id = " + commentId;
        ResultSet rs = DBconn.selectSql(sql);
        Comment comment = new Comment();
        try {
            if (rs.next()){
                comment.setCommenterId(commentId);
                comment.setContent(rs.getString("Content"));
                comment.setCommenterId(rs.getInt("CommenterID"));
                comment.setPostId(rs.getInt("PostID"));
                comment.setTime(rs.getTimestamp("Time").toString().substring(0, 19));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return comment;
        }
        return comment;
    }

    public List<Comment> getLatestCommentsByPostId(int postId) throws SQLException {
        DBconn.init();
        String sql = "select id from comment where postID = " + postId + " order by time desc limit 20";
        ResultSet rs = DBconn.selectSql(sql);
        List<Comment> commentList = new ArrayList<>();
        while(rs.next()){
            Comment comment = getCommentById(rs.getInt("ID"));
            commentList.add(comment);
        }
        return commentList;
    }

    public List<Comment> getCommentsBeforeCommentIdByPostId(int postId, int commentId) throws SQLException {
        DBconn.init();
        String sql = "select id from comment where postID = " + postId + " and id < " + commentId + " order by time desc limit 20";
        ResultSet rs = DBconn.selectSql(sql);
        List<Comment> commentList = new ArrayList<>();
        while(rs.next()){
            Comment comment = getCommentById(rs.getInt("ID"));
            commentList.add(comment);
        }
        return commentList;
    }
}
