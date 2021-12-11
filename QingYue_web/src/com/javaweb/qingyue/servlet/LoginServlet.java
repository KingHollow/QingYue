package com.javaweb.qingyue.servlet;


import com.javaweb.qingyue.dao.UserDao;
import com.javaweb.qingyue.dao.impl.UserDaoImpl;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class LoginServlet extends HttpServlet { //需要继承HttpServlet  并重写doGet  doPost方法
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);  //将信息使用doPost方法执行   对应jsp页面中的form表单中的method
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try (PrintWriter out = response.getWriter()) {

            String name = request.getParameter("username"); //得到jsp页面传过来的参数
            String password = request.getParameter("password");

            UserDao ud = new UserDaoImpl();
            JSONObject jsonObject = new JSONObject();

            if(ud.login(name, password)){
                jsonObject.put("Result", "success");
            }else{
                jsonObject.put("Result", "failed");
            }

            out.write(jsonObject.toString());
        }
    }

}
