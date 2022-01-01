package com.javaweb.qingyue.servlet;

import com.javaweb.qingyue.dao.UserDao;
import com.javaweb.qingyue.dao.impl.CommentDao;
import com.javaweb.qingyue.dao.impl.UserDaoImpl;
import com.javaweb.qingyue.entity.Comment;
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
import java.util.List;

public class commentListServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);  //将信息使用doPost方法执行   对应jsp页面中的form表单中的method
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try (PrintWriter out = response.getWriter()) {

            int postId = Integer.parseInt(request.getParameter("postid")); //得到jsp页面传过来的参数
            int commentId = Integer.parseInt(request.getParameter("commentid"));

            UserDao ud = new UserDaoImpl();

            CommentDao cd = new CommentDao();
            List<Comment> commentList = null;
            try {
                if (commentId == 0) {
                    commentList = cd.getLatestCommentsByPostId(postId);
                } else {
                    commentList = cd.getCommentsBeforeCommentIdByPostId(postId, commentId);
                }
            } catch (SQLException e){
                e.printStackTrace();
            }

            JSONArray jsonArray = new JSONArray();

            if (commentList != null && !commentList.isEmpty()){
                for (Comment comment : commentList) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("commentId", comment.getId());
                    User user = ud.getUserById(comment.getCommenterId());
                    try {
                        jsonObject.put("commenterHeadShot", ImgIOJsonOutputUtils.encodeImage(user.getHeadshoturl()));
                    } catch (IOException e) {
                        e.printStackTrace();
                        jsonObject.put("commenterHeadShot", "");
                    }
                    jsonObject.put("commenterName", user.getName());
                    jsonObject.put("content", comment.getContent());
                    jsonObject.put("time", comment.getTime());
                    jsonArray.add(jsonObject);
                }
            }

            out.write(jsonArray.toString());
        }
    }
}
