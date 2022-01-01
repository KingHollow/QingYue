package com.javaweb.qingyue.servlet;

import com.javaweb.qingyue.dao.UserDao;
import com.javaweb.qingyue.dao.impl.PostDao;
import com.javaweb.qingyue.dao.impl.RUserSingerLikeDao;
import com.javaweb.qingyue.dao.impl.UserDaoImpl;
import com.javaweb.qingyue.entity.Post;
import com.javaweb.qingyue.entity.Singer;
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

public class userPostServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);  //将信息使用doPost方法执行   对应jsp页面中的form表单中的method
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try (PrintWriter out = response.getWriter()) {

            String username = request.getParameter("username");
            int postid = Integer.parseInt(request.getParameter("postid"));

            UserDao ud = new UserDaoImpl();
            User user = ud.getUserByUsername(username);

            PostDao pd = new PostDao();
            List<Post> postList = null;
            try {
                if(postid == 0){
                    postList = pd.getLatestPostsByUserId(user.getId());
                }else if(postid > 0) {
                    postList = pd.getPostsBeforePostIdByUserId(user.getId(), postid);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            JSONArray jsonArray = new JSONArray();

            if(postList != null && !postList.isEmpty()){
                postList.forEach(post -> {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("id", post.getId());
                    jsonObject.put("type", post.getType());
                    jsonObject.put("posterName", post.getPoster().getName());
                    try {
                        jsonObject.put("postPic", ImgIOJsonOutputUtils.encodeImage(post.getPoster().getHeadshoturl()));
                    } catch (IOException e) {
                        e.printStackTrace();
                        jsonObject.put("postPic", "");
                    }
                    jsonObject.put("time", post.getTime());
                    jsonObject.put("content", post.getContent());
                    jsonObject.put("likes", post.getLikes());
                    jsonObject.put("comments", post.getComments());
                    jsonObject.put("reposts", post.getReposts());
                    jsonObject.put("repostedId", post.getRepostedId());
                    jsonObject.put("repostedName", post.getRepostedName());
                    jsonObject.put("repostedContent", post.getRepostedContent());
                    jsonObject.put("cardTitle", post.getCardTitle());
                    jsonObject.put("cardContent", post.getCardContent());
                    try {
                        jsonObject.put("cardImgUrl", ImgIOJsonOutputUtils.encodeImage(post.getCardImgUrl()));
                    } catch (IOException e) {
                        e.printStackTrace();
                        jsonObject.put("cardImgUrl", "");
                    }
                    jsonArray.add(jsonObject);
                });
            }

            out.write(jsonArray.toString());
        }
    }
}
