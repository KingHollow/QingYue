package com.javaweb.qingyue.servlet;

import com.javaweb.qingyue.dao.UserDao;
import com.javaweb.qingyue.dao.impl.*;
import com.javaweb.qingyue.entity.User;
import com.javaweb.qingyue.util.ImgIOJsonOutputUtils;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

public class MineInitServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);  //将信息使用doPost方法执行   对应jsp页面中的form表单中的method
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try (PrintWriter out = response.getWriter()) {

            String name = request.getParameter("username"); //得到jsp页面传过来的参数

            UserDao ud = new UserDaoImpl();
            RUserLabelDao ruld = new RUserLabelDao();
            RUserSingerLikeDao rusld = new RUserSingerLikeDao();
            RUserSongLikeDao rusold = new RUserSongLikeDao();
            PostDao pd = new PostDao();

            User user = ud.getUserByUsername(name);

            JSONObject jsonObject = new JSONObject();

            jsonObject.put("Nickname", user.getNickname());
            jsonObject.put("Sex", user.getSex());
            jsonObject.put("Region", user.getRegion());
            jsonObject.put("Signature", user.getSignature());
            jsonObject.put("Headshot", ImgIOJsonOutputUtils.encodeImage(user.getHeadshoturl()));
            System.out.println(2);
            jsonObject.put("Labels", ruld.labelChoosed(user.getId()));
            System.out.println(3);
            jsonObject.put("SingerLikedNum", rusld.getLikesCountByUserId(user.getId()));
            System.out.println(4);
            jsonObject.put("SingerPicLatestLiked", ImgIOJsonOutputUtils.encodeImage(rusld.getRecentLikeSingerByUserId(user.getId()).getPicUrl()));
            System.out.println(5);
            jsonObject.put("SongLikedNum", rusold.getLikesCountByUserId(user.getId()));
            System.out.println(6);
            jsonObject.put("SongPicLatestLiked", ImgIOJsonOutputUtils.encodeImage(rusold.getRecentLikeSongByUserId(user.getId()).getPicUrl()));
            System.out.println(7);
            jsonObject.put("PostNum", pd.getPostCountByUserId(user.getId()));
            out.write(jsonObject.toString());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
