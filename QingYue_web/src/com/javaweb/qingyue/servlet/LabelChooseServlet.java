package com.javaweb.qingyue.servlet;

import com.javaweb.qingyue.dao.UserDao;
import com.javaweb.qingyue.dao.impl.RUserLabelDao;
import com.javaweb.qingyue.dao.impl.UserDaoImpl;
import com.javaweb.qingyue.entity.User;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Array;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class LabelChooseServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);  //将信息使用doPost方法执行   对应jsp页面中的form表单中的method
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try (PrintWriter out = response.getWriter()) {

            String username = request.getParameter("username"); //得到jsp页面传过来的参数
            int label1 = Integer.parseInt(request.getParameter("label1"));
            int label2 = Integer.parseInt(request.getParameter("label2"));
            int label3 = Integer.parseInt(request.getParameter("label3"));

            UserDao ud = new UserDaoImpl();
            User user = ud.getUserByUsername(username);

            RUserLabelDao ruld = new RUserLabelDao();
            JSONObject jsonObject = new JSONObject();
            try {
                if(ruld.chooseLabels(user.getId(), label1, label2, label3)){
                    jsonObject.put("Result", "success");
                }else{
                    jsonObject.put("Result", "failed");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                jsonObject.put("Result", "failed");
            }

            out.write(jsonObject.toString());
        }
    }
}
