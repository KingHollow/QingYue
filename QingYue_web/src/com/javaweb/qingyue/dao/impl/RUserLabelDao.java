package com.javaweb.qingyue.dao.impl;

import com.javaweb.qingyue.util.DBconn;

import javax.persistence.criteria.CriteriaBuilder;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class RUserLabelDao {
    public void initUser(int id){
        DBconn.init();
        try{
            for(int i = 1; i <= 20; i++){
                String sql = "insert into r_user_label(user_id, label_id, value) values("+id+", "+i+", 0.5)";
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

    public boolean chooseLabels(int userId, int label1, int label2, int label3) throws SQLException {
        DBconn.init();
        String sql = "select * from r_user_label_choosed where user_id = " + userId;
        ResultSet rs = DBconn.selectSql(sql);
        List<Integer> list1 = new ArrayList<>();
        while (rs.next()){
            list1.add(rs.getInt("label_id"));
        }

        CompletableFuture<List<List<Integer>>> completableFuture = CompletableFuture.supplyAsync(() -> {
            List<List<Integer>> result = new ArrayList<>();
            result.add(list1);
            List<Integer> list2 = Arrays.asList(label1, label2, label3);
            result.add(list2.stream()
                    .filter(i -> i>0)
                    .collect(Collectors.toList()));
            return result;
        });
        completableFuture.thenAcceptAsync(result -> {
            result.get(0).forEach(i -> {
                updateValueByUserAndLabel(userId, i, -0.2f);
            });
            result.get(1).forEach(i -> {
                updateValueByUserAndLabel(userId, i, 0.2f);
            });
        });

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

    public void updateValueByUserAndLabel(int userId, int labelId, float deltaValue){
        DBconn.init();
        String sql = "update r_user_label set value = value + " + deltaValue + " where user_id = " + userId + " and label_id = " + labelId;
        DBconn.addUpdDel(sql);
    }

    public void updateValueByUserAndSinger(int userId, int singerId, float weight){
        DBconn.init();
        String sql = "update r_user_label a, r_singer_label b set a.`value` = a.`value` + b.`value` * " + weight + " where a.label_id = b.label_id and a.user_id = " + userId + " and b.singer_id = " + singerId;
        DBconn.addUpdDel(sql);
    }

    public void updateValueByUserAndSong(int userId, int songId, float weight){
        DBconn.init();
        String sql = "update r_user_label a, r_song_label b set a.`value` = a.`value` + b.`value` * " + weight + " where a.label_id = b.label_id and a.user_id = " + userId + " and b.song_id = " + songId;
        DBconn.addUpdDel(sql);
    }
}
