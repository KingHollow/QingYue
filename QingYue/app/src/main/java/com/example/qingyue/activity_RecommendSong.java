package com.example.qingyue;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.qingyue.entity.Song;
import com.example.qingyue.entity.User;
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

public class activity_RecommendSong extends Activity {

    Button btn_rcmd_friend, btn_rcmd_singer;
    ImageButton ib_fresh;
    ListView lv_songs;
    Set<JSONObject> resultJson = new HashSet<>();
    JSONArray jsonArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend_song);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        btn_rcmd_friend = findViewById(R.id.btn_recommend_friend);
        btn_rcmd_singer = findViewById(R.id.btn_recommend_singer);
        ib_fresh = findViewById(R.id.fresh);
        lv_songs = findViewById(R.id.list_recommend_song);

        btn_rcmd_friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), activity_RecommendFriend.class));
                activity_RecommendSong.this.finish();
            }
        });
        btn_rcmd_singer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), activity_RecommendSinger.class));
                activity_RecommendSong.this.finish();
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
        String songJson= PostUtil.doPost("songRecommend",data);
        try {
            jsonArray = new JSONArray(songJson);
            initSongs(jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void initSongs(JSONArray jsonArray) throws JSONException {
        resultJson.clear();
        Random random = new Random();
        while (resultJson.size() < 3) {
            resultJson.add((JSONObject) jsonArray.get(random.nextInt(jsonArray.length())));
        }
        List<JSONObject> resultList = new ArrayList<>(resultJson);
        ArrayList<Song> songList = new ArrayList<>();
        for (int i = 0; i < resultList.size(); i++) {
            Song song = new Song();
            song.setName(resultList.get(i).getString("name"));
            song.setSinger(resultList.get(i).getString("singer"));
            song.setPicString(resultList.get(i).getString("pic"));
            songList.add(song);
        }
        UserSongAdapter adapter = new UserSongAdapter(songList, activity_RecommendSong.this);
        lv_songs.setAdapter(adapter);
    }

    public void aboutSong(View view) {
        TextView tv_songname = view.findViewById(R.id.song_name);
        String name = tv_songname.getText().toString();
        Intent intent = new Intent(getApplicationContext(), activity_aboutSong.class);
        Bundle bundle = new Bundle();
        bundle.putString("song", name);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void fresh(View view) throws JSONException {
        initSongs(jsonArray);
    }

    //homepage
    public void homepage(View view) {

        startActivity(new Intent(getApplicationContext(), activity_forums_square.class));
        activity_RecommendSong.this.finish();

    }

    //chat
    public void chat(View view) {

        startActivity(new Intent(getApplicationContext(), activity_chat.class));
        activity_RecommendSong.this.finish();

    }

    //mine
    public void mine(View view) {

        startActivity(new Intent(getApplicationContext(), activity_mine.class));
        activity_RecommendSong.this.finish();

    }
}