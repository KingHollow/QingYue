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

                //??????????????????
                activity_MinePost.this.setResult(RESULT_OK, intent);

                //??????Activity
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

        Intent intent = new Intent(getApplicationContext(),activity_aboutPost.class);
        Bundle bundle1 = new Bundle();//????????????????????????
        bundle1.putString("postid", id.getText().toString());

        intent.putExtras(bundle1);//????????????????????????
        startActivityForResult(intent,1);
    }

    public void show_o_Post(View view) {

        relativeLayout2 = (RelativeLayout) view;
        linearLayout1 = (LinearLayout) view.getParent();
        o_id = view.findViewById(R.id.o_postid);
        author_username = linearLayout1.findViewById(R.id.author_username);
        o_content = linearLayout1.findViewById(R.id.original_content);


        //??????????????????
        Intent intent = new Intent(getApplicationContext(),activity_aboutPost.class);
        Bundle bundle1 = new Bundle();//????????????????????????
        bundle1.putString("postid", o_id.getText().toString());

        intent.putExtras(bundle1);//????????????????????????
        startActivityForResult(intent,1);
    }

    public void deletePost(View view) {

        showTypeDialog(view);

    }

    private void showTypeDialog(View view) {
        //???????????????
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
                        Toast.makeText(getApplicationContext(), "????????????", Toast.LENGTH_LONG).show();

                        init();
                    } else {
                        Toast.makeText(getApplicationContext(), "????????????", Toast.LENGTH_LONG).show();
                    }

                } catch (org.json.JSONException e) {
                    e.printStackTrace();
                }

                dialog.dismiss();
            }
        });
        no.setOnClickListener(new View.OnClickListener() {// ?????????
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
            //??????????????????
            case 1:
                if (resultCode == RESULT_OK) {
                    new Thread(new Runnable() {
                        public void run() {
                            for (int i = 0; i < 100; i++) {   //???????????????????????????
                            }
                            runOnUiThread(new Runnable() {     //??????textView.setText()?????????UI???????????????
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
            //??????
            case 2:
                if (resultCode == RESULT_OK) {
                    new Thread(new Runnable() {
                        public void run() {
                            for (int i = 0; i < 100; i++) {   //???????????????????????????
                            }
                            runOnUiThread(new Runnable() {     //??????textView.setText()?????????UI???????????????
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
        //like = (LinearLayout) (view.findViewById(R.id.like));
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
                Toast.makeText(getApplicationContext(), "????????????", Toast.LENGTH_LONG).show();

                //init();
                new Thread(new Runnable() {
                    public void run() {
                        runOnUiThread(new Runnable() {     //??????textView.setText()?????????UI???????????????
                            public void run() {
                                like_num.setText(String.valueOf(n_like));
                            }
                        });
                    }
                }).start();

            } else {
                Toast.makeText(getApplicationContext(), "????????????", Toast.LENGTH_LONG).show();
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
        Bundle bundle1 = new Bundle();//????????????????????????
        bundle1.putString("o_id", id.getText().toString());
        bundle1.putString("repost_num", repost_num.getText().toString());
        intent1.putExtras(bundle1);//????????????????????????
        startActivityForResult(intent1,2);
    }


}