package com.example.qingyue;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class activity_RecommendFriend extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend_friend);

        Button btn_rcmd_singer = findViewById(R.id.btn_recommend_singer);
        Button btn_rcmd_song = findViewById(R.id.btn_recommend_song);

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
}