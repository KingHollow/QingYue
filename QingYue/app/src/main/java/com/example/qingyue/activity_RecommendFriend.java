package com.example.qingyue;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.qingyue.entity.User;
import com.example.qingyue.utils.ImgIOJsonOutputUtils;
import com.example.qingyue.utils.PostUtil;

import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class activity_RecommendFriend extends Activity {

    Button btn_rcmd_singer, btn_rcmd_song;
    ImageView iv_hp_mine, iv_hp1, iv_hp2, iv_hp3;
    TextView tv_username1, tv_chengdu1, tv_labels1, tv_singer1, tv_username2, tv_chengdu2, tv_labels2, tv_singer2, tv_username3, tv_chengdu3, tv_labels3, tv_singer3;
    Set<JSONObject> resultJson = new HashSet<>();
    JSONArray jsonArray;
    ImageButton ib_fresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend_friend);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        btn_rcmd_singer = findViewById(R.id.btn_recommend_singer);
        btn_rcmd_song = findViewById(R.id.btn_recommend_song);
        iv_hp_mine = findViewById(R.id.head_picture_mine);
        iv_hp1 = findViewById(R.id.head_picture_1);
        iv_hp2 = findViewById(R.id.head_picture_2);
        iv_hp3 = findViewById(R.id.head_picture_3);
        tv_username1 = findViewById(R.id.user_name_1);
        tv_chengdu1 = findViewById(R.id.user_chengdu_1);
        tv_labels1 = findViewById(R.id.user_labels_1);
        tv_singer1 = findViewById(R.id.like_singer_1);
        tv_username2 = findViewById(R.id.user_name_2);
        tv_chengdu2 = findViewById(R.id.user_chengdu_2);
        tv_labels2 = findViewById(R.id.user_labels_2);
        tv_singer2 = findViewById(R.id.like_singer_2);
        tv_username3 = findViewById(R.id.user_name_3);
        tv_chengdu3 = findViewById(R.id.user_chengdu_3);
        tv_labels3 = findViewById(R.id.user_labels_3);
        tv_singer3 = findViewById(R.id.like_singer_3);
        ib_fresh = findViewById(R.id.fresh);


        btn_rcmd_singer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), activity_RecommendSinger.class));
                activity_RecommendFriend.this.finish();
            }
        });
        btn_rcmd_song.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), activity_RecommendSong.class));
                activity_RecommendFriend.this.finish();
            }
        });

        iv_hp1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), activity_AboutUser.class);
                Bundle bundle = new Bundle();
                bundle.putString("username", tv_username1.getText().toString());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        iv_hp2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), activity_AboutUser.class);
                Bundle bundle = new Bundle();
                bundle.putString("username", tv_username2.getText().toString());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        iv_hp3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), activity_AboutUser.class);
                Bundle bundle = new Bundle();
                bundle.putString("username", tv_username3.getText().toString());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        init();
    }

    public void init() {
        User n_user = (User) getApplication();
        String username = n_user.getUserName();
        String data = null;
        try {
            data = "?username=" + URLEncoder.encode(username, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String friendJson= PostUtil.doPost("friendRecommend",data);
        try {
            JSONObject jsonObject = new JSONObject(friendJson);
            iv_hp_mine.setImageBitmap(ImgIOJsonOutputUtils.base64ToBitmap(jsonObject.getString("headShot")));
            jsonArray = jsonObject.getJSONArray("friendList");
            initFriends(jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("SetTextI18n")
    public void initFriends(JSONArray jsonArray) throws JSONException {
        resultJson.clear();
        Random random = new Random();
        while (resultJson.size() < 3) {
            resultJson.add((JSONObject) jsonArray.get(random.nextInt(jsonArray.length())));
        }
        List<JSONObject> resultList = new ArrayList<>(resultJson);
        iv_hp1.setImageBitmap(ImgIOJsonOutputUtils.base64ToBitmap(resultList.get(0).getString("pic")));
        iv_hp2.setImageBitmap(ImgIOJsonOutputUtils.base64ToBitmap(resultList.get(1).getString("pic")));
        iv_hp3.setImageBitmap(ImgIOJsonOutputUtils.base64ToBitmap(resultList.get(2).getString("pic")));
        tv_username1.setText(resultList.get(0).getString("name"));
        tv_username2.setText(resultList.get(1).getString("name"));
        tv_username3.setText(resultList.get(2).getString("name"));
        tv_chengdu1.setText("相似程度："+resultList.get(0).getString("similarity"));
        tv_chengdu2.setText("相似程度："+resultList.get(1).getString("similarity"));
        tv_chengdu3.setText("相似程度："+resultList.get(2).getString("similarity"));
        List<String> labels1 = new ArrayList<>();
        JSONArray jsonArray1 = resultList.get(0).getJSONArray("labels");
        for (int i = 0; i < jsonArray1.length(); i++) {
            labels1.add(jsonArray1.getString(i));
        }
        labels1.removeAll(Collections.singleton(""));
        tv_labels1.setText("标签："+ StringUtils.join(labels1, "、"));
        List<String> labels2 = new ArrayList<>();
        JSONArray jsonArray2 = resultList.get(1).getJSONArray("labels");
        for (int i = 0; i < jsonArray2.length(); i++) {
            labels2.add(jsonArray2.getString(i));
        }
        labels2.removeAll(Collections.singleton(""));
        tv_labels2.setText("标签："+ StringUtils.join(labels2, "、"));
        List<String> labels3 = new ArrayList<>();
        JSONArray jsonArray3 = resultList.get(2).getJSONArray("labels");
        for (int i = 0; i < jsonArray3.length(); i++) {
            labels3.add(jsonArray3.getString(i));
        }
        labels3.removeAll(Collections.singleton(""));
        tv_labels3.setText("标签："+ StringUtils.join(labels3, "、"));
        tv_singer1.setText("喜好歌手："+resultList.get(0).getString("singerLike"));
        tv_singer2.setText("喜好歌手："+resultList.get(1).getString("singerLike"));
        tv_singer3.setText("喜好歌手："+resultList.get(2).getString("singerLike"));
    }

    //homepage
    public void homepage(View view) {

        startActivity(new Intent(getApplicationContext(), activity_forums_square.class));
        activity_RecommendFriend.this.finish();

    }

    //chat
    public void chat(View view) {

        startActivity(new Intent(getApplicationContext(), activity_chat.class));
        activity_RecommendFriend.this.finish();

    }

    //mine
    public void mine(View view) {

        startActivity(new Intent(getApplicationContext(), activity_mine.class));
        activity_RecommendFriend.this.finish();

    }

    public void fresh(View view) {
        try {
            initFriends(jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}