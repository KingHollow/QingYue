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
        DBconn.closeConn();
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
        DBconn.closeConn();
        return singer;
    }
}
