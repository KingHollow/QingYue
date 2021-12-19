package com.javaweb.qingyue.dao.impl;

import com.javaweb.qingyue.dao.UserDao;
import com.javaweb.qingyue.entity.User;
import com.javaweb.qingyue.util.DBconn;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {
    @Override
    public boolean login(String name, String pwd) {
        boolean flag = false;
        try {
            DBconn.init();
            ResultSet rs = DBconn.selectSql("select * from user where Name='"+name+"' and Password='"+pwd+"'");
            while(rs.next()){
                if(rs.getString("Name").equals(name) && rs.getString("Password").equals(pwd)){
                    flag = true;
                }
            }
            DBconn.closeConn();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flag;

    }

    @Override
    public boolean register(User user) throws SQLException {
        boolean flag = false;
        DBconn.init();
        ResultSet j = DBconn.selectSql("select * from user where Name ='"+user.getName()+"'");
        if(j.next()){
            return flag;
        }
        int i =DBconn.addUpdDel("insert into user(Name,Password,Nickname,Sex,Region,Headshoturl,Signature) " +
                "values('"+user.getName()+"','"+user.getPassword()+"','"+user.getNickname()+"','"+user.getSex()+"','"+user.getRegion()+"','"+user.getHeadshoturl()+"','"+user.getSignature()+"')");
        if(i>0){
            flag = true;
        }
        DBconn.closeConn();
        return flag;

    }

    @Override
    public List<User> getUserAll() {
        List<User> list = new ArrayList<User>();
        try {
            DBconn.init();
            ResultSet rs = DBconn.selectSql("select * from user");
            while(rs.next()){
                User user = new User();
                user.setId(rs.getInt("ID"));
                user.setName(rs.getString("Name"));
                user.setPassword(rs.getString("Password"));
                user.setNickname(rs.getString("Nickname"));
                user.setSex(rs.getString("Sex"));
                user.setRegion(rs.getString("Region"));
                user.setHeadshoturl(rs.getString("Headshoturl"));
                user.setSignature(rs.getString("Signature"));
                list.add(user);
            }
            DBconn.closeConn();
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public User getUserById(int id) {
        User user = new User();
        String sql = "select * from user where ID ="+id;
        try {
            DBconn.init();
            ResultSet rs = DBconn.selectSql(sql);
            if (rs.next()){
                user.setId(rs.getInt("ID"));
                user.setName(rs.getString("Name"));
                user.setPassword(rs.getString("Password"));
                user.setNickname(rs.getString("Nickname"));
                user.setSex(rs.getString("Sex"));
                user.setRegion(rs.getString("Region"));
                user.setHeadshoturl(rs.getString("Headshoturl"));
                user.setSignature(rs.getString("Signature"));
                DBconn.closeConn();
                return user;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean infoModify(String username, String nickname, String sex, String region, String signature) {
        UserDao ud = new UserDaoImpl();
        User user = ud.getUserByUsername(username);
        return update(user.getId(), user.getName(), user.getPassword(), nickname, sex, region, user.getHeadshoturl(), signature);
    }

    public User getUserByUsername(String username) {
        User user = new User();
        String sql = "select * from user where Name ='"+username+"'";
        try {
            DBconn.init();
            ResultSet rs = DBconn.selectSql(sql);
            if (rs.next()){
                user.setId(rs.getInt("ID"));
                user.setName(rs.getString("Name"));
                user.setPassword(rs.getString("Password"));
                user.setNickname(rs.getString("Nickname") == null ? "" : rs.getString("Nickname"));
                user.setSex(rs.getString("Sex") == null ? "" : rs.getString("Sex"));
                user.setRegion(rs.getString("Region") == null ? "" : rs.getString("Region"));
                user.setHeadshoturl(rs.getString("Headshoturl"));
                user.setSignature(rs.getString("Signature") == null ? "" : rs.getString("Signature"));
                DBconn.closeConn();
                return user;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean delete(int id) {
        boolean flag = false;
        DBconn.init();
        String sql = "delete  from user where ID="+id;
        int i =DBconn.addUpdDel(sql);
        if(i>0){
            flag = true;
        }
        DBconn.closeConn();
        return flag;

    }

    @Override
    public boolean update(int id, String name, String password, String nickname, String sex, String region, String headshoturl, String signature) {
        boolean flag = false;
        DBconn.init();
        String sql ="update user set Name ='"+name
                +"' , Password ='"+password
                +"' , Nickname ='"+nickname
                +"' , Sex ='"+sex
                +"' , Region ='"+region
                +"' , Headshoturl ='"+headshoturl
                +"' , Signature ='"+signature+"' where ID = "+id;
        int i =DBconn.addUpdDel(sql);
        if(i>0){
            flag = true;
        }
        DBconn.closeConn();
        return flag;

    }
}
