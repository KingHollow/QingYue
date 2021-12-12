package com.javaweb.qingyue.dao.impl;

import com.javaweb.qingyue.util.DBconn;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RUserLabelDao {
    public void initUser(int id){
        DBconn.init();
        try{
            for(int i = 1; i <= 20; i++){
                String sql = "insert into r_user_label(user_id, label_id, value) values("+id+", "+i+", 0.0)";
                DBconn.addUpdDel(sql);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBconn.closeConn();
        }
    }

    public List<String> labelChoosed(int userId) throws SQLException {
        DBconn.init();
        List<String> list = new ArrayList<>();
        String sql = "select * from (r_user_label_choosed join label on label_id = label.id) where user_id = " + userId;
        ResultSet rs = DBconn.selectSql(sql);
        while(rs.next()){
            list.add(rs.getString("Name"));
        }
        while(list.size() < 3) list.add("");
        DBconn.closeConn();
        return list;
    }
}
