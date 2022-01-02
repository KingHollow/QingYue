package com.example.qingyue;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
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

import com.example.qingyue.entity.Post;
import com.example.qingyue.entity.Singer;
import com.example.qingyue.entity.Song;
import com.example.qingyue.entity.User;
import com.example.qingyue.utils.ImgIOJsonOutputUtils;
import com.example.qingyue.utils.PostUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

public class activity_MinePost extends Activity {

    ArrayList<Post> postdatalist = new ArrayList<Post>();
    private TextView id,type;
    private TextView o_id;
    RelativeLayout relativeLayout1,relativeLayout2;
    LinearLayout like,repost,linearLayout1,linearLayout2;
    TextView like_num,repost_num,comment_num,username,time,content,author_username,o_content,songname,singername,singer;
    ImageView head_picture,song_pic,singer_pic;

    String posterPic,o_time,o_cardImg,o_cardTitle,o_cardContent;
    //    posterPic = "";
//    o_time = "";
//    o_cardImg = "";
//    o_cardTitle = "";
//    o_cardContent = "";
    int o_likes,o_comments,o_reposts,o_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_post);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        ImageButton btn_back = (ImageButton)findViewById(R.id.btn_back);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();

                //设置返回数据
                activity_MinePost.this.setResult(RESULT_OK, intent);

                //关闭Activity
                activity_MinePost.this.finish();
            }
        });

        init();


    }

    private void init() {
        User n_user = (User) getApplication();

        String data="";
        try {
            data = "?username=" + URLEncoder.encode(n_user.getUserName(), "UTF-8") +
            "&postid=0";
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String postJson= PostUtil.doPost("userPost",data);

        try {

            JSONArray jsonArray = new JSONArray(postJson);

            for(int i = 0;i < jsonArray.length();i++){

                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                Post post = new Post();

                post.setID(jsonObject.getInt("id"));
                post.set_o_ID(jsonObject.getInt("repostedId"));
                int type = jsonObject.getInt("type");
                post.setType(type);
                post.setHeadpic(jsonObject.getString("postPic"));
                post.setPoster(jsonObject.getString("posterName"));
                post.setTime(jsonObject.getString("time"));
                String str = jsonObject.getString("content");
                post.setContent(str.substring(str.indexOf("|")+1));
                post.setLikes(jsonObject.getInt("likes"));
                post.setComments(jsonObject.getInt("comments"));
                post.setReposts(jsonObject.getInt("reposts"));

                if (type == 1) {
                    post.setCardImg(jsonObject.getString("cardImgUrl"));
                    post.setCardTitle(jsonObject.getString("cardTitle"));
                    post.setCardContent(jsonObject.getString("cardContent"));
                } else if (type == 2) {
                    post.setCardImg(jsonObject.getString("cardImgUrl"));
                    post.setCardTitle(jsonObject.getString("cardTitle"));
                } else if (type == 3) {
                    post.setRepostedName(jsonObject.getString("repostedName"));
                    String o_str = jsonObject.getString("repostedContent");
                    post.setRepostedContent(o_str.substring(o_str.indexOf("|")+1));
                }

                postdatalist.add(post);
            }

            ListView postList = (ListView) findViewById(R.id.list_mine_post);

            MyPostAdapter adapter = new MyPostAdapter( postdatalist, activity_MinePost.this);
            postList.setAdapter(adapter);

        } catch (org.json.JSONException e) {
            e.printStackTrace();
        }

    }


    public void showPost(View view) {

        relativeLayout1 = (RelativeLayout) view;
        linearLayout1 = (LinearLayout) view.getParent();
        id = view.findViewById(R.id.postid);
        type = view.findViewById(R.id.type);
        repost_num = linearLayout1.findViewById(R.id.repost_num);
        comment_num = linearLayout1.findViewById(R.id.comment_num);
        like_num = linearLayout1.findViewById(R.id.like_num);
        username = linearLayout1.findViewById(R.id.username);
        time = linearLayout1.findViewById(R.id.time);
        content = linearLayout1.findViewById(R.id.post_content);
        songname = linearLayout1.findViewById(R.id.song_name);
        singername = linearLayout1.findViewById(R.id.singer_name);
        singer = linearLayout1.findViewById(R.id.name);
        head_picture = linearLayout1.findViewById(R.id.head_picture);
        song_pic = linearLayout1.findViewById(R.id.song_picture);
        singer_pic = linearLayout1.findViewById(R.id.singer_picture);

        if (type.getText().toString().equals("3")) {
            return;
        }

        relativeLayout1.setOnClickListener (new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(),activity_aboutPost.class);
                Bundle bundle1 = new Bundle();//该类用作携带数据
                bundle1.putString("username",username.getText().toString());
                bundle1.putString("time",time.getText().toString());
                bundle1.putString("content",content.getText().toString());
                bundle1.putString("postid", id.getText().toString());
                bundle1.putString("o_repost", repost_num.getText().toString());
                bundle1.putString("o_comment", comment_num.getText().toString());
                bundle1.putString("o_like", like_num.getText().toString());
                bundle1.putString("head_pic", ImgIOJsonOutputUtils.bitmapToBase64(((BitmapDrawable)head_picture.getDrawable()).getBitmap()));

                if (type.getText().toString().equals("1")) {
                    bundle1.putString("type","1");
                    bundle1.putString("songname",songname.getText().toString());
                    bundle1.putString("singername",singername.getText().toString());
                    bundle1.putString("song_pic",ImgIOJsonOutputUtils.bitmapToBase64(((BitmapDrawable)song_pic.getDrawable()).getBitmap()));
                } else if (type.getText().toString().equals("2")) {
                    bundle1.putString("type","2");
                    bundle1.putString("singer",singer.getText().toString());
                    bundle1.putString("singer_pic",ImgIOJsonOutputUtils.bitmapToBase64(((BitmapDrawable)singer_pic.getDrawable()).getBitmap()));
                } else {
                    bundle1.putString("type","0");
                }

                intent.putExtras(bundle1);//附带上额外的数据
                startActivityForResult(intent,1);
            }
        });
    }

    public void show_o_Post(View view) {

        relativeLayout2 = (RelativeLayout) view;
        linearLayout1 = (LinearLayout) view.getParent();
        o_id = view.findViewById(R.id.o_postid);
        author_username = linearLayout1.findViewById(R.id.author_username);
        o_content = linearLayout1.findViewById(R.id.original_content);


        //请求原贴内容

        String data = "";
        try {
            data = "?postid=" + URLEncoder.encode(o_id.getText().toString(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String opostJson = PostUtil.doPost("aboutOPost", data);


        try {

            JSONObject jsonObject = new JSONObject(opostJson);

            //Looper.prepare();

            posterPic = jsonObject.getString("posterPic");
            o_time = jsonObject.getString("time");
            o_cardImg = jsonObject.getString("cardImg");
            o_cardTitle = jsonObject.getString("cardTitle");
            o_cardContent = jsonObject.getString("cardContent");
            o_likes = jsonObject.getInt("likes");
            o_comments = jsonObject.getInt("comments");
            o_reposts = jsonObject.getInt("reposts");
            o_type = jsonObject.getInt("type");

            //Looper.loop();


        } catch (JSONException e) {
            e.printStackTrace();
        }

        relativeLayout2.setOnClickListener (new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(),activity_aboutPost.class);
                Bundle bundle1 = new Bundle();//该类用作携带数据
                bundle1.putString("postid", o_id.getText().toString());
                bundle1.putString("username",author_username.getText().toString());
                bundle1.putString("time",o_time);
                bundle1.putString("content",o_content.getText().toString());
                bundle1.putString("o_repost", String.valueOf(o_reposts));
                bundle1.putString("o_comment", String.valueOf(o_comments));
                bundle1.putString("o_like", String.valueOf(o_likes));
                bundle1.putString("head_pic",posterPic);

                if (o_type == 1) {
                    bundle1.putString("type","1");
                    bundle1.putString("songname",o_cardTitle);
                    bundle1.putString("singername",o_cardContent);
                    bundle1.putString("song_pic",o_cardImg);
                } else if (o_type == 2) {
                    bundle1.putString("type","2");
                    bundle1.putString("singer",o_cardTitle);
                    bundle1.putString("singer_pic",o_cardImg);
                } else {
                    bundle1.putString("type","0");
                }

                intent.putExtras(bundle1);//附带上额外的数据
                startActivity(intent);
            }
        });
    }

    public void deletePost(View view) {

        showTypeDialog(view);

    }

    private void showTypeDialog(View view) {
        //显示对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog dialog = builder.create();

        View vd = View.inflate(this, R.layout.dialog_select_delete_post, null);
        TextView yes = (TextView) vd.findViewById(R.id.tv_yes);
        TextView no = (TextView) vd.findViewById(R.id.no);
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                id = view.findViewById(R.id.postid);

                String data="";
                try {
                    data = "?postID=" + URLEncoder.encode(id.getText().toString(), "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                String deleteJson= PostUtil.doPost("deletePost",data);

                try {

                    JSONObject jsonObject = new JSONObject(deleteJson);


                    String result = null;
                    result = jsonObject.getString("Result");

                    if (result.equals("success")) {
                        //
                        Toast.makeText(getApplicationContext(), "删除成功", Toast.LENGTH_LONG).show();

                        init();
                    } else {
                        Toast.makeText(getApplicationContext(), "删除失败", Toast.LENGTH_LONG).show();
                    }

                } catch (org.json.JSONException e) {
                    e.printStackTrace();
                }

                dialog.dismiss();
            }
        });
        no.setOnClickListener(new View.OnClickListener() {// 不修改
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.setView(vd);
        dialog.show();
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            //查看帖子详情
            case 1:
                if (resultCode == RESULT_OK) {
                    new Thread(new Runnable() {
                        public void run() {
                            for (int i = 0; i < 100; i++) {   //这是个很耗时的操作
                            }
                            runOnUiThread(new Runnable() {     //这样textView.setText()就会在UI线程中执行
                                public void run() {
                                    repost_num.setText(data.getExtras().getString("n_repost"));
                                    comment_num.setText(data.getExtras().getString("n_comment"));
                                    like_num.setText(data.getExtras().getString("n_like"));
                                }
                            });
                        }
                    }).start();
                }
                break;
            //转发
            case 2:
                if (resultCode == RESULT_OK) {
                    new Thread(new Runnable() {
                        public void run() {
                            for (int i = 0; i < 100; i++) {   //这是个很耗时的操作
                            }
                            runOnUiThread(new Runnable() {     //这样textView.setText()就会在UI线程中执行
                                public void run() {
                                    repost_num.setText(data.getExtras().getString("n_repost"));
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

    public void likePost(View view) {

        linearLayout1 = (LinearLayout) (view.getParent().getParent());
        id = linearLayout1.findViewById(R.id.postid);
        linearLayout2 = findViewById(R.id.like);
        //like = (LinearLayout) (view.findViewById(R.id.like));
        like_num = (TextView) view.findViewById(R.id.like_num);
        int o_like = Integer.valueOf(like_num.getText().toString());
        int n_like = o_like + 1;


        String data="";
        try {
            data = "?postid=" + URLEncoder.encode(id.getText().toString(), "UTF-8");
            Log.d("data:", data);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

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
        linearLayout1 = (LinearLayout) (view.getParent().getParent());
        id = linearLayout1.findViewById(R.id.postid);
        repost_num = (TextView) view.findViewById(R.id.repost_num);
        int o_repost = Integer.parseInt(repost_num.getText().toString());

        Intent intent1 = new Intent(getApplicationContext(),activity_EditRepost.class);
        Bundle bundle1 = new Bundle();//该类用作携带数据
        bundle1.putString("o_id", id.getText().toString());
        bundle1.putString("repost_num", repost_num.getText().toString());
        intent1.putExtras(bundle1);//附带上额外的数据
        startActivityForResult(intent1,2);
    }


}