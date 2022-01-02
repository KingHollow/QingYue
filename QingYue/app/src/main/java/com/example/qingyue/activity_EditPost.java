package com.example.qingyue;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qingyue.entity.User;
import com.example.qingyue.utils.PostUtil;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

public class activity_EditPost extends Activity implements View.OnClickListener {

    ImageButton btn_back;
    Button send;
    String content,n_content;
    EditText edit_content;
    LinearLayout add_singer,add_song;
    int type;
    TextView relatedName;
    private static final String TestApp="TestApp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_post);

        btn_back = (ImageButton)findViewById(R.id.btn_back);


        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity_EditPost.this.finish();
            }
        });


        send = (Button) findViewById(R.id.send);
        edit_content = (EditText) findViewById(R.id.edit_content);
        add_singer = (LinearLayout) findViewById(R.id.add_singer);
        add_song = (LinearLayout) findViewById(R.id.add_song);
        relatedName = (TextView) findViewById(R.id.relaterName);



        add_singer.setOnClickListener(this);
        add_song.setOnClickListener(this);

        type = 0;

    }

    private void init(String content) {
        edit_content.setText(content);

    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_singer:// 更换头像
                showTypeDialog1(v);
                break;
            case R.id.add_song://信息修改
                showTypeDialog2(v);
                break;

        }
    }

    private void showTypeDialog1(View view) {
        //显示对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog dialog = builder.create();

        View vd = View.inflate(this, R.layout.dialog_input_singer, null);
        TextView yes = (TextView) vd.findViewById(R.id.yes);
        TextView cancel = (TextView) vd.findViewById(R.id.cancel);
        EditText n_singer = (EditText) vd.findViewById(R.id.singer);
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String data="";
                try {
                    data = "?type=" + URLEncoder.encode("2", "UTF-8") +
                            "&name=" + URLEncoder.encode(n_singer.getText().toString(), "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                String addJson= PostUtil.doPost("containName",data);

                try {

                    JSONObject jsonObject = new JSONObject(addJson);


                    String result = null;
                    result = jsonObject.getString("Result");

                    if (result.equals("success")) {
                        //
                        Toast.makeText(getApplicationContext(), "添加成功", Toast.LENGTH_LONG).show();

                        relatedName.setText(n_singer.getText().toString());
                        add_singer.clearAnimation();
                        add_song.clearAnimation();
                        add_singer.setVisibility(View.INVISIBLE);
                        add_song.setVisibility(View.INVISIBLE);
                        relatedName.setVisibility(View.VISIBLE);
                        content = edit_content.getText().toString();
                        type= 2;

                        init(content);
                    } else {
                        Toast.makeText(getApplicationContext(), "该歌手不存在", Toast.LENGTH_LONG).show();
                    }

                } catch (org.json.JSONException e) {
                    e.printStackTrace();
                }

                dialog.dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {// 不修改
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.setView(vd);
        dialog.show();
    }

    private void showTypeDialog2(View view) {
        //显示对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog dialog = builder.create();

        View vd = View.inflate(this, R.layout.dialog_input_song, null);
        TextView yes = (TextView) vd.findViewById(R.id.yes);
        TextView cancel = (TextView) vd.findViewById(R.id.cancel);
        EditText n_song = (EditText) vd.findViewById(R.id.song);
        View line = findViewById(R.id.line);
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String data="";
                try {
                    data = "?type=" + URLEncoder.encode("1", "UTF-8") +
                            "&name=" + URLEncoder.encode(n_song.getText().toString(), "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                String addJson= PostUtil.doPost("containName",data);

                try {

                    JSONObject jsonObject = new JSONObject(addJson);


                    String result = null;
                    result = jsonObject.getString("Result");

                    if (result.equals("success")) {
                        //
                        Toast.makeText(getApplicationContext(), "添加成功", Toast.LENGTH_LONG).show();

                        relatedName.setText(n_song.getText().toString());
                        add_singer.clearAnimation();
                        add_song.clearAnimation();
                        add_singer.setVisibility(View.INVISIBLE);
                        add_song.setVisibility(View.INVISIBLE);
                        line.setVisibility(View.INVISIBLE);
                        relatedName.setVisibility(View.VISIBLE);
                        content = edit_content.getText().toString();
                        type = 1;

                        init(content);
                    } else {
                        Toast.makeText(getApplicationContext(), "该歌曲不存在", Toast.LENGTH_LONG).show();
                    }

                } catch (org.json.JSONException e) {
                    e.printStackTrace();
                }

                dialog.dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {// 不修改
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.setView(vd);
        dialog.show();
    }


    public void send(View view){

        n_content = relatedName.getText().toString()+"|"+edit_content.getText().toString();

        User n_user =(User) getApplication();

        String data="";
        try {
            data = "?username="+ URLEncoder.encode(n_user.getUserName(), "UTF-8")+
                    "&content="+ URLEncoder.encode(n_content, "UTF-8")+
                    "&type="+ URLEncoder.encode(String.valueOf(type),"UTF-8")+
                    "&relatedName="+ URLEncoder.encode(relatedName.getText().toString(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String postJson = PostUtil.doPost("newPost",data);

        try {

            JSONObject jsonObject = new JSONObject(postJson);


            String result = null;
            result = jsonObject.getString("Result");
            //Looper.prepare();
            if (result.equals("success")) {
                Toast.makeText(getApplicationContext(), "发送成功", Toast.LENGTH_LONG).show();
                //数据是使用Intent返回
                Intent intent = new Intent();
                //设置返回数据
                activity_EditPost.this.setResult(RESULT_OK, intent);

                //关闭Activity
                activity_EditPost.this.finish();

            } else {
                Toast.makeText(getApplicationContext(), "发送失败", Toast.LENGTH_LONG).show();
            }
            //Looper.loop();

        } catch (org.json.JSONException e) {
            e.printStackTrace();
        }


    }


}