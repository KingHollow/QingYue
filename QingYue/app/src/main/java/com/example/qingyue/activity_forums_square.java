package com.example.qingyue;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Looper;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qingyue.entity.Post;
import com.example.qingyue.entity.User;
import com.example.qingyue.utils.ImgIOJsonOutputUtils;
import com.example.qingyue.utils.PostUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class activity_forums_square extends Activity implements View.OnClickListener {

    private ImageButton btn_add;
    private EditText edit_search;
    private ImageButton btn_search;
    List<Post> postdatalist = new ArrayList<Post>();
    private TextView id,type;
    private TextView o_id;
    RelativeLayout relativeLayout1,relativeLayout2;
    int l_id;
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
        setContentView(R.layout.activity_forums_square);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);//避免自动弹出输入框

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }


        init("");
    }

    private void init(String search){

        postdatalist.clear();

        btn_add = (ImageButton) findViewById(R.id.btn_add);

        //search

        edit_search = (EditText) findViewById(R.id.edit_search);
        btn_search = (ImageButton) findViewById(R.id.btn_search);


        btn_add.setOnClickListener(this);
        btn_search.setOnClickListener(this);

        String data="?postid=0&search="+search;

        String postJson= PostUtil.doPost("postList",data);

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

            ListView postList = (ListView) findViewById(R.id.list_post);
            //最后一篇帖子的id
            JSONObject l_jsonObject = (JSONObject) jsonArray.get(jsonArray.length()-1);
            l_id = l_jsonObject.getInt("id");

            PostAdapter adapter = new PostAdapter( postdatalist, activity_forums_square.this);
            postList.setAdapter(adapter);

        } catch (org.json.JSONException e) {
            e.printStackTrace();
        }
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add:// 发帖
                startActivityForResult(new Intent(getApplicationContext(), activity_EditPost.class),1);
                break;
            case R.id.btn_search:
                searchPost();
                break;
//            case R.id.repost:
//                relativeLayout = (RelativeLayout) (v.getParent());
//                id = relativeLayout.findViewById(R.id.postid);
//                repost_num = (TextView) v.findViewById(R.id.repost_num);
//                int o_repost = Integer.valueOf(repost_num.getText().toString());
//
//                Intent intent1 = new Intent(getApplicationContext(),activity_EditRepost.class);
//                Bundle bundle1 = new Bundle();//该类用作携带数据
//                bundle1.putString("o_id", id.getText().toString());
//                bundle1.putString("repost_num", repost_num.getText().toString());
//                intent1.putExtras(bundle1);//附带上额外的数据
//                startActivityForResult(intent1,3);

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    //init();
                }
                break;
                //查看帖子详情
            case 2:
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
            case 3:
                if (resultCode == RESULT_OK) {
                    new Thread(new Runnable() {
                        public void run() {
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

    public void searchPost() {
        try {
            init(URLEncoder.encode(edit_search .getText().toString(), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            init("");
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
        String postType = type.getText().toString();
        if (postType.equals("3")) {
            return;
        }

        Intent intent = new Intent(getApplicationContext(),activity_aboutPost.class);
        Bundle bundle1 = new Bundle();//该类用作携带数据
        bundle1.putString("postid", id.getText().toString());

        intent.putExtras(bundle1);//附带上额外的数据
        startActivityForResult(intent,2);
    }

    public void show_o_Post(View view) {

        relativeLayout2 = (RelativeLayout) findViewById(R.id.o_post);
        linearLayout1 = (LinearLayout) view.getParent();
        o_id = view.findViewById(R.id.o_postid);
        author_username = linearLayout1.findViewById(R.id.author_username);
        o_content = linearLayout1.findViewById(R.id.original_content);


        //请求原贴内容
        Intent intent = new Intent(getApplicationContext(),activity_aboutPost.class);
        Bundle bundle1 = new Bundle();//该类用作携带数据
        bundle1.putString("postid", o_id.getText().toString());

        intent.putExtras(bundle1);//附带上额外的数据
        startActivity(intent);
    }

    public void likePost(View view) {
        Log.d("like:", "success");
        linearLayout1 = (LinearLayout) (view.getParent().getParent());
        id = linearLayout1.findViewById(R.id.postid);
        Log.d("id:", id.getText().toString());
        like_num = (TextView) view.findViewById(R.id.like_num);
        int o_like = Integer.parseInt(like_num.getText().toString());
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
        startActivityForResult(intent1,3);
    }


    //mine
    public void mine(View view) {

        startActivity(new Intent(getApplicationContext(), activity_mine.class));
        activity_forums_square.this.finish();

    }

    //chat
    public void chat(View view) {

        startActivity(new Intent(getApplicationContext(), activity_chat.class));
        activity_forums_square.this.finish();

    }

    //recommend
    public void recommend(View view) {

        startActivity(new Intent(getApplicationContext(), activity_RecommendFriend.class));
        activity_forums_square.this.finish();

    }

}