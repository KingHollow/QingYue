package com.example.qingyue;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.qingyue.entity.Singer;
import com.example.qingyue.entity.User;
import com.example.qingyue.utils.ImgIOJsonOutputUtils;
import com.example.qingyue.utils.PostUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class activity_RecommendSinger extends Activity {
    Button btn_rcmd_friend, btn_rcmd_song;
    ImageButton ib_fresh, ib_singerpic1, ib_singerpic2, ib_singerpic3;
    TextView tv_singername1, tv_singername2, tv_singername3;
    Set<JSONObject> resultJson = new HashSet<>();
    JSONArray jsonArray;
    ListView lv_singerRcmd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend_singer);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        btn_rcmd_friend = findViewById(R.id.btn_recommend_friend);
        btn_rcmd_song = findViewById(R.id.btn_recommend_song);
        ib_fresh = findViewById(R.id.fresh);
        lv_singerRcmd = findViewById(R.id.lv_singerRcmd);


        btn_rcmd_friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), activity_RecommendFriend.class));
                activity_RecommendSinger.this.finish();
            }
        });
        btn_rcmd_song.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), activity_RecommendSong.class));
                activity_RecommendSinger.this.finish();
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
        String singerJson= PostUtil.doPost("singerRecommend",data);
        try {
            jsonArray = new JSONArray(singerJson);
            initSingers(jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void initSingers(JSONArray jsonArray) throws JSONException {
        resultJson.clear();
        Random random = new Random();
        while (resultJson.size() < 3) {
            resultJson.add((JSONObject) jsonArray.get(random.nextInt(jsonArray.length())));
        }
        List<JSONObject> resultList = new ArrayList<>(resultJson);
        ArrayList<Singer> singerList = new ArrayList<>();
        for (int i = 0; i < resultList.size(); i++) {
            Singer singer = new Singer();
            singer.setPicString(resultList.get(i).getString("pic"));
            singer.setName(resultList.get(i).getString("name"));
            singerList.add(singer);
        }

        UserSingerAdapter adapter = new UserSingerAdapter(singerList, activity_RecommendSinger.this);
        lv_singerRcmd.setAdapter(adapter);
    }

    public void aboutSinger(View view) {
        TextView tv_singer_name = view.findViewById(R.id.singer_name);
        String singerName = tv_singer_name.getText().toString();
        Intent intent = new Intent(getApplicationContext(), activity_aboutSinger.class);
        Bundle bundle = new Bundle();
        bundle.putString("singer", singerName);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    //homepage
    public void homepage(View view) {

        startActivity(new Intent(getApplicationContext(), activity_forums_square.class));
        activity_RecommendSinger.this.finish();

    }

    //chat
    public void chat(View view) {

        startActivity(new Intent(getApplicationContext(), activity_chat.class));
        activity_RecommendSinger.this.finish();

    }

    //mine
    public void mine(View view) {

        startActivity(new Intent(getApplicationContext(), activity_mine.class));
        activity_RecommendSinger.this.finish();

    }

    public void fresh(View view) throws JSONException {
        initSingers(jsonArray);
    }
}