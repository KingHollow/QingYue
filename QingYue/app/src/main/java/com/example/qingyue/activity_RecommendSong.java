package com.example.qingyue;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class activity_RecommendSong extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend_song);

        Button btn_rcmd_friend = findViewById(R.id.btn_recommend_friend);
        Button btn_rcmd_singer = findViewById(R.id.btn_recommend_singer);

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