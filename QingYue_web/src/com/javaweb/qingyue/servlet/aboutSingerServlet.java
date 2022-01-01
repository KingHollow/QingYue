package com.javaweb.qingyue.servlet;

import com.javaweb.qingyue.dao.UserDao;
import com.javaweb.qingyue.dao.impl.RUserSingerLikeDao;
import com.javaweb.qingyue.dao.impl.SingerDao;
import com.javaweb.qingyue.dao.impl.UserDaoImpl;
import com.javaweb.qingyue.entity.Singer;
import com.javaweb.qingyue.entity.User;
import com.javaweb.qingyue.util.ImgIOJsonOutputUtils;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class aboutSingerServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);  //将信息使用doPost方法执行   对应jsp页面中的form表单中的method
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try (PrintWriter out = response.getWriter()) {

            String username = request.getParameter("username"); //得到jsp页面传过来的参数
            String singername = request.getParameter("singer");

            UserDao ud = new UserDaoImpl();
            User user = ud.getUserByUsername(username);

            SingerDao sd = new SingerDao();
            Singer singer = sd.getSingerByName(singername);

            RUserSingerLikeDao rusld = new RUserSingerLikeDao();

            JSONObject jsonObject = new JSONObject();

            jsonObject.put("name", singer.getName());
            if(rusld.haveLikeRelation(user.getId(), singer.getId())){
                jsonObject.put("like", "yes");
            }else{
                jsonObject.put("like", "no");
            }
            jsonObject.put("info", singer.getIntro());
            jsonObject.put("pic", ImgIOJsonOutputUtils.encodeImage(singer.getPicUrl()));
            jsonObject.put("num_like", rusld.getLikesCountBySingerId(singer.getId()));


            out.write(jsonObject.toString());
        }
    }
}
