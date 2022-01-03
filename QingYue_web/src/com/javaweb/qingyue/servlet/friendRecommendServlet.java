package com.javaweb.qingyue.servlet;

import com.javaweb.qingyue.dao.UserDao;
import com.javaweb.qingyue.dao.impl.RUserLabelDao;
import com.javaweb.qingyue.dao.impl.RUserSingerLikeDao;
import com.javaweb.qingyue.dao.impl.RecommendDao;
import com.javaweb.qingyue.dao.impl.UserDaoImpl;
import com.javaweb.qingyue.entity.User;
import com.javaweb.qingyue.util.ImgIOJsonOutputUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

public class friendRecommendServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);  //将信息使用doPost方法执行   对应jsp页面中的form表单中的method
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try (PrintWriter out = response.getWriter()) {

            String username = request.getParameter("username"); //得到jsp页面传过来的参数

            UserDao ud = new UserDaoImpl();
            User user = ud.getUserByUsername(username);


            JSONObject jsonObject = new JSONObject();
            jsonObject.put("headShot", ImgIOJsonOutputUtils.encodeImage(user.getHeadshoturl()));

            RecommendDao rd = new RecommendDao();
            Map<Integer, String> friendRcmd = rd.getFriendRecommendByUserId(user.getId());

            JSONArray jsonArray = new JSONArray();

            RUserLabelDao ruld = new RUserLabelDao();
            RUserSingerLikeDao rusld = new RUserSingerLikeDao();

            friendRcmd.forEach((i, s) -> {
                JSONObject item = new JSONObject();
                item.put("similarity", s);
                User friend = ud.getUserById(i);
                try {
                    item.put("labels", ruld.labelChoosed(i));
                } catch (SQLException e) {
                    e.printStackTrace();
                    item.put("labels", new ArrayList<>());
                }
                try {
                    item.put("singerLike", rusld.getRecentLikeSingerByUserId(i).getName());
                } catch (SQLException e) {
                    e.printStackTrace();
                    item.put("singerLike", "");
                }
                item.put("name", friend.getName());
                item.put("nickName", friend.getNickname());
                try {
                    item.put("pic", ImgIOJsonOutputUtils.encodeImage(friend.getHeadshoturl()));
                } catch (IOException e) {
                    e.printStackTrace();
                    item.put("pic", "");
                }
                jsonArray.add(item);
            });

            jsonObject.put("friendList", jsonArray);
            out.write(jsonObject.toString());
        }
    }
}
