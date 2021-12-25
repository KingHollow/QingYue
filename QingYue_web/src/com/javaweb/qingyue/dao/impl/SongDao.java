package com.javaweb.qingyue.dao.impl;

import com.javaweb.qingyue.entity.Singer;
import com.javaweb.qingyue.entity.Song;
import com.javaweb.qingyue.util.DBconn;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SongDao {
    public Song getSongById(int songId){
        if(songId == 0){
            return new Song();
        }
        DBconn.init();
        String sql = "select * from song where id = " + songId;
        ResultSet rs = DBconn.selectSql(sql);
        Song song = new Song();
        try {
            if(rs.next()){
                song.setId(rs.getInt("ID"));
                song.setName(rs.getString("Name"));
                song.setSinger(rs.getString("Singer"));
                song.setPicUrl(rs.getString("PicUrl"));
                song.setAlbum(rs.getString("Album"));
                song.setLyric(rs.getString("Lyric"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return song;
    }

    public Song getSongByName(String songName){
        if(songName.equals("")){
            return new Song();
        }
        DBconn.init();
        String sql = "select * from song where name = '" + songName + "'";
        ResultSet rs = DBconn.selectSql(sql);
        Song song = new Song();
        try {
            if(rs.next()){
                song.setId(rs.getInt("ID"));
                song.setName(rs.getString("Name"));
                song.setSinger(rs.getString("Singer"));
                song.setPicUrl(rs.getString("PicUrl"));
                song.setAlbum(rs.getString("Album"));
                song.setLyric(rs.getString("Lyric"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return song;
    }

    public Song getRelatedSongByPostId(int postId) {
        DBconn.init();
        String sql = "select * from r_post_song where post_id = " + postId;
        ResultSet rs = DBconn.selectSql(sql);
        Song song;
        try {
            if(rs.next()){
                song = getSongById(rs.getInt("song_id"));

                return song;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new Song();
    }
}
