package com.javaweb.qingyue.servlet;

import com.javaweb.qingyue.dao.UserDao;
import com.javaweb.qingyue.dao.impl.*;
import com.javaweb.qingyue.entity.Singer;
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

public class aboutSongServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);  //将信息使用doPost方法执行   对应jsp页面中的form表单中的method
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try (PrintWriter out = response.getWriter()) {

            String username = request.getParameter("username"); //得到jsp页面传过来的参数
            String songname = request.getParameter("song");

            UserDao ud = new UserDaoImpl();
            User user = ud.getUserByUsername(username);

            SongDao sd = new SongDao();
            Song song = sd.getSongByName(songname);

            RUserSongLikeDao rusld = new RUserSongLikeDao();

            JSONObject jsonObject = new JSONObject();

            jsonObject.put("song", song.getName());
            jsonObject.put("singer", song.getSinger());
            if(rusld.haveLikeRelation(user.getId(), song.getId())){
                jsonObject.put("like", "yes");
            }else{
                jsonObject.put("like", "no");
            }
            jsonObject.put("album", song.getAlbum());
            jsonObject.put("info", song.getLyric());
            jsonObject.put("pic", ImgIOJsonOutputUtils.encodeImage(song.getPicUrl()));
            jsonObject.put("num_like", rusld.getLikesCountBySongId(song.getId()));

            JSONArray Labels = new JSONArray();
            List<String> list;
            try {
                list = rusld.getThreeSongLabelsBySongId(song.getId());
            } catch (SQLException e) {
                e.printStackTrace();
                list = new ArrayList<>();
            }
            Labels.addAll(list);

            jsonObject.put("Labels", Labels);

            out.write(jsonObject.toString());
        }
    }
}
