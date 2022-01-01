package com.javaweb.qingyue.servlet;

import com.javaweb.qingyue.dao.UserDao;
import com.javaweb.qingyue.dao.impl.RUserLabelDao;
import com.javaweb.qingyue.dao.impl.RUserSongLikeDao;
import com.javaweb.qingyue.dao.impl.SongDao;
import com.javaweb.qingyue.dao.impl.UserDaoImpl;
import com.javaweb.qingyue.entity.Song;
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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class chooseSongLikeServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);  //将信息使用doPost方法执行   对应jsp页面中的form表单中的method
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try (PrintWriter out = response.getWriter()) {

            String username = request.getParameter("username");
            String songname = request.getParameter("songname");
            String like = request.getParameter("like");

            UserDao ud = new UserDaoImpl();
            User user = ud.getUserByUsername(username);

            SongDao sd = new SongDao();
            Song song = sd.getSongByName(songname);

            JSONObject jsonObject = new JSONObject();

            RUserSongLikeDao rusld = new RUserSongLikeDao();
            if(like.equals("yes")){
                if(rusld.addSongLike(user.getId(), song.getId())){
                    jsonObject.put("Result", "success");
                    CompletableFuture.runAsync(() -> {
                        RUserLabelDao ruld = new RUserLabelDao();
                        ruld.updateValueByUserAndSong(user.getId(), song.getId(), 0.02f);
                    });
                }else {
                    jsonObject.put("Result", "failed");
                }
            }else if (like.equals("no")){
                if(rusld.removeSongLike(user.getId(), song.getId())){
                    jsonObject.put("Result", "success");
                    CompletableFuture.runAsync(() -> {
                        RUserLabelDao ruld = new RUserLabelDao();
                        ruld.updateValueByUserAndSong(user.getId(), song.getId(), 0.02f);
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
