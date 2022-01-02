package com.example.qingyue;




import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qingyue.entity.Post;
import com.example.qingyue.entity.User;
import com.example.qingyue.utils.ImgIOJsonOutputUtils;
import com.example.qingyue.utils.PostUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

public class activity_aboutSong extends Activity implements View.OnClickListener {

    String song;
    String u_like;
    private TextView num_like;
    private TextView song_name;
    private TextView singer_name;
    private TextView album;
    private TextView label_1;
    private TextView label_2;
    private TextView label_3;
    private TextView info;
    private ImageView song_picture;
    private ImageButton like;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_song);
        Bundle bundle = this.getIntent().getExtras();

        song = bundle.getString("song");

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        ImageButton btn_back = (ImageButton)findViewById(R.id.btn_back);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                activity_aboutSong.this.setResult(RESULT_OK, intent);

                    //关闭Activity
                activity_aboutSong.this.finish();
            }
        });

        init();
    }

    private void init() {

        User n_user = (User) getApplication();

        num_like = (TextView) findViewById(R.id.num_like);
        song_name = (TextView) findViewById(R.id.song_name);
        singer_name = (TextView) findViewById(R.id.singer_name);
        album = (TextView) findViewById(R.id.album);
        info = (TextView) findViewById(R.id.info);
        song_picture = (ImageView) findViewById(R.id.song_picture);
        like = (ImageButton) findViewById(R.id.like);
        label_1 = findViewById(R.id.its_label1);
        label_2 = findViewById(R.id.its_label2);
        label_3 = findViewById(R.id.its_label3);

        like.setOnClickListener(this);

        String data="";
        try {
            data = "?username=" + URLEncoder.encode(n_user.getUserName(), "UTF-8") +
                    "&song=" + URLEncoder.encode(song, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String songJson= PostUtil.doPost("aboutSong",data);

        try {

            JSONObject jsonObject = new JSONObject(songJson);
            JSONArray Labels = jsonObject.getJSONArray("Labels");

            singer_name.setText(jsonObject.getString("singer"));
            song_name.setText(jsonObject.getString("song"));
            album.setText(jsonObject.getString("album"));
            if (jsonObject.getString("like").equals("yes")) {
                like.setImageResource(R.drawable.like_song);
                like.setTag(R.drawable.like_song);
                u_like = "yes";
            } else {
                like.setImageResource(R.drawable.unlike_song);
                like.setTag(R.drawable.unlike_song);
                u_like = "no";
            }
            info.setText(jsonObject.getString("info").replace("\\n", "\n"));
            num_like.setText(jsonObject.getInt("num_like")+"人喜欢");

            if (Labels.getString(0).isEmpty()) {
                label_1.setVisibility(View.INVISIBLE);
            }
            if (Labels.getString(1).isEmpty()) {
                label_2.setVisibility(View.INVISIBLE);
            }
            if (Labels.getString(2).isEmpty()) {
                label_3.setVisibility(View.INVISIBLE);
            }

            label_1.setText(Labels.getString(0));
            label_2.setText(Labels.getString(1));
            label_3.setText(Labels.getString(2));

            song_picture.setImageBitmap(ImgIOJsonOutputUtils.base64ToBitmap(jsonObject.getString("pic")));


        } catch (org.json.JSONException e) {
            e.printStackTrace();
        }

            //添加点击喜欢的事件&取消喜欢。。。。判断一下


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.like:// 更改喜欢

                if (v.getTag().equals(R.drawable.like_song)) {
                    User n_user = (User) getApplication();
                    String data="";
                    try {
                        data = "?username=" + URLEncoder.encode(n_user.getUserName(), "UTF-8") +
                                "&songname=" + URLEncoder.encode(song_name.getText().toString(), "UTF-8") +
                                "&like=no";
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }

                    String likeJson= PostUtil.doPost("chooseSongLike",data);

                    try {

                        JSONObject jsonObject = new JSONObject(likeJson);


                        String result = null;
                        result = jsonObject.getString("Result");

                        if (result.equals("success")) {
                                //
                            Toast.makeText(getApplicationContext(), "修改成功", Toast.LENGTH_LONG).show();

                            like.setImageResource(R.drawable.unlike_song);
                            like.setTag(R.drawable.unlike_song);
                                //init();
                        } else {
                            Toast.makeText(getApplicationContext(), "修改失败", Toast.LENGTH_LONG).show();
                        }

                    } catch (org.json.JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    User n_user = (User) getApplication();
                    String data="";
                    try {
                        data = "?username=" + URLEncoder.encode(n_user.getUserName(), "UTF-8") +
                                "&songname=" + URLEncoder.encode(song_name.getText().toString(), "UTF-8") +
                                "&like=yes";
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }

                    String likeJson= PostUtil.doPost("chooseSongLike",data);

                    try {

                        JSONObject jsonObject = new JSONObject(likeJson);


                        String result = null;
                        result = jsonObject.getString("Result");

                        if (result.equals("success")) {
                                //
                            Toast.makeText(getApplicationContext(), "修改成功", Toast.LENGTH_LONG).show();

                            like.setImageResource(R.drawable.like_song);
                            like.setTag(R.drawable.like_song);
                                //init();
                        } else {
                            Toast.makeText(getApplicationContext(), "修改失败", Toast.LENGTH_LONG).show();
                        }

                    } catch (org.json.JSONException e) {
                        e.printStackTrace();
                    }
                }
                    //showTypeDialog();
                break;

        }
    }
}