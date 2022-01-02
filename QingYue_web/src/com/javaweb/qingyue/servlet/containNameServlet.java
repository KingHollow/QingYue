package com.javaweb.qingyue.servlet;

import com.javaweb.qingyue.dao.UserDao;
import com.javaweb.qingyue.dao.impl.SingerDao;
import com.javaweb.qingyue.dao.impl.SongDao;
import com.javaweb.qingyue.dao.impl.UserDaoImpl;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class containNameServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);  //将信息使用doPost方法执行   对应jsp页面中的form表单中的method
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try (PrintWriter out = response.getWriter()) {

            String name = request.getParameter("name"); //得到jsp页面传过来的参数
            int type = Integer.parseInt(request.getParameter("type"));

            JSONObject jsonObject = new JSONObject();

            if (type == 1) {
                SongDao sd = new SongDao();
                if (sd.existSong(name)) {
                    jsonObject.put("Result", "success");
                } else {
                    jsonObject.put("Result", "failed");
                }
            } else if (type == 2) {
                SingerDao sd = new SingerDao();
                if (sd.existSinger(name)) {
                    jsonObject.put("Result", "success");
                } else {
                    jsonObject.put("Result", "failed");
                }
            } else {
                jsonObject.put("Result", "failed");
            }


            out.write(jsonObject.toString());
        }
    }
}
