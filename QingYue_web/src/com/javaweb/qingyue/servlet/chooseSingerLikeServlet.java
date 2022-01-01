package com.javaweb.qingyue.servlet;

import com.javaweb.qingyue.dao.UserDao;
import com.javaweb.qingyue.dao.impl.*;
import com.javaweb.qingyue.entity.Singer;
import com.javaweb.qingyue.entity.Song;
import com.javaweb.qingyue.entity.User;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.CompletableFuture;

public class chooseSingerLikeServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);  //将信息使用doPost方法执行   对应jsp页面中的form表单中的method
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try (PrintWriter out = response.getWriter()) {

            String username = request.getParameter("username");
            String singername = request.getParameter("singername");
            String like = request.getParameter("like");

            UserDao ud = new UserDaoImpl();
            User user = ud.getUserByUsername(username);

            SingerDao sd = new SingerDao();
            Singer singer = sd.getSingerByName(singername);

            JSONObject jsonObject = new JSONObject();

            RUserSingerLikeDao rusld = new RUserSingerLikeDao();
            if(like.equals("yes")){
                if(rusld.addSingerLike(user.getId(), singer.getId())){
                    jsonObject.put("Result", "success");
                    CompletableFuture.runAsync(() -> {
                        RUserLabelDao ruld = new RUserLabelDao();
                        ruld.updateValueByUserAndSinger(user.getId(), singer.getId(), 0.05f);
                    });
                }else {
                    jsonObject.put("Result", "failed");
                }
            }else if (like.equals("no")){
                if(rusld.removeSingerLike(user.getId(), singer.getId())){
                    jsonObject.put("Result", "success");
                    CompletableFuture.runAsync(() -> {
                        RUserLabelDao ruld = new RUserLabelDao();
                        ruld.updateValueByUserAndSinger(user.getId(), singer.getId(), -0.05f);
                    });
                }else{
                    jsonObject.put("Result", "failed");
                }
            }else{
                jsonObject.put("Result", "failed");
            }

            out.write(jsonObject.toString());
        }
    }
}
