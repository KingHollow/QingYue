package com.javaweb.qingyue.servlet;

import com.javaweb.qingyue.dao.UserDao;
import com.javaweb.qingyue.dao.impl.RUserFriendsDao;
import com.javaweb.qingyue.dao.impl.UserDaoImpl;
import com.javaweb.qingyue.entity.User;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class isFriendServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);  //将信息使用doPost方法执行   对应jsp页面中的form表单中的method
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try (PrintWriter out = response.getWriter()) {

            String username1 = request.getParameter("username1"); //得到jsp页面传过来的参数
            String username2 = request.getParameter("username2");

            UserDao ud = new UserDaoImpl();
            User user1 = ud.getUserByUsername(username1);
            User user2 = ud.getUserByUsername(username2);
            JSONObject jsonObject = new JSONObject();

            RUserFriendsDao rufd = new RUserFriendsDao();

            if(rufd.isFriend(user1.getId(), user2.getId())){
                jsonObject.put("Result", "success");
            }else{
                jsonObject.put("Result", "failed");
            }

            out.write(jsonObject.toString());
        }
    }
}
