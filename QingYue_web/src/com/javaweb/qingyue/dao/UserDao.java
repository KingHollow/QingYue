package com.javaweb.qingyue.dao;


import com.javaweb.qingyue.entity.User;

import java.sql.SQLException;
import java.util.List;

public interface UserDao {
    public boolean login(String name,String password);//登录
    public boolean register(User user) throws SQLException;//注册
    public List<User> getUserAll();//返回用户信息集合
    public boolean delete(int id) ;//根据id删除用户
    public boolean update(int id, String name, String password, String nickname, String sex, String region, String headshoturl, String signature) ;//更新用户信息
    public User getUserByUsername(String username);
    public User getUserById(int id);
}
