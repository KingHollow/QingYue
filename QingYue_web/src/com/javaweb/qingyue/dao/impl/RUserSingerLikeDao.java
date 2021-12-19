package com.javaweb.qingyue.dao.impl;

import com.javaweb.qingyue.entity.Singer;
import com.javaweb.qingyue.util.DBconn;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class RUserSingerLikeDao {
    public int getLikesCountByUserId(int userId) throws SQLException {
        DBconn.init();
        String sql = "select count(*) as num from r_user_singer_like where user_id = " + userId;
        ResultSet rs = DBconn.selectSql(sql);
        if(rs.next()){
            int num =  rs.getInt("num");
            DBconn.closeConn();
            return num;
        }
        DBconn.closeConn();
        return 0;
    }

    public Singer getRecentLikeSingerByUserId(int userId) throws SQLException {
        DBconn.init();
        String sql = "select * from r_user_singer_like where user_id = " + userId + " order by time desc limit 1";
        ResultSet rs = DBconn.selectSql(sql);
        int singerId = 0;
        if(rs.next()){
            singerId = rs.getInt("singer_id");
        }
        SingerDao sd = new SingerDao();
        Singer singer = sd.getSingerById(singerId);
        DBconn.closeConn();
        return singer;
    }

    public List<Singer> getSingerLikeList(int userId) throws SQLException {
        DBconn.init();
        String sql = "select * from r_user_singer_like where user_id = " + userId + " order by time desc";
        List<Singer> singerList = new ArrayList<>();
        SingerDao sd = new SingerDao();
        ResultSet rs = DBconn.selectSql(sql);
        int singerId;
        while(rs.next()){
            singerId = rs.getInt("singer_id");
            Singer singer = sd.getSingerById(singerId);
            singerList.add(singer);
        }
        DBconn.closeConn();;
        return singerList;
    }

    public boolean addSingerLike(int userId, int singerId){
        DBconn.init();
        String sql1 = "select * from r_user_singer_like where user_id = " + userId + " and singer_id = " + singerId;
        ResultSet rs = DBconn.selectSql(sql1);
        try {
            if(rs.next()){
                DBconn.closeConn();
                return false;
            }
            String sql2 = "insert into r_user_singer_like(user_id, singer_id, time) values("+userId+", "+singerId+", STR_TO_DATE('"+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) +"','%Y-%m-%d %H:%i:%s'))";
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

    public boolean removeSingerLike(int userId, int singerId){
        DBconn.init();
        String sql1 = "select * from r_user_singer_like where user_id = " + userId + " and singer_id = " + singerId;
        ResultSet rs = DBconn.selectSql(sql1);
        try {
            if(rs.next()){
                String sql2 = "delete from r_user_singer_like where user_id = " + userId + " and singer_id = " + singerId;
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
