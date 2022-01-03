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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.qingyue.entity.RcmdUser;
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
    ListView lv_friendRcmd;

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
        ib_fresh = findViewById(R.id.fresh);
        lv_friendRcmd = findViewById(R.id.lv_friendRcmd);


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
        List<RcmdUser> friendList = new ArrayList<>();
        for (int i = 0; i < resultList.size(); i++) {
            RcmdUser rcmdUser = new RcmdUser();
            rcmdUser.setHeadPicUrl(resultList.get(i).getString("pic"));
            rcmdUser.setName(resultList.get(i).getString("name"));
            rcmdUser.setSimilarity("相似程度："+resultList.get(i).getString("similarity"));
            rcmdUser.setSingerLike("喜好歌手："+resultList.get(i).getString("singerLike"));
            List<String> labels = new ArrayList<>();
            JSONArray jsonArray1 = resultList.get(i).getJSONArray("labels");
            for (int j = 0; j < jsonArray1.length(); j++) {
                labels.add(jsonArray1.getString(j));
            }
            labels.removeAll(Collections.singleton(""));
            rcmdUser.setLabels("标签："+ StringUtils.join(labels, "、"));
            friendList.add(rcmdUser);
        }
        RcmdFriendAdapter rcmdFriendAdapter = new RcmdFriendAdapter(friendList, activity_RecommendFriend.this);
        lv_friendRcmd.setAdapter(rcmdFriendAdapter);
    }

    public void aboutUser(View view) {
        TextView tv_username = view.findViewById(R.id.username);
        String username = tv_username.getText().toString();
        Intent intent = new Intent(getApplicationContext(), activity_AboutUser.class);
        Bundle bundle = new Bundle();
        bundle.putString("username", username);
        intent.putExtras(bundle);
        startActivity(intent);
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