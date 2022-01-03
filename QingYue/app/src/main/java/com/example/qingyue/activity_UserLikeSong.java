package com.example.qingyue;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.qingyue.entity.Song;
import com.example.qingyue.entity.User;
import com.example.qingyue.utils.PostUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

public class activity_UserLikeSong extends Activity {

    ArrayList<Song> songdatalist = new ArrayList<Song>();
    String username;
    private TextView song;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_like_song);

        Bundle bundle = this.getIntent().getExtras();
        username = bundle.getString("username");

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        ImageButton btn_back = (ImageButton)findViewById(R.id.btn_back);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //关闭Activity
                activity_UserLikeSong.this.finish();
            }
        });

        init();


    }

    private void init() {


        if(songdatalist != null){
            songdatalist.clear();
        } else {
            songdatalist = new ArrayList<Song>();
        }
        String data="";
        try {
            data = "?username=" + URLEncoder.encode(username, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String songJson= PostUtil.doPost("songLike",data);

        try {

            JSONArray jsonArray = new JSONArray(songJson);

            for(int i = 0;i < jsonArray.length();i++){

                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                Song song = new Song();

                song.setPicString(jsonObject.getString("picString"));
                song.setName(jsonObject.getString("name"));
                song.setSinger(jsonObject.getString("singer"));
                songdatalist.add(song);
            }

            ListView songList = (ListView) findViewById(R.id.list_like_song);

            UserSongAdapter adapter = new UserSongAdapter( songdatalist, activity_UserLikeSong.this);
            songList.setAdapter(adapter);

        } catch (org.json.JSONException e) {
            e.printStackTrace();
        }
    }

    public void aboutSong(View view) {

        song = view.findViewById(R.id.song_name);
        Log.d("AppData",song.getText().toString());

        Intent intent = new Intent(getApplicationContext(),activity_aboutSong.class);
        Bundle bundle = new Bundle();//该类用作携带数据
        bundle.putString("song", song.getText().toString());
        intent.putExtras(bundle);//附带上额外的数据
        startActivity(intent);

    }
}