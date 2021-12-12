package com.javaweb.qingyue.dao.impl;

import com.javaweb.qingyue.entity.Singer;
import com.javaweb.qingyue.entity.Song;
import com.javaweb.qingyue.util.DBconn;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SongDao {
    public Song getSongById(int songId) throws SQLException {
        if(songId == 0){
            return new Song();
        }
        DBconn.init();
        String sql = "select * from song where id = " + songId;
        ResultSet rs = DBconn.selectSql(sql);
        DBconn.closeConn();
        Song song = new Song();
        if(rs.next()){
            song.setId(rs.getInt("ID"));
            song.setName(rs.getString("Name"));
            song.setSinger(rs.getString("Singer"));
            song.setPicUrl(rs.getString("PicUrl"));
            song.setAlbum(rs.getString("Album"));
            song.setLyric(rs.getString("Lyric"));
        }
        return song;
    }
}
