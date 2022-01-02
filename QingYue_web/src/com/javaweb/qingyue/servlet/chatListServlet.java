package com.javaweb.qingyue.servlet;

import com.javaweb.qingyue.dao.UserDao;
import com.javaweb.qingyue.dao.impl.RUserFriendsDao;
import com.javaweb.qingyue.dao.impl.UserDaoImpl;
import com.javaweb.qingyue.entity.Friend;
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
import java.util.List;

public class chatListServlet extends HttpServlet {
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
            JSONArray jsonArray = new JSONArray();

            RUserFriendsDao rufd = new RUserFriendsDao();
            List<Friend> friendList = rufd.getFriendsById(user.getId());

            friendList.forEach(f -> {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("name", f.getUser().getNickname());
                try {
                    jsonObject.put("headshotUrl", ImgIOJsonOutputUtils.encodeImage(f.getUser().getHeadshoturl()));
                } catch (IOException e) {
                    e.printStackTrace();
                    jsonObject.put("headshotUrl", "");
                }
                jsonObject.put("chatContent", f.getChatContent());
                jsonObject.put("latestTime", f.getLatestTime());
                jsonArray.add(jsonObject);
            });

            out.write(jsonArray.toString());
        }
    }
}
