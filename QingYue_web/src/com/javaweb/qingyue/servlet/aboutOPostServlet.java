package com.javaweb.qingyue.servlet;

import com.javaweb.qingyue.dao.UserDao;
import com.javaweb.qingyue.dao.impl.PostDao;
import com.javaweb.qingyue.dao.impl.UserDaoImpl;
import com.javaweb.qingyue.entity.Post;
import com.javaweb.qingyue.util.ImgIOJsonOutputUtils;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class aboutOPostServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);  //将信息使用doPost方法执行   对应jsp页面中的form表单中的method
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try (PrintWriter out = response.getWriter()) {

            int postId = Integer.parseInt(request.getParameter("postid")); //得到jsp页面传过来的参数

            PostDao pd = new PostDao();
            JSONObject jsonObject = new JSONObject();

            Post post = pd.getPostById(postId);

            jsonObject.put("posterName", post.getPoster().getName());
            try {
                jsonObject.put("postPic", ImgIOJsonOutputUtils.encodeImage(post.getPoster().getHeadshoturl()));
            } catch (IOException e) {
                e.printStackTrace();
                jsonObject.put("postPic", "");
            }
            jsonObject.put("type", post.getType());
            jsonObject.put("time", post.getTime());
            jsonObject.put("content", post.getContent());
            jsonObject.put("likes", post.getLikes());
            jsonObject.put("comments", post.getComments());
            jsonObject.put("reposts", post.getReposts());
            jsonObject.put("cardTitle", post.getCardTitle());
            jsonObject.put("cardContent", post.getCardContent());
            try {
                jsonObject.put("cardImgUrl", ImgIOJsonOutputUtils.encodeImage(post.getCardImgUrl()));
            } catch (IOException e) {
                e.printStackTrace();
                jsonObject.put("cardImgUrl", "");
            }

            out.write(jsonObject.toString());
        }
    }
}
