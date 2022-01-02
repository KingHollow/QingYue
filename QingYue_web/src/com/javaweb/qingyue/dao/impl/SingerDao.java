package com.javaweb.qingyue.dao.impl;

import com.javaweb.qingyue.entity.Singer;
import com.javaweb.qingyue.util.DBconn;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SingerDao {
    public Singer getSingerById(int singerId){
        if(singerId == 0){
            return new Singer();
        }
        DBconn.init();
        String sql = "select * from singer where id = " + singerId;
        ResultSet rs = DBconn.selectSql(sql);
        Singer singer = new Singer();
        try {
            if(rs.next()){
                singer.setId(rs.getInt("ID"));
                singer.setName(rs.getString("Name"));
                singer.setIntro(rs.getString("Intro"));
                singer.setPicUrl(rs.getString("PicUrl"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return singer;
    }

    public Singer getSingerByName(String singerName){
        if(singerName.equals("")){
            return new Singer();
        }
        DBconn.init();
        String sql = "select * from singer where name = '" + singerName + "'";
        ResultSet rs = DBconn.selectSql(sql);
        Singer singer = new Singer();
        try {
            if(rs.next()){
                singer.setId(rs.getInt("ID"));
                singer.setName(rs.getString("Name"));
                singer.setIntro(rs.getString("Intro"));
                singer.setPicUrl(rs.getString("PicUrl"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return singer;
    }

    public Singer getRelatedSingerByPostId(int postId){
        DBconn.init();
        String sql = "select * from r_post_singer where post_id = " + postId;
        ResultSet rs = DBconn.selectSql(sql);
        Singer singer;
        try {
            if(rs.next()){
                singer = getSingerById(rs.getInt("singer_id"));

                return singer;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new Singer();
    }

    public boolean existSinger(String name){
        DBconn.init();
        String sql = "select * from singer where name = '" + name + "'";
        ResultSet rs = DBconn.selectSql(sql);
        try {
            if (rs.next()) return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }
}
