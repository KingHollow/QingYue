package com.javaweb.qingyue.dao.impl;

import com.javaweb.qingyue.entity.Singer;
import com.javaweb.qingyue.util.DBconn;

import java.sql.ResultSet;
import java.sql.SQLException;

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
}
