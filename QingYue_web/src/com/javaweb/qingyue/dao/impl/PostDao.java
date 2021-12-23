package com.javaweb.qingyue.dao.impl;

import com.javaweb.qingyue.dao.UserDao;
import com.javaweb.qingyue.entity.Post;
import com.javaweb.qingyue.entity.Singer;
import com.javaweb.qingyue.entity.Song;
import com.javaweb.qingyue.util.DBconn;
import javafx.geometry.Pos;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PostDao {
    public int getPostCountByUserId(int userId) throws SQLException {
        DBconn.init();
        String sql = "select count(*) as num from post where authorid = " + userId;
        ResultSet rs = DBconn.selectSql(sql);
        if(rs.next()) {
            return rs.getInt("num");
        }
        DBconn.closeConn();
        return 0;
    }

    public int getRepostsCountByPostId(int postId) {
        DBconn.init();
        String sql = "select count(*) as num from post where repostid = " + postId;
        ResultSet rs = DBconn.selectSql(sql);
        try {
            if(rs.next()) {
                return rs.getInt("num");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public Post getPostById(int postId) {
        DBconn.init();
        String sql = "select * from post where id = " + postId;
        ResultSet rs = DBconn.selectSql(sql);
        Post post = new Post();
        UserDao ud = new UserDaoImpl();
        CommentDao cd = new CommentDao();
        try {
            if(rs.next()){
                if(rs.getInt("Type") == 0){
                    post.setId(postId);
                    post.setType(0);
                    post.setPoster(ud.getUserById(rs.getInt("AuthorID")));
                    post.setTime(rs.getTime("Time").toString());
                    post.setContent(rs.getString("Content"));
                    post.setLikes(rs.getInt("Likes"));
                    post.setComments(cd.getCommentsCountByPostId(postId));
                    post.setReposts(getRepostsCountByPostId(postId));
                    post.setRepostedId(0);
                    post.setRepostedName("");
                    post.setRepostedContent("");
                    post.setCardTitle("");
                    post.setCardContent("");
                    post.setCardImgUrl("");
                }else if(rs.getInt("Type") == 1){
                    post.setId(postId);
                    post.setType(1);
                    post.setPoster(ud.getUserById(rs.getInt("AuthorID")));
                    post.setTime(rs.getTime("Time").toString());
                    post.setContent(rs.getString("Content"));
                    post.setLikes(rs.getInt("Likes"));
                    post.setComments(cd.getCommentsCountByPostId(postId));
                    post.setReposts(getRepostsCountByPostId(postId));
                    post.setRepostedId(0);
                    post.setRepostedName("");
                    post.setRepostedContent("");
                    SongDao sd = new SongDao();
                    Song song = sd.getRelatedSongByPostId(postId);
                    post.setCardTitle(song.getName());
                    post.setCardContent(song.getSinger());
                    post.setCardImgUrl(song.getPicUrl());
                }else if(rs.getInt("Type") == 2){
                    post.setId(postId);
                    post.setType(2);
                    post.setPoster(ud.getUserById(rs.getInt("AuthorID")));
                    post.setTime(rs.getTime("Time").toString());
                    post.setContent(rs.getString("Content"));
                    post.setLikes(rs.getInt("Likes"));
                    post.setComments(cd.getCommentsCountByPostId(postId));
                    post.setReposts(getRepostsCountByPostId(postId));
                    post.setRepostedId(0);
                    post.setRepostedName("");
                    post.setRepostedContent("");
                    SingerDao sd = new SingerDao();
                    Singer singer = sd.getRelatedSingerByPostId(postId);
                    post.setCardTitle(singer.getName());
                    post.setCardContent("");
                    post.setCardImgUrl(singer.getPicUrl());
                }else if(rs.getInt("Type") == 3){
                    post.setId(postId);
                    post.setType(3);
                    post.setPoster(ud.getUserById(rs.getInt("AuthorID")));
                    post.setTime(rs.getTime("Time").toString());
                    post.setContent(rs.getString("Content"));
                    post.setLikes(rs.getInt("Likes"));
                    post.setComments(cd.getCommentsCountByPostId(postId));
                    post.setReposts(getRepostsCountByPostId(postId));
                    post.setRepostedId(rs.getInt("RepostID"));
                    post.setRepostedName(getPostById(rs.getInt("RepostID")).getPoster().getName());
                    post.setRepostedContent(getPostById(rs.getInt("RepostID")).getContent());
                    post.setCardTitle("");
                    post.setCardContent("");
                    post.setCardImgUrl("");
                }
            }
            DBconn.closeConn();
            return post;
        } catch (SQLException e) {
            e.printStackTrace();
            DBconn.closeConn();
            return post;
        }
    }

    public List<Post> getLatestPostsByUserId(int userId) throws SQLException {
        DBconn.init();
        String sql = "select id from post where authorid = " + userId + " order by time desc limit 20";
        ResultSet rs = DBconn.selectSql(sql);
        List<Post> postList = new ArrayList<>();
        UserDao ud = new UserDaoImpl();
        CommentDao cd = new CommentDao();
        while(rs.next()){
            Post post = getPostById(rs.getInt("ID"));
            postList.add(post);
        }
        DBconn.closeConn();
        return postList;
    }

    public List<Post> getPostsBeforePostIdByUserId(int userId, int postId) throws SQLException {
        DBconn.init();
        String sql = "select id from post where authorid = " + userId + " and id < " + postId + " order by time desc limit 20";
        ResultSet rs = DBconn.selectSql(sql);
        List<Post> postList = new ArrayList<>();
        UserDao ud = new UserDaoImpl();
        CommentDao cd = new CommentDao();
        while(rs.next()){
            Post post = getPostById(rs.getInt("ID"));
            postList.add(post);
        }
        DBconn.closeConn();
        return postList;
    }
}
