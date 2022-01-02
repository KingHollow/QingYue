package com.example.qingyue;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qingyue.entity.Comment;
import com.example.qingyue.entity.Post;
import com.example.qingyue.entity.User;
import com.example.qingyue.utils.ImgIOJsonOutputUtils;
import com.example.qingyue.utils.PostUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

public class activity_aboutPost extends Activity {
    //返回时要携带数据，用户可能评论转发点赞
    //本页面展示用户头像，姓名，帖子内容，引用的歌曲or歌手（可跳转
    //下方三个按钮固定，三个方法
    //展示评论，init

    String postid,o_repost,o_comment,o_like,username,content,posterPic,time,cardImg,cardTitle,cardContent;
    int n_repost,n_comment,n_like,type;

    ArrayList<Comment> commentdatalist = new ArrayList<Comment>();
    TextView id,p_type;
    TextView like_num,repost_num,comment_num,p_username,p_time,p_content,author_username,o_content,songname,singername,singer;
    ImageView head_picture,song_pic,singer_pic,commenter_pic;
    RelativeLayout relativeLayout1,p_song,p_singer;
    LinearLayout linearLayout1,linearLayout2,linearLayout3;

//        String posterPic,o_time,o_cardImg,o_cardTitle,o_cardContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_post);

        Bundle bundle = this.getIntent().getExtras();
        postid = bundle.getString("postid");

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        ImageButton btn_back = (ImageButton)findViewById(R.id.btn_back);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString("n_repost", repost_num.getText().toString());
                bundle.putString("n_comment",comment_num.getText().toString());
                bundle.putString("n_like",like_num.getText().toString());
                intent.putExtras(bundle);

                //设置返回数据
                activity_aboutPost.this.setResult(RESULT_OK, intent);

