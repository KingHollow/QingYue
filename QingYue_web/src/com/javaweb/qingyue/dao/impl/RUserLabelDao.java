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
        return list;
    }

    public boolean chooseLabels(int userId, int label1, int label2, int label3){
        DBconn.init();
        String sql1 = "delete from r_user_label_choosed where user_id =" + userId;
        DBconn.addUpdDel(sql1);
        if(label1 > 0){
            String sql2 = "insert into r_user_label_choosed(user_id, label_id) values(" + userId + ", " + label1 + ")";
            int i = DBconn.addUpdDel(sql2);
            if( i <= 0) return false;
        }
        if(label2 > 0){
            String sql3 = "insert into r_user_label_choosed(user_id, label_id) values(" + userId + ", " + label2 + ")";
            int i = DBconn.addUpdDel(sql3);
            if( i <= 0) return false;
        }
        if(label3 > 0){
            String sql4 = "insert into r_user_label_choosed(user_id, label_id) values(" + userId + ", " + label3 + ")";
            int i = DBconn.addUpdDel(sql4);
            return i > 0;
        }
        return true;
    }
}
