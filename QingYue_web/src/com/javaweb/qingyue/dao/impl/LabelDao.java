package com.javaweb.qingyue.dao.impl;

import com.javaweb.qingyue.util.DBconn;

import java.sql.ResultSet;

public class LabelDao {
    public String labelName(int labelId){
        DBconn.init();
        try {
            String sql = "select name from label where id = " + labelId;
            ResultSet rs = DBconn.selectSql(sql);
            if(rs.next()) return rs.getString("Name");
            return "";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
