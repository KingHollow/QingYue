package com.example.qingyue;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.qingyue.entity.Singer;
import com.example.qingyue.entity.User;
import com.example.qingyue.utils.PostUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

public class activity_UserLikeSinger extends Activity {

    ArrayList<Singer> singerdatalist = new ArrayList<Singer>();
    String username;
    private TextView singer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_like_singer);

        Bundle bundle = this.getIntent().getExtras();
        username = bundle.getString("username");

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        ImageButton btn_back = (ImageButton) findViewById(R.id.btn_back);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //关闭Activity
                activity_UserLikeSinger.this.finish();
            }
        });

        init();
    }


    private void init() {

        if (singerdatalist != null) {
            singerdatalist.clear();
        } else {
            singerdatalist = new ArrayList<Singer>();
        }
        String data = "";
        try {
            data = "?username=" + URLEncoder.encode(username, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String singerJson = PostUtil.doPost("singerLike", data);

        try {

            JSONArray jsonArray = new JSONArray(singerJson);

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                Singer singer = new Singer();

                singer.setPicString(jsonObject.getString("picString"));
                singer.setName(jsonObject.getString("name"));
                singerdatalist.add(singer);
            }

            ListView singerList = (ListView) findViewById(R.id.list_like_singer);

            UserSingerAdapter adapter = new UserSingerAdapter(singerdatalist, activity_UserLikeSinger.this);
            singerList.setAdapter(adapter);


        } catch (org.json.JSONException e) {
            e.printStackTrace();
        }
    }

    public void aboutSinger(View view) {

        singer = view.findViewById(R.id.singer_name);

        Intent intent = new Intent(getApplicationContext(),activity_aboutSinger.class);
        Bundle bundle = new Bundle();//该类用作携带数据
        bundle.putString("singer", singer.getText().toString());
        intent.putExtras(bundle);//附带上额外的数据
        startActivity(intent);

    }
}