package com.javaweb.qingyue.servlet;

import com.javaweb.qingyue.dao.UserDao;
import com.javaweb.qingyue.dao.impl.UserDaoImpl;
import com.javaweb.qingyue.entity.User;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class RegisterServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);  //将信息使用doPost方法执行   对应jsp页面中的form表单中的method
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response){
        try (PrintWriter out = response.getWriter()) {
            String username = request.getParameter("username"); //得到jsp页面传过来的参数
            String password = request.getParameter("password");
            String repassword = request.getParameter("repassword");

            UserDao ud = new UserDaoImpl();
            Map<String, String> params = new HashMap<>();
            JSONObject jsonObject = new JSONObject();

            if(!password.equals(repassword)){
                params.put("Result", "notequal");
            } else if(password.length() < 6){
                params.put("Result", "tooshort");
            } else {
                User user = new User();
                user.setName(username);
                user.setPassword(password);
                user.setNickname("");
                user.setRegion("");
                user.setSex("");
                user.setHeadshoturl("D:\\\\QingYue\\\\headshot\\\\null.jpg");
                user.setSignature("");
                if(ud.register(user)){
                    params.put("Result", "success");
                }else{
                    params.put("Result", "usernamerepeat");
                }
            }

            jsonObject.put("params", params);
            out.write(jsonObject.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
