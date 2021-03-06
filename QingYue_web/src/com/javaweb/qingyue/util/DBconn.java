package com.javaweb.qingyue.util;

import java.sql.*;

public class DBconn {
    static String url = "jdbc:mysql://139.224.189.178:3306/qingyue?useUnicode=true&characterEncoding=utf8"; // 数据库连接url
    static String username = "root"; // 数据库用户名
    static String password = "QingYue1109"; // 用户密码
    static Connection conn = null; // 初始化一个数据连接 conn
    static ResultSet rs = null; // 初始化一个结果集 rs
    static PreparedStatement ps =null; // 初始化一个PreparedStatement对象 ps
    public static void init(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(url,username,password);
        } catch (Exception e) {
            System.out.println("init [SQL驱动程序初始化失败！]");
            e.printStackTrace();
        }
    }
    public static int addUpdDel(String sql){
        int i = 0;
        try {
            PreparedStatement ps =  conn.prepareStatement(sql);
            i = ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("sql数据库增删改异常");
            e.printStackTrace();
        }

        return i;
    }

    public static int addUpdDelWithId(String sql){
        int i = 0;
        try {
            PreparedStatement ps =  conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if(rs.next()){
                i = rs.getInt(1);
                return i;
            }
        } catch (SQLException e) {
            System.out.println("sql数据库增删改异常");
            e.printStackTrace();
        }

        return i;
    }
    public static ResultSet selectSql(String sql){
        try {
            ps =  conn.prepareStatement(sql);
            rs =  ps.executeQuery(sql);
        } catch (SQLException e) {
            System.out.println("sql数据库查询异常");
            e.printStackTrace();
        }
        return rs;
    }
    public static void closeConn(){
        try {
            conn.close();
        } catch (SQLException e) {
            System.out.println("sql数据库关闭异常");
            e.printStackTrace();
        }
    }

}
