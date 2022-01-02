package com.javaweb.qingyue.servlet;

import com.javaweb.qingyue.dao.UserDao;
import com.javaweb.qingyue.dao.impl.RecommendDao;
import com.javaweb.qingyue.dao.impl.UserDaoImpl;
import com.javaweb.qingyue.entity.Singer;
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

public class singerRecommendServlet extends HttpServlet {
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

            RecommendDao rd = new RecommendDao();
            List<Singer> singerList = rd.getSingerRecommendByUserId(user.getId());
            singerList.forEach(s -> {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("name", s.getName());
                try {
                    jsonObject.put("pic", ImgIOJsonOutputUtils.encodeImage(s.getPicUrl()));
                } catch (IOException e) {
                    e.printStackTrace();
                    jsonObject.put("pic", "");
                }
                jsonArray.add(jsonObject);
            });

            out.write(jsonArray.toString());
        }
    }
}
