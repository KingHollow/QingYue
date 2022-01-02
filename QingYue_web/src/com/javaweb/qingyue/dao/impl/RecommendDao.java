package com.javaweb.qingyue.dao.impl;

import com.javaweb.qingyue.entity.Singer;
import com.javaweb.qingyue.entity.Song;
import com.javaweb.qingyue.entity.User;
import com.javaweb.qingyue.util.DBconn;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecommendDao {
    public Map<Integer, String> getFriendRecommendByUserId(int userId){
        if(userId <= 0) return new HashMap<>();
        Map<Integer, String> result = new HashMap<>();
        DBconn.init();
        String sql = "select * from user_rcmd where user_id = " + userId;
        ResultSet rs = DBconn.selectSql(sql);

        try {
            while (rs.next()) {
                result.put(rs.getInt("rcmd_id"), rs.getString("similarity"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return result;
        }

        return result;
    }

    public List<Singer> getSingerRecommendByUserId(int userId){
        if(userId <= 0) return new ArrayList<>();
        List<Singer> result = new ArrayList<>();
        DBconn.init();
        String sql = "select * from singer_rcmd where user_id = " + userId;
        ResultSet rs = DBconn.selectSql(sql);
        SingerDao sd = new SingerDao();
        try {
           while (rs.next()){
               result.add(sd.getSingerById(rs.getInt("singer_id")));
           }
        } catch (SQLException e) {
            e.printStackTrace();
            return result;
        }
        return result;
    }

    public List<Song> getSongRecommendByUserId(int userId){
        if(userId <= 0) return new ArrayList<>();
        List<Song> result = new ArrayList<>();
        DBconn.init();
        String sql = "select * from song_rcmd where user_id = " + userId;
        ResultSet rs = DBconn.selectSql(sql);
        SongDao sd = new SongDao();
        try {
            while (rs.next()){
                result.add(sd.getSongById(rs.getInt("song_id")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return result;
        }
        return result;
    }
}