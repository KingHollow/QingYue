package com.javaweb.qingyue.dao.impl;

import com.javaweb.qingyue.dao.UserDao;
import com.javaweb.qingyue.entity.Friend;
import com.javaweb.qingyue.util.DBconn;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RUserFriendsDao {
    public List<Friend> getFriendsById(int userId){
        if (userId <= 0) return new ArrayList<>();
        List<Friend> result = new ArrayList<>();
        DBconn.init();
        String sql = "select * from r_user_friends where user1_id = " + userId;
        ResultSet rs = DBconn.selectSql(sql);
        UserDao ud = new UserDaoImpl();
        try {
            while (rs.next()) {
                Friend friend = new Friend();
                friend.setUser(ud.getUserById(rs.getInt("user2_id")));
                friend.setChatContent(rs.getString("chat_content"));
                friend.setLatestTime(rs.getTimestamp("latest_time").toString().substring(0, 19));
                result.add(friend);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return result;
        }
        return result;
    }

    public boolean isFriend(int userId1, int userId2) {
        DBconn.init();
        String sql = "select * from r_user_friends where user1_id = " + userId1 + " and user2_id = " + userId2;
        ResultSet rs = DBconn.selectSql(sql);
        try {
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
