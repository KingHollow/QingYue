package com.javaweb.qingyue.servlet;

import com.javaweb.qingyue.dao.UserDao;
import com.javaweb.qingyue.dao.impl.UserDaoImpl;
import com.javaweb.qingyue.entity.User;
import com.javaweb.qingyue.util.ImgIOJsonOutputUtils;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class HeadUpdateServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);  //将信息使用doPost方法执行   对应jsp页面中的form表单中的method
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try (PrintWriter out = response.getWriter()) {

            String username = request.getParameter("username");
            String headshot = request.getParameter("headshot");

            UserDao ud = new UserDaoImpl();
            User user = ud.getUserByUsername(username);
            JSONObject jsonObject = new JSONObject();

            boolean flag = true;
            String path = "/usr/source/headshot/"+user.getId()+".jpg";
            try {
                ImgIOJsonOutputUtils.saveImage(headshot, path);
            } catch (IOException e) {
                e.printStackTrace();
                flag = false;
            }

            if(flag){
                jsonObject.put("Result", "success");
                if(!user.getHeadshoturl().equals(path)){
                    ud.update(user.getId(), user.getName(), user.getPassword(), user.getNickname(), user.getSex(), user.getRegion(), path, user.getSignature());
                }
            }else{
                jsonObject.put("Result", "failed");
            }

            out.write(jsonObject.toString());
        }
    }
}
