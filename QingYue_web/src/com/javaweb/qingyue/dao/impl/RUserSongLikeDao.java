package com.javaweb.qingyue.dao.impl;

import com.javaweb.qingyue.entity.Singer;
import com.javaweb.qingyue.entity.Song;
import com.javaweb.qingyue.util.DBconn;

import java.sql.ResultSet;
import java.sql.SQLException;

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
}
