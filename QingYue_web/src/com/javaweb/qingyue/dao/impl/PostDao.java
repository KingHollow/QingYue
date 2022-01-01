package com.javaweb.qingyue.dao.impl;

import com.javaweb.qingyue.dao.UserDao;
import com.javaweb.qingyue.entity.Post;
import com.javaweb.qingyue.entity.Singer;
import com.javaweb.qingyue.entity.Song;
import com.javaweb.qingyue.entity.User;
import com.javaweb.qingyue.util.DBconn;
import javafx.geometry.Pos;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PostDao {
    public int getPostCountByUserId(int userId) throws SQLException {
        DBconn.init();
        String sql = "select count(*) as num from post where authorid = " + userId;
        ResultSet rs = DBconn.selectSql(sql);
        if(rs.next()) {
            return rs.getInt("num");
        }
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

    public boolean addLikeToPostById(int postId){
        DBconn.init();
        String sql = "update post set likes = likes + 1 where ID = " + postId;
        int i =DBconn.addUpdDel(sql);
        return i > 0;
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
                    post.setTime(rs.getTimestamp("Time").toString().substring(0, 19));
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
                    post.setTime(rs.getTimestamp("Time").toString().substring(0, 19));
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
                    post.setTime(rs.getTimestamp("Time").toString().substring(0, 19));
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
                    post.setTime(rs.getTimestamp("Time").toString().substring(0, 19));
                    post.setContent(rs.getString("Content"));
                    post.setLikes(rs.getInt("Likes"));
                    post.setComments(cd.getCommentsCountByPostId(postId));
                    post.setReposts(getRepostsCountByPostId(postId));
                    Post reposted = getPostById(rs.getInt("RepostedID"));
                    post.setRepostedId(reposted.getId());
                    post.setRepostedName(reposted.getPoster().getName());
                    post.setRepostedContent(reposted.getContent());
                    post.setCardTitle("");
                    post.setCardContent("");
                    post.setCardImgUrl("");
                }
            }

            return post;
        } catch (SQLException e) {
            e.printStackTrace();

            return post;
        }
    }

    public boolean addPost(int authorId, String content, int type, String relatedName){
        if (authorId <= 0) return false;
        DBconn.init();
        if(type == 1){
            SongDao sd = new SongDao();
            Song song = sd.getSongByName(relatedName);
            if (song.getId() == 0) return false;
            String sql1 = "insert into post(AuthorID, Content, Likes, Type, RepostedID, Time) values("+authorId+", '"+content+"', 0, 1, 0, STR_TO_DATE('"+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) +"','%Y-%m-%d %H:%i:%s'))";
            int postId = DBconn.addUpdDelWithId(sql1);
            if (postId == 0) return false;
            String sql2 = "insert into r_post_song(post_id, song_id) values("+postId+", "+song.getId()+")";
            DBconn.addUpdDel(sql2);
            return true;
        }else if(type == 2){
            SingerDao sd = new SingerDao();
            Singer singer = sd.getSingerByName(relatedName);
            if(singer.getId() == 0) return false;
            String sql1 = "insert into post(AuthorID, Content, Likes, Type, RepostedID, Time) values("+authorId+", '"+content+"', 0, 2, 0, STR_TO_DATE('"+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) +"','%Y-%m-%d %H:%i:%s'))";
            int postId = DBconn.addUpdDelWithId(sql1);
            if (postId == 0) return false;
            String sql2 = "insert into r_post_singer(post_id, singer_id) values("+postId+", "+singer.getId()+")";
            DBconn.addUpdDel(sql2);
            return true;
        }else if(type == 0){
            String sql = "insert into post(AuthorID, Content, Likes, Type, RepostedID, Time) values("+authorId+", '"+content+"', 0, 0, 0, STR_TO_DATE('"+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) +"','%Y-%m-%d %H:%i:%s'))";
            DBconn.addUpdDel(sql);
            return true;
        }
        return false;
    }

    public boolean addRepost(int authorId, String content, int repostedId){
        if (authorId <= 0) return false;
        DBconn.init();
        String sql = "insert into post(AuthorID, Content, Likes, Type, RepostedID, Time) values("+authorId+", '"+content+"', 0, 3, " + repostedId + ", STR_TO_DATE('"+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) +"','%Y-%m-%d %H:%i:%s'))";
        int i = DBconn.addUpdDel(sql);
        return i > 0;
    }

    public boolean deletePost(int postId){
        if (postId <= 0) return false;
        DBconn.init();
        String sql = "delete from post where ID = " + postId;
        int i = DBconn.addUpdDel(sql);
        return i > 0;
    }

    public List<Post> getLatestPostsByUserId(int userId) throws SQLException {
        DBconn.init();
        String sql = "select id from post where authorid = " + userId + " order by time desc limit 20";
        ResultSet rs = DBconn.selectSql(sql);
        List<Post> postList = new ArrayList<>();
        while(rs.next()){
            Post post = getPostById(rs.getInt("ID"));
            postList.add(post);
        }

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

        return postList;
    }

    public List<Post> getLatestPostsBySearch(String search) throws SQLException {
        DBconn.init();
        String sql = "select id from post where content like '%" + search + "%' order by time desc limit 20";
        ResultSet rs = DBconn.selectSql(sql);
        List<Post> postList = new ArrayList<>();
        while(rs.next()){
            Post post = getPostById(rs.getInt("ID"));
            postList.add(post);
        }

        return postList;
    }

    public List<Post> getPostsBeforePostIdBySearch(String search, int postId) throws SQLException {
        DBconn.init();
        String sql = "select id from post where content like '%" + search + "%' and id < " + postId + " order by time desc limit 20";
        ResultSet rs = DBconn.selectSql(sql);
        List<Post> postList = new ArrayList<>();
        UserDao ud = new UserDaoImpl();
        CommentDao cd = new CommentDao();
        while(rs.next()){
            Post post = getPostById(rs.getInt("ID"));
            postList.add(post);
        }

        return postList;
    }
}
