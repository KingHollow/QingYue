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

public class InfoModifyServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);  //将信息使用doPost方法执行   对应jsp页面中的form表单中的method
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try (PrintWriter out = response.getWriter()) {

            String username = request.getParameter("username");
            String nickname = request.getParameter("nickname"); //得到jsp页面传过来的参数
            String sex = request.getParameter("sex");
            String region = request.getParameter("region");
            String signature = request.getParameter("signature");

            UserDao ud = new UserDaoImpl();
            JSONObject jsonObject = new JSONObject();

            if(ud.infoModify(username, nickname, sex, region, signature)){
                jsonObject.put("Result", "success");
            }else{
                jsonObject.put("Result", "failed");
            }

            out.write(jsonObject.toString());
        }
    }
}
