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
import android.widget.TextView;

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
        ib_singerpic1 = findViewById(R.id.btn_singer_picture_1);
        ib_singerpic2 = findViewById(R.id.btn_singer_picture_2);
        ib_singerpic3 = findViewById(R.id.btn_singer_picture_3);
        tv_singername1 = findViewById(R.id.singer_name_1);
        tv_singername2 = findViewById(R.id.singer_name_2);
        tv_singername3 = findViewById(R.id.singer_name_3);


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

        ib_singerpic1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), activity_aboutSinger.class);
                Bundle bundle = new Bundle();
                bundle.putString("singer", tv_singername1.getText().toString());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        ib_singerpic2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), activity_aboutSinger.class);
                Bundle bundle = new Bundle();
                bundle.putString("singer", tv_singername2.getText().toString());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        ib_singerpic3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), activity_aboutSinger.class);
                Bundle bundle = new Bundle();
                bundle.putString("singer", tv_singername3.getText().toString());
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
        ib_singerpic1.setImageBitmap(ImgIOJsonOutputUtils.base64ToBitmap(resultList.get(0).getString("pic")));
        ib_singerpic2.setImageBitmap(ImgIOJsonOutputUtils.base64ToBitmap(resultList.get(1).getString("pic")));
        ib_singerpic3.setImageBitmap(ImgIOJsonOutputUtils.base64ToBitmap(resultList.get(2).getString("pic")));
        tv_singername1.setText(resultList.get(0).getString("name"));
        tv_singername2.setText(resultList.get(1).getString("name"));
        tv_singername3.setText(resultList.get(2).getString("name"));
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