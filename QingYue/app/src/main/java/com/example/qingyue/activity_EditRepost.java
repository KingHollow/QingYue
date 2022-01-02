package com.example.qingyue;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.qingyue.entity.User;
import com.example.qingyue.utils.PostUtil;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class activity_EditRepost extends Activity {

    ImageButton btn_back;
    Button send;
    String content,n_content,o_id;
    int o_repost;
    EditText edit_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_repost);

        //跳转的时候，携带原贴id
        Bundle bundle = this.getIntent().getExtras();
        o_id = bundle.getString("o_id");
        o_repost = Integer.parseInt(bundle.getString("repost_num"));


        btn_back = (ImageButton)findViewById(R.id.btn_back);


        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity_EditRepost.this.finish();
            }
        });

        send = (Button) findViewById(R.id.send);
        edit_content = (EditText) findViewById(R.id.edit_content);
    }

    public void send(View view){


        User n_user =(User) getApplication();

        String data="";
        try {
            data = "?username="+ URLEncoder.encode(n_user.getUserName(), "UTF-8")+
                    "&content="+ URLEncoder.encode(edit_content.getText().toString(), "UTF-8")+
                    "&repostedid="+ URLEncoder.encode(o_id,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String repostJson = PostUtil.doPost("repost",data);

        try {

            JSONObject jsonObject = new JSONObject(repostJson);


            String result = null;
            result = jsonObject.getString("Result");
            //Looper.prepare();
            if (result.equals("success")) {
                Toast.makeText(getApplicationContext(), "发送成功", Toast.LENGTH_LONG).show();
                //数据是使用Intent返回
                Intent intent = new Intent();
                Bundle bundle = new Bundle();//该类用作携带数据
                bundle.putString("n_repost", String.valueOf(o_repost+1));
                intent.putExtras(bundle);//附带上额外的数据
                //设置返回数据
                activity_EditRepost.this.setResult(RESULT_OK, intent);

                //关闭Activity
                activity_EditRepost.this.finish();

            } else {
                Toast.makeText(getApplicationContext(), "发送失败", Toast.LENGTH_LONG).show();
            }
            //Looper.loop();

        } catch (org.json.JSONException e) {
            e.printStackTrace();
        }


    }
}