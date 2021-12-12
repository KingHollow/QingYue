package com.javaweb.qingyue.dao.impl;

import com.javaweb.qingyue.util.DBconn;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PostDao {
    public int getPostCountByUserId(int userId) throws SQLException {
        DBconn.init();
        String sql = "select count(*) as num from post where authorid = " + userId;
        ResultSet rs = DBconn.selectSql(sql);
        if(rs.next()) {
            return rs.getInt("num");
        }
        DBconn.closeConn();
        return 0;
    }
}
