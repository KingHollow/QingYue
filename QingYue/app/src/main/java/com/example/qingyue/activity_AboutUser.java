package com.example.qingyue;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Looper;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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

public class activity_AboutUser extends Activity implements View.OnClickListener  {

    String n_username;

    private Bitmap head;// 头像Bitmap
    private static String path = "/sdcard/myHead/";// sd路径
    private TextView nickname;
    //判断一下，显示哪个性别
    private ImageView head_picture,sex;
    private String u_sex = "男";
    private TextView region;
    private TextView signature;
    private TextView label_1;
    private TextView label_2;
    private TextView label_3;

    private RelativeLayout LL01,LL02,LL03,add,delete;

    private TextView song_num;
    private TextView singer_num;
    private TextView post_num;
    private ImageView cover_song;
    private ImageView cover_singer;

    List<String> u_Labels = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_user);

        Bundle bundle = this.getIntent().getExtras();
        n_username = bundle.getString("username");

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        ImageButton btn_back = (ImageButton)findViewById(R.id.btn_back);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity_AboutUser.this.finish();
            }
        });

        init();
    }

    private void init() {

        head_picture = findViewById(R.id.head_picture);

        nickname = findViewById(R.id.Nickname);
        //判断一下，显示哪个性别
        sex = findViewById(R.id.sex);
        region = findViewById(R.id.region);
        signature = findViewById(R.id.signature);
        label_1 = findViewById(R.id.label_1);
        label_2 = findViewById(R.id.label_2);
        label_3 = findViewById(R.id.label_3);

        LL01 = (RelativeLayout) findViewById(R.id.rel_song);
        LL02 = (RelativeLayout) findViewById(R.id.rel_singer);
        LL03 = (RelativeLayout) findViewById(R.id.rel_post);

        add = (RelativeLayout) findViewById(R.id.add_friend);
        delete = (RelativeLayout) findViewById(R.id.delete_friend);


        song_num = findViewById(R.id.song_num);
        singer_num = findViewById(R.id.singer_num);
        post_num = findViewById(R.id.post_num);
        cover_song = findViewById(R.id.cover_song);
        cover_singer = findViewById(R.id.cover_singer);

        //必须为按钮设置(this)监听器


        LL01.setOnClickListener(this);
        LL02.setOnClickListener(this);
        LL03.setOnClickListener(this);

        String data1 = "";
        try {
            data1 = "?username=" + URLEncoder.encode(n_username, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String userJson = PostUtil.doPost("MineInit", data1);


        try {

            JSONObject jsonObject = new JSONObject(userJson);

            JSONArray Labels = jsonObject.getJSONArray("Labels");

            nickname.setText(jsonObject.getString("Nickname"));
            if (jsonObject.getString("Sex").equals("女")) {
                sex.setBackgroundResource(R.drawable.woman);
                u_sex = "女";
            } else {
                sex.setBackgroundResource(R.drawable.man);
                u_sex = "男";
            }
            region.setText(jsonObject.getString("Region"));
            signature.setText(jsonObject.getString("Signature"));
            if (Labels.getString(0).isEmpty()) {
                label_1.setVisibility(View.INVISIBLE);
            }
            if (Labels.getString(1).isEmpty()) {
                label_2.setVisibility(View.INVISIBLE);
            }
            if (Labels.getString(2).isEmpty()) {
                label_3.setVisibility(View.INVISIBLE);
            }
            label_1.setText(Labels.getString(0));
            label_2.setText(Labels.getString(1));
            label_3.setText(Labels.getString(2));
            song_num.setText(jsonObject.getInt("SongLikedNum") + "首歌曲");
            singer_num.setText(jsonObject.getInt("SingerLikedNum") + "个歌手");
            post_num.setText(jsonObject.getInt("PostNum") + "篇帖子");

            head_picture.setImageBitmap(ImgIOJsonOutputUtils.base64ToBitmap(jsonObject.getString("Headshot")));
            cover_song.setImageBitmap(ImgIOJsonOutputUtils.base64ToBitmap(jsonObject.getString("SongPicLatestLiked")));
            cover_singer.setImageBitmap(ImgIOJsonOutputUtils.base64ToBitmap(jsonObject.getString("SingerPicLatestLiked")));

            u_Labels.add(Labels.getString(0));
            u_Labels.add(Labels.getString(1));
            u_Labels.add(Labels.getString(2));

        } catch (JSONException e) {
            e.printStackTrace();
        }


            User n_user = (User) getApplication();

            String data2 = "";
            try {
                data2 = "?username1=" + URLEncoder.encode(n_user.getUserName(), "UTF-8") +
                        "?username2=" + URLEncoder.encode(n_username, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            String friendJson = PostUtil.doPost("isFriend", data2);


            try {

                JSONObject jsonObject2 = new JSONObject(friendJson);


                String result = null;
                result = jsonObject2.getString("Result");
                Looper.prepare();
                if (result.equals("success")) {
                    delete.setVisibility(View.VISIBLE);
                } else {
                    add.setVisibility(View.VISIBLE);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rel_song:// 喜欢的歌
                Intent intent1 = new Intent(getApplicationContext(), activity_UserLikeSong.class);
                Bundle bundle1 = new Bundle();
                bundle1.putString("username", n_username);
                intent1.putExtras(bundle1);
                startActivity(intent1);
                break;
            case R.id.rel_singer:// 喜欢的歌手
                Intent intent2 = new Intent(getApplicationContext(), activity_UserLikeSinger.class);
                Bundle bundle2 = new Bundle();
                bundle2.putString("username", n_username);
                intent2.putExtras(bundle2);
                startActivity(intent2);
                break;
            case R.id.rel_post:// 我的帖子
                Intent intent3 = new Intent(getApplicationContext(), activity_UserPost.class);
                Bundle bundle3 = new Bundle();
                bundle3.putString("username", n_username);
                intent3.putExtras(bundle3);
                startActivity(intent3);
                break;
        }
    }

    private void addFriend(){}

    private void deleteFriend(){}
}