                //关闭Activity
                activity_aboutPost.this.finish();
            }
        });

        init();


    }

    private void init() {

        String data1="";
        try {
            data1 = "?postid=" + URLEncoder.encode(postid, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String postJson= PostUtil.doPost("aboutOPost",data1);

        try {

            JSONObject jsonObject = new JSONObject(postJson);

            username = jsonObject.getString("posterName");
            time = jsonObject.getString("time");
            content = jsonObject.getString("content");
            content = content.substring(content.indexOf("|")+1);
            posterPic = jsonObject.getString("postPic");
            o_repost = String.valueOf(jsonObject.getInt("reposts"));
            o_comment = String.valueOf(jsonObject.getInt("comments"));
            o_like = String.valueOf(jsonObject.getInt("likes"));
            type = jsonObject.getInt("type");
            if (type == 1) {
                cardImg = jsonObject.getString("cardImgUrl");
                cardTitle = jsonObject.getString("cardTitle");
                cardContent = jsonObject.getString("cardContent");
            } else if (type == 2) {
                cardImg = jsonObject.getString("cardImgUrl");
                cardTitle = jsonObject.getString("cardTitle");
            }

        } catch (org.json.JSONException e) {
            e.printStackTrace();
        }

        String data2="";
        try {
            data2 = "?postid=" + URLEncoder.encode(postid, "UTF-8") +
                    "&commentid=0";
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String commentJson= PostUtil.doPost("commentList",data2);

        try {

            JSONArray jsonArray = new JSONArray(commentJson);

            for(int i = 0;i < jsonArray.length();i++){

                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                Comment comment = new Comment();

                comment.setID(jsonObject.getInt("commentId"));
                comment.setPicString(jsonObject.getString("commenterHeadShot"));
                comment.setTime(jsonObject.getString("time"));
                comment.setComment(jsonObject.getString("content"));
                comment.setName(jsonObject.getString("commenterName"));


                commentdatalist.add(comment);
            }

            ListView commentList = (ListView) findViewById(R.id.list_comment);

            CommentAdapter adapter = new CommentAdapter( commentdatalist, activity_aboutPost.this);
            commentList.setAdapter(adapter);

        } catch (org.json.JSONException e) {
            e.printStackTrace();
        }
        id = findViewById(R.id.postid);
        p_type = findViewById(R.id.type);

        like_num = findViewById(R.id.like_num);
        repost_num = findViewById(R.id.repost_num);
        comment_num = findViewById(R.id.comment_num);
        p_username = findViewById(R.id.username);
        p_time = findViewById(R.id.time);
        p_content = findViewById(R.id.post_content);
        songname = findViewById(R.id.song_name);
        singername = findViewById(R.id.singer_name);
        singer = findViewById(R.id.name);

        head_picture = findViewById(R.id.head_picture);
        song_pic = findViewById(R.id.song_picture);
        singer_pic = findViewById(R.id.singer_picture);

        p_song = findViewById(R.id.song);
        p_singer = findViewById(R.id.singer);

        id.setText(postid);
        p_type.setText(String.valueOf(type));
        like_num.setText(o_like);
        repost_num.setText(o_repost);
        comment_num.setText(o_comment);
        p_username.setText(username);
        p_time.setText(time);
        p_content.setText(content);
        head_picture.setImageBitmap(ImgIOJsonOutputUtils.base64ToBitmap(posterPic));

        if (type == 1) {
            songname.setText(cardTitle);
            singername.setText(cardContent);
            song_pic.setImageBitmap(ImgIOJsonOutputUtils.base64ToBitmap(cardImg));
            p_song.setVisibility(View.VISIBLE);

        } else if (type == 2) {
            singer.setText(cardTitle);
            singer_pic.setImageBitmap(ImgIOJsonOutputUtils.base64ToBitmap(cardImg));
            p_singer.setVisibility(View.VISIBLE);
        }


    }

    public void likePost(View view) {
        Log.d("like:","success");
        relativeLayout1 = (RelativeLayout) (view.getParent().getParent());
        id = relativeLayout1.findViewById(R.id.postid);
        linearLayout3 = findViewById(R.id.like);
        //like = (LinearLayout) (view.findViewById(R.id.like));
        like_num = (TextView) view.findViewById(R.id.like_num);
        int o_like = Integer.parseInt(like_num.getText().toString());
        n_like = o_like + 1;
        Log.d("n:", String.valueOf(n_like));

        String data="";
        try {
            data = "?postid=" + URLEncoder.encode(id.getText().toString(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Log.d("data:", data);
        String likeJson= PostUtil.doPost("postLike",data);

        try {

            JSONObject jsonObject = new JSONObject(likeJson);


            String result = null;
            result = jsonObject.getString("Result");

            if (result.equals("success")) {
                //
                Toast.makeText(getApplicationContext(), "点赞成功", Toast.LENGTH_LONG).show();

                //init();
                new Thread(new Runnable() {
                    public void run() {
                        runOnUiThread(new Runnable() {     //这样textView.setText()就会在UI线程中执行
                            public void run() {
                                like_num.setText(String.valueOf(n_like));
                            }
                        });
                    }
                }).start();

            } else {
                Toast.makeText(getApplicationContext(), "点赞失败", Toast.LENGTH_LONG).show();
            }

        } catch (org.json.JSONException e) {
            e.printStackTrace();
        }
    }

    public void repostPost(View view) {

        relativeLayout1 = (RelativeLayout) (view.getParent().getParent());
        id = relativeLayout1.findViewById(R.id.postid);
        repost_num = (TextView) view.findViewById(R.id.repost_num);
        int o_repost = Integer.valueOf(repost_num.getText().toString());

        Intent intent1 = new Intent(getApplicationContext(),activity_EditRepost.class);
        Bundle bundle1 = new Bundle();//该类用作携带数据
        bundle1.putString("o_id", id.getText().toString());
        bundle1.putString("repost_num", repost_num.getText().toString());
        intent1.putExtras(bundle1);//附带上额外的数据
        startActivityForResult(intent1,1);
    }

    //todo: comment

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {

            case 1:
                if (resultCode == RESULT_OK) {
                    new Thread(new Runnable() {
                        public void run() {
                            for (int i = 0; i < 100; i++) {   //这是个很耗时的操作
                            }
                            runOnUiThread(new Runnable() {     //这样textView.setText()就会在UI线程中执行
                                public void run() {
                                    repost_num.setText(data.getStringExtra("n_repost"));
                                }
                            });
                        }
                    }).start();
                }
                break;


            default:
                break;

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}