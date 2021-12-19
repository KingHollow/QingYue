package com.javaweb.qingyue.dao.impl;

import com.javaweb.qingyue.entity.Singer;
import com.javaweb.qingyue.entity.Song;
import com.javaweb.qingyue.util.DBconn;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RUserSongLikeDao {
    public int getLikesCountByUserId(int userId) throws SQLException {
        DBconn.init();
        String sql = "select count(*) as num from r_user_song_like where user_id = " + userId;
        ResultSet rs = DBconn.selectSql(sql);
        if(rs.next()){
            int num = rs.getInt("num");
            DBconn.closeConn();
            return num;
        }
        DBconn.closeConn();
        return 0;
    }

    public Song getRecentLikeSongByUserId(int userId) throws SQLException {
        DBconn.init();
        String sql = "select * from r_user_song_like where user_id = " + userId + " order by time desc limit 1";
        ResultSet rs = DBconn.selectSql(sql);
        int songId = 0;
        if(rs.next()){
            songId = rs.getInt("song_id");
        }
        SongDao sd = new SongDao();
        Song song = sd.getSongById(songId);
        DBconn.closeConn();
        return song;
    }

    public List<Song> getSongLikeList(int userId) throws SQLException {
        DBconn.init();
        String sql = "select * from r_user_song_like where user_id = " + userId + " order by time desc";
        List<Song> songList = new ArrayList<>();
        SongDao sd = new SongDao();
        ResultSet rs = DBconn.selectSql(sql);
        int songId;
        while(rs.next()){
            songId = rs.getInt("song_id");
            Song song = sd.getSongById(songId);
            songList.add(song);
        }
        DBconn.closeConn();;
        return songList;
    }

    public boolean addSongLike(int userId, int songId){
        DBconn.init();
        String sql1 = "select * from r_user_song_like where user_id = " + userId + " and song_id = " + songId;
        ResultSet rs = DBconn.selectSql(sql1);
        try {
            if(rs.next()){
                DBconn.closeConn();
                return false;
            }
            String sql2 = "insert into r_user_song_like(user_id, song_id, time) values("+userId+", "+songId+", STR_TO_DATE('"+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) +"','%Y-%m-%d %H:%i:%s'))";
            int i = DBconn.addUpdDel(sql2);
            if(i > 0){
                DBconn.closeConn();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        DBconn.closeConn();
        return false;
    }

    public boolean removeSongLike(int userId, int songId){
        DBconn.init();
        String sql1 = "select * from r_user_song_like where user_id = " + userId + " and song_id = " + songId;
        ResultSet rs = DBconn.selectSql(sql1);
        try {
            if(rs.next()){
                String sql2 = "delete from r_user_song_like where user_id = " + userId + " and song_id = " + songId;
                int i = DBconn.addUpdDel(sql2);
                if(i > 0){
                    DBconn.closeConn();
                    return true;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        DBconn.closeConn();
        return false;
    }
}
