package com.example.qingyue;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.qingyue.entity.Friend;
import com.example.qingyue.entity.Post;
import com.example.qingyue.entity.User;
import com.example.qingyue.utils.PostUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class activity_chat extends Activity {

    String username;
    List<Friend> friendList = new ArrayList<>();
    ImageButton btn_add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);//避免自动弹出输入框

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        try {
            init();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public void init() throws UnsupportedEncodingException {
        User n_user = (User) getApplication();
        username = n_user.getUserName();
        friendList.clear();

        btn_add = findViewById(R.id.btn_add);

        String data="?username="+ URLEncoder.encode(username, "UTF-8");

        String postJson= PostUtil.doPost("chatList",data);

        try {

            JSONArray jsonArray = new JSONArray(postJson);

            for(int i = 0;i < jsonArray.length();i++){

                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                Friend friend = new Friend();
                friend.setName(jsonObject.getString("name"));
                friend.setHeadshotUrl(jsonObject.getString("headshotUrl"));
                friend.setChatContent(jsonObject.getString("chatContent"));
                friend.setLatestTime(jsonObject.getString("latestTime"));
                friendList.add(friend);
            }

            ListView friend_list = (ListView) findViewById(R.id.list_chat);

            FriendAdapter adapter = new FriendAdapter(friendList, activity_chat.this);
            friend_list.setAdapter(adapter);

        } catch (org.json.JSONException e) {
            e.printStackTrace();
        }
    }

    //mine
    public void mine(View view) {

        startActivity(new Intent(getApplicationContext(), activity_mine.class));
        activity_chat.this.finish();

    }

    //homepage
    public void homepage(View view) {

        startActivity(new Intent(getApplicationContext(), activity_forums_square.class));
        activity_chat.this.finish();

    }

    //recommend
    public void recommend(View view) {

        startActivity(new Intent(getApplicationContext(), activity_RecommendFriend.class));
        activity_chat.this.finish();

    }
}